package coreLogic

import gcm.http.{FacebookPostData, FacebookRest, TwitterRest}
import service.api.ThirdPartyService
import service.dto.{FacebookToken, TwitterTokens}
import util.UserId

import scala.collection.mutable

class ThirdPartyFacade() extends ThirdPartyService{

  val twitterRepo = mutable.HashMap.empty[(UserId), TwitterTokens]
  val facebookRepo = mutable.HashMap.empty[(UserId), FacebookToken]

  override def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit =
    twitterRepo += userId -> twitterTokens

  override def tokens(userId: UserId): (Option[TwitterTokens], Option[FacebookToken]) = {
    (twitterRepo.get(userId), facebookRepo.get(userId))
  }

  override def storeFacebookToken(userId: UserId, facebookToken: FacebookToken): Unit = {
    facebookRepo += userId -> facebookToken
  }

  override def removeTwitterTokens(userId: UserId): Unit = twitterRepo.remove(userId)

  override def removeFacebookTokens(userId: UserId): Unit = facebookRepo.remove(userId)

  override def userLastPost(userId: UserId): Option[FacebookPostData] = {
    facebookRepo.get(userId).flatMap(token => {
      FacebookRest.lastPostOf(token.token)
    })
  }

  override def userTweet(userId: UserId): Option[String] = {
    for {
      userTokens <- tokens(userId)._1
      userId = userTokens.id
      userTweet <- TwitterRest.lastTweetOf(userId)
    } yield userTweet.text
  }

  override def usersLastPosts(): Seq[(UserId, FacebookPostData)] = {
    for {
      (userId, tokens) <- facebookRepo.toSeq
      post <- FacebookRest.lastPostOf(tokens.token)
    } yield (userId, post)
  }

  override def usersLastTweet(): Seq[(UserId, String)] = {
    for {
      (userId, tokens) <- twitterRepo.toSeq
      tweet <- TwitterRest.lastTweetOf(tokens.id)
    } yield (userId, tweet.id_str)
  }

}

