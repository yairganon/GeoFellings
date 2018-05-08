package coreLogic.repos.mySql

import coreLogic.repos.UsersRepository
import enums.RegisterStatus

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import service.dto.{Location, User}
import util.UserId

class MySqlUsersRepo(template: NamedParameterJdbcTemplate) extends UsersRepository {

  override def add(user: User): RegisterStatus = ???

  override def get(userId: UserId): User = ???

  override def getAll: Seq[User] = ???

  override def isExist(userName: String, passWord: String): Option[UserId] = ???

  override def update(user: User): Unit = ???

  override def setUserCurrentLocation(userId: UserId, location: Location): Unit = ???

  override def getUserLastLocation(userId: UserId): Option[Location] = ???
}
