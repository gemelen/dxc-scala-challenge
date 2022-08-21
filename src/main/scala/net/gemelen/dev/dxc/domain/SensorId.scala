package net.gemelen.dev.dxc.domain

import cats.Show

case class SensorId(id: String) extends AnyVal
object SensorId {
  implicit val sensorIdShow: Show[SensorId] = Show.show(_.id)
}
