package typo.dsl

import java.lang.{Boolean as JBool, Byte as JByte, Double as JDouble, Float as JFloat, Long as JLong, Short as JShort}
import java.math.{BigInteger, BigDecimal as JBigDec}
import java.net.{URI, URL}
import java.sql.{Timestamp, Types}
import java.time.OffsetDateTime
import java.util.{Date, UUID as JUUID}

/** ** Copied from anorm**
  *
  * Parameter meta data for type `T`
  */
@annotation.implicitNotFound(
  "Meta data not found for parameter of type ${T}: `typo.dsl.ParameterMetaData[${T}]` required; See https://github.com/playframework/anorm/blob/main/docs/manual/working/scalaGuide/main/sql/ScalaAnorm.md#parameters"
)
trait ParameterMetaData[T] {

  /** Name of SQL type (see `java.sql.Types`)
    */
  def sqlType: String

  /** JDBC type (see `java.sql.Types`)
    */
  def jdbcType: Int
}

/** ParameterMetaData companion, providing defaults based on SQL92.
  */
object ParameterMetaData extends JavaTimeParameterMetaData {
  def instance[T](_sqlType: String, _jdbcType: Int): ParameterMetaData[T] = new ParameterMetaData[T] {
    val sqlType = _sqlType
    val jdbcType = _jdbcType
  }

  /** Binary meta data */
  implicit object BlobParameterMetaData extends ParameterMetaData[java.sql.Blob] {
    val sqlType = "BLOB"
    val jdbcType = Types.BLOB
  }

  /** Array of byte meta data */
  implicit object ByteArrayParameterMetaData extends ParameterMetaData[Array[Byte]] {
    val sqlType = "LONGVARBINARY"
    val jdbcType = Types.LONGVARBINARY
  }

  implicit object InputStreamParameterMetaData extends ParameterMetaData[java.io.InputStream] {
    val sqlType = ByteArrayParameterMetaData.sqlType
    val jdbcType = ByteArrayParameterMetaData.jdbcType
  }

  /** Boolean parameter meta data */
  implicit object BooleanParameterMetaData extends ParameterMetaData[Boolean] {
    val sqlType = "BOOLEAN"
    val jdbcType = Types.BOOLEAN
  }

  implicit object JBooleanParameterMetaData extends ParameterMetaData[JBool] {
    val sqlType = BooleanParameterMetaData.sqlType
    val jdbcType = BooleanParameterMetaData.jdbcType
  }

  /** Clob meta data */
  implicit object ClobParameterMetaData extends ParameterMetaData[java.sql.Clob] {
    val sqlType = "CLOB"
    val jdbcType = Types.CLOB
  }

  /** Double parameter meta data */
  implicit object DoubleParameterMetaData extends ParameterMetaData[Double] {
    val sqlType = "DOUBLE PRECISION"
    val jdbcType = Types.DOUBLE
  }

  implicit object JDoubleParameterMetaData extends ParameterMetaData[JDouble] {
    val sqlType = DoubleParameterMetaData.sqlType
    val jdbcType = DoubleParameterMetaData.jdbcType
  }

  /** Float parameter meta data */
  implicit object FloatParameterMetaData extends ParameterMetaData[Float] {
    val sqlType = "FLOAT"
    val jdbcType = Types.FLOAT
  }

  implicit object JFloatParameterMetaData extends ParameterMetaData[JFloat] {
    val sqlType = FloatParameterMetaData.sqlType
    val jdbcType = FloatParameterMetaData.jdbcType
  }

  /** Integer parameter meta data */
  implicit object IntParameterMetaData extends ParameterMetaData[Int] {
    val sqlType = "INTEGER"
    val jdbcType = Types.INTEGER
  }

  implicit object ByteParameterMetaData extends ParameterMetaData[Byte] {
    val sqlType = IntParameterMetaData.sqlType
    val jdbcType = Types.TINYINT
  }

  implicit object JByteParameterMetaData extends ParameterMetaData[JByte] {
    val sqlType = IntParameterMetaData.sqlType
    val jdbcType = ByteParameterMetaData.jdbcType
  }

  implicit object IntegerParameterMetaData extends ParameterMetaData[Integer] {
    val sqlType = IntParameterMetaData.sqlType
    val jdbcType = IntParameterMetaData.jdbcType
  }

  implicit object ShortParameterMetaData extends ParameterMetaData[Short] {
    val sqlType = IntParameterMetaData.sqlType
    val jdbcType = Types.SMALLINT
  }

  implicit object JShortParameterMetaData extends ParameterMetaData[JShort] {
    val sqlType = IntParameterMetaData.sqlType
    val jdbcType = ShortParameterMetaData.jdbcType
  }

  /** Numeric (big integer) parameter meta data */
  implicit object BigIntParameterMetaData extends ParameterMetaData[BigInt] {
    val sqlType = "NUMERIC"
    val jdbcType = Types.BIGINT
  }

  implicit object BigIntegerParameterMetaData extends ParameterMetaData[BigInteger] {
    val sqlType = BigIntParameterMetaData.sqlType
    val jdbcType = BigIntParameterMetaData.jdbcType
  }

  implicit object LongParameterMetaData extends ParameterMetaData[Long] {
    val sqlType = BigIntParameterMetaData.sqlType
    val jdbcType = BigIntParameterMetaData.jdbcType
  }

  implicit object JLongParameterMetaData extends ParameterMetaData[JLong] {
    val sqlType = BigIntParameterMetaData.sqlType
    val jdbcType = BigIntParameterMetaData.jdbcType
  }

  /** Decimal (big decimal) parameter meta data */
  implicit object BigDecimalParameterMetaData extends ParameterMetaData[BigDecimal] {
    val sqlType = "DECIMAL"
    val jdbcType = Types.DECIMAL
  }

  implicit object JBigDecParameterMetaData extends ParameterMetaData[JBigDec] {
    val sqlType = BigDecimalParameterMetaData.sqlType
    val jdbcType = BigDecimalParameterMetaData.jdbcType
  }

  /** Timestamp parameter meta data */
  implicit object TimestampParameterMetaData extends ParameterMetaData[Timestamp] {
    val sqlType = "TIMESTAMP"
    val jdbcType = Types.TIMESTAMP
  }

  implicit object DateParameterMetaData extends ParameterMetaData[Date] {
    val sqlType = TimestampParameterMetaData.sqlType
    val jdbcType = TimestampParameterMetaData.jdbcType
  }

  @SuppressWarnings(Array("MethodNames"))
  implicit def TimestampWrapper1MetaData[T <: { def getTimestamp: java.sql.Timestamp }]: ParameterMetaData[T] = new ParameterMetaData[T] {
    val sqlType = TimestampParameterMetaData.sqlType
    val jdbcType = TimestampParameterMetaData.jdbcType
  }

  /** String/VARCHAR parameter meta data */
  implicit object StringParameterMetaData extends ParameterMetaData[String] {
    val sqlType = "VARCHAR"
    val jdbcType = Types.VARCHAR
  }

  implicit object UUIDParameterMetaData extends ParameterMetaData[JUUID] {
    val sqlType = StringParameterMetaData.sqlType
    val jdbcType = StringParameterMetaData.jdbcType
  }

  implicit object URIParameterMetaData extends ParameterMetaData[URI] {
    val sqlType = StringParameterMetaData.sqlType
    val jdbcType = StringParameterMetaData.jdbcType
  }

  implicit object URLParameterMetaData extends ParameterMetaData[URL] {
    val sqlType = StringParameterMetaData.sqlType
    val jdbcType = StringParameterMetaData.jdbcType
  }

  implicit object CharacterStreamMetaData extends ParameterMetaData[java.io.Reader] {
    val sqlType = StringParameterMetaData.sqlType
    val jdbcType = StringParameterMetaData.jdbcType
  }

  /** Character parameter meta data */
  implicit object CharParameterMetaData extends ParameterMetaData[Char] {
    val sqlType = "CHAR"
    val jdbcType = Types.CHAR
  }

  implicit object CharacterParameterMetaData extends ParameterMetaData[Character] {
    val sqlType = CharParameterMetaData.sqlType
    val jdbcType = CharParameterMetaData.jdbcType
  }
}

sealed trait JavaTimeParameterMetaData {
  import java.time.{Instant, LocalDate, LocalDateTime, ZonedDateTime}

  /** Parameter metadata for Java8 instant */
  implicit object InstantParameterMetaData extends ParameterMetaData[Instant] {
    val sqlType = "TIMESTAMPZ"
    val jdbcType = Types.TIMESTAMP_WITH_TIMEZONE
  }

  /** Parameter metadata for Java8 local date/time */
  implicit object LocalDateTimeParameterMetaData extends ParameterMetaData[LocalDateTime] {
    val sqlType = "TIMESTAMP"
    val jdbcType = Types.TIMESTAMP
  }

  /** Parameter metadata for Java8 local date */
  implicit object LocalDateParameterMetaData extends ParameterMetaData[LocalDate] {
    val sqlType = "TIMESTAMP"
    val jdbcType = Types.TIMESTAMP
  }

  /** Parameter metadata for Java8 zoned date/time */
  implicit object ZonedDateTimeParameterMetaData extends ParameterMetaData[ZonedDateTime] {
    val sqlType = "TIMESTAMPZ"
    val jdbcType = Types.TIMESTAMP_WITH_TIMEZONE
  }

  /** Parameter metadata for Java8 offset date/time */
  implicit object OffsetDateTimeParameterMetaData extends ParameterMetaData[OffsetDateTime] {
    val sqlType = "TIMESTAMPZ"
    val jdbcType = Types.TIMESTAMP_WITH_TIMEZONE
  }

  // added in typo. this is postgres-specific
  implicit def arrayParameterMetaData[T](implicit T: ParameterMetaData[T]): ParameterMetaData[Array[T]] = new ParameterMetaData[Array[T]] {
    override def sqlType: java.lang.String = "_" + T.sqlType
    override def jdbcType: scala.Int = java.sql.Types.ARRAY
  }
}
