package spring.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, ResponseBody}
import service.dto.{UserLoginRequest, UserRegisterRequest}
import util.UserId

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
  def register(@RequestBody request: UserRegisterRequest): Unit = {
    print(request)
  }
}
