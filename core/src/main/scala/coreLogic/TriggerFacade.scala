package coreLogic

import coreLogic.repos.TriggerRepository
import service.api.TriggerService
import service.dto.{CreateTriggerRequest, Trigger}
import util.QuestionnaireId

class TriggerFacade(triggerRepository: TriggerRepository) extends TriggerService {

  override def addTrigger(request: CreateTriggerRequest): Unit = {
    triggerRepository.addTrigger(Trigger(
      triggerName = request.triggerName,
      questionnaireId = request.questionnaireId,
      locationTrigger = request.locationTrigger,
      socialNetworkTrigger = request.socialNetworkTrigger,
      timeRangeTrigger = request.timeRangeTrigger
    ))
  }

  override def getTwitterTriggers: Seq[QuestionnaireId] = {
    triggerRepository.getTwitterTriggers
  }
}
