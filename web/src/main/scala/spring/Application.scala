package spring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

object Application extends App {
  SpringApplication.run(classOf[BootConfig])
}

@SpringBootApplication
class BootConfig

object Config {
  final val TwitterAppId = "yRm6u04SFsTgYxDMyqHSrTvWc"
  final val TwitterSecret = "pU1LYQ3o6kfyuDyOlexvckDmjr5EF6q7YE5teLRidsqA43Bkb3"
}