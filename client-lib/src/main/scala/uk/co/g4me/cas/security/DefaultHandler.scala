package uk.co.g4me.cas.security

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import be.objectify.deadbolt.core.models.Subject
import be.objectify.deadbolt.scala.DeadboltHandler
import be.objectify.deadbolt.scala.DynamicResourceHandler
import play.api.mvc.Request
import play.api.mvc.Result
import play.api.mvc.Results
import uk.co.g4me.cas.models.CasUser
import uk.co.g4me.cas.controllers.AuthenticatedRequest
import org.pac4j.play.StorageHelper
import org.pac4j.cas.profile.CasProfile
import uk.co.g4me.cas.models.CasUser
import uk.co.g4me.cas.models.CasUser
import play.api.mvc.AnyContent
import uk.co.g4me.cas.models.CasUser

class DefaultHandler(dynamicResourceHandler: Option[DynamicResourceHandler] = None) extends DeadboltHandler {
  
  def beforeAuthCheck[A](request: Request[A]) = None

  override def getDynamicResourceHandler[A](request: Request[A]): Option[DynamicResourceHandler] = {
    if (dynamicResourceHandler.isDefined) dynamicResourceHandler
    else None
  }

  override def getSubject[A](request: Request[A]): Future[Option[Subject]] = {
    var profile = null.asInstanceOf[CasProfile]
    val sessionId = request.session.get("pac4jSessionId")

    if (sessionId.isDefined) {
      // get the user profile
      profile = StorageHelper.getProfile(sessionId.get).asInstanceOf[CasProfile]
    }
    
    if (profile == null) {
      Future.successful(None)
    } else {
      Future.successful(Some(new CasUser(profile.getId)))
    }
  }

  def onAuthFailure[A](request: Request[A]): Future[Result] = {
    Future.successful(Results.Forbidden)
  }

}