/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_database_conflicts

import adventureworks.pg_catalog.pg_database.PgDatabaseId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

class PgStatDatabaseConflictsViewStructure[Row](val prefix: Option[String], val extract: Row => PgStatDatabaseConflictsViewRow, val merge: (Row, PgStatDatabaseConflictsViewRow) => Row)
  extends Relation[PgStatDatabaseConflictsViewFields, PgStatDatabaseConflictsViewRow, Row]
    with PgStatDatabaseConflictsViewFields[Row] { outer =>

  override val datid = new Field[PgDatabaseId, Row](prefix, "datid", None, None)(x => extract(x).datid, (row, value) => merge(row, extract(row).copy(datid = value)))
  override val datname = new Field[String, Row](prefix, "datname", None, None)(x => extract(x).datname, (row, value) => merge(row, extract(row).copy(datname = value)))
  override val conflTablespace = new OptField[Long, Row](prefix, "confl_tablespace", None, None)(x => extract(x).conflTablespace, (row, value) => merge(row, extract(row).copy(conflTablespace = value)))
  override val conflLock = new OptField[Long, Row](prefix, "confl_lock", None, None)(x => extract(x).conflLock, (row, value) => merge(row, extract(row).copy(conflLock = value)))
  override val conflSnapshot = new OptField[Long, Row](prefix, "confl_snapshot", None, None)(x => extract(x).conflSnapshot, (row, value) => merge(row, extract(row).copy(conflSnapshot = value)))
  override val conflBufferpin = new OptField[Long, Row](prefix, "confl_bufferpin", None, None)(x => extract(x).conflBufferpin, (row, value) => merge(row, extract(row).copy(conflBufferpin = value)))
  override val conflDeadlock = new OptField[Long, Row](prefix, "confl_deadlock", None, None)(x => extract(x).conflDeadlock, (row, value) => merge(row, extract(row).copy(conflDeadlock = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](datid, datname, conflTablespace, conflLock, conflSnapshot, conflBufferpin, conflDeadlock)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => PgStatDatabaseConflictsViewRow, merge: (NewRow, PgStatDatabaseConflictsViewRow) => NewRow): PgStatDatabaseConflictsViewStructure[NewRow] =
    new PgStatDatabaseConflictsViewStructure(prefix, extract, merge)
}