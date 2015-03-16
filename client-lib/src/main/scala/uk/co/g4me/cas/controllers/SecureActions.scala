package uk.co.g4me.cas.controllers

import scala.concurrent.Future
import org.pac4j.core.client.RedirectAction
import org.pac4j.core.exception.TechnicalException
import play.api.http.ContentTypes
import play.api.mvc.{ Action, ActionBuilder, Request, Result, Results, WrappedRequest }
import uk.co.g4me.cas.models.CasUser
import uk.co.g4me.cas.security.{ DefaultSecureHandler, SecureHandler}
import org.slf4j.LoggerFactory
import org.pac4j.play.java.RequiresAuthentication
import org.pac4j.play.java.RequiresAuthenticationAction

// Authenticated request class, only exists once a request has been passed through an Authenticate action
case class AuthenticatedRequest[A](user: CasUser, request: Request[A]) extends WrappedRequest(request)

/**
 * @author Nick Shaw
 * @since 1.0.0
 * 
 * Secure Actions provides an Authenticated request and all the actions that use it.
 */
trait SecureActions extends Results {
  
  def log = LoggerFactory.getLogger("uk.co.g4me.cas.controllers.SecureActions")
  
  case class PreAuthenticate[A](action: Action[A])(implicit val handler: SecureHandler) extends Action[A] {
    
    log.debug("PreAuthenticate")
    
    lazy val parser = action.parser
    
    def apply(request: Request[A]): Future[Result] = {
      handler.beforeAuthenticationCheck(request) match {
        case Some(result) => result
        case _ =>
          action(request)
      } 
            
    }
  } // PreAuthenticate
  
  case class PreAuthorise[A](action: Action[A], implicit val handler: SecureHandler) extends Action[A] {
    
    log.debug("PreAuthorise")
    
    lazy val parser = action.parser
    
    def apply(request: Request[A]): Future[Result] = {
      handler.beforeAuthorisationCheck(request) match {
        case Some(result) => result
        case _ =>
          action(request)
      }     
    }
  } // PreAuthorise
  
//  cate[A](action: Action[A])(implicit val handler: SecureHandler) extends Action[A] {
//    
//    logger.debug("Authenticate")
//    
//    lazy val parser = action.parser
//    
//    def apply(request: Request[A]): Future[Result] = {
//      
//      val userOption = handler.getUser[A](request)
//      
//      if (userOption.isDefined) {
//        action(AuthenticatedRequest(userOption.get, request))  
//      } else {
//        //Future.successful(Unauthorized)
//        val redirectAction = handler.getAuthenticationRedirect(request)
//        val newSession = handler.getOrCreateSessionId(request)
//        
//        redirectAction.getType() match {
//          case RedirectAction.RedirectType.REDIRECT => Future.successful(Redirect(redirectAction.getLocation()).withSession(newSession))
//          case RedirectAction.RedirectType.SUCCESS => Future.successful(Ok(redirectAction.getContent()).withSession(newSession).as(ContentTypes.HTML))
//          case _ => throw new TechnicalException("Unexpected RedirectAction : " + redirectAction.getType)
//        }
//        
//      } // else
//    } // apply    
//  } // Authenticate
  
}