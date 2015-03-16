package uk.co.g4me.cas.models

import be.objectify.deadbolt.core.models.Role

class SecurityRole(val roleName: String) extends Role {
  def getName: String = roleName
}