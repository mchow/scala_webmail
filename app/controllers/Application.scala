package controllers

import javax.inject.Inject

import play.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{Json, JsValue, JsString, JsObject}
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.libs.ws._
import play.api.libs.json._
import play.api.Play.current
import com.webmail.WebMail

import scala.concurrent.Future

object Application extends Controller {
  val webmail = new WebMail(WS.client)

  def index = Action {
    Ok("hello world")
  }

  def sendEmail = Action.async(parse.json) { request =>

    try {
      val jsonBody = request.body
      val to = (jsonBody \ "to").as[String]
      val subject = (jsonBody \ "subject").as[String]
      val templateName = (jsonBody \ "template").as[String]
      val from = "Excited User <mailgun@sandbox4bc32b23f7044d5e8ec59628029ae496.mailgun.org>"

      val template = webmail.findReplaceTemplateVars(templateName)

      // todo : retryable exceptions with exponential backoff
      for {
        response <- webmail.sendMail(to, from, subject, template)
      } yield {
        Logger.debug(s"Response Status: ${response.status} ${response.body}")
        Ok(response.status.toString())
      }
    } catch {
      case e:JsResultException => Future(BadRequest(e.getMessage()))
    }
  }

  def getTemplates() = Action.async {
    Future(Ok(
      Json.obj(
        "templates" -> Json.toJson(webmail.getAvailableEmailTemplates())
          )))
  }

}