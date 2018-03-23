package service.api

import service.dto.{Location, UpdateUserRequest}
import util.UserId

trait UserService {

  def patchUser(userId: UserId, updateUserRequest: UpdateUserRequest): Unit

  def lastLocations(): Map[UserId, Location]
}
