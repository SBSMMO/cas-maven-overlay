package uk.co.g4me.cas.security

import scala.concurrent.Future
import play.api.mvc.{Request, Result}
import uk.co.g4me.cas.models.CasUser
import uk.co.g4me.cas.controllers.AuthenticatedRequest

trait SecureHandler {
  
  def getUser[A](request: Request[A]): Future[Option[CasUser]]
  
  def beforeAuthenticationCheck[A](request: Request[A]): Option[Future[Result]]
  
  def beforeAuthorisationCheck[A](request: Request[A]): Option[Future[Result]]

}