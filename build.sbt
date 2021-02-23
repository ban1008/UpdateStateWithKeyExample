name := "UpdateStateWithKeySpark"

version := "0.1"

scalaVersion := "2.12.12"

idePackagePrefix := Some("org.updatestatewithkey.example")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.0.0",
  "org.apache.spark" %% "spark-sql" % "3.0.0",
  "org.apache.spark" %% "spark-streaming" % "3.0.0",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.1.0",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.0.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.holdenkarau" %% "spark-testing-base" % "3.0.0_1.0.0" % Test
)