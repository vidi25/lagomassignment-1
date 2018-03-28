
name := "lagom-assignment-1"

version := "0.1"

scalaVersion := "2.12.5"

organization in ThisBuild := "com.knoldus"

scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `external-user-service` = (project in file("."))
  .aggregate(`external-user-service-api`, `external-user-service-impl`)

lazy val `external-user-service-api` = (project in file("external-user-service-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `external-user-service-impl` = (project in file("external-user-service-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`external-user-service-api`)

lagomUnmanagedServices in ThisBuild := Map("external-service" -> "https://jsonplaceholder.typicode.com")
