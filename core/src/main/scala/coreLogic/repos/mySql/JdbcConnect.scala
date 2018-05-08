package coreLogic.repos.mySql

import java.sql.Connection

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource

object JdbcConnect {

  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost/mysql"
  val username = "root"
  val password = "1q2w3e4r"
  var connection: Connection = _

  try {

    Class.forName(driver)
    val dataSource = new DriverManagerDataSource
    dataSource.setDriverClassName(driver)
    dataSource.setUrl(url)
    dataSource.setUsername(username)
    dataSource.setPassword(password)
    val storageTemplate: JdbcTemplate = new JdbcTemplate(dataSource)
    val schema = scala.io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("db/schema.sql")).mkString.split(";")
    schema.foreach(storageTemplate.update)
  } catch {
    case e: Throwable => e.printStackTrace()
  }
  connection.close()
}
