package net.gemelen.dev.dxc

import net.gemelen.dev.dxc.processors.DataAggregator
import net.gemelen.dev.dxc.validators.LaunchValidator

import java.nio.file.Path

import cats.effect._
import cats.implicits._
import org.typelevel.log4cats._
import org.typelevel.log4cats.slf4j.Slf4jFactory

object DxcChallenge extends IOApp with DataAggregator {

  implicit val logging: LoggerFactory[IO]   = Slf4jFactory[IO]
  val logger: SelfAwareStructuredLogger[IO] = LoggerFactory[IO].getLogger

  private def program(path: String): IO[ExitCode] =
    this
      .aggregate[IO](Path.of(path))
      .flatMap(x => IO(println(x.show)))
      .map(_ => ExitCode.Success)

  override def run(args: List[String]): IO[ExitCode] = {
    LaunchValidator.validate(args).toEither match {
      case Left(errors) =>
        val errorMessage = errors.toList.map(_.show).mkString(",")
        logger.error(errorMessage) >> IO(ExitCode.Error)
      case Right(value) => program(value)
    }
  }

}
