package uk.co.g4me.cas.controllers

import org.pac4j.play.scala.ScalaController
import org.slf4j.LoggerFactory
import play.api.mvc.{ Request, ActionBuilder }
import uk.co.g4me.cas.security._
import play.api.mvc.Action
import scala.concurrent.Future
import play.api.mvc.Result
import play.api.mvc.Controller

/**
 * 
 * @author Nick Shaw
 * @since 1.0.0
 * 
 */
trait SecureController extends Controller with SecureActions {
  
}