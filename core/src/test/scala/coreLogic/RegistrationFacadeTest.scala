package coreLogic

import coreLogic.repos.inMemory.InMemoryUsersRepo
import enums.{Gender, RegisterStatus}
import org.specs2.mutable.Specification
import service.api.RegistrationService
import service.dto.{User, UserLoginRequest, UserRegisterRequest}

class RegistrationFacadeTest extends Specification {

  "RegistrationFacadeTest" should {
    val registrationFacade: RegistrationService = new RegistrationFacade(new InMemoryUsersRepo)
    val userName = "random name"
    val passWord = "random password"
    val gender = Gender.MALE
    val age = 6
    val request = UserRegisterRequest(userName, passWord, gender, age, None)
    "registerUser and then login" in {




      registrationFacade.registerUser(request) === RegisterStatus.CREATED

      val userId = registrationFacade.loginUser(UserLoginRequest(userName, passWord)).get

      val expectedUser = User(
        userId = userId,
        userName = userName,
        password = passWord,
        gender = gender,
        age = age)
      registrationFacade.user(userId) === expectedUser
    }

    "to same login will return conflict" in {
      registrationFacade.registerUser(request) === RegisterStatus.CREATED
      registrationFacade.registerUser(request) === RegisterStatus.CONFLICT
    }

  }
}
