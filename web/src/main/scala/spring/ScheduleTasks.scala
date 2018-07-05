package spring

import gcm.http.FacebookPostData
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import service.api.{QuestionsService, ThirdPartyService, TriggerService, UserService}
import util.UserId

import scala.collection.mutable

@Component
class ScheduleTasks(thirdPartyService: ThirdPartyService,
                    questionsService: QuestionsService,
                    triggerService: TriggerService,
                    userService: UserService) {

  val userLastTweet = mutable.HashMap.empty[UserId, String]

  val userLastFacebookPost = mutable.HashMap.empty[UserId, String]


  @Scheduled(fixedRate = 20000)
  def checkForTriggers(): Unit = {
    checkForTweets()
    checkForLocation()
    checkForFacebookPosts()
  }

  private def checkForTweets(): Unit = {
    println("Try to Get Tweets")
    thirdPartyService.usersLastTweet().foreach{ case (uId, tId) =>
      userLastTweet.get(uId) match {
        case None => userLastTweet += uId -> tId
        case Some(oldTweet) if oldTweet != tId =>
          userLastTweet += uId -> tId
          triggerService.getTwitterTriggers.foreach(questionsService.addQuestionnaireTo(uId, _))
          println("New Tweet!!")
        case _ =>
      }
    }
  }

  private def checkForFacebookPosts(): Unit = {
    println("Try to Get Facebook posts")
    thirdPartyService.usersLastPosts().foreach{
      case (uId, FacebookPostData(id, _, _)) =>
        userLastFacebookPost.get(uId) match {
          case None => userLastFacebookPost += uId -> id
          case Some(oldPost) if oldPost != id =>
            userLastFacebookPost += uId -> id
            triggerService.getTwitterTriggers.foreach(questionsService.addQuestionnaireTo(uId, _))
          case _ =>
        }
    }
  }

  private def checkForLocation(): Unit = {
    userService.lastLocations()
      .foreach{case (uId, location) =>
        triggerService.getLocationTriggersInRange(location).foreach(questionsService.addQuestionnaireTo(uId, _))
      }
  }
}
