package coreLogic.repos

import enums.RegisterStatus
import service.dto.User
import util.UserId

trait RegistrationRepository {

  def add(user: User): RegisterStatus

  def get(userId: UserId): User

  def isExist(userName: String, passWord: String): Option[UserId]
}
