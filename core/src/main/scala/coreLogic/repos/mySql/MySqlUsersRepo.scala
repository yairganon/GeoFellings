package coreLogic.repos.mySql

import coreLogic.repos.UsersRepository
import enums.RegisterStatus
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import service.dto.{Location, User}
import util.UserId
import util.Utils._

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

class MySqlUsersRepo(template: NamedParameterJdbcTemplate)
  extends UsersRepository
    with GeoServerRowMapper {

  override def add(user: User): RegisterStatus = {
    val sql =
      """
        |INSERT INTO geoFeelings.users (userId, userName, passWord, data) VALUES
        |(:userId, :userName, :passWord, :data);
      """.stripMargin
    val data = user.toJsonString
    val paramMap = Map(
      "userId" -> user.userId.getId,
      "userName" -> user.userName,
      "passWord" -> user.password,
      "data" -> data)
    Try(template.update(sql, paramMap.asJava)) match {
      case Success(_) => RegisterStatus.CREATED
      case Failure(_: DuplicateKeyException) => RegisterStatus.CONFLICT
      case Failure(e) => throw e
    }
  }

  override def get(userId: UserId): User = {
    val sql =
      """
        |SELECT `data` FROM geoFeelings.users WHERE `userId` = :userId;
      """.stripMargin
    val paramMap = Map(
      "userId" -> userId.getId)
    template.query(sql, paramMap.asJava, rowMapper[User]).asScala.head
  }

  override def getAll: Seq[User] = {
    val sql =
      """
        |SELECT `data` FROM geoFeelings.users;
      """.stripMargin
    template.query(sql,  Map.empty[String, String].asJava, rowMapper[User]).asScala
  }

  override def isExist(userName: String, passWord: String): Option[UserId] = {
    val sql =
      """
        |SELECT `data` FROM geoFeelings.users WHERE `userName` = :userName and `passWord` = :passWord;
      """.stripMargin
    val paramMap = Map(
      "userName" -> userName,
      "passWord" -> passWord)
    template.query(sql, paramMap.asJava, rowMapper[User]).asScala.headOption.map(_.userId)
  }

  override def update(user: User): Unit = {
    val sql =
      """
        |UPDATE geoFeelings.users SET
        |`userName` = :userName,
        |`passWord` = :passWord,
        |`data` = :data WHERE `userId` = :userId
      """.stripMargin
    val data = user.toJsonString
    val paramMap = Map(
      "userId" -> user.userId.getId,
      "userName" -> user.userName,
      "passWord" -> user.password,
      "data" -> data)
    template.update(sql, paramMap.asJava)
  }

  override def setUserCurrentLocation(userId: UserId, location: Location): Unit = {
    val sql =
      """
        |INSERT INTO geoFeelings.userLocations (userId, data) VALUES
        |(:userId, :data);
      """.stripMargin
    val data = location.toJsonString
    val paramMap = Map(
      "userId" -> userId.getId,
      "data" -> data)
    template.update(sql, paramMap.asJava)
  }

  override def getUserLastLocation(userId: UserId): Option[Location] = None
}
