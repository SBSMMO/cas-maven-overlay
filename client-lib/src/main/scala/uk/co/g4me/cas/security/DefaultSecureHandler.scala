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
import play.Play
import org.pac4j.cas.client.CasClient
import play.api.mvc.Session


class DefaultSecureHandler extends SecureHandler {
  
  protected val logger = LoggerFactory.getLogger("uk.co.g4me.cas.security.DefaultSecureHandler")
  
  // Return a user using the current session
  def getUser[A](request: Request[A]): Option[CasUser] = {
    val sessionId = request.session.get(Constants.SESSION_ID)
      
    if (sessionId.isDefined) {
      //TODO: Session management
      // SessionManager.getUser(sessionId.get)
      Some(new CasUser())
    } else {
      None
    }
  }
  
  // Things to check before we attempt authentication
  def beforeAuthenticationCheck[A](request: Request[A]): Option[Future[Result]] = None
  
  // Things to check before we attempt authorisation
  def beforeAuthorisationCheck[A](request: Request[A]): Option[Future[Result]] = None
  
  def getAuthenticationRedirect[A](request: Request[A]): RedirectAction = {
    val newSession = getOrCreateSessionId(request)
    getRedirectAction(request, newSession, "", true, false)
  }
  
  def getRedirectAction[A](request: Request[A], newSession: Session, targetUrl: String, protectedPage: Boolean, isAjax: Boolean): RedirectAction = {
    val sessionId = newSession.get(Constants.SESSION_ID).get
    logger.debug("sessionId for getRedirectionUrl() : {}", sessionId)
    // save requested url to save
    val requestedUrlToSave = CallbackController.defaultUrl(targetUrl, request.uri)
    logger.debug("requestedUrlToSave : {}", requestedUrlToSave)
    //StorageHelper.saveRequestedUrl(sessionId, clientName, requestedUrlToSave);
    // context
    val scalaWebContext = new ScalaWebContext(request, newSession)
    
    val client = new CasClient()
    val casUrl = Play.application.configuration.getString(Constants.CAS_URL)
    client.setCasLoginUrl(casUrl)
    
    val action = client.getRedirectAction(scalaWebContext, protectedPage, isAjax)
    logger.debug("redirectAction to : {}", action)
    action  
  }
  
  /**
   * Get or create a new sessionId.
   *
   * @param request
   * @return the (updated) session
   */
  def getOrCreateSessionId[A](request: Request[A]): Session = {
    var newSession = request.session
    val optionSessionId = newSession.get(Constants.SESSION_ID)
    logger.debug("getOrCreateSessionId : {}", optionSessionId)
    if (!optionSessionId.isDefined) {
      newSession += Constants.SESSION_ID -> java.util.UUID.randomUUID().toString()
    }
    newSession
  }
}