package coreLogic

import coreLogic.repos.UsersRepository
import enums.RegisterStatus
import service.api.{QuestionsService, RegistrationService}
import service.dto.{User, UserLoginRequest, UserRegisterRequest}
import util.UserId

class RegistrationFacade(registrationRepo: UsersRepository,
                         questionsService: QuestionsService) extends RegistrationService {

  override def registerUser(request: UserRegisterRequest): RegisterStatus = {
    val user = User(UserId.random, request.userName, request.password, request.gender, request.age, None)
    val status = registrationRepo.add(user)
    if(status == RegisterStatus.CREATED)
      request.questionnaireAnswer.foreach(questionsService.submit(user.userId, _))
    status
  }

  override def loginUser(request: UserLoginRequest): Option[UserId] = {
    registrationRepo.isExist(request.userName, request.password)
  }

  override def user(userId: UserId): User = registrationRepo.get(userId)
}
