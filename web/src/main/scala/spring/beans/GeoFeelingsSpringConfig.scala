package spring.beans

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import coreLogic._
import coreLogic.repos._
import coreLogic.repos.inMemory._
import coreLogic.repos.mySql.MysqlDaos
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
    mapper.registerModules(DefaultScalaModule, new JodaModule)
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
      .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    jsonConverter.setObjectMapper(mapper)
    jsonConverter
  }

  @Bean
  def daos: Daos = {
    MysqlDaos.getInstance()
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
  def registrationRepo(daos: Daos): UsersRepository = daos.usersRepository

  @Bean
  def notificationService(notificationTokenRepository: NotificationTokenRepository,
                          sendPushNotification: SendPushNotification): NotificationService = {
    new NotificationService(notificationTokenRepository, sendPushNotification)
  }

  @Bean
  def questionsRepository(): QuestionsRepository = new InMemoryQuestionRepo

  @Bean
  def questionsService(daos: Daos,
                       thirdPartyService: ThirdPartyService,
                       notificationsRepository: NotificationsRepository,
                       usersRepository: UsersRepository): QuestionsService = {
    new QuestionsFacade(daos.questionsRepository, thirdPartyService, notificationsRepository, usersRepository)
  }

  @Bean
  def triggerRepository: TriggerRepository = {
    new InMemoryTriggerRepo
  }

  @Bean
  def thirdPartyTokensRepository(daos: Daos): ThirdPartyTokensRepository = {
    daos.thirdPartyTokensRepository
  }

  @Bean
  def triggerService(daos: Daos): TriggerService = {
    new TriggerFacade(daos.triggerRepository)
  }

  @Bean
  def adminController(questionsService: QuestionsService,
                      userService: UserService,
                      triggerService: TriggerService): AdminController = {
    new AdminController(questionsService, userService, triggerService)
  }

  @Bean
  def rootController(notificationTokenRepository: NotificationTokenRepository,
                     notificationService: NotificationService): RootController =
    new RootController(notificationTokenRepository, notificationService)

  @Bean
  def registrationService(registrationRepo: UsersRepository,
                          questionsService: QuestionsService): RegistrationService = {
    new RegistrationFacade(registrationRepo, questionsService)
  }

  @Bean
  def scheduleTasks(thirdPartyService: ThirdPartyService,
                    questionsService: QuestionsService,
                    triggerService: TriggerService,
                    userService: UserService): ScheduleTasks = {
    new ScheduleTasks(thirdPartyService, questionsService, triggerService, userService)
  }

  @Bean
  def thirdPartyService(thirdPartyTokensRepository: ThirdPartyTokensRepository): ThirdPartyService = {
    new ThirdPartyFacade(thirdPartyTokensRepository)
  }

  @Bean
  def userService(usersRepository: UsersRepository,
                  thirdPartyService: ThirdPartyService): UserService = {
    new LogInUserFacade(usersRepository, thirdPartyService)
  }

  @Bean
  def notificationsRepository: NotificationsRepository = {
    new InMemoryNotificationsRepo
  }

  @Bean
  def appUsersController(registrationService: RegistrationService,
                         questionsService: QuestionsService,
                         userService: UserService,
                         triggerService: TriggerService): AppUsersController = {
    new AppUsersController(registrationService, questionsService, userService, triggerService)
  }
}
