package org.updatestatewithkey.example

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object UpdateStateWithKey {

  Logger.getLogger("org").setLevel(Level.ERROR)

  val updateFunc = (values: Seq[Int], state: Option[Int]) => {
    val currentCount = values.sum
    val previousCount = state.getOrElse(0)
    Some(currentCount + previousCount)
  }

  def countWords(words: DStream[String]):DStream[(String,Int)]={

    val temp = words.flatMap(_.split(" "))

    val count = temp.map(x => (x, 1)).reduceByKey(_ + _)
      .updateStateByKey(updateFunc)

    count

  }

  def main(args:Array[String]){

    val broker_id = "localhost:9092"

      val topics = Seq("testtopic")

      val group_id = "GRP1"

      val sparkconf = new SparkConf().setMaster("local[*]").setAppName("UpdateStateWithKeyExample")

      val ssc = new StreamingContext(sparkconf, Seconds(5))

      val kafkaParams = Map[String, Object](
        "bootstrap.servers" -> broker_id,
        "key.deserializer" -> classOf[StringDeserializer],
        "value.deserializer" -> classOf[StringDeserializer],
        "group.id" -> group_id,
        "auto.offset.reset" -> "latest",
        "enable.auto.commit" -> (false: java.lang.Boolean)
      )

      val kafkaStream = KafkaUtils.createDirectStream[String, String](
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
      )

    val words = kafkaStream.map(_.value())

    ssc.checkpoint("data/checkpoint")

    val count = countWords(words)

    count.print()

    ssc.start()

    ssc.awaitTermination()
  }
}
