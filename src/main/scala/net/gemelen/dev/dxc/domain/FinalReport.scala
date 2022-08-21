package net.gemelen.dev.dxc.domain

import scala.collection.concurrent.TrieMap

import cats.Show
import cats.implicits._

case class FinalReport(stats: ProcessingStats, aggregatedSensors: AggregatedSensors) extends Product with Serializable

object FinalReport {
  implicit val frShow: Show[FinalReport] = Show.show { fr =>
    val table = fr.aggregatedSensors.sensors.values.toList
      .sortWith { (x, y) =>
        (x.averageHumidity, y.averageHumidity) match {
          case (RealHumidity(hl), RealHumidity(hr)) => hl <= hr
          case (RealHumidity(_), NoHumidityData)    => false
          case (NoHumidityData, RealHumidity(_))    => true
          case (NoHumidityData, NoHumidityData)     => false
        }
      }
      .map(_.show)
      .mkString("\n")

    s"""
Num of processed files: ${fr.stats.processedFilesCount}
Num of processed measurements: ${fr.stats.processedMeasurementsCount}
Num of failed measurements: ${fr.stats.failedMeasurementsCount}

Sensors with highest avg humidity:

sensor-id,min,avg,max
${table}
      """
  }
}
