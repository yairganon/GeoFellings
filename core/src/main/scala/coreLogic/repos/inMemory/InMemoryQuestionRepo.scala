package coreLogic.repos.inMemory

import coreLogic.repos.QuestionsRepository
import service.dto.{Question, Questionnaire, QuestionnaireAnswer}
import util.{QuestionId, QuestionnaireId, UserId}

import scala.collection.mutable
import scala.util.Random

class InMemoryQuestionRepo extends QuestionsRepository {

  val questionRepo = mutable.HashMap.empty[QuestionId, Question]

  val userQuestionnaireRepo = mutable.HashMap.empty[UserId, QuestionnaireId]

  val questionnaireRepo = mutable.HashMap.empty[QuestionnaireId, Questionnaire]

  val questionnaireAnswerRepo = mutable.HashMap.empty[(UserId,QuestionnaireId) , QuestionnaireAnswer]

  override def add(question: Question): Unit = questionRepo += question.id -> question

  override def getQuestions: Seq[Question] = questionRepo.values.toSeq

  override def add(questionnaire: Questionnaire): Unit = questionnaireRepo += questionnaire.id -> questionnaire

  override def getQuestionnaires: Seq[Questionnaire] = questionnaireRepo.values.toSeq

  override def registerQuestionnaire: Option[Questionnaire] = questionnaireRepo.values.find(_.isRegistration)

  override def defaultQuestionnaire: Option[Questionnaire] = questionnaireRepo.values.find(_.isDefault)

  override def submit(questionnaireAnswer: QuestionnaireAnswer): Unit = {
    if(userQuestionnaireRepo
      .get(questionnaireAnswer.userId)
      .contains(questionnaireAnswer.questionnaireId)){
      userQuestionnaireRepo.remove(questionnaireAnswer.userId)
    }
    questionnaireAnswerRepo += (questionnaireAnswer.userId, questionnaireAnswer.questionnaireId) -> questionnaireAnswer
  }

  override def getAnswers(userId: UserId): Seq[QuestionnaireAnswer] =
    questionnaireAnswerRepo.collect{case ((`userId`, _), q) => q}.toSeq

  override def getQuestionnaire(questionnaireId: QuestionnaireId): Questionnaire = questionnaireRepo(questionnaireId)

  override def getQuestion(questionId: QuestionId): Question = questionRepo(questionId)

  override def addQuestionnaireTo(userId: UserId): Unit = {
    Random.shuffle(questionnaireRepo.keySet)
      .headOption.foreach(id => userQuestionnaireRepo += userId -> id)
  }

  override def getWaitingQuestionnaireFor(userId: UserId): Option[QuestionnaireId] = userQuestionnaireRepo.get(userId)
}
