/**
 * File automatically generated by `typo` for its own test suite.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN
 */
package testdb
package hardcoded
package myschema

import anorm.Column
import anorm.ParameterMetaData
import anorm.SqlMappingError
import anorm.ToStatement
import java.sql.Types
import play.api.libs.json.JsError
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsValue
import play.api.libs.json.Reads
import play.api.libs.json.Writes

/** Enum `myschema.sector`
  *  - PUBLIC
  *  - PRIVATE
  *  - OTHER
  */
sealed abstract class Sector(val value: String)

object Sector {
  def apply(str: String): Either[String, Sector] =
    ByName.get(str).toRight(s"'$str' does not match any of the following legal values: $Names")
  def force(str: String): Sector =
    apply(str) match {
      case Left(msg) => sys.error(msg)
      case Right(value) => value
    }
  case object `_public` extends Sector("PUBLIC")
  case object `_private` extends Sector("PRIVATE")
  case object `_other` extends Sector("OTHER")
  val All: List[Sector] = List(`_public`, `_private`, `_other`)
  val Names: String = All.map(_.value).mkString(", ")
  val ByName: Map[String, Sector] = All.map(x => (x.value, x)).toMap
              
  implicit lazy val arrayColumn: Column[Array[Sector]] = Column.columnToArray[String](Column.columnToString, implicitly).map(_.map(Sector.force))
  implicit lazy val arrayToStatement: ToStatement[Array[Sector]] = ToStatement[Array[Sector]]((ps, i, arr) => ps.setArray(i, ps.getConnection.createArrayOf("myschema.sector", arr.map[AnyRef](_.value))))
  implicit lazy val column: Column[Sector] = Column.columnToString.mapResult(str => Sector(str).left.map(SqlMappingError.apply))
  implicit lazy val ordering: Ordering[Sector] = Ordering.by(_.value)
  implicit lazy val parameterMetadata: ParameterMetaData[Sector] = new ParameterMetaData[Sector] {
    override def sqlType: String = "myschema.sector"
    override def jdbcType: Int = Types.OTHER
  }
  implicit lazy val reads: Reads[Sector] = Reads[Sector]{(value: JsValue) => value.validate(Reads.StringReads).flatMap(str => Sector(str).fold(JsError.apply, JsSuccess(_)))}
  implicit lazy val text: Text[Sector] = new Text[Sector] {
    override def unsafeEncode(v: Sector, sb: StringBuilder) = Text.stringInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: Sector, sb: StringBuilder) = Text.stringInstance.unsafeArrayEncode(v.value, sb)
  }
  implicit lazy val toStatement: ToStatement[Sector] = ToStatement.stringToStatement.contramap(_.value)
  implicit lazy val writes: Writes[Sector] = Writes[Sector](value => Writes.StringWrites.writes(value.value))
}
