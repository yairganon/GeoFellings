package coreLogic

import service.api.{QuestionsRepository, QuestionsService}
import service.dto.{CreateQuestionRequest, Question}

class QuestionsFacade(questionsRepository: QuestionsRepository) extends QuestionsService {

  override def addQuestion(questionRequest: CreateQuestionRequest): Unit = {
    val question = Question(
      `type` = questionRequest.questionType,
      questionString = questionRequest.data.questionString,
      numOfOptions = questionRequest.data.numOfOptions
    )
    questionsRepository.add(question)
  }

  override def getAll: Seq[Question] = questionsRepository.getAll
}
