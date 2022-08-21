package net.gemelen.dev.dxc.processors

import net.gemelen.dev.dxc.domain._

import java.nio.file.Files
import java.nio.file.Path

import scala.collection.concurrent.TrieMap
import scala.collection.mutable.ListBuffer

import cats.effect.kernel.Async
import kantan.csv.ReadError

trait DataAggregator {

  private def summarize(files: Seq[Path], data: AggregatedSensors): FinalReport = {
    val (total, processed) = data.sensors
      .mapValues(x => (x.totalCount, x.processedCount))
      .values
      .foldLeft((0L, 0L))(
        (
            x: (Long, Long),
            y: (Long, Long)
        ) => (x._1 + y._1, x._2 + y._2)
      )

    val finalStats = ProcessingStats(
      processedFilesCount = files.length,
      processedMeasurementsCount = processed,
      failedMeasurementsCount = total - processed
    )
    FinalReport(
      stats = finalStats,
      aggregatedSensors = data
    )
  }

  def aggregate[F[_]: Async](dataDirectory: Path): F[FinalReport] = {
    val store = AggregatedSensors.apply()
    val files = new ListBuffer[Path]() // this is shit, u know
    val fileVisitor = new CsvFileVisitor {
      override def addCsvReadFailure(file: Path, error: ReadError): Unit =
        AddCsvReadFailure.addCsvReadFailure(file, error)
      override def addMeasurement(data: SensorData): Unit =
        AddMeasurement.addMeasurementToTrie(store)(data)
      override def addFileToStats(file: Path) = AddFileToStats.addFileToStats(files)(file)
    }
    Async[F].blocking {
      Files.walkFileTree(dataDirectory, fileVisitor)
      summarize(files.toSeq, store)
    }
  }

}
