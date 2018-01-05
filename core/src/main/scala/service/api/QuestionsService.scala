package service.api

import service.dto.{CreateQuestionRequest, Question}

trait QuestionsService {

  def getAll: Seq[Question]

  def addQuestion(question: CreateQuestionRequest): Unit
}


