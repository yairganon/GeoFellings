package coreLogic.repos

import service.dto.Question

trait QuestionsRepository {

  def add(question: Question): Unit

  def getAll: Seq[Question]

}
