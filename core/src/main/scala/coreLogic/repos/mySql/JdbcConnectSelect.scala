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


    val schema = scala.io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("db/schema.sql")).mkString.split(";")
    val statement = connection.createStatement()
    schema.foreach(statement.executeUpdate)
  } catch {
    case e: Throwable => e.printStackTrace()
  }
  connection.close()
}
