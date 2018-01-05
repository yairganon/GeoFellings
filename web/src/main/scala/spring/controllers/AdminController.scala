package spring.controllers

import service.dto.CreateQuestionRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(Array("/api/admin"))
class AdminController {

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("question"))
  @ResponseBody
  def createQuestion(@RequestBody request: CreateQuestionRequest): Unit = {
    println(request)
  }
}
