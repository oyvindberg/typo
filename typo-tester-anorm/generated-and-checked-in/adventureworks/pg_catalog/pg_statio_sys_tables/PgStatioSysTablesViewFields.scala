/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_statio_sys_tables

import adventureworks.pg_catalog.pg_class.PgClassId
import typo.dsl.SqlExpr.OptField

trait PgStatioSysTablesViewFields[Row] {
  val relid: OptField[PgClassId, Row]
  val schemaname: OptField[String, Row]
  val relname: OptField[String, Row]
  val heapBlksRead: OptField[/* nullability unknown */ Long, Row]
  val heapBlksHit: OptField[/* nullability unknown */ Long, Row]
  val idxBlksRead: OptField[/* nullability unknown */ Long, Row]
  val idxBlksHit: OptField[/* nullability unknown */ Long, Row]
  val toastBlksRead: OptField[/* nullability unknown */ Long, Row]
  val toastBlksHit: OptField[/* nullability unknown */ Long, Row]
  val tidxBlksRead: OptField[/* nullability unknown */ Long, Row]
  val tidxBlksHit: OptField[/* nullability unknown */ Long, Row]
}
object PgStatioSysTablesViewFields extends PgStatioSysTablesViewStructure[PgStatioSysTablesViewRow](None, identity, (_, x) => x)
