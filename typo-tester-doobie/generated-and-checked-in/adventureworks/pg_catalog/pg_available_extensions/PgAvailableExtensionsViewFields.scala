/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_available_extensions

import typo.dsl.SqlExpr.OptField

trait PgAvailableExtensionsViewFields[Row] {
  val name: OptField[String, Row]
  val defaultVersion: OptField[String, Row]
  val installedVersion: OptField[String, Row]
  val comment: OptField[String, Row]
}
object PgAvailableExtensionsViewFields extends PgAvailableExtensionsViewStructure[PgAvailableExtensionsViewRow](None, identity, (_, x) => x)
