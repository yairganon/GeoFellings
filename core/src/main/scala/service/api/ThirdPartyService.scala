package service.api

import service.dto.TwitterTokens
import util.UserId

trait ThirdPartyService {

  def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit

  def twitterTokens(userId: UserId): Option[TwitterTokens]

  def removeTokens(userId: UserId): Unit
}
