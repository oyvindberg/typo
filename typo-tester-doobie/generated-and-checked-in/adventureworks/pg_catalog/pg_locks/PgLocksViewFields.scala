/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_locks

import adventureworks.customtypes.TypoInstant
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoXid
import typo.dsl.SqlExpr.OptField

trait PgLocksViewFields[Row] {
  val locktype: OptField[String, Row]
  val database: OptField[/* oid */ Long, Row]
  val relation: OptField[/* oid */ Long, Row]
  val page: OptField[Int, Row]
  val tuple: OptField[TypoShort, Row]
  val virtualxid: OptField[String, Row]
  val transactionid: OptField[TypoXid, Row]
  val classid: OptField[/* oid */ Long, Row]
  val objid: OptField[/* oid */ Long, Row]
  val objsubid: OptField[TypoShort, Row]
  val virtualtransaction: OptField[String, Row]
  val pid: OptField[Int, Row]
  val mode: OptField[String, Row]
  val granted: OptField[Boolean, Row]
  val fastpath: OptField[Boolean, Row]
  val waitstart: OptField[TypoInstant, Row]
}
object PgLocksViewFields extends PgLocksViewStructure[PgLocksViewRow](None, identity, (_, x) => x)
