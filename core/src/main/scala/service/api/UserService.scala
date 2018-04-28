package service.api

import service.dto.{Location, UpdateUserRequest, User}
import util.UserId

trait UserService {

  def patchUser(userId: UserId, updateUserRequest: UpdateUserRequest): Unit

  def lastLocations(): Map[UserId, Location]

  def getUser(userId: UserId): User

  def getAllUser(): Seq[User]
}
