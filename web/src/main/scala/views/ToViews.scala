package views

import service.dto.{ Question, Questionnaire }

object ToViews {

  implicit class `Question -> QuestionView`(q: Question) {
    def toView: QuestionView = {
      QuestionView(q.id.getId, q.`type`, QuestionDataView(q.questionString, q.numOfOptions))
    }
  }

  implicit class `Questionnaire -> QuestionnaireView`(questionnaire: Questionnaire) {
    def toView(questions: Seq[Question]): QuestionnaireView = {
      QuestionnaireView(
        questionnaire.id.getId,
        questionnaire.name,
        questionnaire.isRegistration,
        questionnaire.isDefault,
        questions.collect { case q if questionnaire.questions.contains(q.id) => q.toView }
      )
    }
  }
}
