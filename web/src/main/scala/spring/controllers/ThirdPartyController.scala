package spring.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._
import service.api.ThirdPartyService
import service.dto.{FacebookToken, TwitterTokens}
import util.UserId

@Controller
@RequestMapping(Array("/api/user"))
class ThirdPartyController(thirdPartyService: ThirdPartyService) {

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("twitter"))
  @ResponseBody
  def saveTwitterTokens(@RequestBody request: TwitterTokens,
                        @RequestHeader(value = "userId") userId: UserId): Unit = {
    thirdPartyService.storeTwitterTokens(userId, request)
  }

  @RequestMapping(method = Array(RequestMethod.DELETE), value = Array("twitter"))
  @ResponseBody
  def deleteTwitterTokens(@RequestHeader(value = "userId") userId: UserId): Unit = {
    thirdPartyService.removeTokens(userId)
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("twitter"))
  @ResponseBody
  def getTwitterTokens(@RequestHeader(value = "userId") userId: UserId): Option[TwitterTokens] = {
    thirdPartyService.twitterTokens(userId)
  }

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("facebook"))
  @ResponseBody
  def saveFacebookTokens(@RequestBody request: FacebookToken,
                        @RequestHeader(value = "userId") userId: UserId): Unit = {
    println(FacebookToken)
  }
}

@Controller
@RequestMapping(Array("/webhook"))
class ThirdPartyWebHooksController() {

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("twitter"))
  @ResponseBody
  def twitterPostNotification(@RequestBody request: Map[String, String]): Unit = {
    println(request)
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("twitter"))
  @ResponseBody
  def twitterGetNotification(@RequestParam(value = "crc_token", required = true) crcToken: String): Unit = {
    println(crcToken)
  }
}
