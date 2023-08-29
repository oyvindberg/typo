/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_archiver

import adventureworks.customtypes.TypoOffsetDateTime
import typo.dsl.SqlExpr.OptField

trait PgStatArchiverViewFields[Row] {
  val archivedCount: OptField[Long, Row]
  val lastArchivedWal: OptField[String, Row]
  val lastArchivedTime: OptField[TypoOffsetDateTime, Row]
  val failedCount: OptField[Long, Row]
  val lastFailedWal: OptField[String, Row]
  val lastFailedTime: OptField[TypoOffsetDateTime, Row]
  val statsReset: OptField[TypoOffsetDateTime, Row]
}
object PgStatArchiverViewFields extends PgStatArchiverViewStructure[PgStatArchiverViewRow](None, identity, (_, x) => x)
