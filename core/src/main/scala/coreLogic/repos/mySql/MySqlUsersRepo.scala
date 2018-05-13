package coreLogic.repos.mySql

import coreLogic.repos.UsersRepository
import enums.RegisterStatus

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import service.dto.{Location, User}
import util.UserId
import util.Utils._

import scala.collection.JavaConverters._

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
    template.update(sql, paramMap.asJava)
    RegisterStatus.CREATED
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

  override def getAll: Seq[User] = ???

  override def isExist(userName: String, passWord: String): Option[UserId] = {
    val sql =
      """
        |SELECT `data` FROM geoFeelings.users WHERE `userName` = :userId and `passWord` = :passWord;
      """.stripMargin
    val paramMap = Map(
      "userName" -> userName,
      "passWord" -> passWord)
    template.query(sql, paramMap.asJava, rowMapper[User]).asScala.headOption.map(_.userId)
  }

  override def update(user: User): Unit = ???

  override def setUserCurrentLocation(userId: UserId, location: Location): Unit = ???

  override def getUserLastLocation(userId: UserId): Option[Location] = ???
}
