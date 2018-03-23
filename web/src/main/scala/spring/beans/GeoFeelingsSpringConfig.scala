package spring.beans

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import coreLogic._
import coreLogic.repos._
import coreLogic.repos.inMemory._
import gcm.http.{HttpGcm, SendPushNotification}
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import service.api._
import spring.ScheduleTasks
import spring.controllers.{AdminController, AppUsersController, RootController}

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
                       thirdPartyService: ThirdPartyService,
                       notificationsRepository: NotificationsRepository,
                       usersRepository: UsersRepository): QuestionsService = {
    new QuestionsFacade(questionsRepository, thirdPartyService, notificationsRepository, usersRepository)
  }

  @Bean
  def triggerRepository: TriggerRepository = {
    new InMemoryTriggerRepo
  }

  @Bean
  def triggerService(triggerRepository: TriggerRepository): TriggerService = {
    new TriggerFacade(triggerRepository)
  }

  @Bean
  def adminController(questionsService: QuestionsService,
                      usersRepository: UsersRepository,
                      triggerService: TriggerService): AdminController = {
    new AdminController(questionsService, usersRepository, triggerService)
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
  def scheduleTasks(thirdPartyService: ThirdPartyService,
                    questionsService: QuestionsService,
                    triggerService: TriggerService,
                    userService: UserService): ScheduleTasks ={
    new ScheduleTasks(thirdPartyService, questionsService, triggerService, userService)
  }

  @Bean
  def thirdPartyService: ThirdPartyService = {
    new ThirdPartyFacade()
  }

  @Bean
  def userService(usersRepository: UsersRepository): UserService = {
    new LogInUserFacade(usersRepository)
  }

  @Bean
  def notificationsRepository: NotificationsRepository = {
    new InMemoryNotificationsRepo
  }

  @Bean
  def appUsersController(registrationService: RegistrationService,
                         questionsService: QuestionsService,
                         userService: UserService,
                         usersRepository: UsersRepository): AppUsersController = {
    new AppUsersController(registrationService, questionsService, userService, usersRepository)
  }
}
