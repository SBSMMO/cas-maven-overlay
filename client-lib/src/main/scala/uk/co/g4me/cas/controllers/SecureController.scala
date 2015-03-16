package uk.co.g4me.cas.controllers

import org.pac4j.play.scala.ScalaController
import play.api.mvc.Action
import uk.co.g4me.cas.security.SecureHandler
import play.api.mvc.AnyContent
import scala.concurrent.Future
import play.api.mvc.Result
import play.api.mvc.Request
import play.api.mvc.ActionBuilder
import uk.co.g4me.cas.security.DefaultSecureHandler
import org.slf4j.LoggerFactory

trait SecureController extends ScalaController with SecureActions {
    
  protected implicit val handler: SecureHandler = new DefaultSecureHandler
  
  def Authenticate[A](action: Action[A]): Action[A] = {
      log.debug("Authenticate")
      RequiresAuthentication[A]("CasClient", "", action.parser, false) { profile => 
        action
      }  
  }
  
//  case class Authenticate[A](action: Action[A]) extends Action[A] {
//    
//    logger.debug("Authenticate")
//    
//    lazy val parser = action.parser
//    
//    def apply(request: Request[A]): Action[A] = {  
//      RequiresAuthentication[A]("CasClient", "", action.parser, false) { profile => 
//          action
//        }
//      }  
//  }
  
  object AuthenticatedAction extends ActionBuilder[Request] {
    
    log.debug("AuthenticatedAction")
    
    def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = block(request)
   
    override def composeAction[A](action: Action[A]) = Authenticate(PreAuthenticate(action))
  }

}