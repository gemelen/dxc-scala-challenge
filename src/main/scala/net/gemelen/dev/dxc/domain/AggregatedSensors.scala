package net.gemelen.dev.dxc.domain

import scala.collection.concurrent.TrieMap

case class AggregatedSensors(sensors: TrieMap[SensorId, AggregatedHumidity]) extends Product with Serializable
object AggregatedSensors {
  def apply(): AggregatedSensors =
    AggregatedSensors(
      sensors = TrieMap[SensorId, AggregatedHumidity]().empty
    )
}
