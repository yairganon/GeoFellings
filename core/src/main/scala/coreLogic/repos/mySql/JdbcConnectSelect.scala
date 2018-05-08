package coreLogic.repos.mySql

import java.sql.DriverManager
import java.sql.Connection

object JdbcConnectSelect {

  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost/mysql"
  val username = "root"
  val password = "1q2w3e4r"
  var connection: Connection = _

  try {
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()
    statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `geoFeelings`;CREATE TABLE IF NOT " +
      "EXISTS `geoFeelings.questions` (`questionId` varchar(50) NOT NULL,  `data` mediumblob,  " +
      "`creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, `update_date` timestamp NOT NULL DEFAULT " +
      "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  PRIMARY KEY (`questionId`)) ENGINE=InnoDB DEFAULT " +
      "CHARSET=utf8;")
  } catch {
    case e: Throwable => e.printStackTrace()
  }
  connection.close()
}
