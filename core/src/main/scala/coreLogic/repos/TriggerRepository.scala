package coreLogic.repos

import service.dto.{LocationTrigger, Trigger}
import util.QuestionnaireId

trait TriggerRepository {

  def getAll(): Seq[Trigger]

  def addTrigger(trigger: Trigger): Unit

  def getTwitterTriggers: Seq[QuestionnaireId]

  def getLocationTriggers: Seq[(QuestionnaireId, LocationTrigger)]
}
