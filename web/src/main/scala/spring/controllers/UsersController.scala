package spring.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._
import service.api.{QuestionsService, RegistrationService}
import service.dto.{UserLoginRequest, UserRegisterRequest}
import views.{QuestionnaireView, QuestionsView, RegisterStatusView}
import views.ToViews._

@Controller
@RequestMapping(Array("/api/user"))
class UsersController(registrationService: RegistrationService,
                      questionsService: QuestionsService) {

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("login"))
  @ResponseBody
  def login(@RequestBody request: UserLoginRequest): Option[String] = {
    registrationService.loginUser(request).map(_.getId)
  }

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("register"))
  @ResponseBody
  def register(@RequestBody request: UserRegisterRequest): RegisterStatusView = {
    print(request)
    RegisterStatusView(registrationService.registerUser(request))
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("registerQuestionnaire"))
  @ResponseBody
  def registerQuestionnaire(): Option[QuestionnaireView] = {
    val questions = questionsService.getAll
    questionsService.registerQuestionnaire.map(_.toView(questions))
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("defaultQuestionnaire"))
  @ResponseBody
  def defaultQuestionnaire(): Option[QuestionnaireView] = {
    val questions = questionsService.getAll
    questionsService.defaultQuestionnaire.map(_.toView(questions))
  }


}
