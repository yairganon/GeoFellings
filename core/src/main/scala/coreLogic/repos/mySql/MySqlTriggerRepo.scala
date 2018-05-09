package coreLogic.repos.mySql

import coreLogic.repos.TriggerRepository

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import service.dto.{LocationTrigger, Trigger}
import util.QuestionnaireId

class MySqlTriggerRepo(template: NamedParameterJdbcTemplate)
  extends TriggerRepository
    with GeoServerRowMapper {

  override def getAll(): Seq[Trigger] = ???

  override def remove(questionnaireId: QuestionnaireId): Unit = ???

  override def addTrigger(trigger: Trigger): Unit = ???

  override def getTwitterTriggers: Seq[QuestionnaireId] = ???

  override def getLocationTriggers: Seq[(QuestionnaireId, LocationTrigger)] = ???
}
