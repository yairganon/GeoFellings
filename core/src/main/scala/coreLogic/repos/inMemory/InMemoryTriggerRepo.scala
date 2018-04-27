package coreLogic.repos.inMemory

import coreLogic.repos.TriggerRepository
import service.dto.{LocationTrigger, SocialNetworkTrigger, Trigger}
import util.QuestionnaireId

import scala.collection.mutable

class InMemoryTriggerRepo extends TriggerRepository {

  val repo = mutable.HashMap.empty[QuestionnaireId, Trigger]

  override def addTrigger(trigger: Trigger): Unit = {
    repo += trigger.questionnaireId -> trigger
  }

  override def getTwitterTriggers: Seq[QuestionnaireId] = {
    repo.collect{ case (id, Trigger(_, _, _, Some(SocialNetworkTrigger(true)), _)) => id }.toSeq
  }

  override def getLocationTriggers: Seq[(QuestionnaireId, LocationTrigger)] = {
    repo.collect{ case (id, Trigger(_, _, Some(locationTrigger), _, _)) => (id, locationTrigger) }.toSeq
  }

  override def getAll(): Seq[Trigger] = repo.values.toSeq

  override def remove(questionnaireId: QuestionnaireId): Unit = {
    repo.remove(questionnaireId)
  }
}
