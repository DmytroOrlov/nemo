lazy val root = (project in file("."))
  .settings(
    name := "nemo",
    version := "1.0",

    scalaVersion := "2.12.1",
    libraryDependencies ++= Seq(
      "io.monix" %% "monix" % "2.1.2",
      "com.typesafe.akka" %% "akka-stream" % "2.4.16",

      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  )
