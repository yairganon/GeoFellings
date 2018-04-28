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

  val twitterRepo = mutable.HashMap.empty[(UserId), TwitterTokens]
  val facebookRepo = mutable.HashMap.empty[(UserId), FacebookToken]

  override def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit =
    twitterRepo += userId -> twitterTokens

  override def tokens(userId: UserId): (Option[TwitterTokens], Option[FacebookToken]) = {
    (twitterRepo.get(userId), facebookRepo.get(userId))
  }

  override def removeTwitterTokens(userId: UserId): Unit = twitterRepo.remove(userId)

  override def removeFacebookTokens(userId: UserId): Unit = facebookRepo.remove(userId)

  override def userTweet(userId: UserId): Option[String] = {
    for {
      userTokens <- tokens(userId)._1
      userId = userTokens.id
      userTweet <- TwitterRest.lastTweetOf(userId)
    } yield userTweet.text
  }

  override def usersLastTweet(): Seq[(UserId, String)] = {
    for {
      (userId, tokens) <- twitterRepo.toSeq
      tweet <- TwitterRest.lastTweetOf(tokens.id)
    } yield (userId, tweet.id_str)
  }

  val pipeline: HttpRequest => Future[HttpResponse] = {
    val actorSystem = ActorSystem("HttpGcm798720955034")
    sendReceive(actorSystem, actorSystem.dispatcher)
  }

  override def storeFacebookToken(userId: UserId, facebookToken: FacebookToken): Unit = {
    facebookRepo += userId -> facebookToken
    val request = HttpRequest(
      method = GET,
      uri = s"https://graph.facebook.com/v2.12/me?fields=posts.limit(2)&access_token=${facebookToken.token}")
    pipeline(request).foreach(data => {
      println(data.entity.asString.fromJsonString[FacebookResponse])
    })
  }

}

case class FacebookResponse(posts: FacebookPosts)

case class FacebookPosts(data: Seq[FacebookPostData])

case class FacebookPostData(id: String,
                            message: String)
