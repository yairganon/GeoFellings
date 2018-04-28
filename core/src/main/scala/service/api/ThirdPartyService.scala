package service.api

import service.dto.{FacebookToken, TwitterTokens}
import util.UserId

trait ThirdPartyService {

  def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit

  def twitterTokens(userId: UserId): (Option[TwitterTokens], Option[FacebookToken])

  def removeTokens(userId: UserId): Unit

  def userTweet(userId: UserId): Option[String]

  def usersLastTweet(): Seq[(UserId, String)]

  def storeFacebookToken(userId: UserId, facebookToken: FacebookToken): Unit
}
