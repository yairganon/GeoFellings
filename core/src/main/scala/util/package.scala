import java.util.UUID

import service.dto.{Question, Questionnaire, User}

package object util {
  type QuestionId = Guid[Question]
  object QuestionId {
    def apply(id: String): QuestionId = new QuestionId(id)
    def random: Guid[Question] = new Guid[Question](UUID.randomUUID)
  }

  type UserId = Guid[User]
  object UserId {
    def apply(id: String): UserId = new UserId(id)
    def random: Guid[User] = new Guid[User](UUID.randomUUID)
  }

  type QuestionnaireId = Guid[Questionnaire]
  object QuestionnaireId {
    def apply(id: String): QuestionnaireId = new QuestionnaireId(id)
    def random: Guid[Questionnaire] = new Guid[Questionnaire](UUID.randomUUID)
  }
}
