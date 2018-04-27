package service.api

import org.joda.time.DateTime
import service.dto._
import util.{QuestionId, QuestionnaireId, UserId}

trait QuestionsService {

  def getQuestionnaire(questionnaireId: QuestionnaireId): Questionnaire


  def getUserQuestionnaires(userId: UserId): Seq[QuestionnaireWithAnswersDto]


  def getAll: Seq[Question]

  def registerQuestionnaire: Option[Questionnaire]

  def defaultQuestionnaire: Option[Questionnaire]

  def addQuestion(question: CreateQuestionRequest): QuestionId

  def addQuestionnaire(request: CreateQuestionnaireRequest): Unit

  def getQuestionnaires: Seq[Questionnaire]

  def submit(userId: UserId, request: QuestionnaireAnswerRequest, submitTime: DateTime = DateTime.now): Unit

  def addQuestionnaireTo(userId: UserId, questionnaireId: QuestionnaireId): Unit

  def getWaitingQuestionnaireForUser(userId: UserId): Set[QuestionnaireId]
}

