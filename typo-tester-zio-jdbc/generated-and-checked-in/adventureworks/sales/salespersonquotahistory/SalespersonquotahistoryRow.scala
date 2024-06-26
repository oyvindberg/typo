/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salespersonquotahistory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** Table: sales.salespersonquotahistory
    Sales performance tracking.
    Composite primary key: businessentityid, quotadate */
case class SalespersonquotahistoryRow(
  /** Sales person identification number. Foreign key to SalesPerson.BusinessEntityID.
      Points to [[salesperson.SalespersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Sales quota date. */
  quotadate: TypoLocalDateTime,
  /** Sales quota amount.
      Constraint CK_SalesPersonQuotaHistory_SalesQuota affecting columns salesquota: ((salesquota > 0.00)) */
  salesquota: BigDecimal,
  /** Default: uuid_generate_v1() */
  rowguid: TypoUUID,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val compositeId: SalespersonquotahistoryId = SalespersonquotahistoryId(businessentityid, quotadate)
   val id = compositeId
   def toUnsavedRow(rowguid: Defaulted[TypoUUID] = Defaulted.Provided(this.rowguid), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): SalespersonquotahistoryRowUnsaved =
     SalespersonquotahistoryRowUnsaved(businessentityid, quotadate, salesquota, rowguid, modifieddate)
 }

object SalespersonquotahistoryRow {
  def apply(compositeId: SalespersonquotahistoryId, salesquota: BigDecimal, rowguid: TypoUUID, modifieddate: TypoLocalDateTime) =
    new SalespersonquotahistoryRow(compositeId.businessentityid, compositeId.quotadate, salesquota, rowguid, modifieddate)
  implicit lazy val jdbcDecoder: JdbcDecoder[SalespersonquotahistoryRow] = new JdbcDecoder[SalespersonquotahistoryRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, SalespersonquotahistoryRow) =
      columIndex + 4 ->
        SalespersonquotahistoryRow(
          businessentityid = BusinessentityId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          quotadate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          salesquota = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 2, rs)._2,
          rowguid = TypoUUID.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 4, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[SalespersonquotahistoryRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val businessentityid = jsonObj.get("businessentityid").toRight("Missing field 'businessentityid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val quotadate = jsonObj.get("quotadate").toRight("Missing field 'quotadate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    val salesquota = jsonObj.get("salesquota").toRight("Missing field 'salesquota'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(TypoUUID.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (businessentityid.isRight && quotadate.isRight && salesquota.isRight && rowguid.isRight && modifieddate.isRight)
      Right(SalespersonquotahistoryRow(businessentityid = businessentityid.toOption.get, quotadate = quotadate.toOption.get, salesquota = salesquota.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](businessentityid, quotadate, salesquota, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[SalespersonquotahistoryRow] = new JsonEncoder[SalespersonquotahistoryRow] {
    override def unsafeEncode(a: SalespersonquotahistoryRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""businessentityid":""")
      BusinessentityId.jsonEncoder.unsafeEncode(a.businessentityid, indent, out)
      out.write(",")
      out.write(""""quotadate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.quotadate, indent, out)
      out.write(",")
      out.write(""""salesquota":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.salesquota, indent, out)
      out.write(",")
      out.write(""""rowguid":""")
      TypoUUID.jsonEncoder.unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[SalespersonquotahistoryRow] = Text.instance[SalespersonquotahistoryRow]{ (row, sb) =>
    BusinessentityId.text.unsafeEncode(row.businessentityid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.quotadate, sb)
    sb.append(Text.DELIMETER)
    Text.bigDecimalInstance.unsafeEncode(row.salesquota, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
}
