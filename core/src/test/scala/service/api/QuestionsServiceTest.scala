package service.api

import coreLogic.QuestionsFacade
import coreLogic.repos.inMemory.InMemoryQuestionRepo
import enums.QuestionType
import org.specs2.mutable.Specification
import service.dto.{CreateQuestionData, CreateQuestionRequest}

class QuestionsServiceTest extends Specification {

  "QuestionsServiceTest" should {
    val service: QuestionsService = new QuestionsFacade(new InMemoryQuestionRepo)

    "addQuestion" in {
      val request = CreateQuestionRequest(QuestionType.OPEN, CreateQuestionData("Hey", None))
      service.addQuestion(request)
      service.getAll must haveSize(1)
    }
  }
}
