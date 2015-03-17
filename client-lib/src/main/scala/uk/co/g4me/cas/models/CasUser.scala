package uk.co.g4me.cas.models

import org.pac4j.cas.profile.CasProfile
import be.objectify.deadbolt.core.models.Subject
import play.libs.Scala
import scala.collection.JavaConverters._
import scala.collection.mutable.MutableList
import scala.collection.mutable.ListBuffer

class CasUser(val profile: CasProfile) extends Subject {
  
  def userId: String = {
    profile.getId
  }
  
  protected def getRoles: java.util.List[SecurityRole] = {
    val casRoles = profile.getRoles().asScala
    
    val roles = ListBuffer[SecurityRole]()
    
    casRoles.map(roles += new SecurityRole(_))
    
    roles.asJava    
  }  

  protected def getPermissions: java.util.List[UserPermission] = {
    val casPermissions = profile.getPermissions().asScala
    
    val permissions = ListBuffer[UserPermission]()
    
    casPermissions.map(permissions += new UserPermission(_))
    
    permissions.asJava
  }

  def getIdentifier: String = userId
  
}