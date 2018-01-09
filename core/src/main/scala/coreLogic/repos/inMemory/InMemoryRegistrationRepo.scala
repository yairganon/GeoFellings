package coreLogic.repos.inMemory

import coreLogic.repos.RegistrationRepository
import enums.RegisterStatus
import service.dto.User
import util.UserId

import scala.collection.mutable

class InMemoryRegistrationRepo extends RegistrationRepository {

  val repo = mutable.HashMap.empty[(UserId, String, String), User]

  override def add(user: User): RegisterStatus = {
    isExist(user.userName, user.password) match {
      case None =>
        repo += ((user.userId, user.userName, user.password) -> user)
        RegisterStatus.CREATED
      case _ => RegisterStatus.CONFLICT
    }
  }

  override def get(userId: UserId): User = repo.collectFirst { case ((`userId`, _, _), user) => user }.get

  override def isExist(userName: String, passWord: String): Option[UserId] =
    repo.collectFirst { case ((uId, `userName`, `passWord`), _) => uId }
}
