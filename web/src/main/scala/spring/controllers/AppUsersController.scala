package spring.controllers

import enums.{Gender, QuestionType}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation._
import service.api.{QuestionsService, RegistrationService, TriggerService, UserService}
import service.dto._
import util.{QuestionnaireId, UserId}
import views.ToViews._
import views.{QuestionnaireView, QuestionnairesView, RegisterStatusView, UserView}

import scala.util.{Failure, Try}

@Controller
@RequestMapping(Array("/api/user"))
class AppUsersController(registrationService: RegistrationService,
                         questionsService: QuestionsService,
                         userService: UserService,
                         triggerService: TriggerService) {

  Try{
    val qId1 = questionsService.addQuestion(CreateQuestionRequest(QuestionType.RADIO, CreateQuestionData("Question-1", Some(7), None)))
    val qId2 = questionsService.addQuestion(CreateQuestionRequest(QuestionType.OPEN, CreateQuestionData("Question-2", None, None)))
    val qId3 = questionsService.addQuestion(CreateQuestionRequest(QuestionType.MULTIPLE, CreateQuestionData("Question-3", None, Some(Seq("opt-1", "opt-2", "opt-3")))))
    val id1 = questionsService.addQuestionnaire(CreateQuestionnaireRequest("Questionnaire-1", true, true, Seq(qId1, qId2, qId3)))
    val id2 = questionsService.addQuestionnaire(CreateQuestionnaireRequest("Questionnaire-2", true, true, Seq(qId1, qId2, qId3)))
      registrationService.registerUser(UserRegisterRequest("1", "1", Gender.MALE, 26, None))
    triggerService.addTrigger(CreateTriggerRequest("Trigger Name", id1, None, Some(SocialNetworkTrigger(true)), None))
    questionsService.addQuestionnaireTo(userService.getAllUser()(0).userId, id1)
    questionsService.addQuestionnaireTo(userService.getAllUser()(0).userId, id2)
  } match {
    case Failure(e) =>
      println("*" * 50)
      println(e)
      println("*" * 50)
    case _ =>
  }

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
  def haveWaitingQuestionnaire(@RequestHeader(value = "userId") userId: UserId): QuestionnairesView = {
    val questions = questionsService.getAll
    QuestionnairesView(questionsService.getWaitingQuestionnaireForUser(userId)
      .map(questionsService.getQuestionnaire)
      .map(_.toView(questions))
      .toSeq)
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
    userService.getUser(userId).toView
  }

}
