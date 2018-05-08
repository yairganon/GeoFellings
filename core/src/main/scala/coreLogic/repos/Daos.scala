package coreLogic.repos

trait Daos {

  val notificationsRepository: NotificationsRepository
  val questionsRepository: QuestionsRepository
  val notificationTokenRepository: NotificationTokenRepository
  val triggerRepository: TriggerRepository
  val usersRepository: UsersRepository
  val thirdPartyTokensRepository: ThirdPartyTokensRepository
}
