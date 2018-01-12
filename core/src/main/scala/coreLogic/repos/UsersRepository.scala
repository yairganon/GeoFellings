package coreLogic.repos

import enums.RegisterStatus
import service.dto.User
import util.UserId

trait UsersRepository {

  def add(user: User): RegisterStatus

  def get(userId: UserId): User

  def getAll: Seq[User]

  def isExist(userName: String, passWord: String): Option[UserId]
}
