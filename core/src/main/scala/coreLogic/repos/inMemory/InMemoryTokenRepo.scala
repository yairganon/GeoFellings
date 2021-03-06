package coreLogic.repos.inMemory

import coreLogic.repos.NotificationTokenRepository

import scala.collection.mutable

class InMemoryTokenRepo extends NotificationTokenRepository {

  val tokens: mutable.Set[String] = mutable.Set[String]()

  override def addToken(token: String): Unit = tokens += token

  override def getTokens: Seq[String] = tokens.toSeq
}
