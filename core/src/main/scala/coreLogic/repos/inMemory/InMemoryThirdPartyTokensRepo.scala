package coreLogic.repos.inMemory

import coreLogic.repos.ThirdPartyTokensRepository
import service.dto.{FacebookTokenDo, TwitterTokensDo}
import util.UserId

import scala.collection.mutable

class InMemoryThirdPartyTokensRepo extends ThirdPartyTokensRepository {

  val twitterRepo = mutable.HashMap.empty[(UserId), TwitterTokensDo]
  val facebookRepo = mutable.HashMap.empty[(UserId), FacebookTokenDo]

  override def storeTwitterTokens(twitterTokens: TwitterTokensDo): Unit =
    twitterRepo += twitterTokens.userId -> twitterTokens

  override def tokens(userId: UserId): (Option[TwitterTokensDo], Option[FacebookTokenDo]) = {
    (twitterRepo.get(userId), facebookRepo.get(userId))
  }

  override def storeFacebookToken(facebookToken: FacebookTokenDo): Unit = {
    facebookRepo += facebookToken.userId -> facebookToken
  }

  override def removeTwitterTokens(userId: UserId): Unit = twitterRepo.remove(userId)

  override def removeFacebookTokens(userId: UserId): Unit = facebookRepo.remove(userId)

  override def allTwitterTokens: Seq[TwitterTokensDo] = twitterRepo.values.toSeq

  override def allFacebookTokens: Seq[FacebookTokenDo] = facebookRepo.values.toSeq
}
