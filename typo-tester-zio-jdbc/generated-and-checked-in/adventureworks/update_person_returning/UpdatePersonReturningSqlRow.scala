/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package update_person_returning

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.userdefined.FirstName
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** SQL file: update_person_returning.sql */
case class UpdatePersonReturningSqlRow(
  /** Points to [[person.person.PersonRow.firstname]] */
  firstname: /* user-picked */ FirstName,
  /** Points to [[person.person.PersonRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object UpdatePersonReturningSqlRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[UpdatePersonReturningSqlRow] = new JdbcDecoder[UpdatePersonReturningSqlRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, UpdatePersonReturningSqlRow) =
      columIndex + 1 ->
        UpdatePersonReturningSqlRow(
          firstname = FirstName.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[UpdatePersonReturningSqlRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val firstname = jsonObj.get("firstname").toRight("Missing field 'firstname'").flatMap(_.as(FirstName.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (firstname.isRight && modifieddate.isRight)
      Right(UpdatePersonReturningSqlRow(firstname = firstname.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](firstname, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[UpdatePersonReturningSqlRow] = new JsonEncoder[UpdatePersonReturningSqlRow] {
    override def unsafeEncode(a: UpdatePersonReturningSqlRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""firstname":""")
      FirstName.jsonEncoder.unsafeEncode(a.firstname, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
}
