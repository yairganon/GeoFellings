package coreLogic

import coreLogic.repos.TriggerRepository
import service.api.TriggerService
import service.dto.{CreateTriggerRequest, Location, LocationTrigger, Trigger}
import util.{DistanceCalculator, QuestionnaireId}

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

  override def getLocationTriggersInRange(userLocation: Location): Seq[QuestionnaireId] = {
    triggerRepository
      .getLocationTriggers.collect{
            case (id, LocationTrigger(location, radius))  if isInRange(userLocation, location, radius) => id
    }
  }

  private def isInRange(userLocation: Location, location: Location, radius: Int) = {
    DistanceCalculator.calculateDistanceInKilometer(userLocation, location) <= radius
  }

  override def getAll(): Seq[Trigger] = {
    triggerRepository.getAll()
  }

  override def remove(questionnaireId: QuestionnaireId): Unit = {
    triggerRepository.remove(questionnaireId)
  }
}
