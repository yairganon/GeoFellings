package stam

import org.springframework.boot.SpringApplication

class MyMain {

  val x: X = (_) => 5
}

trait X {

  def doX(x: Int): Int
}

object Application extends App {
  SpringApplication.run(classOf[BootConfig])
}

