package coreLogic.repos

import service.dto.{FacebookTokenDo, TwitterTokensDo}
import util.UserId

trait ThirdPartyTokensRepository {

  def storeTwitterTokens(twitterTokens: TwitterTokensDo): Unit

  def tokens(userId: UserId): (Option[TwitterTokensDo], Option[FacebookTokenDo])

  def storeFacebookToken(facebookToken: FacebookTokenDo): Unit

  def removeTwitterTokens(userId: UserId): Unit

  def removeFacebookTokens(userId: UserId): Unit

  def allTwitterTokens: Seq[TwitterTokensDo]

  def allFacebookTokens: Seq[FacebookTokenDo]
}
