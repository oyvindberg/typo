/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesperson

import adventureworks.Text
import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.sales.salesterritory.SalesterritoryId
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** This class corresponds to a row in table `sales.salesperson` which has not been persisted yet */
case class SalespersonRowUnsaved(
  /** Primary key for SalesPerson records. Foreign key to Employee.BusinessEntityID
      Points to [[humanresources.employee.EmployeeRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Territory currently assigned to. Foreign key to SalesTerritory.SalesTerritoryID.
      Points to [[salesterritory.SalesterritoryRow.territoryid]] */
  territoryid: Option[SalesterritoryId],
  /** Projected yearly sales.
      Constraint CK_SalesPerson_SalesQuota affecting columns s, a, l, e, s, q, u, o, t, a:  ((salesquota > 0.00)) */
  salesquota: Option[BigDecimal],
  /** Default: 0.00
      Bonus due if quota is met.
      Constraint CK_SalesPerson_Bonus affecting columns b, o, n, u, s:  ((bonus >= 0.00)) */
  bonus: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: 0.00
      Commision percent received per sale.
      Constraint CK_SalesPerson_CommissionPct affecting columns c, o, m, m, i, s, s, i, o, n, p, c, t:  ((commissionpct >= 0.00)) */
  commissionpct: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: 0.00
      Sales total year to date.
      Constraint CK_SalesPerson_SalesYTD affecting columns s, a, l, e, s, y, t, d:  ((salesytd >= 0.00)) */
  salesytd: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: 0.00
      Sales total of previous year.
      Constraint CK_SalesPerson_SalesLastYear affecting columns s, a, l, e, s, l, a, s, t, y, e, a, r:  ((saleslastyear >= 0.00)) */
  saleslastyear: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[TypoUUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(bonusDefault: => BigDecimal, commissionpctDefault: => BigDecimal, salesytdDefault: => BigDecimal, saleslastyearDefault: => BigDecimal, rowguidDefault: => TypoUUID, modifieddateDefault: => TypoLocalDateTime): SalespersonRow =
    SalespersonRow(
      businessentityid = businessentityid,
      territoryid = territoryid,
      salesquota = salesquota,
      bonus = bonus match {
                case Defaulted.UseDefault => bonusDefault
                case Defaulted.Provided(value) => value
              },
      commissionpct = commissionpct match {
                        case Defaulted.UseDefault => commissionpctDefault
                        case Defaulted.Provided(value) => value
                      },
      salesytd = salesytd match {
                   case Defaulted.UseDefault => salesytdDefault
                   case Defaulted.Provided(value) => value
                 },
      saleslastyear = saleslastyear match {
                        case Defaulted.UseDefault => saleslastyearDefault
                        case Defaulted.Provided(value) => value
                      },
      rowguid = rowguid match {
                  case Defaulted.UseDefault => rowguidDefault
                  case Defaulted.Provided(value) => value
                },
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object SalespersonRowUnsaved {
  implicit lazy val jsonDecoder: JsonDecoder[SalespersonRowUnsaved] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val businessentityid = jsonObj.get("businessentityid").toRight("Missing field 'businessentityid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val territoryid = jsonObj.get("territoryid").fold[Either[String, Option[SalesterritoryId]]](Right(None))(_.as(JsonDecoder.option(using SalesterritoryId.jsonDecoder)))
    val salesquota = jsonObj.get("salesquota").fold[Either[String, Option[BigDecimal]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.scalaBigDecimal)))
    val bonus = jsonObj.get("bonus").toRight("Missing field 'bonus'").flatMap(_.as(Defaulted.jsonDecoder(JsonDecoder.scalaBigDecimal)))
    val commissionpct = jsonObj.get("commissionpct").toRight("Missing field 'commissionpct'").flatMap(_.as(Defaulted.jsonDecoder(JsonDecoder.scalaBigDecimal)))
    val salesytd = jsonObj.get("salesytd").toRight("Missing field 'salesytd'").flatMap(_.as(Defaulted.jsonDecoder(JsonDecoder.scalaBigDecimal)))
    val saleslastyear = jsonObj.get("saleslastyear").toRight("Missing field 'saleslastyear'").flatMap(_.as(Defaulted.jsonDecoder(JsonDecoder.scalaBigDecimal)))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(Defaulted.jsonDecoder(TypoUUID.jsonDecoder)))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(Defaulted.jsonDecoder(TypoLocalDateTime.jsonDecoder)))
    if (businessentityid.isRight && territoryid.isRight && salesquota.isRight && bonus.isRight && commissionpct.isRight && salesytd.isRight && saleslastyear.isRight && rowguid.isRight && modifieddate.isRight)
      Right(SalespersonRowUnsaved(businessentityid = businessentityid.toOption.get, territoryid = territoryid.toOption.get, salesquota = salesquota.toOption.get, bonus = bonus.toOption.get, commissionpct = commissionpct.toOption.get, salesytd = salesytd.toOption.get, saleslastyear = saleslastyear.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](businessentityid, territoryid, salesquota, bonus, commissionpct, salesytd, saleslastyear, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[SalespersonRowUnsaved] = new JsonEncoder[SalespersonRowUnsaved] {
    override def unsafeEncode(a: SalespersonRowUnsaved, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""businessentityid":""")
      BusinessentityId.jsonEncoder.unsafeEncode(a.businessentityid, indent, out)
      out.write(",")
      out.write(""""territoryid":""")
      JsonEncoder.option(using SalesterritoryId.jsonEncoder).unsafeEncode(a.territoryid, indent, out)
      out.write(",")
      out.write(""""salesquota":""")
      JsonEncoder.option(using JsonEncoder.scalaBigDecimal).unsafeEncode(a.salesquota, indent, out)
      out.write(",")
      out.write(""""bonus":""")
      Defaulted.jsonEncoder(JsonEncoder.scalaBigDecimal).unsafeEncode(a.bonus, indent, out)
      out.write(",")
      out.write(""""commissionpct":""")
      Defaulted.jsonEncoder(JsonEncoder.scalaBigDecimal).unsafeEncode(a.commissionpct, indent, out)
      out.write(",")
      out.write(""""salesytd":""")
      Defaulted.jsonEncoder(JsonEncoder.scalaBigDecimal).unsafeEncode(a.salesytd, indent, out)
      out.write(",")
      out.write(""""saleslastyear":""")
      Defaulted.jsonEncoder(JsonEncoder.scalaBigDecimal).unsafeEncode(a.saleslastyear, indent, out)
      out.write(",")
      out.write(""""rowguid":""")
      Defaulted.jsonEncoder(TypoUUID.jsonEncoder).unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      Defaulted.jsonEncoder(TypoLocalDateTime.jsonEncoder).unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[SalespersonRowUnsaved] = Text.instance[SalespersonRowUnsaved]{ (row, sb) =>
    BusinessentityId.text.unsafeEncode(row.businessentityid, sb)
    sb.append(Text.DELIMETER)
    Text.option(SalesterritoryId.text).unsafeEncode(row.territoryid, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.bigDecimalInstance).unsafeEncode(row.salesquota, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.bigDecimalInstance).unsafeEncode(row.bonus, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.bigDecimalInstance).unsafeEncode(row.commissionpct, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.bigDecimalInstance).unsafeEncode(row.salesytd, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.bigDecimalInstance).unsafeEncode(row.saleslastyear, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoUUID.text).unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoLocalDateTime.text).unsafeEncode(row.modifieddate, sb)
  }
}