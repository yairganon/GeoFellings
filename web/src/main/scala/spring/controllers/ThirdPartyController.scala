package spring.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._
import service.api.ThirdPartyService
import service.dto.{FacebookToken, TwitterTokens}
import util.UserId
import views.SocialNetworkTokens

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

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("tokens"))
  @ResponseBody
  def getTwitterTokens(@RequestHeader(value = "userId") userId: UserId): SocialNetworkTokens = {
    val tokens = thirdPartyService.twitterTokens(userId)
    SocialNetworkTokens(tokens._1, tokens._2)
  }

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("facebook"))
  @ResponseBody
  def saveFacebookTokens(@RequestBody request: FacebookToken,
                        @RequestHeader(value = "userId") userId: UserId): Unit = {
    thirdPartyService.storeFacebookToken(userId, request)
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

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("facebook"))
  @ResponseBody
  def facebookGetNotification(@RequestParam(value = "hub.mode", required = false) mode: String,
                              @RequestParam(value = "hub.challenge", required = false) challenge: String,
                              @RequestParam(value = "hub.verify_token", required = false) verify_token: String): String = {
    println(mode)
    println(challenge)
    println(verify_token)
    challenge
  }

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("facebook"))
  @ResponseBody
  def facebookPOSTNotification(@RequestBody request: Map[String, String]): Unit = {
    println(request)
  }
}
