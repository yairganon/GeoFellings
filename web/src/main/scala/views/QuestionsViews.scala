package views

import enums.{QuestionType, RegisterStatus}

case class QuestionsView(questions: Seq[QuestionView])

case class QuestionView(id: String, questionType: QuestionType, data: QuestionDataView)

case class QuestionDataView(questionString: String, numOfOptions: Option[Int])

case class RegisterStatusView(status: RegisterStatus)