package coreLogic.repos

import service.dto.{FacebookTokenDo, TwitterTokens}
import util.UserId

trait ThirdPartyTokensRepository {

  def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit

  def tokens(userId: UserId): (Option[TwitterTokens], Option[FacebookTokenDo])

  def storeFacebookToken(facebookToken: FacebookTokenDo): Unit

  def removeTwitterTokens(userId: UserId): Unit

  def removeFacebookTokens(userId: UserId): Unit

  def allTwitterTokens: Seq[(UserId, TwitterTokens)]

  def allFacebookTokens: Seq[(UserId, FacebookTokenDo)]
}
