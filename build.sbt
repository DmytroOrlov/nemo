name := "nemo"

version := "1.0"

scalaVersion := "2.12.0"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "io.monix" %% "monix" % "2.1.1",
  "org.typelevel" %% "machinist" % "0.6.1",

  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
