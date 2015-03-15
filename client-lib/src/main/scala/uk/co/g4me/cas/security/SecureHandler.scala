package uk.co.g4me.cas.security

import play.api.mvc.Request
import uk.co.g4me.cas.models.CasUser
import uk.co.g4me.cas.config.Constants
import scala.concurrent.Future
import play.api.mvc.Result
import uk.co.g4me.cas.controllers.AuthenticatedRequest
import org.pac4j.core.client.RedirectAction
import play.api.mvc.Session

trait SecureHandler {
  
  // Return a user using the current session
  def getUser[A](request: Request[A]): Option[CasUser]
  
  // Things to check before we attempt authentication
  def beforeAuthenticationCheck[A](request: Request[A]): Option[Future[Result]]
  
  // Things to check before we attempt authorisation
  def beforeAuthorisationCheck[A](request: Request[A]): Option[Future[Result]]
  
  def getRedirectAction[A](request: Request[A], newSession: Session, targetUrl: String, protectedPage: Boolean, isAjax: Boolean): RedirectAction
  
  def getAuthenticationRedirect[A](request: Request[A]): RedirectAction
  
  def getOrCreateSessionId[A](request: Request[A]): Session

}