package oen9

import cats.effect._
import cats.effect.std.Console
import cats.implicits._
import io.circe.Codec
import org.http4s.circe.CirceEntityDecoder.*
import org.http4s.ember.client.EmberClientBuilder

object Main extends IOApp:
  case class Fortune(fortune: String) derives Codec.AsObject

  override def run(args: List[String]): IO[ExitCode] = for {
    appConfig <- AppConfig.load[IO]()
    _         <- app[IO](appConfig)
  } yield ExitCode.Success

  private def app[F[_]: Async: Console](appConfig: AppConfig): F[Unit] =
    EmberClientBuilder.default[F].build.use { case client =>
      for {
        result <- client.expect[Fortune](appConfig.fortune.baseUri)
        _      <- Console[F].println(result.fortune)
      } yield ()
    }

def msg = "I was compiled by Scala 3. :)"
