package views

import enums.{Gender, QuestionType, RegisterStatus}
import service.dto._
import util.QuestionnaireId

case class QuestionsView(questions: Seq[QuestionView])

case class QuestionView(id: String, questionType: QuestionType, data: QuestionDataView)

case class QuestionDataView(questionString: String,
                            numOfOptions: Option[Int],
                            radioOptions: Option[Seq[String]])

case class RegisterStatusView(status: RegisterStatus)

case class QuestionnairesView(questionnaires: Seq[QuestionnaireView])

case class QuestionnaireView(id: String,
                             name: String,
                             isRegistration: Boolean,
                             isDefault: Boolean,
                             questions: Seq[QuestionView])

case class UserView(userId: String,
                    userName: String,
                    gender: Gender,
                    age: Int,
                    limitQuestionnaire: Option[Int],
                    image: Option[String])


case class FullUserView(userId: String,
                        userName: String,
                        gender: Gender,
                        age: Int,
                        image: Option[String],
                        questionnaires: Seq[QuestionnaireWithAnswersDto])

case class UsersView(users: Seq[UserView])

case class TriggerView(id: String,
                       triggerName: String,
                       questionnaireName: String,
                       locationTrigger: Option[LocationTrigger],
                       socialNetworkTrigger: Option[SocialNetworkTrigger],
                       timeRangeTrigger: Option[TimeRangeTrigger])


case class SocialNetworkTokens(twitter: Option[TwitterTokens], facebook: Option[FacebookToken])