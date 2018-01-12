package coreLogic

import coreLogic.repos.QuestionsRepository
import service.api.QuestionsService
import service.dto.{ CreateQuestionRequest, CreateQuestionnaireRequest, Question, Questionnaire }
import util.QuestionnaireId

class QuestionsFacade(questionsRepository: QuestionsRepository) extends QuestionsService {

  override def addQuestion(questionRequest: CreateQuestionRequest): Unit = {
    val question = Question(
      `type` = questionRequest.questionType,
      questionString = questionRequest.data.questionString,
      numOfOptions = questionRequest.data.numOfOptions
    )
    questionsRepository.add(question)
  }

  override def getAll: Seq[Question] = questionsRepository.getQuestions

  override def addQuestionnaire(request: CreateQuestionnaireRequest): Unit = {
    questionsRepository
      .add(Questionnaire(
        id = QuestionnaireId.random,
        name = request.name,
        isRegistration = false,
        isDefault = false,
        questions = request.ids
      ))
  }

  override def getQuestionnaires: Seq[Questionnaire] = {
    questionsRepository.getQuestionnaires
  }

  override def registerQuestionnaire: Option[Questionnaire] = questionsRepository.registerQuestionnaire
}
