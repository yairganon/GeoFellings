package service.dto

import enums.{ QuestionType, Gender }
import util.{ QuestionId, UserId }

case class Question(
  id: QuestionId = QuestionId.random,
  `type`: QuestionType,
  questionString: String,
  numOfOptions: Option[Int]
)

case class User(userId: UserId, password: String, gender: Gender, age: Int)
