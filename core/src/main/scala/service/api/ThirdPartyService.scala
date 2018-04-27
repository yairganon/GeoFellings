package service.api

import service.dto.{FacebookToken, TwitterTokens}
import util.UserId

trait ThirdPartyService {

  def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit

  def twitterTokens(userId: UserId): Option[TwitterTokens]

  def removeTokens(userId: UserId): Unit

  def userTweet(userId: UserId): Option[String]

  def usersLastTweet(): Seq[(UserId, String)]

  def storeFacebookToken(facebookToken: FacebookToken): Unit
}
