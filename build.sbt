import Dependencies._


lazy val springVersion = "1.5.3.RELEASE"
lazy val thymeleafVersion = "2.1.5.RELEASE"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      scalaVersion := "2.12.4",
      version      := "0.1"
    )),
    name := "spring-boot-scala-web",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-web" % springVersion,
    libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "4.0.0" % "test")
  )

scalacOptions in Test ++= Seq("-Yrangepos")

mainClass in (Compile, run) := Some("stam.Application")
