/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks

import anorm.Column
import anorm.MetaDataItem
import anorm.ParameterMetaData
import anorm.SqlRequestError
import anorm.ToStatement
import anorm.TypeDoesNotMatch
import java.sql.PreparedStatement
import java.sql.Types
import org.postgresql.jdbc.PgArray
import org.postgresql.util.PGobject
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

/** int2vector (via PGObject) */
case class TypoInt2Vector(value: String)
object TypoInt2Vector {
  implicit val oFormat: OFormat[TypoInt2Vector] = new OFormat[TypoInt2Vector]{
    override def writes(o: TypoInt2Vector): JsObject =
      Json.obj(
        "value" -> o.value
      )
  
    override def reads(json: JsValue): JsResult[TypoInt2Vector] = {
      JsResult.fromTry(
        Try(
          TypoInt2Vector(
            value = json.\("value").as[String]
          )
        )
      )
    }
  }
  implicit val TypoInt2VectorDb: ToStatement[TypoInt2Vector] with ParameterMetaData[TypoInt2Vector] with Column[TypoInt2Vector] = new ToStatement[TypoInt2Vector] with ParameterMetaData[TypoInt2Vector] with Column[TypoInt2Vector] {
    override def sqlType: String = "int2vector"
    override def jdbcType: Int = Types.OTHER
    override def set(s: PreparedStatement, index: Int, v: TypoInt2Vector): Unit =
      s.setObject(index, {
                           val obj = new PGobject
                           obj.setType("int2vector")
                           obj.setValue(v.value)
                           obj
                         })
    override def apply(v: Any, v2: MetaDataItem): Either[SqlRequestError, TypoInt2Vector] =
      v match {
        case v: PGobject => Right(TypoInt2Vector(v.getValue))
        case other => Left(TypeDoesNotMatch(s"Expected instance of PGobject from JDBC to produce a TypoInt2Vector, got ${other.getClass.getName}"))
      }
  }
  
  implicit val TypoInt2VectorDbArray: ToStatement[Array[TypoInt2Vector]] with ParameterMetaData[Array[TypoInt2Vector]] with Column[Array[TypoInt2Vector]] = new ToStatement[Array[TypoInt2Vector]] with ParameterMetaData[Array[TypoInt2Vector]] with Column[Array[TypoInt2Vector]] {
    override def sqlType: String = "_int2vector"
    override def jdbcType: Int = Types.ARRAY
    override def set(s: PreparedStatement, index: Int, v: Array[TypoInt2Vector]): Unit =
      s.setArray(index, s.getConnection.createArrayOf("int2vector", v.map(v => {
                                                                                 val obj = new PGobject
                                                                                 obj.setType("int2vector")
                                                                                 obj.setValue(v.value)
                                                                                 obj
                                                                               })))
    override def apply(v: Any, v2: MetaDataItem): Either[SqlRequestError, Array[TypoInt2Vector]] =
      v match {
        case v: PgArray =>
         v.getArray match {
           case v: Array[_] =>
             Right(v.map(v => TypoInt2Vector(v.asInstanceOf[String])))
           case other => Left(TypeDoesNotMatch(s"Expected one-dimensional array from JDBC to produce an array of TypoInt2Vector, got ${other.getClass.getName}"))
         }
        case other => Left(TypeDoesNotMatch(s"Expected PgArray from JDBC to produce an array of TypoInt2Vector, got ${other.getClass.getName}"))
      }
  }

}