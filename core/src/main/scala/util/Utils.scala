package util

import java.util.concurrent.TimeUnit

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Utils {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModules(DefaultScalaModule, new JodaModule)
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  implicit class `AnyRef -> JsonString`(any: Any) {


    def toJsonString: String = mapper.writeValueAsString(any)
  }

  implicit class `String -> T`(str: String) {

    def fromJsonString[T](implicit manifest: Manifest[T]): T = mapper.readValue[T](str)
  }

  implicit class `Future Await`[T](future: Future[T]) {

    def awaitResult: T = Await.result(future, Duration(1, TimeUnit.MINUTES))
  }

}
