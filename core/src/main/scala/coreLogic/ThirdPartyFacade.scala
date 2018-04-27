package coreLogic

import akka.actor.ActorSystem
import gcm.http.TwitterRest
import service.api.ThirdPartyService
import service.dto.{FacebookToken, TwitterTokens}
import spray.client.pipelining.sendReceive
import spray.http.HttpMethods.GET
import spray.http.{HttpRequest, HttpResponse}
import util.UserId
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable
import scala.concurrent.Future
import util.Utils._

class ThirdPartyFacade() extends ThirdPartyService{

  val repo = mutable.HashMap.empty[(UserId), TwitterTokens]

  override def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit =
    repo += userId -> twitterTokens

  override def twitterTokens(userId: UserId): Option[TwitterTokens] = repo.get(userId)

  override def removeTokens(userId: UserId): Unit = repo.remove(userId)

  override def userTweet(userId: UserId): Option[String] = {
    for {
      userTokens <- twitterTokens(userId)
      userId = userTokens.id
      userTweet <- TwitterRest.lastTweetOf(userId)
    } yield userTweet.text
  }

  override def usersLastTweet(): Seq[(UserId, String)] = {
    for {
      (userId, tokens) <- repo.toSeq
      tweet <- TwitterRest.lastTweetOf(tokens.id)
    } yield (userId, tweet.id_str)
  }

  val pipeline: HttpRequest => Future[HttpResponse] = {
    val actorSystem = ActorSystem("HttpGcm798720955034")
    sendReceive(actorSystem, actorSystem.dispatcher)
  }

  override def storeFacebookToken(facebookToken: FacebookToken): Unit = {
    val request = HttpRequest(
      method = GET,
      uri = s"https://graph.facebook.com/v2.12/me?fields=posts.limit(2)&access_token=${facebookToken.token}")
    pipeline(request).foreach(data => {
      println(data.entity.fromJsonString[FacebookResponse])
    })
  }
}

case class FacebookResponse(posts: FacebookPosts)

case class FacebookPosts(data: Seq[FacebookPostData])

case class FacebookPostData(id: String,
                            message: String)
