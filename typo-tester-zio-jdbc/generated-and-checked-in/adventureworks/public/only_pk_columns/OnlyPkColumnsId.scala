/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package only_pk_columns

import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** Type for the composite primary key of table `public.only_pk_columns` */
case class OnlyPkColumnsId(
  keyColumn1: String,
  keyColumn2: Int
)
object OnlyPkColumnsId {
  implicit lazy val jsonDecoder: JsonDecoder[OnlyPkColumnsId] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val keyColumn1 = jsonObj.get("key_column_1").toRight("Missing field 'key_column_1'").flatMap(_.as(JsonDecoder.string))
    val keyColumn2 = jsonObj.get("key_column_2").toRight("Missing field 'key_column_2'").flatMap(_.as(JsonDecoder.int))
    if (keyColumn1.isRight && keyColumn2.isRight)
      Right(OnlyPkColumnsId(keyColumn1 = keyColumn1.toOption.get, keyColumn2 = keyColumn2.toOption.get))
    else Left(List[Either[String, Any]](keyColumn1, keyColumn2).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[OnlyPkColumnsId] = new JsonEncoder[OnlyPkColumnsId] {
    override def unsafeEncode(a: OnlyPkColumnsId, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""key_column_1":""")
      JsonEncoder.string.unsafeEncode(a.keyColumn1, indent, out)
      out.write(",")
      out.write(""""key_column_2":""")
      JsonEncoder.int.unsafeEncode(a.keyColumn2, indent, out)
      out.write("}")
    }
  }
  implicit lazy val ordering: Ordering[OnlyPkColumnsId] = Ordering.by(x => (x.keyColumn1, x.keyColumn2))
}