package coreLogic.repos.mySql

import coreLogic.repos.ThirdPartyTokensRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import service.dto.{FacebookTokenDo, TwitterTokensDo}
import util.UserId
import util.Utils._

import scala.collection.JavaConverters._

class MySqlThirdPartyTokensRepo(template: NamedParameterJdbcTemplate)
  extends ThirdPartyTokensRepository
    with GeoServerRowMapper {


  override def tokens(userId: UserId): (Option[TwitterTokensDo], Option[FacebookTokenDo]) = {
    val paramMap = Map("userId" -> userId.getId)

    def facebookTokens() = {
      val sql =
        """
          |SELECT `data` FROM geoFeelings.facebookTokens
          |WHERE `userId` = :userId;
        """.stripMargin
      template.query(sql, paramMap.asJava, rowMapper[FacebookTokenDo]).asScala.headOption
    }

    def twitterTokens() = {
      val sql =
        """
          |SELECT `data` FROM geoFeelings.twitterTokens
          |WHERE `userId` = :userId;
        """.stripMargin
      template.query(sql, paramMap.asJava, rowMapper[TwitterTokensDo]).asScala.headOption
    }
    (twitterTokens(), facebookTokens())
  }

  override def storeTwitterTokens(twitterTokens: TwitterTokensDo): Unit = {
    val sql =
      """
        |INSERT INTO geoFeelings.twitterTokens (userId, data) VALUES
        |(:userId, :data)
        |ON DUPLICATE KEY UPDATE
        |`data` = :data;
      """.stripMargin
    val data = twitterTokens.toJsonString
    val paramMap = Map(
      "userId" -> twitterTokens.userId.getId,
      "data" -> data)
    template.update(sql, paramMap.asJava)
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

  override def removeTwitterTokens(userId: UserId): Unit = {
    val sql =
      """
        |DELETE FROM geoFeelings.twitterTokens WHERE `userId` = :userId;
      """.stripMargin
    val paramMap = Map(
      "userId" -> userId.getId)
    template.update(sql, paramMap.asJava)
  }

  override def removeFacebookTokens(userId: UserId): Unit = {
    val sql =
      """
        |DELETE FROM geoFeelings.facebookTokens WHERE `userId` = :userId;
      """.stripMargin
    val paramMap = Map(
      "userId" -> userId.getId)
    template.update(sql, paramMap.asJava)
  }

  override def allTwitterTokens: Seq[TwitterTokensDo] = Seq.empty

  override def allFacebookTokens: Seq[FacebookTokenDo] = Seq.empty
}
