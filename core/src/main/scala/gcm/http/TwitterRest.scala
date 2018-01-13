package gcm.http

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken, Tweet}
import util.Utils._

import scala.util.Try

object TwitterRest {

  val consumerToken = ConsumerToken(key = Config.TwitterAppId, secret = Config.TwitterSecret)
  val accessToken = AccessToken(key = Config.TwitterAccessToken, secret = Config.TwitterSecretAccessTokenSecret)
  val restClient = TwitterRestClient(consumerToken, accessToken)

  def lastTweetOf(userId: Long): Option[Tweet] = {
    Try(restClient.userTimelineForUserId(userId, count = 1)
      .awaitResult
      .data
      .headOption).getOrElse(None)
  }
}

object Config {
  final val TwitterAppId = "yRm6u04SFsTgYxDMyqHSrTvWc"
  final val TwitterSecret = "pU1LYQ3o6kfyuDyOlexvckDmjr5EF6q7YE5teLRidsqA43Bkb3"
  final val TwitterAccessToken = "467656164-vAF2kaZUZ9v7NAfXUAZ9bXKRVzqkQnpIhPHeBXwp"
  final val TwitterSecretAccessTokenSecret = "iliTDk0EIDKDBGZAjHalqZPjRn2vmwuNoyVqo4eyaFaMq"
}