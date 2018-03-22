package coreLogic.repos

import service.dto.Trigger
import util.QuestionnaireId

trait TriggerRepository {

  def addTrigger(trigger: Trigger): Unit

  def getTwitterTriggers: Seq[QuestionnaireId]
}
