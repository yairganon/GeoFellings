package coreLogic.repos.mySql

import coreLogic.repos.ThirdPartyTokensRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import service.dto.{FacebookTokenDo, TwitterTokens}
import util.UserId
import util.Utils._

import scala.collection.JavaConverters._

class MySqlThirdPartyTokensRepo(template: NamedParameterJdbcTemplate)
  extends ThirdPartyTokensRepository
    with GeoServerRowMapper {

  override def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit = ???

  override def tokens(userId: UserId): (Option[TwitterTokens], Option[FacebookTokenDo]) = {
      val sql =
        """
          |SELECT `data` FROM geoFeelings.facebookTokens
          |WHERE `userId` = :userId;
        """.stripMargin
      val paramMap = Map(
        "userId" -> userId.getId)
      val fbToken = template.query(sql, paramMap.asJava, rowMapper[FacebookTokenDo]).asScala.headOption
    (None, fbToken)
  }

  override def storeFacebookToken(facebookToken: FacebookTokenDo): Unit = {
    val sql =
      """
        |INSERT INTO geoFeelings.facebookTokens (userId, data) VALUES
        |(:userId, :data)
        |ON DUPLICATE KEY UPDATE
        |`data` = :data;
      """.stripMargin
    val data = facebookToken.toJsonString
    val paramMap = Map(
      "userId" -> facebookToken.userId.getId,
      "data" -> data)
    template.update(sql, paramMap.asJava)
  }

  override def removeTwitterTokens(userId: UserId): Unit = ???

  override def removeFacebookTokens(userId: UserId): Unit = {
    val sql =
      """
        |DELETE FROM geoFeelings.facebookTokens WHERE `userId` = :userId;
      """.stripMargin
    val paramMap = Map(
      "userId" -> userId.getId)
    template.update(sql, paramMap.asJava)
  }

  override def allTwitterTokens: Seq[(UserId, TwitterTokens)] = Seq.empty

  override def allFacebookTokens: Seq[(UserId, FacebookTokenDo)] = Seq.empty
}
