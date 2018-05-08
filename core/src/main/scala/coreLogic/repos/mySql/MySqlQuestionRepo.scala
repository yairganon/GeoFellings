package coreLogic.repos.mySql

import coreLogic.repos.QuestionsRepository
import org.springframework.jdbc.core.JdbcTemplate
import service.dto.{Question, Questionnaire, QuestionnaireAnswer}
import util.{QuestionId, QuestionnaireId, UserId}
import util.Utils._
import scala.collection.JavaConverters._

class MySqlQuestionRepo(template: JdbcTemplate) extends QuestionsRepository {

  override def add(question: Question): QuestionId = {
    val sql =
      """
        |INSERT INTO geoFeelings.questions (questionId, data) VALUES
        |(:questionId, :data)
        |ON DUPLICATE KEY UPDATE
        |`data` = :data;
      """.stripMargin
    val data = question.toJsonString
    val paramMap = Map(
      "questionId" -> question.id,
      "data" -> data)
    template.update(sql, paramMap.asJava)
    question.id
  }

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
