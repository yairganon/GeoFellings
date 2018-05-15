package spring.controllers

import coreLogic.repos.{NotificationService, NotificationTokenRepository}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation.{RequestBody, RequestHeader, RequestMapping, ResponseBody}
import service.dto.{Location, RegisterTokenRequest}
import util.UserId

@Controller
class RootController(tokenRepo: NotificationTokenRepository,
                     notificationService: NotificationService) {

  @RequestMapping(method = Array(POST), value = Array("api/registerToken"))
  @ResponseBody
  def registerToken(@RequestBody cmd: RegisterTokenRequest,
                    @RequestHeader(value = "userId") userId: UserId): Unit = {
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