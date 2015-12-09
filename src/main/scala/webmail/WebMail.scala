package webmail

import com.typesafe.config.{Config, ConfigFactory}
import play.api.libs.ws._
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global


object WebMail {
  def main(args:Array[String]):Unit = {
    val mail = new WebMail

    //todo : string/json validation
    val input = args(0)
    println(input)
    val json: JsValue = Json.parse(input.toString())
    //todo to, from, body checking or catch NoSuchElementException and do something
    val to = (json \ "to").as[String]
    val subject = (json \ "subject").as[String]
    val body = (json \ "body").as[String]
    val from = "Excited User <mailgun@sandbox4bc32b23f7044d5e8ec59628029ae496.mailgun.org>"

    for {
      response <- mail.sendMail(to, from, subject, body)
    } yield {
      System.exit(1)
    }
  }

  //todo : load from application.properties
  val API_KEY = ""
  val BASE_URI = ""

  val client = {
    val builder = new com.ning.http.client.AsyncHttpClientConfig.Builder()
    new play.api.libs.ws.ning.NingWSClient(builder.build())
  }
}

class WebMail {

  def sendMail(to:String, from:String, subject:String, body:String) = {

    val formData = Map("to" -> Seq(to),
                        "subject" -> Seq(subject),
                        "text" -> Seq(body),
                        "from" -> Seq(from) )

    WebMail.client.url(WebMail.BASE_URI)
      .withHeaders("Accept" -> "multipart/form-data")
      .withAuth("api", WebMail.API_KEY, WSAuthScheme.BASIC)
      .post(formData)

  }

}