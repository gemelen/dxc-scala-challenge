import sbt._

object V {
  val cats     = "2.7.0"
  val ce       = "3.3.11"
  val kantan   = "0.6.2"
  val l4c      = "2.4.0"
  val logback  = "1.2.10"
  val pureconf = "0.17.1"
  val slf4j    = "1.7.35"
  val weaver   = "0.7.6"
}

object TV {
  val mu = "0.7.29"
}

object D {
  val conf = Seq(
    "com.github.pureconfig" %% "pureconfig" % V.pureconf
  )

  val cats = Seq(
    "org.typelevel" %% "cats-core"   % V.cats,
    "org.typelevel" %% "cats-effect" % V.ce
  )

  val kantan = Seq(
    "com.nrinaudo" %% "kantan.csv",
    "com.nrinaudo" %% "kantan.csv-generic",
    "com.nrinaudo" %% "kantan.csv-cats"
  ).map(_ % V.kantan)

  val logging = Seq(
    "org.typelevel" %% "log4cats-slf4j"  % V.l4c,
    "org.slf4j"      % "slf4j-api"       % V.slf4j,
    "ch.qos.logback" % "logback-classic" % V.logback
  )

  val tests = Seq(
    "org.scalameta" %% "munit" % TV.mu
  ).map(_ % Test)
}
