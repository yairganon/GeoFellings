package spring.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._
import service.api.RegistrationService
import service.dto.{UserLoginRequest, UserRegisterRequest}
import views.RegisterStatusView

@Controller
@RequestMapping(Array("/api/user"))
class UsersRegistrationController(registrationService: RegistrationService) {

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("login"))
  @ResponseBody
  def login(@RequestBody request: UserLoginRequest): Option[String] = {
    registrationService.loginUser(request).map(_.getId)
  }

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("register"))
  @ResponseBody
  def register(@RequestBody request: UserRegisterRequest): RegisterStatusView = {
    RegisterStatusView(registrationService.registerUser(request))
  }
}
