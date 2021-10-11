package oen9

import cats.effect.Sync
import cats.implicits._
import com.typesafe.config.ConfigFactory
import org.http4s.Uri

import AppConfig._

case class AppConfig(fortune: Fortune)

object AppConfig:
  case class Fortune(baseUri: Uri)

  def load[F[_]: Sync](): F[AppConfig] = for {
    cfg           <- Sync[F].delay(ConfigFactory.load())
    fortuneCfg    <- Sync[F].delay(cfg.getConfig("fortune"))
    baseUriString <- Sync[F].delay(fortuneCfg.getString("baseUri"))
    baseUri       <- Uri.fromString(baseUriString).pure[F].rethrow
    fortune = Fortune(baseUri = baseUri)
  } yield AppConfig(fortune)
