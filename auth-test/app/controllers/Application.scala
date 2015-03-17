package controllers

import play.api._
import play.api.mvc._
import org.pac4j.play._
import org.pac4j.play.scala._
import play.api.libs.json.Json
import org.pac4j.cas.profile._
import uk.co.g4me.cas.controllers.SecureController
import uk.co.g4me.cas.security.SecureHandler
import security.CustomHandler
import be.objectify.deadbolt.scala.DeadboltHandler
import uk.co.g4me.cas.security.DefaultSecureHandler

object Application extends SecureController {
  
  val deadboltHandler: DeadboltHandler = new DefaultSecureHandler

  def index = AuthenticatedAction { implicit request =>
    
    Ok(views.html.main("CAS Authentication Test App", views.html.tests(deadboltHandler)))
    
  }

}