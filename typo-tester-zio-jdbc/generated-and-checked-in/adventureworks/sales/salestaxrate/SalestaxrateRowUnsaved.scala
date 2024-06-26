/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salestaxrate

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.person.stateprovince.StateprovinceId
import adventureworks.public.Name
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** This class corresponds to a row in table `sales.salestaxrate` which has not been persisted yet */
case class SalestaxrateRowUnsaved(
  /** State, province, or country/region the sales tax applies to.
      Points to [[person.stateprovince.StateprovinceRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** 1 = Tax applied to retail transactions, 2 = Tax applied to wholesale transactions, 3 = Tax applied to all sales (retail and wholesale) transactions.
      Constraint CK_SalesTaxRate_TaxType affecting columns taxtype:  (((taxtype >= 1) AND (taxtype <= 3))) */
  taxtype: TypoShort,
  /** Tax rate description. */
  name: Name,
  /** Default: nextval('sales.salestaxrate_salestaxrateid_seq'::regclass)
      Primary key for SalesTaxRate records. */
  salestaxrateid: Defaulted[SalestaxrateId] = Defaulted.UseDefault,
  /** Default: 0.00
      Tax rate amount. */
  taxrate: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[TypoUUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(salestaxrateidDefault: => SalestaxrateId, taxrateDefault: => BigDecimal, rowguidDefault: => TypoUUID, modifieddateDefault: => TypoLocalDateTime): SalestaxrateRow =
    SalestaxrateRow(
      stateprovinceid = stateprovinceid,
      taxtype = taxtype,
      name = name,
      salestaxrateid = salestaxrateid match {
                         case Defaulted.UseDefault => salestaxrateidDefault
                         case Defaulted.Provided(value) => value
                       },
      taxrate = taxrate match {
                  case Defaulted.UseDefault => taxrateDefault
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
object SalestaxrateRowUnsaved {
  implicit lazy val jsonDecoder: JsonDecoder[SalestaxrateRowUnsaved] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val stateprovinceid = jsonObj.get("stateprovinceid").toRight("Missing field 'stateprovinceid'").flatMap(_.as(StateprovinceId.jsonDecoder))
    val taxtype = jsonObj.get("taxtype").toRight("Missing field 'taxtype'").flatMap(_.as(TypoShort.jsonDecoder))
    val name = jsonObj.get("name").toRight("Missing field 'name'").flatMap(_.as(Name.jsonDecoder))
    val salestaxrateid = jsonObj.get("salestaxrateid").toRight("Missing field 'salestaxrateid'").flatMap(_.as(Defaulted.jsonDecoder(SalestaxrateId.jsonDecoder)))
    val taxrate = jsonObj.get("taxrate").toRight("Missing field 'taxrate'").flatMap(_.as(Defaulted.jsonDecoder(JsonDecoder.scalaBigDecimal)))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(Defaulted.jsonDecoder(TypoUUID.jsonDecoder)))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(Defaulted.jsonDecoder(TypoLocalDateTime.jsonDecoder)))
    if (stateprovinceid.isRight && taxtype.isRight && name.isRight && salestaxrateid.isRight && taxrate.isRight && rowguid.isRight && modifieddate.isRight)
      Right(SalestaxrateRowUnsaved(stateprovinceid = stateprovinceid.toOption.get, taxtype = taxtype.toOption.get, name = name.toOption.get, salestaxrateid = salestaxrateid.toOption.get, taxrate = taxrate.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](stateprovinceid, taxtype, name, salestaxrateid, taxrate, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[SalestaxrateRowUnsaved] = new JsonEncoder[SalestaxrateRowUnsaved] {
    override def unsafeEncode(a: SalestaxrateRowUnsaved, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""stateprovinceid":""")
      StateprovinceId.jsonEncoder.unsafeEncode(a.stateprovinceid, indent, out)
      out.write(",")
      out.write(""""taxtype":""")
      TypoShort.jsonEncoder.unsafeEncode(a.taxtype, indent, out)
      out.write(",")
      out.write(""""name":""")
      Name.jsonEncoder.unsafeEncode(a.name, indent, out)
      out.write(",")
      out.write(""""salestaxrateid":""")
      Defaulted.jsonEncoder(SalestaxrateId.jsonEncoder).unsafeEncode(a.salestaxrateid, indent, out)
      out.write(",")
      out.write(""""taxrate":""")
      Defaulted.jsonEncoder(JsonEncoder.scalaBigDecimal).unsafeEncode(a.taxrate, indent, out)
      out.write(",")
      out.write(""""rowguid":""")
      Defaulted.jsonEncoder(TypoUUID.jsonEncoder).unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      Defaulted.jsonEncoder(TypoLocalDateTime.jsonEncoder).unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[SalestaxrateRowUnsaved] = Text.instance[SalestaxrateRowUnsaved]{ (row, sb) =>
    StateprovinceId.text.unsafeEncode(row.stateprovinceid, sb)
    sb.append(Text.DELIMETER)
    TypoShort.text.unsafeEncode(row.taxtype, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(SalestaxrateId.text).unsafeEncode(row.salestaxrateid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.bigDecimalInstance).unsafeEncode(row.taxrate, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoUUID.text).unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoLocalDateTime.text).unsafeEncode(row.modifieddate, sb)
  }
}
