package testdb
package hardcoded
package myschema

import anorm.Column
import anorm.SqlMappingError
import anorm.ToStatement
import play.api.libs.json.JsError
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsValue
import play.api.libs.json.Reads
import play.api.libs.json.Writes

sealed abstract class SectorEnum(val value: String)
object SectorEnum {
  case object PUBLIC extends SectorEnum("PUBLIC")
  case object PRIVATE extends SectorEnum("PRIVATE")
  case object OTHER extends SectorEnum("OTHER")

  val All: List[SectorEnum] = List(PUBLIC, PRIVATE, OTHER)
  val Names: String = All.map(_.value).mkString(", ")
  val ByName: Map[String, SectorEnum] = All.map(x => (x.value, x)).toMap

  implicit val column: Column[SectorEnum] =
    implicitly[Column[String]]
      .mapResult { str => ByName.get(str).toRight(SqlMappingError(s"$str was not among ${ByName.keys}")) }
  implicit val toStatement: ToStatement[SectorEnum] =
    implicitly[ToStatement[String]].contramap(_.value)
  implicit val reads: Reads[SectorEnum] = (value: JsValue) =>
    value.validate[String].flatMap { str =>
      ByName.get(str) match {
        case Some(value) => JsSuccess(value)
        case None => JsError(s"'$str' does not match any of the following legal values: $Names")
      }
    }

  implicit val writes: Writes[SectorEnum] = value => implicitly[Writes[String]].writes(value.value)
}
