/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritoryhistory

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.sales.salesterritory.SalesterritoryId
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** Type for the composite primary key of table `sales.salesterritoryhistory` */
case class SalesterritoryhistoryId(
  businessentityid: BusinessentityId,
  startdate: TypoLocalDateTime,
  territoryid: SalesterritoryId
)
object SalesterritoryhistoryId {
  implicit lazy val jsonDecoder: JsonDecoder[SalesterritoryhistoryId] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val businessentityid = jsonObj.get("businessentityid").toRight("Missing field 'businessentityid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val startdate = jsonObj.get("startdate").toRight("Missing field 'startdate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    val territoryid = jsonObj.get("territoryid").toRight("Missing field 'territoryid'").flatMap(_.as(SalesterritoryId.jsonDecoder))
    if (businessentityid.isRight && startdate.isRight && territoryid.isRight)
      Right(SalesterritoryhistoryId(businessentityid = businessentityid.toOption.get, startdate = startdate.toOption.get, territoryid = territoryid.toOption.get))
    else Left(List[Either[String, Any]](businessentityid, startdate, territoryid).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[SalesterritoryhistoryId] = new JsonEncoder[SalesterritoryhistoryId] {
    override def unsafeEncode(a: SalesterritoryhistoryId, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""businessentityid":""")
      BusinessentityId.jsonEncoder.unsafeEncode(a.businessentityid, indent, out)
      out.write(",")
      out.write(""""startdate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.startdate, indent, out)
      out.write(",")
      out.write(""""territoryid":""")
      SalesterritoryId.jsonEncoder.unsafeEncode(a.territoryid, indent, out)
      out.write("}")
    }
  }
  implicit def ordering(implicit O0: Ordering[TypoLocalDateTime]): Ordering[SalesterritoryhistoryId] = Ordering.by(x => (x.businessentityid, x.startdate, x.territoryid))
}
