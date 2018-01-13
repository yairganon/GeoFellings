package spring.controllers

import com.danielasfregola.twitter4s.{TwitterRestClient, TwitterStreamingClient}
import com.danielasfregola.twitter4s.entities.streaming.StreamingMessage
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken, Tweet}
import spring.Config


object TwitterStream {
  val consumerToken = ConsumerToken(key = Config.TwitterAppId, secret = Config.TwitterSecret)
  val accessToken = AccessToken(key = Config.TwitterAccessToken, secret = Config.TwitterSecretAccessTokenSecret)
  val streamingClient = TwitterStreamingClient(consumerToken, accessToken)
  val restClient = TwitterRestClient(consumerToken, accessToken)

  def printTweetText: PartialFunction[StreamingMessage, Unit] = {
    case tweet: Tweet => println(tweet.text)
  }

  val s: Seq[Long] = Seq(467656164L)
  streamingClient.siteEvents(follow = s)(printTweetText)

}
