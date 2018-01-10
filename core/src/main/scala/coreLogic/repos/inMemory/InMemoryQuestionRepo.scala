package coreLogic.repos.inMemory

import coreLogic.repos.QuestionsRepository
import service.dto.{ Question, Questionnaire }
import util.{ QuestionId, QuestionnaireId }

import scala.collection.mutable

class InMemoryQuestionRepo extends QuestionsRepository {

  val questionRepo = mutable.HashMap.empty[QuestionId, Question]

  val questionnaireRepo = mutable.HashMap.empty[QuestionnaireId, Questionnaire]

  override def add(question: Question): Unit = questionRepo += question.id -> question

  override def getQuestions: Seq[Question] = questionRepo.values.toSeq

  override def add(questionnaire: Questionnaire): Unit = questionnaireRepo += questionnaire.id -> questionnaire

  override def getQuestionnaires: Seq[Questionnaire] = questionnaireRepo.values.toSeq
}
