/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_views

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

case class PgViewsViewRow(
  /** Points to [[pg_namespace.PgNamespaceRow.nspname]] */
  schemaname: Option[String],
  /** Points to [[pg_class.PgClassRow.relname]] */
  viewname: String,
  viewowner: /* nullability unknown */ Option[String],
  definition: /* nullability unknown */ Option[String]
)

object PgViewsViewRow {
  implicit lazy val reads: Reads[PgViewsViewRow] = Reads[PgViewsViewRow](json => JsResult.fromTry(
      Try(
        PgViewsViewRow(
          schemaname = json.\("schemaname").toOption.map(_.as(Reads.StringReads)),
          viewname = json.\("viewname").as(Reads.StringReads),
          viewowner = json.\("viewowner").toOption.map(_.as(Reads.StringReads)),
          definition = json.\("definition").toOption.map(_.as(Reads.StringReads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[PgViewsViewRow] = RowParser[PgViewsViewRow] { row =>
    Success(
      PgViewsViewRow(
        schemaname = row(idx + 0)(Column.columnToOption(Column.columnToString)),
        viewname = row(idx + 1)(Column.columnToString),
        viewowner = row(idx + 2)(Column.columnToOption(Column.columnToString)),
        definition = row(idx + 3)(Column.columnToOption(Column.columnToString))
      )
    )
  }
  implicit lazy val writes: OWrites[PgViewsViewRow] = OWrites[PgViewsViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "schemaname" -> Writes.OptionWrites(Writes.StringWrites).writes(o.schemaname),
      "viewname" -> Writes.StringWrites.writes(o.viewname),
      "viewowner" -> Writes.OptionWrites(Writes.StringWrites).writes(o.viewowner),
      "definition" -> Writes.OptionWrites(Writes.StringWrites).writes(o.definition)
    ))
  )
}