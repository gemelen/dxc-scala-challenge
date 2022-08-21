package net.gemelen.dev.dxc.processors

import net.gemelen.dev.dxc.domain.SensorData

import java.io.IOException
import java.nio.file._
import java.nio.file.attribute.BasicFileAttributes

import kantan.csv._
import kantan.csv.ops._

abstract class CsvFileVisitor extends FileVisitor[Path] with AddMeasurement with AddCsvReadFailure with AddFileToStats {

  // there is a MIME enum somewhere in OpenJDK though
  val csvMime = "text/csv"

  private def processFile(filePath: Path): Unit = {
    val fileMime = Files.probeContentType(filePath)
    if (csvMime.equals(fileMime)) {
      import CsvData._
      val inputIterator = filePath.asCsvReader[SensorData](rfc.withHeader)

      inputIterator.foreach(
        _.fold(this.addCsvReadFailure(filePath, _), this.addMeasurement)
      )
    }
  }

  override def visitFile(filePath: Path, attrs: BasicFileAttributes): FileVisitResult = {
    processFile(filePath)
    this.addFileToStats(filePath)
    FileVisitResult.CONTINUE
  }
  override def preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult = { FileVisitResult.CONTINUE }

  override def visitFileFailed(file: Path, exc: IOException): FileVisitResult = { FileVisitResult.CONTINUE }

  override def postVisitDirectory(dir: Path, exc: IOException): FileVisitResult = { FileVisitResult.CONTINUE }
}
