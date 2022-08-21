package net.gemelen.dev.dxc.math

import net.gemelen.dev.dxc.domain._

import scala.math.max
import scala.math.min

trait MovingNumbers {

  def calculate(lhs: AggregatedHumidity, rhs: Humidity): AggregatedHumidity = {
    rhs match {
      case NoHumidityData =>
        lhs.copy(totalCount = lhs.totalCount + 1)
      case RealHumidity(newHmumidity) =>
        lhs.averageHumidity match {
          case NoHumidityData =>
            lhs.copy(
              totalCount = lhs.totalCount + 1,
              processedCount = lhs.processedCount + 1,
              averageHumidity = rhs,
              minimalHumidity = rhs,
              maximalHumidity = rhs
            )
          case RealHumidity(oldHumdity) =>
            lhs.copy(
              totalCount = lhs.totalCount + 1,
              processedCount = lhs.processedCount + 1,
              averageHumidity = RealHumidity(
                ((newHmumidity + lhs.totalCount * oldHumdity) / (lhs.totalCount + 1)).toInt
              ),
              maximalHumidity = RealHumidity(max(oldHumdity, newHmumidity)),
              minimalHumidity = RealHumidity(min(oldHumdity, newHmumidity))
            )
        }
    }
  }
}
