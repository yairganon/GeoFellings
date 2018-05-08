package coreLogic.repos.mySql

import coreLogic.repos.TriggerRepository
import org.springframework.jdbc.core.JdbcTemplate
import service.dto.{LocationTrigger, Trigger}
import util.QuestionnaireId

class MySqlTriggerRepo(template: JdbcTemplate) extends TriggerRepository {

  override def getAll(): Seq[Trigger] = ???

  override def remove(questionnaireId: QuestionnaireId): Unit = ???

  override def addTrigger(trigger: Trigger): Unit = ???

  override def getTwitterTriggers: Seq[QuestionnaireId] = ???

  override def getLocationTriggers: Seq[(QuestionnaireId, LocationTrigger)] = ???
}
