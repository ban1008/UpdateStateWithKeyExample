package org.updatestatewithkey.example

import com.holdenkarau.spark.testing.StreamingSuiteBase
import org.apache.log4j.{Level, Logger}
import org.scalatest.{WordSpec}

class UpdateStateWithKeyTest extends WordSpec with StreamingSuiteBase {

    Logger.getLogger("org").setLevel(Level.ERROR)

    "StreamingOperations" should {

        "count words" in {
            val inputPair = List(List("hello world world"))
            val outputPair = List(List(("hello",1),("world",2)))
            testOperation(inputPair, UpdateStateWithKey.countWords, outputPair, ordered = false)
        }
    }

}
