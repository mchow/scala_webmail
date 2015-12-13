package com.webmail

import javax.inject.Inject

import com.typesafe.config.ConfigFactory
import play.api.libs.ws.{WSAuthScheme, WSClient}
import play.api.Play.current

import scala.io.Source

object WebMail {
  val API_KEY = ConfigFactory.load().getString("mailgun.apikey")
  val BASE_URI = ConfigFactory.load().getString("mailgun.baseuri")

}

class WebMail @Inject() (ws: WSClient) {

  def sendMail(to:String, from:String, subject:String, body:String) = {

    val formData = Map("to" -> Seq(to),
                        "subject" -> Seq(subject),
                        "html" -> Seq(body),
                        "from" -> Seq(from) )

    val client = ws.url(s"${WebMail.BASE_URI}/messages")
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