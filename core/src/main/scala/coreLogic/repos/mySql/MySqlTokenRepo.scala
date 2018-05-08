package coreLogic.repos.mySql

import coreLogic.repos.NotificationTokenRepository
import org.springframework.jdbc.core.JdbcTemplate

class MySqlTokenRepo(template: JdbcTemplate) extends NotificationTokenRepository {

  override def addToken(token: String): Unit = ???

  override def getTokens: Seq[String] = ???
}
