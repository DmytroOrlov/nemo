lazy val root = (project in file("."))
  .settings(
    name := "nemo",
    version := "1.0",

    scalaVersion := "2.12.1",
    libraryDependencies ++= Seq(
      "io.monix" %% "monix" % "2.1.2",
      "org.typelevel" %% "machinist" % "0.6.1",

      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  )
