/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package `_pg_foreign_table_columns`

import anorm.Column
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class PgForeignTableColumnsViewRow(
  /** Points to [[pg_catalog.pg_namespace.PgNamespaceRow.nspname]] */
  nspname: String,
  /** Points to [[pg_catalog.pg_class.PgClassRow.relname]] */
  relname: String,
  /** Points to [[pg_catalog.pg_attribute.PgAttributeRow.attname]] */
  attname: String,
  /** Points to [[pg_catalog.pg_attribute.PgAttributeRow.attfdwoptions]] */
  attfdwoptions: Option[Array[String]]
)

object PgForeignTableColumnsViewRow {
  implicit lazy val reads: Reads[PgForeignTableColumnsViewRow] = Reads[PgForeignTableColumnsViewRow](json => JsResult.fromTry(
      Try(
        PgForeignTableColumnsViewRow(
          nspname = json.\("nspname").as(Reads.StringReads),
          relname = json.\("relname").as(Reads.StringReads),
          attname = json.\("attname").as(Reads.StringReads),
          attfdwoptions = json.\("attfdwoptions").toOption.map(_.as(Reads.ArrayReads[String](Reads.StringReads, implicitly)))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[PgForeignTableColumnsViewRow] = RowParser[PgForeignTableColumnsViewRow] { row =>
    Success(
      PgForeignTableColumnsViewRow(
        nspname = row(idx + 0)(Column.columnToString),
        relname = row(idx + 1)(Column.columnToString),
        attname = row(idx + 2)(Column.columnToString),
        attfdwoptions = row(idx + 3)(Column.columnToOption(Column.columnToArray[String](Column.columnToString, implicitly)))
      )
    )
  }
  implicit lazy val writes: OWrites[PgForeignTableColumnsViewRow] = OWrites[PgForeignTableColumnsViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "nspname" -> Writes.StringWrites.writes(o.nspname),
      "relname" -> Writes.StringWrites.writes(o.relname),
      "attname" -> Writes.StringWrites.writes(o.attname),
      "attfdwoptions" -> Writes.OptionWrites(Writes.arrayWrites[String](implicitly, Writes.StringWrites)).writes(o.attfdwoptions)
    ))
  )
}