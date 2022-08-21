package net.gemelen.dev.dxc.domain

case class ProcessingStats(
    processedFilesCount: Long,
    processedMeasurementsCount: Long,
    failedMeasurementsCount: Long
) extends Product
    with Serializable
