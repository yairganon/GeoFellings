package coreLogic

import coreLogic.repos.UsersRepository
import service.api.UserService
import service.dto.UpdateUserRequest
import util.UserId

class LogInUserFacade(usersRepository: UsersRepository) extends UserService {

  def patchUser(userId: UserId, updateUserRequest: UpdateUserRequest): Unit = {
    updateUserRequest.limitQuestionnaire.foreach(limitQuestionnaire => {
      val user = usersRepository.get(userId)
      usersRepository.update(user.copy(limitQuestionnaire = limitQuestionnaire.limit))
    })
    updateUserRequest.location.foreach(usersRepository.setUserCurrentLocation(userId, _))
  }
}
