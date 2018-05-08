package coreLogic.repos.mySql

import java.sql.DriverManager
import java.sql.Connection

object JdbcConnectSelect {

  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost/mysql"
  val username = "root"
  val password = "1q2w3e4r"
  var connection: Connection = null

  try {
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)

    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT host, user FROM user")
    while(resultSet.next()) {
      val host = resultSet.getString("host")
      val user = resultSet.getString("user")
      println("host, user = " + host + ", " + user)
    }
  } catch {
    case e => e.printStackTrace
  }
  connection.close()
}
