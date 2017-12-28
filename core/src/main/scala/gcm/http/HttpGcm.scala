package gcm.http

import akka.actor.ActorSystem
import spray.client.pipelining._
import spray.http.ContentTypes._
import spray.http.HttpMethods._
import spray.http._
import spray.httpx.TransformerAux._
import util.Utils._
import scala.concurrent.Future

trait SendPushNotification {
  def send(msg: Message): Future[HttpResponse]
  def isApiKeyValid: Boolean
}

class HttpGcm() extends SendPushNotification{

  val pipeline: HttpRequest => Future[HttpResponse] = {
    val actorSystem = ActorSystem("HttpGcm798720955034")
    addHeader("Authorization", "key=AIzaSyAHx4hWo79xfxEtaE5VVFjjmCcwwUEHYAk") ~>
      sendReceive(actorSystem, actorSystem.dispatcher)
  }


  private def jsonRequest(content: String) =
    HttpRequest(
      method = POST,
      uri = "https://gcm-http.googleapis.com/gcm/send",
      entity = HttpEntity(`application/json`, content)
    )

  def isApiKeyValid: Boolean = {
    val req = pipeline(jsonRequest("""{"to": "ABC"}"""))
    req.awaitResult.status != StatusCodes.Unauthorized
  }

  def send(msg: Message): Future[HttpResponse] = {
    pipeline(jsonRequest(msg.toJsonString))
  }
}
