name := "ClientHTTP"

version := "0.1"

scalaVersion := "2.13.7"
val typesafeConfigVersion = "1.4.1"
val scalacticVersion = "3.2.9"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % typesafeConfigVersion,
  "org.scalactic" %% "scalactic" % scalacticVersion,
  "org.scalatest" %% "scalatest" % scalacticVersion % Test,
  "org.scalatest" %% "scalatest-featurespec" % scalacticVersion % Test
)

