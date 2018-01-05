package service.dto

import enums.QuestionType

case class RegisterTokenRequest(token: String)

case class UpdateLocationRequest(latitude: Double, longitude: Double)

case class CreateQuestionRequest(questionType: QuestionType, data: CreateQuestionData)

case class CreateQuestionData(questionString: String, numOfOptions: Option[Int])