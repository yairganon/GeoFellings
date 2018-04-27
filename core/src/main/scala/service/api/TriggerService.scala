package service.api

import service.dto.{CreateTriggerRequest, Location, Trigger}
import util.QuestionnaireId

trait TriggerService {

  def addTrigger(request: CreateTriggerRequest): Unit

  def getAll(): Seq[Trigger]

  def getTwitterTriggers: Seq[QuestionnaireId]

  def getLocationTriggersInRange(location: Location): Seq[QuestionnaireId]
}
