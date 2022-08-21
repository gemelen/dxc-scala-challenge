package net.gemelen.dev.dxc.validators

import net.gemelen.dev.dxc.domain.errors._

import cats.data._
import cats.implicits._

sealed trait LaunchValidator {

  type ValidationResult[A] = ValidatedNec[PreConditions, A]

  private def validateArgumentsCount(arguments: List[String]): ValidationResult[String] = {
    if (arguments.isEmpty) { NoPathProvided.invalidNec }
    else { arguments.head.validNec }
  }

  def validate(arguments: List[String]): ValidationResult[String] = {
    validateArgumentsCount(arguments)
  }
}

object LaunchValidator extends LaunchValidator
