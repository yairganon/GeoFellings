package coreLogic

import coreLogic.repos.{NotificationsRepository, QuestionsRepository, UsersRepository}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import service.api.{QuestionsService, ThirdPartyService}
import service.dto._
import util.{QuestionId, QuestionnaireId, UserId}

class QuestionsFacade(questionsRepository: QuestionsRepository,
                      thirdPartyFacade: ThirdPartyService,
                      notificationsRepository: NotificationsRepository,
                      usersRepository: UsersRepository) extends QuestionsService {

  override def addQuestion(questionRequest: CreateQuestionRequest): QuestionId = {
    val question = Question(
      `type` = questionRequest.questionType,
      questionString = questionRequest.data.questionString,
      numOfOptions = questionRequest.data.numOfOptions,
      radioOptions = questionRequest.data.radioOptions
    )
    questionsRepository.add(question)
  }

  def updateQuestionnaire(questionnaireId: QuestionnaireId, request: UpdateQuestionreRequest): Unit = {
    val questionnaire = getQuestionnaire(questionnaireId)
    questionsRepository.add(questionnaire.copy(
      isDefault = request.isDefault.getOrElse(questionnaire.isDefault),
      isRegistration = request.isRegistration.getOrElse(questionnaire.isRegistration)))
  }

  override def getAll: Seq[Question] = questionsRepository.getQuestions

  override def addQuestionnaire(request: CreateQuestionnaireRequest): QuestionnaireId = {
    val id = QuestionnaireId.random
    questionsRepository
      .add(Questionnaire(
        id = id,
        name = request.name,
        isRegistration = request.isRegistration,
        isDefault = request.isDefault,
        questions = request.ids
      ))
    id
  }

  override def getQuestionnaires: Seq[Questionnaire] = {
    questionsRepository.getQuestionnaires
  }

  override def registerQuestionnaire: Option[Questionnaire] = questionsRepository.registerQuestionnaire

  override def defaultQuestionnaire: Option[Questionnaire] = questionsRepository.defaultQuestionnaire

  override def submit(userId: UserId, request: QuestionnaireAnswerRequest, submitTime: DateTime): Unit = {
    notificationsRepository.completeNotification(userId, request.id)
    questionsRepository.submit(QuestionnaireAnswer(
      userId,
      request.id,
      submitTime,
      request.location,
      request.answers,
      thirdPartyFacade.userTweet(userId),
      thirdPartyFacade.userLastPost(userId)
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
          questionnaireAnswer.lastPost,
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

  override def addQuestionnaireTo(userId: UserId, questionnaireId: QuestionnaireId): Unit = {
    usersRepository.get(userId).limitQuestionnaire match {
      case Some(limit) if notificationsRepository.getPendingUserNotifications(userId).size >= limit =>
      case _ =>
        notificationsRepository.addNotificationTo(userId, questionnaireId)
    }
  }

  override def getWaitingQuestionnaireForUser(userId: UserId): Set[QuestionnaireId] = {
    notificationsRepository.getPendingUserNotifications(userId)
  }

  override def getQuestionnaire(questionnaireId: QuestionnaireId): Questionnaire = {
    questionsRepository.getQuestionnaire(questionnaireId)
  }
}
