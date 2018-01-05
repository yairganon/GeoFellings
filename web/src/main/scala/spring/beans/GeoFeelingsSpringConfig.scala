package spring.beans

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import coreLogic.repos.InMemoryTokenRepo
import gcm.http.{HttpGcm, SendPushNotification}
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import service.api.{NotificationService, NotificationTokenRepository}
import spring.controllers.RootController

@org.springframework.context.annotation.Configuration
class GeoFeelingsSpringConfig {

  @Bean
  def mappingJackson2HttpMessageConverter: MappingJackson2HttpMessageConverter = {
    val jsonConverter = new MappingJackson2HttpMessageConverter
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    jsonConverter.setObjectMapper(mapper)
    jsonConverter
  }

  @Bean
  def notificationTokenRepository: NotificationTokenRepository = {
    new InMemoryTokenRepo
  }

  @Bean
  def sendPushNotification: SendPushNotification = {
    new HttpGcm()
  }

  @Bean
  def notificationService(notificationTokenRepository: NotificationTokenRepository,
                          sendPushNotification: SendPushNotification
                         ): NotificationService = {
    new NotificationService(notificationTokenRepository, sendPushNotification)
  }

  @Bean
  def rootController(notificationTokenRepository: NotificationTokenRepository,
                     notificationService: NotificationService): RootController =
    new RootController(notificationTokenRepository, notificationService)

}
