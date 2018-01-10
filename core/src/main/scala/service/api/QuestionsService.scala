package service.api

import service.dto.{ CreateQuestionRequest, CreateQuestionnaireRequest, Question, Questionnaire }

trait QuestionsService {

  def getAll: Seq[Question]

  def addQuestion(question: CreateQuestionRequest): Unit

  def addQuestionnaire(request: CreateQuestionnaireRequest): Unit

  def getQuestionnaires: Seq[Questionnaire]
}

