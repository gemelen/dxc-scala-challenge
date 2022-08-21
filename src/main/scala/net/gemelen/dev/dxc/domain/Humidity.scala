package net.gemelen.dev.dxc.domain

import scala.util._

import cats.Show

sealed trait Humidity
case class RealHumidity(value: Int) extends Humidity
case object NoHumidityData          extends Humidity

object Humidity {
  def parse(input: String): Humidity = {
    Try(input.toInt) match {
      case Failure(exception) => NoHumidityData
      case Success(value)     => RealHumidity(value)
    }
  }

  implicit val showHumidity: Show[Humidity] = Show.show(h =>
    h match {
      case RealHumidity(value) => value.toString()
      case NoHumidityData      => "NaN"
    }
  )
}
