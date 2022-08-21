package net.gemelen.dev.dxc.processors

import net.gemelen.dev.dxc.domain.Humidity
import net.gemelen.dev.dxc.domain.SensorData
import net.gemelen.dev.dxc.domain.SensorId

import java.nio.file.Files
import java.nio.file.Path

import kantan.csv.CellDecoder
import kantan.csv.CsvSource
import kantan.csv.DecodeResult
import kantan.csv.HeaderDecoder
import kantan.csv.ParseResult

object CsvData {
  implicit val pathCsvSource     = CsvSource.from[Path](path => ParseResult(Files.newBufferedReader(path)))
  implicit val sensorIdDecoder   = CellDecoder.from[SensorId](s => DecodeResult(SensorId(s)))
  implicit val himidityDecoder   = CellDecoder.from[Humidity](s => DecodeResult(Humidity.parse(s)))
  implicit val sensorDataDecoder = HeaderDecoder.decoder("sensor-id", "humidity")(SensorData.apply _)
}
