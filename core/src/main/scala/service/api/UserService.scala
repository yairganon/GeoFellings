package service.api

import service.dto.UpdateUserRequest
import util.UserId

trait UserService {

  def patchUser(userId: UserId, updateUserRequest: UpdateUserRequest): Unit
}
