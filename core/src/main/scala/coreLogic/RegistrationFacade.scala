package coreLogic

import coreLogic.repos.UsersRepository
import enums.RegisterStatus
import service.api.RegistrationService
import service.dto.{ User, UserLoginRequest, UserRegisterRequest }
import util.UserId

class RegistrationFacade(registrationRepo: UsersRepository) extends RegistrationService {

  override def registerUser(request: UserRegisterRequest): RegisterStatus = {
    registrationRepo.add(User(UserId.random, request.userName, request.password, request.gender, request.age))
  }

  override def loginUser(request: UserLoginRequest): Option[UserId] = {
    registrationRepo.isExist(request.userName, request.password)
  }

  override def user(userId: UserId): User = registrationRepo.get(userId)
}
