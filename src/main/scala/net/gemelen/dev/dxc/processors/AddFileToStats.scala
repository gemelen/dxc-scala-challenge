package net.gemelen.dev.dxc.processors

import java.nio.file.Path

trait AddFileToStats {
  def addFileToStats(filePath: Path): Unit
}

object AddFileToStats {
  def addFileToStats(files: scala.collection.mutable.ListBuffer[Path])(filePath: Path): Unit = {
    files.addOne(filePath)
  }
}
