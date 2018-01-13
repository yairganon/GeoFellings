package coreLogic

import coreLogic.repos.QuestionsRepository
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import service.api.{QuestionsService, ThirdPartyService}
import service.dto._
import util.{QuestionnaireId, UserId}

class QuestionsFacade(questionsRepository: QuestionsRepository,
                      thirdPartyFacade: ThirdPartyService) extends QuestionsService {

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
        isRegistration = request.isRegistration,
        isDefault = request.isDefault,
        questions = request.ids
      ))
  }

  override def getQuestionnaires: Seq[Questionnaire] = {
    questionsRepository.getQuestionnaires
  }

  override def registerQuestionnaire: Option[Questionnaire] = questionsRepository.registerQuestionnaire

  override def defaultQuestionnaire: Option[Questionnaire] = questionsRepository.defaultQuestionnaire

  override def submit(userId: UserId, request: QuestionnaireAnswerRequest, submitTime: DateTime): Unit = {
    questionsRepository.submit(QuestionnaireAnswer(
      userId,
      request.id,
      submitTime,
      request.location,
      request.answers,
      thirdPartyFacade.userTweet(userId)
    ))
  }

  override def getUserQuestionnaires(userId: UserId): Seq[QuestionnaireWithAnswersDto] = {
    questionsRepository
      .getAnswers(userId)
      .map(questionnaireAnswer => {
        val questionnaire = questionsRepository.getQuestionnaire(questionnaireAnswer.questionnaireId)
        QuestionnaireWithAnswersDto(
          questionnaire.id.getId,
          questionnaire.name,
          questionnaireAnswer.location,
          questionnaireAnswer.time.toString(DateTimeFormat.forPattern("d MMMM, yyyy 'at' HH:mm")),
          questionnaire.isRegistration,
          questionnaire.isDefault,
          questionnaireAnswer.lastTweet,
          questionnaireAnswer.answers.map(questionnaireAnswer => {
            val question = questionsRepository.getQuestion(questionnaireAnswer.id)
            QuestionWithAnswersDto(
              question.id.getId,
              question.`type`,
              question.questionString,
              question.numOfOptions,
              questionnaireAnswer.answer
            )
          })
        )
      }
      )
  }

  override def addQuestionnaireTo(userId: UserId): Unit = {
    questionsRepository.addQuestionnaireTo(userId)
  }

  override def getWaitingQuestionnaireForUser(userId: UserId): Option[QuestionnaireId] = {
    questionsRepository.getWaitingQuestionnaireFor(userId)
  }
}
