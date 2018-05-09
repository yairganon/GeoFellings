package coreLogic.repos.mySql

import coreLogic.repos.QuestionsRepository

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import service.dto.{Question, Questionnaire, QuestionnaireAnswer}
import util.{QuestionId, QuestionnaireId, UserId}
import util.Utils._

import scala.collection.JavaConverters._

class MySqlQuestionRepo(template: NamedParameterJdbcTemplate)
  extends QuestionsRepository
    with GeoServerRowMapper {

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
      "questionId" -> question.id.getId,
      "data" -> data)
    template.update(sql, paramMap.asJava)
    question.id
  }

  override def getQuestions: Seq[Question] = {
    val sql =
      """
        |SELECT `data` FROM geoFeelings.questions;
      """.stripMargin
    template.query(sql, Map.empty[String, String].asJava, rowMapper[Question]).asScala
  }

  override def getQuestion(questionId: QuestionId): Question = {
    val sql =
      """
        |SELECT `data` FROM geoFeelings.questions
        |WHERE `questionId` = :questionId;
      """.stripMargin
    val paramMap = Map(
      "questionId" -> questionId.getId)
    template.query(sql, paramMap.asJava, rowMapper[Question]).asScala.head
  }

  override def add(questionnaire: Questionnaire): Unit = {
    val sql =
      """
        |INSERT INTO geoFeelings.questionnaire (questionnaireId, data) VALUES
        |(:questionnaireId, :data)
        |ON DUPLICATE KEY UPDATE
        |`data` = :data;
      """.stripMargin
    val data = questionnaire.toJsonString
    val paramMap = Map(
      "questionnaireId" -> questionnaire.id.getId,
      "data" -> data)
    template.update(sql, paramMap.asJava)
  }

  override def getQuestionnaires: Seq[Questionnaire] = {
    val sql =
      """
        |SELECT `data` FROM geoFeelings.questionnaire;
      """.stripMargin
    template.query(sql, Map.empty[String, String].asJava, rowMapper[Questionnaire]).asScala
  }

  override def getQuestionnaire(questionnaireId: QuestionnaireId): Questionnaire = {
    val sql =
      """
        |SELECT `data` FROM geoFeelings.questionnaire
        |WHERE `questionnaireId` = :questionnaireId;
      """.stripMargin
    val paramMap = Map(
      "questionnaireId" -> questionnaireId.getId)
    template.query(sql, paramMap.asJava, rowMapper[Questionnaire]).asScala.head
  }

  override def submit(questionnaireAnswer: QuestionnaireAnswer): Unit = {
    val sql =
      """
        |INSERT INTO geoFeelings.questionnaireAnswer (userId, questionnaireId, data) VALUES
        |(:userId, :questionnaireId, :data)
        |ON DUPLICATE KEY UPDATE
        |`data` = :data;
      """.stripMargin
    val data = questionnaireAnswer.toJsonString
    val paramMap = Map(
      "questionnaireId" -> questionnaireAnswer.questionnaireId.getId,
      "userId" -> questionnaireAnswer.userId.getId,
      "data" -> data)
    template.update(sql, paramMap.asJava)
  }

  override def getAnswers(userId: UserId): Seq[QuestionnaireAnswer] = {
    val sql =
      """
        |SELECT `data` FROM geoFeelings.questionnaireAnswer;
        |WHERE `userId` = :userId;
      """.stripMargin
    val paramMap = Map(
      "userId" -> userId.getId)
    template.query(sql, paramMap.asJava, rowMapper[QuestionnaireAnswer]).asScala
  }

  override def registerQuestionnaire: Option[Questionnaire] = {
    getQuestionnaires.find(_.isRegistration)
  }

  override def defaultQuestionnaire: Option[Questionnaire] = {
    getQuestionnaires.find(_.isDefault)
  }
}
