package service.dto

import enums.QuestionType
import util.QuestionId

case class RegisterTokenRequest(token: String)

case class UpdateLocationRequest(latitude: Double, longitude: Double)

case class CreateQuestionRequest(questionType: QuestionType, data: CreateQuestionData)

case class CreateQuestionData(questionString: String, numOfOptions: Option[Int])

case class CreateQuestionnaireRequest(name: String, ids: Seq[QuestionId])

case class UserLoginRequest(userName: String, password: String)