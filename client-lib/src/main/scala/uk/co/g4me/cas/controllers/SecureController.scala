package uk.co.g4me.cas.controllers

import scala.concurrent.Future
import org.pac4j.play.scala.ScalaController
import be.objectify.deadbolt.scala.DeadboltHandler
import play.api.mvc.Action
import play.api.mvc.ActionBuilder
import play.api.mvc.Request
import play.api.mvc.Result
import uk.co.g4me.cas.security.DefaultHandler
import play.api.mvc.RequestHeader
import org.pac4j.play.StorageHelper
import org.pac4j.cas.profile.CasProfile
import uk.co.g4me.cas.models.CasUser
import org.pac4j.core.profile.CommonProfile
import play.api.mvc.AnyContent

trait SecureController extends ScalaController with SecureActions {
    
  protected implicit val handler: DeadboltHandler = new DefaultHandler()
  
  def Authenticate[A](action: Action[A]): Action[A] = {
      RequiresAuthentication[A]("CasClient", "", action.parser, false) { profile =>
        log.debug("Authenticate") 
        action
      }    
  }  
  
  object AuthenticatedAction extends ActionBuilder[AuthenticatedRequest] {    
    log.debug("AuthenticatedAction")
    
    def invokeBlock[A](request: Request[A],block: (AuthenticatedRequest[A]) => Future[Result]) = {
      
      log.debug("AuthenticatedAction.invokeBlock")
      
      val handler: DeadboltHandler = new DefaultHandler()
      
      val user: CasUser = handler.getSubject(request).asInstanceOf[CasUser]
     
      block(AuthenticatedRequest(user, request)) 
    }
    
    override def composeAction[A](action: Action[A]) = Authenticate(PreAuthenticate(action))
  }

}