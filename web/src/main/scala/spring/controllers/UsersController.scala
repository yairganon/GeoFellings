package spring.controllers

import enums.RegisterStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, ResponseBody}
import service.dto.{UserLoginRequest, UserRegisterRequest}
import util.UserId
import views.RegisterStatusView

@Controller
@RequestMapping(Array("/api/user"))
class UsersController {

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("login"))
  @ResponseBody
  def login(@RequestBody request: UserLoginRequest): UserId = {
    print(request)
    UserId.random
  }

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("register"))
  @ResponseBody
  def register(@RequestBody request: UserRegisterRequest): RegisterStatusView = {
    if(request.userName == "1")
      RegisterStatusView(RegisterStatus.CONFLICT)
    else
      RegisterStatusView(RegisterStatus.CREATED)
  }
}
