package net.gemelen.dev.dxc.domain.errors

import cats.Show

sealed trait PreConditions {
  def errorMessage: String
}

case object NoPathProvided extends PreConditions {
  override def errorMessage: String = "No data path is provided. Application expects one string argument."
}

case object PathDoesNotExist extends PreConditions {
  override def errorMessage: String = "Provided path does not exist."
}

object PreConditions {
  implicit val showPreConditions: Show[PreConditions] = Show.show(pc => pc.errorMessage)
}
