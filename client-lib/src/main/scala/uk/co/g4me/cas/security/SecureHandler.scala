package uk.co.g4me.cas.security

import scala.concurrent.Future
import play.api.mvc.{Request, Result}
import uk.co.g4me.cas.models.CasUser
import uk.co.g4me.cas.controllers.AuthenticatedRequest
import be.objectify.deadbolt.scala.DeadboltHandler

trait SecureHandler {
  
  def beforeAuthenticationCheck[A](request: Request[A]): Option[Future[Result]]  
  
}