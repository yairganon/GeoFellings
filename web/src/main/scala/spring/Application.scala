package spring

import coreLogic.repos.mySql.JdbcConnect
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

object Application extends App {
  JdbcConnect
  SpringApplication.run(classOf[BootConfig])
}

@SpringBootApplication
@EnableScheduling
class BootConfig