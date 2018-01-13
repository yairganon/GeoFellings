package views

import enums.{Gender, QuestionType, RegisterStatus}
import service.dto.QuestionnaireWithAnswersDto

case class QuestionsView(questions: Seq[QuestionView])

case class QuestionView(id: String, questionType: QuestionType, data: QuestionDataView)

case class QuestionDataView(questionString: String, numOfOptions: Option[Int])

case class RegisterStatusView(status: RegisterStatus)

case class QuestionnairesView(questionnaires: Seq[QuestionnaireView])

case class QuestionnairesIdView(id: Option[String])

case class QuestionnaireView(id: String,
                             name: String,
                             isRegistration: Boolean,
                             isDefault: Boolean,
                             questions: Seq[QuestionView])

case class UserView(userId: String,
                    userName: String,
                    gender: Gender,
                    age: Int)


case class FullUserView(userId: String,
                        userName: String,
                        gender: Gender,
                        age: Int,
                        questionnaires: Seq[QuestionnaireWithAnswersDto])

case class UsersView(users: Seq[UserView])