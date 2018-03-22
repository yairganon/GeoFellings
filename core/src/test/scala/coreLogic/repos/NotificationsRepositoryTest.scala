package coreLogic.repos

import coreLogic.repos.inMemory.InMemoryNotificationsRepo
import enums.NotficationStatus
import org.specs2.mutable.Specification
import util.{QuestionnaireId, UserId}

class NotificationsRepositoryTest extends Specification {

  "NotificationsRepositoryTest" should {

    val repo = new InMemoryNotificationsRepo
    val userId = UserId.random

    "addNotificationTo and completeNotification" in {
      val questionnaireId = QuestionnaireId.random
      val questionnaireId1 = QuestionnaireId.random

      repo.addNotificationTo(userId, questionnaireId)
      repo.addNotificationTo(userId, questionnaireId1)

      repo.getUserNotifications(userId) === Set((questionnaireId, NotficationStatus.NotRead), (questionnaireId1, NotficationStatus.NotRead))

      repo.completeNotification(userId, questionnaireId)

      repo.getUserNotifications(userId) === Set((questionnaireId, NotficationStatus.Read), (questionnaireId1, NotficationStatus.NotRead))

      repo.getPendingUserNotifications(userId) === Set(questionnaireId1)
    }

  }
}
