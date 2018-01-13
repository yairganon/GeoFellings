package coreLogic.repos

import service.dto.{Question, Questionnaire, QuestionnaireAnswer}
import util.{QuestionId, QuestionnaireId, UserId}

trait QuestionsRepository {

  def addQuestionnaireTo(userId: UserId): Unit


  def add(question: Question): Unit

  def add(questionnaire: Questionnaire): Unit

  def getQuestions: Seq[Question]

  def getQuestionnaires: Seq[Questionnaire]

  def registerQuestionnaire: Option[Questionnaire]

  def defaultQuestionnaire: Option[Questionnaire]

  def submit(questionnaireAnswer: QuestionnaireAnswer): Unit

  def getAnswers(userId: UserId): Seq[QuestionnaireAnswer]

  def getQuestionnaire(questionnaireId: QuestionnaireId): Questionnaire

  def getWaitingQuestionnaireFor(userId: UserId): Option[QuestionnaireId]

  def getQuestion(questionId: QuestionId): Question
}
