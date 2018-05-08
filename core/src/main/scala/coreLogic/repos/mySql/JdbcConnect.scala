package coreLogic.repos.mySql

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource

import scala.io.Source._

object JdbcConnect {

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

}
