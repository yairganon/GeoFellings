package service.api

import service.dto.CreateTriggerRequest
import util.QuestionnaireId

trait TriggerService {

  def addTrigger(request: CreateTriggerRequest): Unit

  def getTwitterTriggers: Seq[QuestionnaireId]
}
