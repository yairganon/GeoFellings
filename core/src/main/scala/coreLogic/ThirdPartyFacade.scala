package coreLogic

import service.api.ThirdPartyService
import service.dto.TwitterTokens
import util.UserId

import scala.collection.mutable

class ThirdPartyFacade() extends ThirdPartyService{

  val repo = mutable.HashMap.empty[(UserId), TwitterTokens]

  override def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit =
    repo += userId -> twitterTokens

  override def twitterTokens(userId: UserId): Option[TwitterTokens] = repo.get(userId)

  override def removeTokens(userId: UserId): Unit = repo.remove(userId)
}