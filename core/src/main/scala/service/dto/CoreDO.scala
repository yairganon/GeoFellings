package service.dto

import enums.{ Gender, QuestionType }
import util.{ QuestionId, QuestionnaireId, UserId }

case class Question(
  id: QuestionId = QuestionId.random,
  `type`: QuestionType,
  questionString: String,
  numOfOptions: Option[Int]
)

case class User(
  userId: UserId,
  userName: String,
  password: String,
  gender: Gender,
  age: Int
)

case class Questionnaire(
  id: QuestionnaireId,
  name: String,
  isRegistration: Boolean,
  isDefault: Boolean,
  questions: Seq[QuestionId]
)
