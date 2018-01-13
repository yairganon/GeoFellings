package service.api

import org.joda.time.DateTime
import service.dto._
import util.{QuestionnaireId, UserId}

trait QuestionsService {

  def getUserQuestionnaires(userId: UserId): Seq[QuestionnaireWithAnswersDto]


  def getAll: Seq[Question]

  def registerQuestionnaire: Option[Questionnaire]

  def defaultQuestionnaire: Option[Questionnaire]

  def addQuestion(question: CreateQuestionRequest): Unit

  def addQuestionnaire(request: CreateQuestionnaireRequest): Unit

  def getQuestionnaires: Seq[Questionnaire]

  def submit(userId: UserId, request: QuestionnaireAnswerRequest, submitTime: DateTime = DateTime.now): Unit

  def addQuestionnaireTo(userId: UserId): Unit

  def getWaitingQuestionnaireForUser(userId: UserId): Option[QuestionnaireId]
}

