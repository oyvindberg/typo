/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package currency

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** This class corresponds to a row in table `sales.currency` which has not been persisted yet */
case class CurrencyRowUnsaved(
  /** The ISO code for the Currency. */
  currencycode: CurrencyId,
  /** Currency name. */
  name: Name,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(modifieddateDefault: => TypoLocalDateTime): CurrencyRow =
    CurrencyRow(
      currencycode = currencycode,
      name = name,
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object CurrencyRowUnsaved {
  implicit lazy val jsonDecoder: JsonDecoder[CurrencyRowUnsaved] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val currencycode = jsonObj.get("currencycode").toRight("Missing field 'currencycode'").flatMap(_.as(CurrencyId.jsonDecoder))
    val name = jsonObj.get("name").toRight("Missing field 'name'").flatMap(_.as(Name.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(Defaulted.jsonDecoder(TypoLocalDateTime.jsonDecoder)))
    if (currencycode.isRight && name.isRight && modifieddate.isRight)
      Right(CurrencyRowUnsaved(currencycode = currencycode.toOption.get, name = name.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](currencycode, name, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[CurrencyRowUnsaved] = new JsonEncoder[CurrencyRowUnsaved] {
    override def unsafeEncode(a: CurrencyRowUnsaved, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""currencycode":""")
      CurrencyId.jsonEncoder.unsafeEncode(a.currencycode, indent, out)
      out.write(",")
      out.write(""""name":""")
      Name.jsonEncoder.unsafeEncode(a.name, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      Defaulted.jsonEncoder(TypoLocalDateTime.jsonEncoder).unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[CurrencyRowUnsaved] = Text.instance[CurrencyRowUnsaved]{ (row, sb) =>
    CurrencyId.text.unsafeEncode(row.currencycode, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoLocalDateTime.text).unsafeEncode(row.modifieddate, sb)
  }
}
