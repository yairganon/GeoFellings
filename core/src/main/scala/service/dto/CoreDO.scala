package service.dto

import enums.QuestionType
import util.QuestionId

case class Question(
  id: QuestionId = QuestionId.random,
  `type`: QuestionType,
  questionString: String,
  numOfOptions: Option[Int]
)
