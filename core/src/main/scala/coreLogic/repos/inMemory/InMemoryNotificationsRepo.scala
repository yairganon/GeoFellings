package coreLogic.repos.inMemory

import coreLogic.repos.NotificationsRepository
import enums.NotficationStatus
import enums.NotficationStatus._
import util.{QuestionnaireId, UserId}

import scala.collection.mutable

class InMemoryNotificationsRepo extends NotificationsRepository {

  val userQuestionnaireRepo = mutable.HashMap.empty[UserId, Set[(QuestionnaireId, NotficationStatus)]]

  override def addNotificationTo(userId: UserId, questionnaireId: QuestionnaireId): Unit = {
    userQuestionnaireRepo.get(userId) match {
      case None =>
        userQuestionnaireRepo += userId -> Set((questionnaireId, NotRead))
      case Some(set) =>
        userQuestionnaireRepo += userId -> (set ++ Set((questionnaireId, NotRead)))

    }
  }

  override def completeNotification(userId: UserId, questionnaireId: QuestionnaireId): Unit = {
    val newSet = userQuestionnaireRepo(userId).filterNot(_._1 == questionnaireId) ++ Set((questionnaireId, Read))
    userQuestionnaireRepo += userId -> newSet
  }

  override def getUserNotifications(userId: UserId): Set[(QuestionnaireId, NotficationStatus)] = {
    userQuestionnaireRepo.getOrElse(userId, Set.empty)
  }

  override def getPendingUserNotifications(userId: UserId): Set[QuestionnaireId] = {
    getUserNotifications(userId).collect{case (id, NotRead) => id}
  }
}
