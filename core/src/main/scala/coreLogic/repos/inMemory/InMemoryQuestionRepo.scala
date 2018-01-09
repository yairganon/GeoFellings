package coreLogic.repos.inMemory

import coreLogic.repos.QuestionsRepository
import service.dto.Question
import util.QuestionId

import scala.collection.mutable

class InMemoryQuestionRepo extends QuestionsRepository {

  val repo = mutable.HashMap.empty[QuestionId, Question]

  override def add(question: Question): Unit = repo += question.id -> question

  override def getAll: Seq[Question] = repo.values.toSeq
}
