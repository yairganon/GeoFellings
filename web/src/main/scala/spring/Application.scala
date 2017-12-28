package spring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

object Application extends App {
  SpringApplication.run(classOf[BootConfig])
}

@SpringBootApplication
class BootConfig