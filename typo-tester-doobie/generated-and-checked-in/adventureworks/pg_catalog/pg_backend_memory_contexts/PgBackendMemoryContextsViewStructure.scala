/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_backend_memory_contexts

import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

class PgBackendMemoryContextsViewStructure[Row](val prefix: Option[String], val extract: Row => PgBackendMemoryContextsViewRow, val merge: (Row, PgBackendMemoryContextsViewRow) => Row)
  extends Relation[PgBackendMemoryContextsViewFields, PgBackendMemoryContextsViewRow, Row]
    with PgBackendMemoryContextsViewFields[Row] { outer =>

  override val name = new OptField[String, Row](prefix, "name", None, None)(x => extract(x).name, (row, value) => merge(row, extract(row).copy(name = value)))
  override val ident = new OptField[String, Row](prefix, "ident", None, None)(x => extract(x).ident, (row, value) => merge(row, extract(row).copy(ident = value)))
  override val parent = new OptField[String, Row](prefix, "parent", None, None)(x => extract(x).parent, (row, value) => merge(row, extract(row).copy(parent = value)))
  override val level = new OptField[Int, Row](prefix, "level", None, None)(x => extract(x).level, (row, value) => merge(row, extract(row).copy(level = value)))
  override val totalBytes = new OptField[Long, Row](prefix, "total_bytes", None, None)(x => extract(x).totalBytes, (row, value) => merge(row, extract(row).copy(totalBytes = value)))
  override val totalNblocks = new OptField[Long, Row](prefix, "total_nblocks", None, None)(x => extract(x).totalNblocks, (row, value) => merge(row, extract(row).copy(totalNblocks = value)))
  override val freeBytes = new OptField[Long, Row](prefix, "free_bytes", None, None)(x => extract(x).freeBytes, (row, value) => merge(row, extract(row).copy(freeBytes = value)))
  override val freeChunks = new OptField[Long, Row](prefix, "free_chunks", None, None)(x => extract(x).freeChunks, (row, value) => merge(row, extract(row).copy(freeChunks = value)))
  override val usedBytes = new OptField[Long, Row](prefix, "used_bytes", None, None)(x => extract(x).usedBytes, (row, value) => merge(row, extract(row).copy(usedBytes = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](name, ident, parent, level, totalBytes, totalNblocks, freeBytes, freeChunks, usedBytes)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => PgBackendMemoryContextsViewRow, merge: (NewRow, PgBackendMemoryContextsViewRow) => NewRow): PgBackendMemoryContextsViewStructure[NewRow] =
    new PgBackendMemoryContextsViewStructure(prefix, extract, merge)
}