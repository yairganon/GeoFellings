package service.api

import gcm.http.FacebookPostData
import service.dto.{FacebookToken, FacebookTokenDo, TwitterTokens, TwitterTokensDo}
import util.UserId

trait ThirdPartyService {

  def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit

  def tokens(userId: UserId): (Option[TwitterTokensDo], Option[FacebookTokenDo])

  def removeTwitterTokens(userId: UserId): Unit

  def removeFacebookTokens(userId: UserId): Unit

  def userTweet(userId: UserId): Option[String]

  def userLastPost(userId: UserId): Option[FacebookPostData]

  def usersLastTweet(): Seq[(UserId, String)]

  def usersLastPosts(): Seq[(UserId, FacebookPostData)]

  def storeFacebookToken(userId: UserId, facebookToken: FacebookToken): Unit
}
