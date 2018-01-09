package views

import enums.QuestionType
import util.UserId

case class QuestionsView(questions: Seq[QuestionView])

case class QuestionView(id: String, questionType: QuestionType, data: QuestionDataView)

case class QuestionDataView(questionString: String, numOfOptions: Option[Int])

case class UserIdView(id: UserId)
