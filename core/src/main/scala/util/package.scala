import java.util.UUID

import service.dto.Question

package object util {
  type QuestionId = Guid[Question]
  object QuestionId {
    def apply(id: String): QuestionId = new QuestionId(id)
    def random: Guid[Question] = new Guid[Question](UUID.randomUUID)
  }
}
