package coreLogic.repos.inMemory

import coreLogic.repos.UsersRepository
import enums.RegisterStatus
import org.joda.time.DateTime
import service.dto.{Location, User, UserLocation}
import util.UserId

import scala.collection.mutable

class InMemoryUsersRepo extends UsersRepository {

  val repo = mutable.HashMap.empty[(UserId, String, String), User]
  val locationRepo = mutable.HashMap.empty[(UserId, Long), Location]

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

  override def getAll: Seq[User] = repo.values.toSeq

  override def update(user: User): Unit = repo += ((user.userId, user.userName, user.password) -> user)

  override def setUserCurrentLocation(userId: UserId, location: Location): Unit = {
    locationRepo += (userId, DateTime.now.getMillis) -> location
  }

  override def getUserLastLocation(userId: UserId): Option[Location] = {

    locationRepo.collect{case ((`userId`, time), location) =>  (time, location)}.toSeq match {
      case Nil => None
      case l => Some(l.maxBy(_._1)._2)
    }
  }

  override def locations():Seq[UserLocation] = Seq.empty
}
