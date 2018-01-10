package spring.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ RequestBody, RequestMapping, RequestMethod, ResponseBody }
import service.api.QuestionsService
import service.dto.{ CreateQuestionRequest, CreateQuestionnaireRequest }
import views.ToViews._
import views.{ QuestionnairesView, QuestionsView }

@Controller
@RequestMapping(Array("/api/admin"))
class AdminController(questionsService: QuestionsService) {

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
}
