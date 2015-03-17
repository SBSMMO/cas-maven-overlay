package uk.co.g4me.cas.models

import org.pac4j.cas.profile.CasProfile
import be.objectify.deadbolt.core.models.Subject
import play.libs.Scala

class CasUser(val profile: CasProfile) extends Subject {
  
  def userId: String = {
    profile.getId
  }
  
  def getRoles: java.util.List[SecurityRole] = {
    Scala.asJava(List(new SecurityRole("foo"),
                      new SecurityRole("bar")))
  }

  def getPermissions: java.util.List[UserPermission] = {
    Scala.asJava(List(new UserPermission("printers.edit")))
  }

  def getIdentifier: String = userId
}