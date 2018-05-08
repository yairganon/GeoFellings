package coreLogic.repos.mySql

import coreLogic.repos.NotificationsRepository
import enums.NotficationStatus
import org.springframework.jdbc.core.JdbcTemplate
import util.{QuestionnaireId, UserId}

class MySqlNotificationsRepo(template: JdbcTemplate) extends NotificationsRepository {

  override def addNotificationTo(userId: UserId, questionnaireId: QuestionnaireId): Unit = ???

  override def completeNotification(userId: UserId, questionnaireId: QuestionnaireId): Unit = ???

  override def getUserNotifications(userId: UserId): Set[(QuestionnaireId, NotficationStatus)] = ???

  override def getPendingUserNotifications(userId: UserId): Set[QuestionnaireId] = ???
}
