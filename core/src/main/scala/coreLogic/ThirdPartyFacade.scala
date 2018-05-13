package coreLogic

import coreLogic.repos.ThirdPartyTokensRepository
import gcm.http.{FacebookPostData, FacebookRest, TwitterRest}
import service.api.ThirdPartyService
import service.dto.{FacebookToken, FacebookTokenDo, TwitterTokens}
import util.UserId

class ThirdPartyFacade(repo: ThirdPartyTokensRepository) extends ThirdPartyService {

  override def storeTwitterTokens(userId: UserId, twitterTokens: TwitterTokens): Unit =
    repo.storeTwitterTokens(userId, twitterTokens)

  override def tokens(userId: UserId): (Option[TwitterTokens], Option[FacebookTokenDo]) = {
    repo.tokens(userId)
  }

  override def storeFacebookToken(userId: UserId, facebookToken: FacebookToken): Unit = {
    repo.storeFacebookToken(FacebookTokenDo(userId, facebookToken.token))
  }

  override def removeTwitterTokens(userId: UserId): Unit = repo.removeTwitterTokens(userId)

  override def removeFacebookTokens(userId: UserId): Unit = repo.removeFacebookTokens(userId)

  override def userLastPost(userId: UserId): Option[FacebookPostData] = {
    tokens(userId)._2.flatMap(token => {
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
      (userId, tokens) <- repo.allFacebookTokens
      post <- FacebookRest.lastPostOf(tokens.token)
    } yield (userId, post)
  }

  override def usersLastTweet(): Seq[(UserId, String)] = {
    for {
      (userId, tokens) <- repo.allTwitterTokens
      tweet <- TwitterRest.lastTweetOf(tokens.id)
    } yield (userId, tweet.id_str)
  }

}

