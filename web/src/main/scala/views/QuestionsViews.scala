package views

import enums.{ QuestionType, RegisterStatus }

case class QuestionsView(questions: Seq[QuestionView])

case class QuestionView(id: String, questionType: QuestionType, data: QuestionDataView)

case class QuestionDataView(questionString: String, numOfOptions: Option[Int])

case class RegisterStatusView(status: RegisterStatus)

case class QuestionnairesView(questionnaires: Seq[QuestionnaireView])

case class QuestionnaireView(
  id: String,
  name: String,
  isRegistration: Boolean,
  isDefault: Boolean,
  questions: Seq[QuestionView]
)