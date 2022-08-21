package net.gemelen.dev.dxc.processors

import java.nio.file.Path

import kantan.csv.ReadError
import org.slf4j.LoggerFactory

trait AddCsvReadFailure {
  def addCsvReadFailure(file: Path, error: ReadError): Unit
}

object AddCsvReadFailure extends AddCsvReadFailure {

  val logger = LoggerFactory.getLogger(classOf[DataAggregator])

  override def addCsvReadFailure(file: Path, error: ReadError): Unit = {
    logger.error(s"In file ${file}, ${error.getMessage()}")
  }
}
