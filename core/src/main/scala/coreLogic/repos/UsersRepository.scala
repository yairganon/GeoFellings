package coreLogic.repos

import enums.RegisterStatus
import service.dto.{Location, User, UserLocation}
import util.UserId

trait UsersRepository {

  def locations(): Seq[UserLocation]


  def add(user: User): RegisterStatus

  def get(userId: UserId): User

  def getAll: Seq[User]

  def isExist(userName: String, passWord: String): Option[UserId]

  def update(user: User): Unit

  def setUserCurrentLocation(userId: UserId, location: Location)

  def getUserLastLocation(userId: UserId): Option[Location]
}
