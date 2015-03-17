package uk.co.g4me.cas.controllers

import scala.concurrent.Future

import org.slf4j.LoggerFactory

import be.objectify.deadbolt.core.DeadboltAnalyzer
import be.objectify.deadbolt.core.models.Subject
import play.api.mvc.Action
import play.api.mvc.BodyParsers
import play.api.mvc.Request
import play.api.mvc.Result
import play.api.mvc.Results
import play.api.mvc.WrappedRequest
import uk.co.g4me.cas.models.CasUser
import uk.co.g4me.cas.security.SecureHandler

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
  
  case class PreAuthenticate[A](action: Action[A])(implicit val handler: SecureHandler) extends Action[A] {
    
    log.debug("PreAuthenticate")
    
    lazy val parser = action.parser
    
    def apply(request: Request[A]): Future[Result] = {      
      handler.beforeAuthenticationCheck(request) match {
        case Some(result) => result
        case _ => action(request)
      }
            
    } // apply
  } // PreAuthenticate
  
  case class PreAuthorise[A](action: Action[A])(implicit val handler: SecureHandler) extends Action[A] {
    
    log.debug("PreAuthorise")
    
    lazy val parser = action.parser
    
    def apply(request: Request[A]): Future[Result] = {
      handler.beforeAuthorisationCheck(request) match {
        case Some(result) => result
        case _ => action(request) 
      }
      
    } // apply
  } // PreAuthorise
  
  case class RestrictRoles[A](roleNames: Array[String])(action: Action[A]) extends Action[A] {
    
    log.debug("RestrictRoles")
    
    lazy val parser = action.parser
    
    def apply(request: Request[A]): Future[Result] = {
      RestrictGroups[A](List(roleNames))(action).apply(request)
    }    
  }
  
  case class RestrictGroups[A](roleGroups: List[Array[String]])(action: Action[A]) extends Action[A] {
    
    log.debug("RestrictGroups")
    
    lazy val parser = action.parser
    
    def apply(request: Request[A]): Future[Result] = {
      request match {
        case authRequest: AuthenticatedRequest[A] => 
          val user = authRequest.user
          
          if (check(user, roleGroups.head, roleGroups.tail)) {
            action(request)
          } else {
            Future.successful(Results.Unauthorized)
          }
        case _ => throw new IllegalArgumentException("Request has not been authenticated. Please wrap in an AuthenticatedAction")
      }      
    }
    
    def check(subject: Subject, current: Array[String], remaining: List[Array[String]]): Boolean = {
      if (DeadboltAnalyzer.checkRole(subject, current)) true
      else if (remaining.isEmpty) false
      else check(subject, remaining.head, remaining.tail)
    }
  }  

}