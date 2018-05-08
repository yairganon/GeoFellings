import Dependencies._


lazy val springVersion = "1.5.3.RELEASE"
lazy val thymeleafVersion = "2.1.5.RELEASE"

inThisBuild(List(
  scalaVersion := "2.11.8",
  version := "0.1"
))

lazy val core = (project in file("core"))
  .settings(
    name := "core",
    libraryDependencies ++= {
      val akkaV = "2.3.9"
      val sprayV = "1.3.3"
      Seq(
        "io.spray" %% "spray-client" % sprayV,
        "io.spray" %% "spray-can" % sprayV,
        "io.spray" %% "spray-routing" % sprayV,
        "io.spray" %% "spray-testkit" % sprayV % "test",
        "com.typesafe.akka" %% "akka-actor" % akkaV,
        "com.typesafe.akka" %% "akka-testkit" % akkaV % "test"
      )
    },
//    libraryDependencies += "org.springframework" % "spring-jdbc" % "2.0.6",
    libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.16",
    libraryDependencies += "com.danielasfregola" %% "twitter4s" % "5.3",
    libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7",
    libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.7",
    libraryDependencies += "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.8.7",
    libraryDependencies += "com.google.firebase" % "firebase-admin" % "5.6.0",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "4.0.0" % "test")
  )

lazy val web = (project in file("web"))
  .dependsOn(core)
  .settings(
    name := "web",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-web" % springVersion,
    libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "4.0.0" % "test"))

lazy val root = (project in file(".")).aggregate(core, web)

scalacOptions in Test ++= Seq("-Yrangepos")

