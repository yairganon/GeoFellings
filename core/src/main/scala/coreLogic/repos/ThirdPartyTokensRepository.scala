package coreLogic.repos

import service.dto.{FacebookToken, TwitterTokens}
import util.UserId

trait ThirdPartyTokensRepository {

  def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit

  def tokens(userId: UserId): (Option[TwitterTokens], Option[FacebookToken])

  def storeFacebookToken(userId: UserId, facebookToken: FacebookToken): Unit

  def removeTwitterTokens(userId: UserId): Unit

  def removeFacebookTokens(userId: UserId): Unit

  def allTwitterTokens: Seq[(UserId, TwitterTokens)]

  def allFacebookTokens: Seq[(UserId, FacebookToken)]
}
