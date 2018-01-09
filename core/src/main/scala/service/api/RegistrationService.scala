package service.api

import enums.RegisterStatus
import service.dto.{ User, UserLoginRequest, UserRegisterRequest }
import util.UserId

trait RegistrationService {

  def registerUser(request: UserRegisterRequest): RegisterStatus

  def loginUser(request: UserLoginRequest): Option[UserId]

  def user(userId: UserId): User
}
