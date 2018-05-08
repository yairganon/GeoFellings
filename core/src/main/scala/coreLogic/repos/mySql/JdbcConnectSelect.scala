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
    val resultSet = statement.executeQuery("CREATE DATABASE IF NOT EXISTS `geoFeelings`;\n\nCREATE TABLE IF NOT " +
      "EXISTS `geoFeelings.questions` (\n  `questionId` varchar(50) NOT NULL,\n  `data` mediumblob,\n  " +
      "`creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n  `update_date` timestamp NOT NULL DEFAULT " +
      "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n  PRIMARY KEY (`questionId`)\n) ENGINE=InnoDB DEFAULT " +
      "CHARSET=utf8;")
    while(resultSet.next()) {
      val host = resultSet.getString("host")
      val user = resultSet.getString("user")
      println("host, user = " + host + ", " + user)
    }
  } catch {
    case e: Throwable => e.printStackTrace()
  }
  connection.close()
}
