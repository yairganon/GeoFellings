package spring.controllers

import coreLogic.repos.UsersRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._
import service.api.{QuestionsService, TriggerService}
import service.dto.{CreateQuestionRequest, CreateQuestionnaireRequest, CreateTriggerRequest}
import util.UserId
import views.ToViews._
import views._

@Controller
@RequestMapping(Array("/api/admin"))
class AdminController(questionsService: QuestionsService,
                      usersRepository: UsersRepository,
                      triggerService: TriggerService) {

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("question"))
  @ResponseBody
  def createQuestion(@RequestBody request: CreateQuestionRequest): Unit = {
    questionsService.addQuestion(request)
  }

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("questionnaire"))
  @ResponseBody
  def createQuestionnaire(@RequestBody request: CreateQuestionnaireRequest): Unit = {
    questionsService.addQuestionnaire(request)
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("questionnaire"))
  @ResponseBody
  def getQuestionnaires: QuestionnairesView = {
    val questions = questionsService.getAll
    QuestionnairesView(questionsService.getQuestionnaires.map(_.toView(questions)))
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("question"))
  @ResponseBody
  def getQuestions: QuestionsView = {
    QuestionsView(
      questionsService.getAll
        .map(_.toView)
    )
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("user"))
  @ResponseBody
  def getUsers: UsersView = {
    UsersView(usersRepository.getAll.map(_.toView))
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("user/{userId}"))
  @ResponseBody
  def getUser(@PathVariable("userId") userId: UserId): FullUserView = {
    val user = usersRepository.get(userId)
    FullUserView(
      user.userId.getId,
      user.userName,
      user.gender,
      user.age,
      questionsService.getUserQuestionnaires(userId)
    )
  }

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("trigger"))
  @ResponseBody
  def createTrigger(@RequestBody request: CreateTriggerRequest): Unit = {
    triggerService.addTrigger(request)
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("triggers"))
  @ResponseBody
  def getAllTriggers(): Seq[TriggerView] = {
    triggerService.getAll()
      .map(trigger => trigger.toView(questionsService.getQuestionnaire(trigger.questionnaireId).name))
  }
}
