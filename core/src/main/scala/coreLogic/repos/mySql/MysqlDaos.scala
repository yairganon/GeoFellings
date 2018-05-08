package coreLogic.repos.mySql

import coreLogic.repos._
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource

import scala.io.Source.fromInputStream


object MysqlDaos {

  private var dao: Option[MySqlDaosImpl] = None

  def getInstance(): Daos = {
    dao.getOrElse{
      val newInstance = new MySqlDaosImpl
      dao = Some(newInstance)
      newInstance
    }
  }

  private class MySqlDaosImpl extends Daos {

    private val driver = "com.mysql.jdbc.Driver"
    private val url = "jdbc:mysql://localhost/mysql"
    private val username = "root"
    private val password = "1q2w3e4r"
    private val dataSource = {
      val builder = new DriverManagerDataSource
      builder.setDriverClassName(driver)
      builder.setUrl(url)
      builder.setUsername(username)
      builder.setPassword(password)
      builder
    }
    private val schema = fromInputStream(getClass.getClassLoader.getResourceAsStream("db/schema.sql"))
      .mkString
      .replace("\n", "")
      .split(";")
      .filter(_.nonEmpty)
    private val storageTemplate = new JdbcTemplate(dataSource)
    schema.foreach(storageTemplate.execute)

    val notificationsRepository = new MySqlNotificationsRepo(storageTemplate)
    val questionsRepository = new MySqlQuestionRepo(storageTemplate)
    val notificationTokenRepository = new MySqlTokenRepo(storageTemplate)
    val triggerRepository = new MySqlTriggerRepo(storageTemplate)
    val usersRepository = new MySqlUsersRepo(storageTemplate)
    val thirdPartyTokensRepository = new MySqlThirdPartyTokensRepo(storageTemplate)
  }

}
