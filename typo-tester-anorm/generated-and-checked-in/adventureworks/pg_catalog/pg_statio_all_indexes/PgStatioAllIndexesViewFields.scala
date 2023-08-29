/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_statio_all_indexes

import adventureworks.pg_catalog.pg_class.PgClassId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.OptField

trait PgStatioAllIndexesViewFields[Row] {
  val relid: Field[PgClassId, Row]
  val indexrelid: Field[PgClassId, Row]
  val schemaname: OptField[String, Row]
  val relname: Field[String, Row]
  val indexrelname: Field[String, Row]
  val idxBlksRead: OptField[Long, Row]
  val idxBlksHit: OptField[Long, Row]
}
object PgStatioAllIndexesViewFields extends PgStatioAllIndexesViewStructure[PgStatioAllIndexesViewRow](None, identity, (_, x) => x)
