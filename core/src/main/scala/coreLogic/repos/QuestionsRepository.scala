package coreLogic.repos

import service.dto.{ Question, Questionnaire }

trait QuestionsRepository {

  def add(question: Question): Unit

  def add(questionnaire: Questionnaire): Unit

  def getQuestions: Seq[Question]

  def getQuestionnaires: Seq[Questionnaire]

  def registerQuestionnaire: Option[Questionnaire]

  def defaultQuestionnaire: Option[Questionnaire]

}
