package spring.controllers

import service.dto.{ CreateQuestionRequest, CreateQuestionnaireRequest }
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ RequestBody, RequestMapping, RequestMethod, ResponseBody }
import service.api.QuestionsService
import views.{ QuestionDataView, QuestionView, QuestionsView }

@Controller
@RequestMapping(Array("/api/admin"))
class AdminController(questionsService: QuestionsService) {

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("question"))
  @ResponseBody
  def createQuestion(@RequestBody request: CreateQuestionRequest): Unit = {
    questionsService.addQuestion(request)
    println(request)
  }

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("questionnaire"))
  @ResponseBody
  def createQuestionnaire(@RequestBody request: CreateQuestionnaireRequest): Unit = {
    print(request)
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("question"))
  @ResponseBody
  def getQuestions: QuestionsView = {
    QuestionsView(
      questionsService
        .getAll
        .map(q => QuestionView(q.id.getId, q.`type`, QuestionDataView(q.questionString, q.numOfOptions)))
    )
  }
}
