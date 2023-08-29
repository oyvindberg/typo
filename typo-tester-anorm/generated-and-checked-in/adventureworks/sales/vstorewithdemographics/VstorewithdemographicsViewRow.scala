/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vstorewithdemographics

import adventureworks.customtypes.TypoMoney
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

case class VstorewithdemographicsViewRow(
  /** Points to [[store.StoreRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[store.StoreRow.name]] */
  name: Name,
  AnnualSales: /* nullability unknown */ Option[TypoMoney],
  AnnualRevenue: /* nullability unknown */ Option[TypoMoney],
  BankName: /* nullability unknown */ Option[/* max 50 chars */ String],
  BusinessType: /* nullability unknown */ Option[/* max 5 chars */ String],
  YearOpened: /* nullability unknown */ Option[Int],
  Specialty: /* nullability unknown */ Option[/* max 50 chars */ String],
  SquareFeet: /* nullability unknown */ Option[Int],
  Brands: /* nullability unknown */ Option[/* max 30 chars */ String],
  Internet: /* nullability unknown */ Option[/* max 30 chars */ String],
  NumberEmployees: /* nullability unknown */ Option[Int]
)

object VstorewithdemographicsViewRow {
  implicit lazy val reads: Reads[VstorewithdemographicsViewRow] = Reads[VstorewithdemographicsViewRow](json => JsResult.fromTry(
      Try(
        VstorewithdemographicsViewRow(
          businessentityid = json.\("businessentityid").as(BusinessentityId.reads),
          name = json.\("name").as(Name.reads),
          AnnualSales = json.\("AnnualSales").toOption.map(_.as(TypoMoney.reads)),
          AnnualRevenue = json.\("AnnualRevenue").toOption.map(_.as(TypoMoney.reads)),
          BankName = json.\("BankName").toOption.map(_.as(Reads.StringReads)),
          BusinessType = json.\("BusinessType").toOption.map(_.as(Reads.StringReads)),
          YearOpened = json.\("YearOpened").toOption.map(_.as(Reads.IntReads)),
          Specialty = json.\("Specialty").toOption.map(_.as(Reads.StringReads)),
          SquareFeet = json.\("SquareFeet").toOption.map(_.as(Reads.IntReads)),
          Brands = json.\("Brands").toOption.map(_.as(Reads.StringReads)),
          Internet = json.\("Internet").toOption.map(_.as(Reads.StringReads)),
          NumberEmployees = json.\("NumberEmployees").toOption.map(_.as(Reads.IntReads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[VstorewithdemographicsViewRow] = RowParser[VstorewithdemographicsViewRow] { row =>
    Success(
      VstorewithdemographicsViewRow(
        businessentityid = row(idx + 0)(BusinessentityId.column),
        name = row(idx + 1)(Name.column),
        AnnualSales = row(idx + 2)(Column.columnToOption(TypoMoney.column)),
        AnnualRevenue = row(idx + 3)(Column.columnToOption(TypoMoney.column)),
        BankName = row(idx + 4)(Column.columnToOption(Column.columnToString)),
        BusinessType = row(idx + 5)(Column.columnToOption(Column.columnToString)),
        YearOpened = row(idx + 6)(Column.columnToOption(Column.columnToInt)),
        Specialty = row(idx + 7)(Column.columnToOption(Column.columnToString)),
        SquareFeet = row(idx + 8)(Column.columnToOption(Column.columnToInt)),
        Brands = row(idx + 9)(Column.columnToOption(Column.columnToString)),
        Internet = row(idx + 10)(Column.columnToOption(Column.columnToString)),
        NumberEmployees = row(idx + 11)(Column.columnToOption(Column.columnToInt))
      )
    )
  }
  implicit lazy val writes: OWrites[VstorewithdemographicsViewRow] = OWrites[VstorewithdemographicsViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "businessentityid" -> BusinessentityId.writes.writes(o.businessentityid),
      "name" -> Name.writes.writes(o.name),
      "AnnualSales" -> Writes.OptionWrites(TypoMoney.writes).writes(o.AnnualSales),
      "AnnualRevenue" -> Writes.OptionWrites(TypoMoney.writes).writes(o.AnnualRevenue),
      "BankName" -> Writes.OptionWrites(Writes.StringWrites).writes(o.BankName),
      "BusinessType" -> Writes.OptionWrites(Writes.StringWrites).writes(o.BusinessType),
      "YearOpened" -> Writes.OptionWrites(Writes.IntWrites).writes(o.YearOpened),
      "Specialty" -> Writes.OptionWrites(Writes.StringWrites).writes(o.Specialty),
      "SquareFeet" -> Writes.OptionWrites(Writes.IntWrites).writes(o.SquareFeet),
      "Brands" -> Writes.OptionWrites(Writes.StringWrites).writes(o.Brands),
      "Internet" -> Writes.OptionWrites(Writes.StringWrites).writes(o.Internet),
      "NumberEmployees" -> Writes.OptionWrites(Writes.IntWrites).writes(o.NumberEmployees)
    ))
  )
}