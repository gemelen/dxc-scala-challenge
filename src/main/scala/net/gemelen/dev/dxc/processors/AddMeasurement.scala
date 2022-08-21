package net.gemelen.dev.dxc.processors

import net.gemelen.dev.dxc.domain._
import net.gemelen.dev.dxc.math.MovingNumbers

import scala.collection.concurrent.TrieMap

trait AddMeasurement {
  def addMeasurement(data: SensorData): Unit
}

object AddMeasurement extends MovingNumbers {

  def addMeasurementToTrie(store: AggregatedSensors)(data: SensorData): Unit = {
    val maybeData = store.sensors.get(data.sensor)

    val newData = (maybeData, data.humidity) match {
      case (Some(alreadyProcessed), RealHumidity(h)) =>
        // data is present, new measurement is present => aggregate and store
        this.calculate(alreadyProcessed, data.humidity)
      case (None, RealHumidity(h)) =>
        // data is absent, new measurement is present => store
        AggregatedHumidity(
          id = data.sensor,
          totalCount = 1,
          processedCount = 1,
          minimalHumidity = RealHumidity(h),
          maximalHumidity = RealHumidity(h),
          averageHumidity = RealHumidity(h)
        )
      case (None, NoHumidityData) =>
        // data is absent, new NaN measurement => account for total measurements
        AggregatedHumidity(data.sensor)
      case (Some(alreadyProcessed), NoHumidityData) =>
        // data is present, new NaN measurement => account for total measurements
        alreadyProcessed.copy(totalCount = alreadyProcessed.totalCount + 1)
    }

    store.sensors.put(data.sensor, newData)
  }

}
