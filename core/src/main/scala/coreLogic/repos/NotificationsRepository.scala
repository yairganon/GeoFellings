package coreLogic.repos

import enums.NotficationStatus
import util.{QuestionnaireId, UserId}

trait NotificationsRepository {

  def addNotificationTo(userId: UserId, questionnaireId: QuestionnaireId): Unit

  def completeNotification(userId: UserId, questionnaireId: QuestionnaireId): Unit

  def getUserNotifications(userId: UserId): Set[(QuestionnaireId, NotficationStatus)]

  def getPendingUserNotifications(userId: UserId): Set[QuestionnaireId]
}
