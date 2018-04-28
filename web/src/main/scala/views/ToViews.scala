package views

import service.dto.{Question, Questionnaire, Trigger, User}

object ToViews {

  implicit class `Question -> QuestionView`(q: Question) {

    def toView: QuestionView = {
      QuestionView(q.id.getId, q.`type`, QuestionDataView(q.questionString, q.numOfOptions, q.radioOptions))
    }
  }

  implicit class `Questionnaire -> QuestionnaireView`(questionnaire: Questionnaire) {

    def toView(questions: Seq[Question]): QuestionnaireView = {
      QuestionnaireView(
        id = questionnaire.id.getId,
        name = questionnaire.name,
        isRegistration = questionnaire.isRegistration,
        isDefault = questionnaire.isDefault,
        questions = questions.collect{ case q if questionnaire.questions.contains(q.id) => q.toView }
      )
    }
  }

  implicit class `User -> UserView`(user: User) {

    def toView: UserView = {
      UserView(
        userId = user.userId.getId,
        userName = user.userName,
        gender = user.gender,
        age = user.age,
        limitQuestionnaire = user.limitQuestionnaire,
        image = user.fbProfilePicture
      )
    }
  }

  implicit class `Trigger -> TriggerView`(trigger: Trigger) {

    def toView(questionnaireName: String): TriggerView = {
      TriggerView(
        id = trigger.questionnaireId.getId,
        triggerName = trigger.triggerName,
        questionnaireName = questionnaireName,
        locationTrigger = trigger.locationTrigger,
        socialNetworkTrigger = trigger.socialNetworkTrigger,
        timeRangeTrigger = trigger.timeRangeTrigger)
    }
  }

}
