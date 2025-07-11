/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package test_sak_soknadsalternativ

import adventureworks.public.test_utdanningstilbud.TestUtdanningstilbudId
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** Type for the composite primary key of table `public.test_sak_soknadsalternativ` */
case class TestSakSoknadsalternativId(
  organisasjonskodeSaksbehandler: String,
  utdanningsmulighetKode: String
)
object TestSakSoknadsalternativId {
  def from(TestUtdanningstilbudId: TestUtdanningstilbudId, organisasjonskodeSaksbehandler: String): TestSakSoknadsalternativId = TestSakSoknadsalternativId(
    organisasjonskodeSaksbehandler = organisasjonskodeSaksbehandler,
    utdanningsmulighetKode = TestUtdanningstilbudId.utdanningsmulighetKode
  )
  implicit lazy val jsonDecoder: JsonDecoder[TestSakSoknadsalternativId] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val organisasjonskodeSaksbehandler = jsonObj.get("organisasjonskode_saksbehandler").toRight("Missing field 'organisasjonskode_saksbehandler'").flatMap(_.as(JsonDecoder.string))
    val utdanningsmulighetKode = jsonObj.get("utdanningsmulighet_kode").toRight("Missing field 'utdanningsmulighet_kode'").flatMap(_.as(JsonDecoder.string))
    if (organisasjonskodeSaksbehandler.isRight && utdanningsmulighetKode.isRight)
      Right(TestSakSoknadsalternativId(organisasjonskodeSaksbehandler = organisasjonskodeSaksbehandler.toOption.get, utdanningsmulighetKode = utdanningsmulighetKode.toOption.get))
    else Left(List[Either[String, Any]](organisasjonskodeSaksbehandler, utdanningsmulighetKode).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[TestSakSoknadsalternativId] = new JsonEncoder[TestSakSoknadsalternativId] {
    override def unsafeEncode(a: TestSakSoknadsalternativId, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""organisasjonskode_saksbehandler":""")
      JsonEncoder.string.unsafeEncode(a.organisasjonskodeSaksbehandler, indent, out)
      out.write(",")
      out.write(""""utdanningsmulighet_kode":""")
      JsonEncoder.string.unsafeEncode(a.utdanningsmulighetKode, indent, out)
      out.write("}")
    }
  }
  implicit lazy val ordering: Ordering[TestSakSoknadsalternativId] = Ordering.by(x => (x.organisasjonskodeSaksbehandler, x.utdanningsmulighetKode))
}
