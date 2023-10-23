package scripts

import bleep.model

object projectsToPublish {
  // will publish these with dependencies
  def include(crossName: model.CrossProjectName): Boolean =
    crossName.name.value match {
      case "typo"              => true
      case "typo-dsl-anorm"    => true
      case "typo-dsl-doobie"   => true
      case "typo-dsl-zio-jdbc" => true
      case _                   => false
    }
}
