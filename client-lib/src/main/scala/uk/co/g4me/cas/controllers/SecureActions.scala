package uk.co.g4me.cas.controllers

import scala.concurrent.Future
import org.pac4j.core.client.RedirectAction
import org.pac4j.core.exception.TechnicalException
import play.api.http.ContentTypes
import play.api.mvc.{ Action, ActionBuilder, Request, Result, Results, WrappedRequest }
import uk.co.g4me.cas.models.CasUser
import org.slf4j.LoggerFactory
import org.pac4j.play.java.RequiresAuthentication
import org.pac4j.play.java.RequiresAuthenticationAction
import be.objectify.deadbolt.scala.DeadboltHandler
import play.api.mvc.BodyParsers
import be.objectify.deadbolt.core.models.Subject

// Authenticated request class, only exists once a request has been passed through an Authenticate action
case class AuthenticatedRequest[A](user: CasUser, request: Request[A]) extends WrappedRequest(request)

/**
 * @author Nick Shaw
 * @since 1.0.0
 * 
 * Secure Actions provides an Authenticated request and all the actions that use it.
 */
trait SecureActions extends Results with BodyParsers {
  
  def log = LoggerFactory.getLogger("uk.co.g4me.cas.controllers.SecureActions")
  
  case class PreAuthenticate[A](action: Action[A])(implicit val handler: DeadboltHandler) extends Action[A] {
    
    log.debug("PreAuthenticate")
    
    lazy val parser = action.parser
    
    def apply(request: Request[A]): Future[Result] = {      
      
      action(request)     
            
    } // apply
  } // PreAuthenticate
  
  case class PreAuthorise[A](action: Action[A], implicit val handler: DeadboltHandler) extends Action[A] {
    
    log.debug("PreAuthorise")
    
    lazy val parser = action.parser
    
    def apply(request: Request[A]): Future[Result] = {
      
      handler.beforeAuthCheck(request) match {
        case Some(result) => result
        case _ => action(request)
      }
      
    } // apply
  } // PreAuthorise
  
  
  

}