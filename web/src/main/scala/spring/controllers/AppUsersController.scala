package spring.controllers

import coreLogic.repos.UsersRepository
import enums.{Gender, QuestionType}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation._
import service.api.{QuestionsService, RegistrationService, TriggerService, UserService}
import service.dto._
import util.{QuestionnaireId, UserId}
import views.ToViews._
import views.{QuestionnaireView, QuestionnairesIdView, RegisterStatusView, UserView}

@Controller
@RequestMapping(Array("/api/user"))
class AppUsersController(registrationService: RegistrationService,
                         questionsService: QuestionsService,
                         userService: UserService,
                         usersRepository: UsersRepository,
                         triggerService: TriggerService) {

  private val qId1 = questionsService.addQuestion(CreateQuestionRequest(QuestionType.RADIO, CreateQuestionData("Question-1", Some(7), None)))
  private val qId2 = questionsService.addQuestion(CreateQuestionRequest(QuestionType.OPEN, CreateQuestionData("Question-2", None, None)))
  private val qId3 = questionsService.addQuestion(CreateQuestionRequest(QuestionType.MULTIPLE, CreateQuestionData("Question-3", None, Some(Seq("opt-1", "opt-2", "opt-3")))))
  val id = questionsService.addQuestionnaire(CreateQuestionnaireRequest("Questionnaire", true, true, Seq(qId1, qId2, qId3)))
  registrationService.registerUser(UserRegisterRequest("1", "1", Gender.MALE, 26, None))
  triggerService.addTrigger(CreateTriggerRequest("Trigger Name", id, None, Some(SocialNetworkTrigger(true)), None))

  @RequestMapping(method = Array(POST), value = Array("login"))
  @ResponseBody
  def login(@RequestBody request: UserLoginRequest): Option[String] = {
    registrationService.loginUser(request).map(_.getId)
  }

  @RequestMapping(method = Array(POST), value = Array("register"))
  @ResponseBody
  def register(@RequestBody request: UserRegisterRequest): RegisterStatusView = {
    RegisterStatusView(registrationService.registerUser(request))
  }

  @RequestMapping(method = Array(GET), value = Array("registerQuestionnaire"))
  @ResponseBody
  def registerQuestionnaire(): Option[QuestionnaireView] = {
    val questions = questionsService.getAll
    questionsService.registerQuestionnaire.map(_.toView(questions))
  }

  @RequestMapping(method = Array(GET), value = Array("defaultQuestionnaire"))
  @ResponseBody
  def defaultQuestionnaire(): Option[QuestionnaireView] = {
    val questions = questionsService.getAll
    questionsService.defaultQuestionnaire.map(_.toView(questions))
  }

  @RequestMapping(method = Array(GET), value = Array("questionnaire/waiting"))
  @ResponseBody
  def haveWaitingQuestionnaire(@RequestHeader(value = "userId") userId: UserId): QuestionnairesIdView = {
    QuestionnairesIdView(questionsService.getWaitingQuestionnaireForUser(userId).map(_.getId))
  }

  @RequestMapping(method = Array(POST), value = Array("questionnaire/submit"))
  @ResponseBody
  def submit(@RequestBody request: QuestionnaireAnswerRequest,
             @RequestHeader(value = "userId") userId: UserId): Unit = {
    questionsService.submit(userId, request)
  }

  @RequestMapping(method = Array(GET), value = Array("questionnaire/{id}"))
  @ResponseBody
  def getQuestionnaire(@PathVariable("id") questionnaireId: QuestionnaireId,
                       @RequestHeader(value = "userId") userId: UserId): QuestionnaireView = {
    val questions = questionsService.getAll
    questionsService.getQuestionnaire(questionnaireId).toView(questions)
  }

  @RequestMapping(method = Array(PATCH), value = Array(""))
  @ResponseBody
  def updateUser(@RequestBody request: UpdateUserRequest,
                 @RequestHeader(value = "userId") userId: UserId): Unit = {
    println(request)
    userService.patchUser(userId, request)
  }

  @RequestMapping(method = Array(GET), value = Array(""))
  @ResponseBody
  def getUser(@RequestHeader(value = "userId") userId: UserId): UserView = {
    usersRepository.get(userId).toView
  }

}
