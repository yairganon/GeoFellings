package service.api

import service.dto.Question

trait QuestionsRepository {

  def add(question: Question): Unit

  def getAll: Seq[Question]

}
