val scala3Version = "3.0.2"

val ver = new {
  val http4s         = "0.23.5"
  val catsEffect     = "3.2.9"
  val log4cats       = "2.1.1"
  val logback        = "1.2.5"
  val circe          = "0.14.1"
  val typesafeConfig = "1.4.1"
}

Global / excludeLintKeys += nativeImageVersion

lazy val root = project
  .in(file("."))
  .settings(
    name         := "fortune-fetcher",
    version      := "0.0.1-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl"          % ver.http4s,
      "org.http4s" %% "http4s-ember-client" % ver.http4s,
      "org.http4s" %% "http4s-circe"        % ver.http4s
    ),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "log4cats-core"   % ver.log4cats,
      "org.typelevel" %% "log4cats-slf4j"  % ver.log4cats,
      "ch.qos.logback" % "logback-classic" % ver.logback,
      "org.typelevel" %% "cats-effect"     % ver.catsEffect,
      "io.circe"      %% "circe-core"      % ver.circe,
      "io.circe"      %% "circe-generic"   % ver.circe,
      "com.typesafe"   % "config"          % ver.typesafeConfig
    ),
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    Compile / mainClass                  := Some("oen9.Main"),
    nativeImageVersion                   := "21.2.0",
    nativeImageAgentMerge                := true,
    nativeImageAgentOutputDir            := sourceDirectory.value / "graal",
    nativeImageOptions ++= Seq(
      s"-H:ReflectionConfigurationFiles=${sourceDirectory.value / "graal" / "reflect-config.json"}",
      s"-H:ResourceConfigurationFiles=${sourceDirectory.value / "graal" / "resource-config.json"}",
      "-H:+ReportExceptionStackTraces",
      "--no-fallback",
      "--allow-incomplete-classpath"
    )
  )
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(NativeImagePlugin)
