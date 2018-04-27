package views

import enums.{Gender, QuestionType, RegisterStatus}
import service.dto.{LocationTrigger, QuestionnaireWithAnswersDto, SocialNetworkTrigger, TimeRangeTrigger}
import util.QuestionnaireId

case class QuestionsView(questions: Seq[QuestionView])

case class QuestionView(id: String, questionType: QuestionType, data: QuestionDataView)

case class QuestionDataView(questionString: String,
                            numOfOptions: Option[Int],
                            radioOptions: Option[Seq[String]])

case class RegisterStatusView(status: RegisterStatus)

case class QuestionnairesView(questionnaires: Seq[QuestionnaireView])

case class QuestionnairesIdView(ids: Set[String])

case class QuestionnaireView(id: String,
                             name: String,
                             isRegistration: Boolean,
                             isDefault: Boolean,
                             questions: Seq[QuestionView])

case class UserView(userId: String,
                    userName: String,
                    gender: Gender,
                    age: Int,
                    limitQuestionnaire: Option[Int])


case class FullUserView(userId: String,
                        userName: String,
                        gender: Gender,
                        age: Int,
                        questionnaires: Seq[QuestionnaireWithAnswersDto])

case class UsersView(users: Seq[UserView])

case class TriggerView(id: String,
                       triggerName: String,
                       questionnaireName: String,
                       locationTrigger: Option[LocationTrigger],
                       socialNetworkTrigger: Option[SocialNetworkTrigger],
                       timeRangeTrigger: Option[TimeRangeTrigger])