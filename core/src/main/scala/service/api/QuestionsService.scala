package service.api

import enums.QuestionType
import util.QuestionId

trait QuestionsService {

  def addQuestion(question: Question): Unit
}

case class Question(id: QuestionId = QuestionId.random,
                   `type`: QuestionType,
                    questionString: String,
                    numOfOptions: Option[Int])

