package coreLogic

import coreLogic.repos.UsersRepository
import gcm.http.FacebookRest
import service.api.{ThirdPartyService, UserService}
import service.dto.{Location, UpdateUserRequest, User}
import util.UserId

class LogInUserFacade(usersRepository: UsersRepository,
                      thirdPartyFacade: ThirdPartyService) extends UserService {

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

  override def getUser(userId: UserId): User = {
    val maybeProfilePicture = thirdPartyFacade.tokens(userId)._2
      .flatMap(fbToken => FacebookRest.profilePictureOf(fbToken.token))
    usersRepository
      .get(userId)
      .copy(fbProfilePicture = maybeProfilePicture)

  }
}
