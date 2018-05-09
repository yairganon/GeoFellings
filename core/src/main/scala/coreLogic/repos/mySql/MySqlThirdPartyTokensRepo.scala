package coreLogic.repos.mySql

import coreLogic.repos.ThirdPartyTokensRepository

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import service.dto.{FacebookToken, TwitterTokens}
import util.UserId

class MySqlThirdPartyTokensRepo(template: NamedParameterJdbcTemplate)
  extends ThirdPartyTokensRepository
    with GeoServerRowMapper {

  override def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit = ???

  override def tokens(userId: UserId): (Option[TwitterTokens], Option[FacebookToken]) = ???

  override def storeFacebookToken(userId: UserId, facebookToken: FacebookToken): Unit = ???

  override def removeTwitterTokens(userId: UserId): Unit = ???

  override def removeFacebookTokens(userId: UserId): Unit = ???

  override def allTwitterTokens: Seq[(UserId, TwitterTokens)] = ???

  override def allFacebookTokens: Seq[(UserId, FacebookToken)] = ???
}
