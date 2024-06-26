/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package countryregioncurrency

import adventureworks.person.countryregion.CountryregionId
import adventureworks.sales.currency.CurrencyId
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** Type for the composite primary key of table `sales.countryregioncurrency` */
case class CountryregioncurrencyId(
  countryregioncode: CountryregionId,
  currencycode: CurrencyId
)
object CountryregioncurrencyId {
  implicit lazy val jsonDecoder: JsonDecoder[CountryregioncurrencyId] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val countryregioncode = jsonObj.get("countryregioncode").toRight("Missing field 'countryregioncode'").flatMap(_.as(CountryregionId.jsonDecoder))
    val currencycode = jsonObj.get("currencycode").toRight("Missing field 'currencycode'").flatMap(_.as(CurrencyId.jsonDecoder))
    if (countryregioncode.isRight && currencycode.isRight)
      Right(CountryregioncurrencyId(countryregioncode = countryregioncode.toOption.get, currencycode = currencycode.toOption.get))
    else Left(List[Either[String, Any]](countryregioncode, currencycode).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[CountryregioncurrencyId] = new JsonEncoder[CountryregioncurrencyId] {
    override def unsafeEncode(a: CountryregioncurrencyId, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""countryregioncode":""")
      CountryregionId.jsonEncoder.unsafeEncode(a.countryregioncode, indent, out)
      out.write(",")
      out.write(""""currencycode":""")
      CurrencyId.jsonEncoder.unsafeEncode(a.currencycode, indent, out)
      out.write("}")
    }
  }
  implicit lazy val ordering: Ordering[CountryregioncurrencyId] = Ordering.by(x => (x.countryregioncode, x.currencycode))
}
