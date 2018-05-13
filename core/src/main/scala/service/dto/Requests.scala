package service.dto

import enums.{Gender, QuestionType}
import util.{QuestionId, QuestionnaireId, UserId}

case class RegisterTokenRequest(token: String)

case class Location(latitude: Double, longitude: Double)

case class CreateQuestionRequest(questionType: QuestionType, data: CreateQuestionData)

case class CreateQuestionData(questionString: String,
                              numOfOptions: Option[Int],
                              radioOptions: Option[Seq[String]])

case class CreateQuestionnaireRequest(name: String, isRegistration: Boolean, isDefault: Boolean, ids: Seq[QuestionId])

case class UserLoginRequest(userName: String, password: String)

case class QuestionAnswer(id: QuestionId, answer: String)

case class UserRegisterRequest(userName: String,
                               password: String,
                               gender: Gender,
                               age: Int,
                               questionnaireAnswer: Option[QuestionnaireAnswerRequest])

case class TwitterTokens(accessToken: String, accessTokenSecret: String, id: Long, name: String)

case class FacebookToken(token: String)

case class FacebookTokenDo(userId: UserId, token: String)

case class QuestionnaireAnswerRequest(id: QuestionnaireId, location: Option[Location], answers: Seq[QuestionAnswer])

case class UpdateUserRequest(location: Option[Location],
                             limitQuestionnaire: Option[LimitQuestionnaire])

case class LimitQuestionnaire(limit: Option[Int])


case class LocationTrigger(location: Location, radius: Int)

case class SocialNetworkTrigger(onNewActivity: Boolean)

case class TimeRangeTrigger(from: String, to: String)

case class CreateTriggerRequest(triggerName: String,
                                questionnaireId: QuestionnaireId,
                                locationTrigger: Option[LocationTrigger],
                                socialNetworkTrigger: Option[SocialNetworkTrigger],
                                timeRangeTrigger: Option[TimeRangeTrigger])