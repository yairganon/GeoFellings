package gcm.http

case class Message(
  to: String,
  notification: Notification = Notification()
)

object Messages {

  def sendToSync(to: String) = Message(to)

}

case class Notification(
  title: String = "Ronnen",
  body: String = "This is Body",
  icon: String = "myicon"
)