package coreLogic

import coreLogic.repos.UsersRepository
import service.api.UserService
import service.dto.{Location, UpdateUserRequest}
import util.UserId

class LogInUserFacade(usersRepository: UsersRepository) extends UserService {

  def patchUser(userId: UserId, updateUserRequest: UpdateUserRequest): Unit = {
    updateUserRequest.limitQuestionnaire.foreach(limitQuestionnaire => {
      val user = usersRepository.get(userId)
      usersRepository.update(user.copy(limitQuestionnaire = limitQuestionnaire.limit))
    })
    updateUserRequest.location.foreach(usersRepository.setUserCurrentLocation(userId, _))
  }

  override def lastLocations(): Map[UserId, Location] = {
    usersRepository.getAll
      .map(user => user.userId -> usersRepository.getUserLastLocation(user.userId))
      .collect{ case (uId, Some(location)) => uId -> location }
      .toMap
  }
}
