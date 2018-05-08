package coreLogic.repos.mySql
//
//import org.springframework.jdbc.core.JdbcTemplate
//import org.springframework.jdbc.datasource.DriverManagerDataSource

object JdbcConnect {
//
//  val driver = "com.mysql.jdbc.Driver"
//  val url = "jdbc:mysql://localhost/mysql"
//  val username = "root"
//  val password = "1q2w3e4r"
//
//  try {
//    val schema = scala.io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("db/schema.sql"))
//      .mkString.replace("\n", "")
//      .split(";")
//      .filter(_.nonEmpty)
//    Class.forName(driver)
//    val dataSource = new DriverManagerDataSource
//    dataSource.setDriverClassName(driver)
//    dataSource.setUrl(url)
//    dataSource.setUsername(username)
//    dataSource.setPassword(password)
//    val storageTemplate: JdbcTemplate = new JdbcTemplate(dataSource)
//
//    schema.foreach(storageTemplate.execute)
//  } catch {
//    case e: Throwable =>
//      println("*" * 200)
//      e.printStackTrace()
//      println("*" * 200)
//  }
}
