/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package enabled_roles

import typo.dsl.SqlExpr.OptField

trait EnabledRolesViewFields[Row] {
  val roleName: OptField[String, Row]
}
object EnabledRolesViewFields extends EnabledRolesViewStructure[EnabledRolesViewRow](None, identity, (_, x) => x)
