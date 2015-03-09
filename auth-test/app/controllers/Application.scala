package controllers

import play.api._
import play.api.mvc._
import org.pac4j.play._
import org.pac4j.play.scala._
import play.api.libs.json.Json
import org.pac4j.cas.profile._

object Application extends ScalaController {

  def index = RequiresAuthentication("CasClient") { profile =>
    Action { request =>
      Ok(views.html.main("CAS Authentication Test App", views.html.tests(profile.asInstanceOf[CasProfile])))
	}
  }

}