package uk.co.g4me.cas.security

import play.api.mvc.Request
import uk.co.g4me.cas.models.CasUser
import uk.co.g4me.cas.config.Constants
import scala.concurrent.Future
import play.api.mvc.Result
import uk.co.g4me.cas.controllers.AuthenticatedRequest
import org.pac4j.core.client.RedirectAction
import org.slf4j.LoggerFactory
import org.pac4j.play.CallbackController
import org.pac4j.play.scala.ScalaWebContext
import org.pac4j.cas.client.CasClient
import play.api.mvc.Session
import play.api.Play
import org.pac4j.core.context._
//import uk.co.g4me.cas.StorageHelper


class DefaultSecureHandler extends SecureHandler {
  
  protected val logger = LoggerFactory.getLogger("uk.co.g4me.cas.security.DefaultSecureHandler")
  
  // Return a user using the current session
  def getUser[A](request: Request[A]): Option[CasUser] = {
    val sessionId = request.session.get("pac4jSessionId")
      
    if (sessionId.isDefined) {
      //TODO: Session management
      // SessionManager.getUser(sessionId.get)
      logger.debug("SessionId: {}", sessionId)
      Some(new CasUser())
    } else {
      logger.debug("No session")
      None
    }
  }
  
  // Things to check before we attempt authentication
  def beforeAuthenticationCheck[A](request: Request[A]): Option[Future[Result]] = None
  
  // Things to check before we attempt authorisation
  def beforeAuthorisationCheck[A](request: Request[A]): Option[Future[Result]] = None
  
}