package gcm.http


import org.specs2.mutable.Specification

class HttpGcmTest extends Specification {

  "HttpGcmTest" should {
    "isApiKeyValid" in {
      new HttpGcm().isApiKeyValid === true
    }

  }
}
