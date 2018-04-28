package gcm.http

import akka.actor.ActorSystem
import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken, Tweet}
import spray.client.pipelining.sendReceive
import spray.http.HttpMethods.GET
import spray.http.{HttpRequest, HttpResponse}
import util.Utils._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
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

object FacebookRest {

  val pipeline: HttpRequest => Future[HttpResponse] = {
    val actorSystem = ActorSystem("HttpGcm798720955034")
    sendReceive(actorSystem, actorSystem.dispatcher)
  }

  def lastPostOf(userToken: String): Option[FacebookPostData] = {
    val request = HttpRequest(
      method = GET,
      uri = s"https://graph.facebook.com/v2.12/me?fields=posts.limit(2)&access_token=$userToken")
    val eventualResponse = pipeline(request).map(data => {
      data.entity.asString.fromJsonString[FacebookResponse]
    })
    eventualResponse.awaitResult.posts.data.headOption
  }

  def profilePictureOf(userToken: String): Option[String] = Try{
    val request = HttpRequest(
      method = GET,
      uri = s"https://graph.facebook.com/v2.12/me?fields=picture.width(720).height(720)&access_token=$userToken")
    val eventualResponse = pipeline(request).map(data => {
      data.entity.asString.fromJsonString[FacebookPictureResponse]
    })
    eventualResponse.awaitResult.picture.data.url
  }.toOption
}

object Config {

  final val TwitterAppId = "yRm6u04SFsTgYxDMyqHSrTvWc"
  final val TwitterSecret = "pU1LYQ3o6kfyuDyOlexvckDmjr5EF6q7YE5teLRidsqA43Bkb3"
  final val TwitterAccessToken = "467656164-vAF2kaZUZ9v7NAfXUAZ9bXKRVzqkQnpIhPHeBXwp"
  final val TwitterSecretAccessTokenSecret = "iliTDk0EIDKDBGZAjHalqZPjRn2vmwuNoyVqo4eyaFaMq"
}


case class FacebookResponse(posts: FacebookPosts)

case class FacebookPosts(data: Seq[FacebookPostData])

case class FacebookPostData(id: String,
                            message: String)

case class FacebookPictureResponse(picture: FacebookPicture)

case class FacebookPicture(data: FacebookPictureData)

case class FacebookPictureData(url: String)