package spring.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ RequestBody, RequestMapping, RequestMethod, ResponseBody }
import service.api.RegistrationService
import service.dto.{ UserLoginRequest, UserRegisterRequest }
import util.UserId
import views.RegisterStatusView

@Controller
@RequestMapping(Array("/api/user"))
class UsersRegistrationController(registrationService: RegistrationService) {

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("login"))
  @ResponseBody
  def login(@RequestBody request: UserLoginRequest): Option[UserId] = {
    registrationService.loginUser(request)
  }

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("register"))
  @ResponseBody
  def register(@RequestBody request: UserRegisterRequest): RegisterStatusView = {
    print(request)
    RegisterStatusView(registrationService.registerUser(request))
  }
}
