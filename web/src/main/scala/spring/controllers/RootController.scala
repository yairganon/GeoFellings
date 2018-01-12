package spring.controllers

import coreLogic.repos.{ NotificationService, NotificationTokenRepository }
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation.{ RequestBody, RequestMapping, ResponseBody }
import service.dto.{ RegisterTokenRequest, Location }

@Controller
class RootController(
  tokenRepo: NotificationTokenRepository,
    notificationService: NotificationService
) {

  @RequestMapping(method = Array(POST), value = Array("/registerToken"))
  @ResponseBody
  def registerToken(@RequestBody cmd: RegisterTokenRequest): Unit = {
    tokenRepo.addToken(cmd.token)
    print(cmd)
  }

  @RequestMapping(method = Array(POST), value = Array("/sendLocation"))
  @ResponseBody
  def updateLocation(@RequestBody cmd: Location): Unit = {
    print(cmd)
  }

  @RequestMapping(method = Array(GET), value = Array("/sendNotificationToAll"))
  @ResponseBody
  def sendNotificationToAll(): Unit = {
    notificationService.sendToAllDummy()
  }
}