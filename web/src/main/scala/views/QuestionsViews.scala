package views

import enums.{Gender, QuestionType, RegisterStatus}
import service.dto.QuestionnaireWithAnswersDto
import util.UserId

case class QuestionsView(questions: Seq[QuestionView])

case class QuestionView(id: String, questionType: QuestionType, data: QuestionDataView)

case class QuestionDataView(questionString: String, numOfOptions: Option[Int])

case class RegisterStatusView(status: RegisterStatus)

case class QuestionnairesView(questionnaires: Seq[QuestionnaireView])

case class QuestionnaireView(id: String,
                             name: String,
                             isRegistration: Boolean,
                             isDefault: Boolean,
                             questions: Seq[QuestionView])

case class UserView(userId: UserId,
                    userName: String,
                    gender: Gender,
                    age: Int)


case class FullUserView(userId: UserId,
                        userName: String,
                        gender: Gender,
                        age: Int,
                        questionnaires: Seq[QuestionnaireWithAnswersDto])

case class UsersView(users: Seq[UserView])