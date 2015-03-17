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

object Application extends SecureController {
  
  def index = AuthenticatedAction { implicit request =>
    
    Ok(views.html.main("CAS Authentication Test App", views.html.tests(handler.asInstanceOf[DeadboltHandler])))
    
  }

}