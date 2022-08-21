package net.gemelen.dev.dxc.domain

import cats.Show
import cats.implicits._

case class AggregatedHumidity(
    id: SensorId,
    totalCount: Long,
    processedCount: Long,
    minimalHumidity: Humidity,
    averageHumidity: Humidity,
    maximalHumidity: Humidity
) extends Product
    with Serializable

object AggregatedHumidity {
  def apply(id: SensorId): AggregatedHumidity =
    AggregatedHumidity(
      id = id,
      totalCount = 0,
      processedCount = 0,
      minimalHumidity = NoHumidityData,
      maximalHumidity = NoHumidityData,
      averageHumidity = NoHumidityData
    )

  implicit val showAH: Show[AggregatedHumidity] =
    Show.show(ah => s"${ah.id.show},${ah.minimalHumidity.show},${ah.averageHumidity.show},${ah.maximalHumidity.show}")
}
