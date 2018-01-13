package spring

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import service.api.{QuestionsService, ThirdPartyService}
import util.UserId

import scala.collection.mutable

@Component
class ScheduleTasks(thirdPartyService: ThirdPartyService,
                    questionsService: QuestionsService) {

  val repo = mutable.HashMap.empty[UserId, String]

  @Scheduled(fixedRate = 60000)
  def print(): Unit = {
    println("Try to Get Tweets")
    thirdPartyService.usersLastTweet().foreach{case (uId, tId) =>
      repo.get(uId) match {
        case None => repo += uId -> tId
        case Some(oldTweet) if oldTweet != tId =>
          repo += uId -> tId
          questionsService.addQuestionnaireTo(uId)
          println("New Tweet!!")
        case _ =>
      }
    }
  }
}
