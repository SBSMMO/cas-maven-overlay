package uk.co.g4me.cas.security

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc.{Request, Result, Results}
import uk.co.g4me.cas.controllers.AuthenticatedRequest
import uk.co.g4me.cas.models.CasUser

class DefaultSecureHandler extends SecureHandler {
  
  override def beforeAuthenticationCheck[A](request: Request[A]) = None
  
  override def beforeAuthorisationCheck[A](request: Request[A]): Option[Future[Result]] = {
    request match {
      case authRequest: AuthenticatedRequest[A] => None
      case _ => Some(Future.successful(Results.Forbidden("Request has not been authenticated. Please wrap in an AuthenticatedAction")))
    }
  }
  
  override def getUser[A](request: Request[A]) = {
    Future.successful(None)
  }

}