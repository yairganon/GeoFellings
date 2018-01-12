package service.dto

import enums.{Gender, QuestionType}
import org.joda.time.DateTime
import util.{QuestionId, QuestionnaireId, UserId}

case class Question(id: QuestionId = QuestionId.random,
                    `type`: QuestionType,
                    questionString: String,
                    numOfOptions: Option[Int])

case class User(userId: UserId,
                userName: String,
                password: String,
                gender: Gender,
                age: Int)

case class Questionnaire(id: QuestionnaireId,
                         name: String,
                         isRegistration: Boolean,
                         isDefault: Boolean,
                         questions: Seq[QuestionId])

case class QuestionnaireAnswer(userId: UserId,
                               questionnaireId: QuestionnaireId,
                               time: DateTime,
                               location: Option[Location],
                               answers: Seq[QuestionAnswer])


case class QuestionWithAnswersDto(id: String,
                                  questionType: QuestionType,
                                  questionString: String,
                                  numOfOptions: Option[Int],
                                  answer: String)

case class QuestionnaireWithAnswersDto(id: String,
                                       name: String,
                                       location: Option[Location],
                                       formattedTime: String,
                                       isRegistration: Boolean,
                                       isDefault: Boolean,
                                       questions: Seq[QuestionWithAnswersDto])