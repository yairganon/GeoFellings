package coreLogic.repos.mySql

import coreLogic.repos.NotificationTokenRepository

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class MySqlTokenRepo(template: NamedParameterJdbcTemplate) extends NotificationTokenRepository {

  override def addToken(token: String): Unit = ???

  override def getTokens: Seq[String] = ???
}
