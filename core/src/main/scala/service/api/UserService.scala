package service.api

import service.dto.{Location, UpdateUserRequest, User, UserLocation}
import util.UserId

trait UserService {

  def patchUser(userId: UserId, updateUserRequest: UpdateUserRequest): Unit

  def lastLocations(): Map[UserId, Location]

  def locations(): Seq[UserLocation]

  def getUser(userId: UserId): User

  def getAllUser(): Seq[User]
}
