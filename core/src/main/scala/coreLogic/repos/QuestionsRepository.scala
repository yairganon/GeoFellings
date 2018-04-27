package coreLogic.repos

import service.dto.{Question, Questionnaire, QuestionnaireAnswer}
import util.{QuestionId, QuestionnaireId, UserId}

trait QuestionsRepository {

  def add(question: Question): QuestionId

  def add(questionnaire: Questionnaire): Unit

  def getQuestions: Seq[Question]

  def getQuestionnaires: Seq[Questionnaire]

  def registerQuestionnaire: Option[Questionnaire]

  def defaultQuestionnaire: Option[Questionnaire]

  def submit(questionnaireAnswer: QuestionnaireAnswer): Unit

  def getAnswers(userId: UserId): Seq[QuestionnaireAnswer]

  def getQuestionnaire(questionnaireId: QuestionnaireId): Questionnaire

  def getQuestion(questionId: QuestionId): Question
}
