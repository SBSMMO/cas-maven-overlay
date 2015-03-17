package uk.co.g4me.cas.security

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc.{Request, Result, Results}
import uk.co.g4me.cas.controllers.AuthenticatedRequest
import uk.co.g4me.cas.models.CasUser
import be.objectify.deadbolt.scala.DeadboltHandler
import be.objectify.deadbolt.core.models.Subject
import be.objectify.deadbolt.scala.DynamicResourceHandler

class DefaultSecureHandler extends SecureHandler {
  
  override def beforeAuthenticationCheck[A](request: Request[A]) = None
  
  override def beforeAuthCheck[A](request: Request[A]): Option[Future[Result]] = {
    request match {
      case authRequest: AuthenticatedRequest[A] => None
      case _ => Some(Future.successful(Results.Forbidden("Request has not been authenticated. Please wrap in an AuthenticatedAction")))
    }
  }
  
  def getSubject[A](request: Request[A]): Future[Option[Subject]] = {
    request match {
      case authRequest: AuthenticatedRequest[A] => Future.successful(Some(authRequest.user))
      case _ => Future.successful(None)
    }
  }
  
  def onAuthFailure[A](request: Request[A]): Future[Result] = {
    Future { Results.Forbidden }
  }
  
  def getDynamicResourceHandler[A](request: Request[A]): Option[DynamicResourceHandler] = None

}