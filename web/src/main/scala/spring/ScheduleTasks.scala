package spring

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

  val repo = mutable.HashMap.empty[UserId, String]


  @Scheduled(fixedRate = 1000000)
  def checkForTriggers(): Unit = {
    checkForTweets()
    checkForLocation()
  }

  private def checkForTweets(): Unit = {
    println("Try to Get Tweets")
    thirdPartyService.usersLastTweet().foreach{ case (uId, tId) =>
      repo.get(uId) match {
        case None => repo += uId -> tId
        case Some(oldTweet) if oldTweet != tId =>
          repo += uId -> tId
          triggerService.getTwitterTriggers.foreach(questionsService.addQuestionnaireTo(uId, _))
          println("New Tweet!!")
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
