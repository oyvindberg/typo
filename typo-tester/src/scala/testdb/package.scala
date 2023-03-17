import anorm.{Column, MetaDataItem, ParameterMetaData, SqlRequestError, ToStatement}
import org.postgresql.geometric._
import org.postgresql.util.{PGInterval, PGmoney, PGobject}
import play.api.libs.json.Format

import java.sql.PreparedStatement
import java.util.UUID

package object testdb {
  type ToDb[T] = ToStatement[T] with ParameterMetaData[T]

  def writeArray[T](typeName: String)(implicit ev: T => AnyRef): ToDb[Array[T]] =
    new ToStatement[Array[T]] with ParameterMetaData[Array[T]] {
      override def sqlType: String = typeName

      override def jdbcType: Int = java.sql.Types.ARRAY

      override def set(ps: PreparedStatement, index: Int, v: Array[T]): Unit =
        ps.setArray(index, ps.getConnection.createArrayOf(typeName, v.map(ev)))
    }

  implicit val str: ToDb[Array[String]] = writeArray[String]("_varchar")
  implicit val float: ToDb[Array[Float]] = writeArray[Float]("_float4")
  implicit val int: ToDb[Array[Int]] = writeArray[Int]("_int4")
  implicit val long: ToDb[Array[Long]] = writeArray[Long]("_int8")
  implicit val boolean: ToDb[Array[Boolean]] = writeArray[Boolean]("_bool")
  implicit val double: ToDb[Array[Double]] = writeArray[Double]("_float8")
  implicit val uuid: ToDb[Array[UUID]] = writeArray[UUID]("_uuid")(identity)
  implicit val bigDecimal: ToDb[Array[BigDecimal]] = writeArray[BigDecimal]("_decimal")(identity)

  def castOther[T](typeName: String): ToDb[T] with Column[T] = new ToStatement[T] with ParameterMetaData[T] with Column[T] {
    override def sqlType: String = typeName
    override def jdbcType: Int = java.sql.Types.OTHER
    override def set(s: PreparedStatement, index: Int, v: T): Unit = s.setObject(index, v)
    override def apply(v1: Any, v2: MetaDataItem): Either[SqlRequestError, T] = Right(v1.asInstanceOf[T])
  }

  implicit val boxDb: ToDb[PGbox] with Column[PGbox] = castOther[PGbox]("box")
  implicit val circleDb: ToDb[PGcircle] with Column[PGcircle] = castOther[PGcircle]("circle")
  implicit val lineDb: ToDb[PGline] with Column[PGline] = castOther[PGline]("line")
  implicit val lsegDb: ToDb[PGlseg] with Column[PGlseg] = castOther[PGlseg]("lseg")
  implicit val pathDb: ToDb[PGpath] with Column[PGpath] = castOther[PGpath]("path")
  implicit val pointDb: ToDb[PGpoint] with Column[PGpoint] = castOther[PGpoint]("point")
  implicit val polygonDb: ToDb[PGpolygon] with Column[PGpolygon] = castOther[PGpolygon]("polygon")
  implicit val moneyDb: ToDb[PGmoney] with Column[PGmoney] = castOther[PGmoney]("money")
  implicit val intervalDb: ToDb[PGInterval] with Column[PGInterval] = castOther[PGInterval]("interval")

  implicit val hstoreDb = castOther[java.util.Map[String, String]]("hstore")

  def pgObjectFormat[T <: PGobject](f: String => T): Format[T] =
    implicitly[Format[String]].bimap[T](f, _.getValue)

  implicit val boxCodec: Format[PGbox] = pgObjectFormat(new PGbox(_))
  implicit val circleCodec: Format[PGcircle] = pgObjectFormat(new PGcircle(_))
  implicit val lineCodec: Format[PGline] = pgObjectFormat(new PGline(_))
  implicit val lsegCodec: Format[PGlseg] = pgObjectFormat(new PGlseg(_))
  implicit val pathCodec: Format[PGpath] = pgObjectFormat(new PGpath(_))
  implicit val pointCodec: Format[PGpoint] = pgObjectFormat(new PGpoint(_))
  implicit val polygonCodec: Format[PGpolygon] = pgObjectFormat(new PGpolygon(_))
  implicit val moneyCodec: Format[PGmoney] = pgObjectFormat(new PGmoney(_))
  implicit val intervalCodec: Format[PGInterval] = pgObjectFormat(new PGInterval(_))

  import scala.jdk.CollectionConverters._
  implicit val hstoreCodec: Format[java.util.Map[String, String]] = implicitly[Format[Map[String, String]]].bimap(_.asJava, _.asScala.toMap)
}
