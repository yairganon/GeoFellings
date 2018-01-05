package service.api

import gcm.http.{ Messages, SendPushNotification }

trait NotificationTokenRepository {

  def addToken(token: String): Unit
  def getTokens: Seq[String]
}

class NotificationService(
  tokenRepo: NotificationTokenRepository,
    sendPushNotification: SendPushNotification
) {

  def sendToAllDummy(): Unit = {
    tokenRepo.getTokens.foreach(token => sendPushNotification.send(Messages.sendToSync(token)))
  }
}

