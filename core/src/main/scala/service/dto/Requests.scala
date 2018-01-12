package service.dto

import enums.{Gender, QuestionType}
import util.{QuestionId, QuestionnaireId}

case class RegisterTokenRequest(token: String)

case class Location(latitude: Double, longitude: Double)

case class CreateQuestionRequest(questionType: QuestionType, data: CreateQuestionData)

case class CreateQuestionData(questionString: String, numOfOptions: Option[Int])

case class CreateQuestionnaireRequest(name: String, isRegistration: Boolean, isDefault: Boolean, ids: Seq[QuestionId])

case class UserLoginRequest(userName: String, password: String)

case class QuestionAnswer(id: QuestionId, answer: String)

case class UserRegisterRequest(userName: String,
                               password: String,
                               gender: Gender,
                               age: Int,
                               questionnaireAnswer: Option[QuestionnaireAnswer])

case class TwitterTokens(accessToken: String, accessTokenSecret: String)

case class QuestionnaireAnswer(id: QuestionnaireId, location: Option[Location], answers: Seq[QuestionAnswer])