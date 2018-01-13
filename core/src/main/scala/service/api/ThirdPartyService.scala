package service.api

import org.joda.time.DateTime
import service.dto.TwitterTokens
import util.UserId

trait ThirdPartyService {

  def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit

  def twitterTokens(userId: UserId): Option[TwitterTokens]

  def removeTokens(userId: UserId): Unit

  def userTweet(userId: UserId): Option[String]

  def usersLastTweet(): Seq[(UserId, String)]
}
