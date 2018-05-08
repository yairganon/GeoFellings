package coreLogic.repos.mySql

import coreLogic.repos.QuestionsRepository
import org.springframework.jdbc.core.JdbcTemplate
import service.dto.{Question, Questionnaire, QuestionnaireAnswer}
import util.{QuestionId, QuestionnaireId, UserId}

class MySqlQuestionRepo(template: JdbcTemplate) extends QuestionsRepository {

  override def add(question: Question): QuestionId = ???

  override def add(questionnaire: Questionnaire): Unit = ???

  override def getQuestions: Seq[Question] = ???

  override def getQuestionnaires: Seq[Questionnaire] = ???

  override def registerQuestionnaire: Option[Questionnaire] = ???

  override def defaultQuestionnaire: Option[Questionnaire] = ???

  override def submit(questionnaireAnswer: QuestionnaireAnswer): Unit = ???

  override def getAnswers(userId: UserId): Seq[QuestionnaireAnswer] = ???

  override def getQuestionnaire(questionnaireId: QuestionnaireId): Questionnaire = ???

  override def getQuestion(questionId: QuestionId): Question = ???
}
