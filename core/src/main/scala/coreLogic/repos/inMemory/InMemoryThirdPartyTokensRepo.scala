package coreLogic.repos.inMemory

import coreLogic.repos.ThirdPartyTokensRepository
import service.dto.{FacebookTokenDo, TwitterTokens}
import util.UserId

import scala.collection.mutable

class InMemoryThirdPartyTokensRepo extends ThirdPartyTokensRepository {

  val twitterRepo = mutable.HashMap.empty[(UserId), TwitterTokens]
  val facebookRepo = mutable.HashMap.empty[(UserId), FacebookTokenDo]

  override def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit =
    twitterRepo += userId -> twitterTokens

  override def tokens(userId: UserId): (Option[TwitterTokens], Option[FacebookTokenDo]) = {
    (twitterRepo.get(userId), facebookRepo.get(userId))
  }

  override def storeFacebookToken(facebookToken: FacebookTokenDo): Unit = {
    facebookRepo += facebookToken.userId -> facebookToken
  }

  override def removeTwitterTokens(userId: UserId): Unit = twitterRepo.remove(userId)

  override def removeFacebookTokens(userId: UserId): Unit = facebookRepo.remove(userId)

  override def allTwitterTokens: Seq[(UserId, TwitterTokens)] = twitterRepo.toSeq

  override def allFacebookTokens: Seq[(UserId, FacebookTokenDo)] = facebookRepo.toSeq
}
