package spring.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, ResponseBody}
import service.dto.UserLoginRequest
import util.UserId
import views.UserIdView

@Controller
@RequestMapping(Array("/api/user"))
class UsersController {

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("login"))
  @ResponseBody
  def login(@RequestBody request: UserLoginRequest): UserIdView = {
    print(request)
    UserIdView(UserId.random)
  }
}
