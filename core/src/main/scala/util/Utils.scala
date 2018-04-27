package util

import java.util.concurrent.TimeUnit

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }

object Utils {

  implicit class `AnyRef -> JsonString`(any: Any) {

    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)

    def toJsonString: String = mapper.writeValueAsString(any)

    def fromJsonString[T](str: String): T = mapper.readValue[T](str)
  }

  implicit class `Future Await`[T](future: Future[T]) {
    def awaitResult: T = Await.result(future, Duration(1, TimeUnit.MINUTES))
  }

}
