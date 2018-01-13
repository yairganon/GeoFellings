package spring.beans

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import coreLogic.{QuestionsFacade, RegistrationFacade, ThirdPartyFacade}
import coreLogic.repos.{NotificationService, NotificationTokenRepository, QuestionsRepository, UsersRepository}
import coreLogic.repos.inMemory.{InMemoryQuestionRepo, InMemoryUsersRepo, InMemoryTokenRepo}
import gcm.http.{HttpGcm, SendPushNotification}
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import service.api.{QuestionsService, RegistrationService, ThirdPartyService}
import spring.controllers.{AdminController, RootController, AppUsersController}

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
  def registrationRepo: UsersRepository = {
    new InMemoryUsersRepo
  }

  @Bean
  def notificationService(
    notificationTokenRepository: NotificationTokenRepository,
    sendPushNotification: SendPushNotification
  ): NotificationService = {
    new NotificationService(notificationTokenRepository, sendPushNotification)
  }

  @Bean
  def questionsRepository(): QuestionsRepository = new InMemoryQuestionRepo

  @Bean
  def questionsService(questionsRepository: QuestionsRepository,
                       thirdPartyService: ThirdPartyService): QuestionsService = {
    new QuestionsFacade(questionsRepository, thirdPartyService)
  }

  @Bean
  def adminController(questionsService: QuestionsService,
                      usersRepository: UsersRepository): AdminController = {
    new AdminController(questionsService, usersRepository)
  }

  @Bean
  def rootController(
    notificationTokenRepository: NotificationTokenRepository,
    notificationService: NotificationService
  ): RootController =
    new RootController(notificationTokenRepository, notificationService)

  @Bean
  def registrationService(registrationRepo: UsersRepository): RegistrationService = {
    new RegistrationFacade(registrationRepo)
  }

  @Bean
  def thirdPartyService: ThirdPartyService = {
    new ThirdPartyFacade()
  }

  @Bean
  def appUsersController(registrationService: RegistrationService,
                         questionsService: QuestionsService): AppUsersController = {
    new AppUsersController(registrationService, questionsService)
  }
}
