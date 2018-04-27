package service.api

import coreLogic.repos.inMemory.{InMemoryNotificationsRepo, InMemoryQuestionRepo, InMemoryUsersRepo}
import coreLogic.{QuestionsFacade, ThirdPartyFacade}
import enums.QuestionType
import org.specs2.mutable.Specification
import service.dto.{CreateQuestionData, CreateQuestionRequest}

class QuestionsServiceTest extends Specification {


  "QuestionsServiceTest" should {
    val service: QuestionsService = new QuestionsFacade(
      new InMemoryQuestionRepo,
      new ThirdPartyFacade,
      new InMemoryNotificationsRepo,
      new InMemoryUsersRepo)

    "addQuestion" in {
      val request = CreateQuestionRequest(QuestionType.OPEN, CreateQuestionData("Hey", None, None))
      service.addQuestion(request)
      service.getAll must haveSize(1)
    }

    "getAll" in {
      ok
    }

    "submit" in {
      ok
    }

    "submit$default$3" in {
      ok
    }

    "getWaitingQuestionnaireForUser" in {
      ok
    }

    "addQuestion" in {
      ok
    }

    "defaultQuestionnaire" in {
      ok
    }

    "getQuestionnaire" in {
      ok
    }

    "getUserQuestionnaires" in {
      ok
    }

    "registerQuestionnaire" in {
      ok
    }

    "getQuestionnaires" in {
      ok
    }

    "addQuestionnaireTo" in {
      ok
    }

    "addQuestionnaire" in {
      ok
    }

  }
}
