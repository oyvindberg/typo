/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vsalespersonsalesbyfiscalyearsdata

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
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

/** View: sales.vsalespersonsalesbyfiscalyearsdata */
case class VsalespersonsalesbyfiscalyearsdataViewRow(
  /** Points to [[salesorderheader.SalesorderheaderRow.salespersonid]] */
  salespersonid: Option[BusinessentityId],
  fullname: /* nullability unknown */ Option[String],
  /** Points to [[humanresources.employee.EmployeeRow.jobtitle]] */
  jobtitle: /* max 50 chars */ String,
  /** Points to [[salesterritory.SalesterritoryRow.name]] */
  salesterritory: Name,
  salestotal: /* nullability unknown */ Option[BigDecimal],
  fiscalyear: /* nullability unknown */ Option[BigDecimal]
)

object VsalespersonsalesbyfiscalyearsdataViewRow {
  implicit lazy val reads: Reads[VsalespersonsalesbyfiscalyearsdataViewRow] = Reads[VsalespersonsalesbyfiscalyearsdataViewRow](json => JsResult.fromTry(
      Try(
        VsalespersonsalesbyfiscalyearsdataViewRow(
          salespersonid = json.\("salespersonid").toOption.map(_.as(BusinessentityId.reads)),
          fullname = json.\("fullname").toOption.map(_.as(Reads.StringReads)),
          jobtitle = json.\("jobtitle").as(Reads.StringReads),
          salesterritory = json.\("salesterritory").as(Name.reads),
          salestotal = json.\("salestotal").toOption.map(_.as(Reads.bigDecReads)),
          fiscalyear = json.\("fiscalyear").toOption.map(_.as(Reads.bigDecReads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[VsalespersonsalesbyfiscalyearsdataViewRow] = RowParser[VsalespersonsalesbyfiscalyearsdataViewRow] { row =>
    Success(
      VsalespersonsalesbyfiscalyearsdataViewRow(
        salespersonid = row(idx + 0)(Column.columnToOption(BusinessentityId.column)),
        fullname = row(idx + 1)(Column.columnToOption(Column.columnToString)),
        jobtitle = row(idx + 2)(Column.columnToString),
        salesterritory = row(idx + 3)(Name.column),
        salestotal = row(idx + 4)(Column.columnToOption(Column.columnToScalaBigDecimal)),
        fiscalyear = row(idx + 5)(Column.columnToOption(Column.columnToScalaBigDecimal))
      )
    )
  }
  implicit lazy val writes: OWrites[VsalespersonsalesbyfiscalyearsdataViewRow] = OWrites[VsalespersonsalesbyfiscalyearsdataViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "salespersonid" -> Writes.OptionWrites(BusinessentityId.writes).writes(o.salespersonid),
      "fullname" -> Writes.OptionWrites(Writes.StringWrites).writes(o.fullname),
      "jobtitle" -> Writes.StringWrites.writes(o.jobtitle),
      "salesterritory" -> Name.writes.writes(o.salesterritory),
      "salestotal" -> Writes.OptionWrites(Writes.BigDecimalWrites).writes(o.salestotal),
      "fiscalyear" -> Writes.OptionWrites(Writes.BigDecimalWrites).writes(o.fiscalyear)
    ))
  )
}
