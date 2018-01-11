package service.dto

import enums.{Gender, QuestionType}
import util.QuestionId

case class RegisterTokenRequest(token: String)

case class UpdateLocationRequest(latitude: Double, longitude: Double)

case class CreateQuestionRequest(questionType: QuestionType, data: CreateQuestionData)

case class CreateQuestionData(questionString: String, numOfOptions: Option[Int])

case class CreateQuestionnaireRequest(name: String, ids: Seq[QuestionId])

case class UserLoginRequest(userName: String, password: String)

case class QuestionAnswer(id: QuestionId, answer: String)

case class UserRegisterRequest(userName: String,
                               password: String,
                               gender: Gender,
                               age: Int,
                               questionnaireAnswer: Option[Seq[QuestionAnswer]])

case class TwitterTokens(accessToken: String, accessTokenSecret: String)