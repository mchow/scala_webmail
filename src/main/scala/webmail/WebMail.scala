package webmail

import com.typesafe.config.{Config, ConfigFactory}
import play.api.libs.ws._
import play.api.libs.json._
import scala.io.Source
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
    val templateName = (json \ "template").as[String]
    val from = "Excited User <mailgun@sandbox4bc32b23f7044d5e8ec59628029ae496.mailgun.org>"
    val template = mail.findReplaceTemplateVars(templateName)

    for {
      response <- mail.sendMail(to, from, subject, template)
    } yield {
      response.json
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
                        "html" -> Seq(body),
                        "from" -> Seq(from) )

    val client = WebMail.client.url(s"${WebMail.BASE_URI}/messages")
      .withHeaders("Accept" -> "multipart/form-data")
      .withRequestTimeout(10000)
      .withAuth("api", WebMail.API_KEY, WSAuthScheme.BASIC)

    client.post(formData)

  }

  def getTemplateByName(templateName:String): String = {
    //todo: get templates from database
    val source = Source.fromFile(s"public/html/${templateName}")
    try source.mkString finally source.close()
  }

  //todo: store templates in database
  def getAvailableEmailTemplates(): Array[String] = {
    Array("password_reset.html", "welcome_email.html")
  }

  //todo : interate through some html object structure
  def findReplaceTemplateVars(templateName:String) = {
    val html = getTemplateByName(templateName)
    val pattern = "\\$\\{[a-zA-Z]*\\}".r
    pattern.findAllMatchIn(html).foreach {
      print(_)
    }
    val firstName = getUserData("${firstName}")
    html.replaceAll("\\$\\{firstName\\}", firstName)
  }

  //todo: lookup from user database
  def getUserData(field:String) = {
    field match {
      case "${firstName}" => "Mark"
      case _ => ""
    }
  }

}