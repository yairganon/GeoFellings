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
    val schema = scala.io.Source.fromURL(getClass.getResource("db/schema.sql")).mkString
    val statement = connection.createStatement()
    statement.executeLargeUpdate(schema)
  } catch {
    case e: Throwable => e.printStackTrace()
  }
  connection.close()
}
