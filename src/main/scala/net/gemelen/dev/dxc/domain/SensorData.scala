package net.gemelen.dev.dxc.domain

case class SensorData(sensor: SensorId, humidity: Humidity) extends Product with Serializable
