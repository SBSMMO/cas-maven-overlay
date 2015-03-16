package uk.co.g4me.cas.models

import be.objectify.deadbolt.core.models.Permission

class UserPermission(val value: String) extends Permission {
  def getValue: String = value
}