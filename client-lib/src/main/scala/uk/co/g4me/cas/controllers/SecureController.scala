package uk.co.g4me.cas.controllers

import scala.concurrent.Future
import org.pac4j.play.scala.ScalaController
import be.objectify.deadbolt.scala.DeadboltHandler
import play.api.mvc.Action
import play.api.mvc.ActionBuilder
import play.api.mvc.Request
import play.api.mvc.Result
import play.api.mvc.RequestHeader
import org.pac4j.play.StorageHelper
import org.pac4j.cas.profile.CasProfile
import uk.co.g4me.cas.models.CasUser
import org.pac4j.core.profile.CommonProfile
import play.api.mvc.AnyContent
import uk.co.g4me.cas.security.DefaultSecureHandler

trait SecureController extends ScalaController with SecureActions {
  
  protected implicit def handler: DefaultSecureHandler
  
  def Authenticate[A](action: Action[A]): Action[A] = {
    log.debug("Authenticate") 
    
      RequiresAuthentication[A]("CasClient", "", action.parser, false) { profile =>        
        action
      }    
  }  
  
  object AuthenticatedAction extends ActionBuilder[AuthenticatedRequest] {    
    log.debug("AuthenticatedAction")
    
    def invokeBlock[A](request: Request[A],block: (AuthenticatedRequest[A]) => Future[Result]) = {
      
      log.debug("AuthenticatedAction.invokeBlock")
      
      val profile = getUserProfile(request).asInstanceOf[CasProfile]
           
      block(AuthenticatedRequest(new CasUser(profile), request)) 
    }
    
    override def composeAction[A](action: Action[A]) = Authenticate(PreAuthenticate(action))
  }  

}