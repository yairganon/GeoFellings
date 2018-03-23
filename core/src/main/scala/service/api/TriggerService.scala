package service.api

import service.dto.{CreateTriggerRequest, Location}
import util.QuestionnaireId

trait TriggerService {

  def addTrigger(request: CreateTriggerRequest): Unit

  def getTwitterTriggers: Seq[QuestionnaireId]

  def getLocationTriggersInRange(location: Location): Seq[QuestionnaireId]
}
