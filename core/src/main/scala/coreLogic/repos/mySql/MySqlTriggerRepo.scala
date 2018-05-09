package coreLogic.repos.mySql

import coreLogic.repos.TriggerRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import service.dto.{LocationTrigger, SocialNetworkTrigger, Trigger}
import util.QuestionnaireId
import util.Utils._

import scala.collection.JavaConverters._

class MySqlTriggerRepo(template: NamedParameterJdbcTemplate)
  extends TriggerRepository
    with GeoServerRowMapper {

  override def addTrigger(trigger: Trigger): Unit = {
    val sql =
      """
        |INSERT INTO geoFeelings.triggers (questionnaireId, data) VALUES
        |(:questionnaireId, :data)
        |ON DUPLICATE KEY UPDATE
        |`data` = :data;
      """.stripMargin
    val data = trigger.toJsonString
    val paramMap = Map(
      "questionnaireId" -> trigger.questionnaireId.getId,
      "data" -> data)
    template.update(sql, paramMap.asJava)
  }

  override def getAll(): Seq[Trigger] = {
    val sql =
      """
        |SELECT `data` FROM geoFeelings.triggers;
      """.stripMargin
    template.query(sql, Map.empty[String, String].asJava, rowMapper[Trigger]).asScala
  }

  override def remove(questionnaireId: QuestionnaireId): Unit = {
    val sql =
      """
        |DELETE FROM geoFeelings.triggers WHERE `questionnaireId` = :questionnaireId;
      """.stripMargin
    val paramMap = Map(
      "questionnaireId" -> questionnaireId.getId)
    template.update(sql, paramMap.asJava)
  }


  override def getTwitterTriggers: Seq[QuestionnaireId] = {
    getAll()
      .collect{ case Trigger(_, id, _, Some(SocialNetworkTrigger(true)), _) => id }
  }

  override def getLocationTriggers: Seq[(QuestionnaireId, LocationTrigger)] = {
    getAll()
      .collect{ case Trigger(_, id, Some(locationTrigger), _, _) => (id, locationTrigger) }
  }
}
