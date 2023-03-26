package typo
package sqlscripts

import java.nio.file.Path

case class SqlScript(relPath: Path, content: String, metadataParams: List[MetadataParameterColumn], cols: List[db.Col])

object SqlScript {
  // todo: provide missing instances somehow
  def scalaType(col: MetadataColumn): Option[sc.Type] = {
    def other = {
      col.columnTypeName match {
        case "hstore" =>
          sc.Type.JavaMap.of(sc.Type.String, sc.Type.String).withComment(col.columnTypeName)
        case "regtype" | "anyarray" =>
          sc.Type.PGobject.withComment(col.columnTypeName)
        case other =>
          sc.Type.Qualified(col.columnClassName).withComment(other)
      }
    }

    val baseType: Option[sc.Type] =
      col.columnType match {
        case JdbcType.Array =>
          // adding to this list? check if you should also generate instances for it
          col.columnTypeName match {
            case "_bit" | "_bool"        => Some(sc.Type.Array.of(sc.Type.Boolean))
            case "_int4"                 => Some(sc.Type.Array.of(sc.Type.Int))
            case "_int8"                 => Some(sc.Type.Array.of(sc.Type.Long))
            case "_oid"                  => Some(sc.Type.Array.of(sc.Type.Long)) // true enough, really unsigned int
            case "_float4"               => Some(sc.Type.Array.of(sc.Type.Float))
            case "_float8"               => Some(sc.Type.Array.of(sc.Type.Double))
            case "_uuid"                 => Some(sc.Type.Array.of(sc.Type.UUID))
            case "_regtype"              => Some(sc.Type.Array.of(sc.Type.PGobject))
            case "_decimal" | "_numeric" => Some(sc.Type.Array.of(sc.Type.BigDecimal))
            case "_varchar" | "_char" | "_text" | "_bpchar" | "_name" =>
              Some(sc.Type.Array.of(sc.Type.String))
            case _ => None
          }
        case JdbcType.BigInt                => Some(sc.Type.Long)
        case JdbcType.Binary                => Some(sc.Type.Array.of(sc.Type.Byte))
        case JdbcType.Bit                   => Some(sc.Type.Boolean)
        case JdbcType.Blob                  => None
        case JdbcType.Boolean               => Some(sc.Type.Boolean)
        case JdbcType.Char                  => Some(sc.Type.String)
        case JdbcType.Clob                  => None
        case JdbcType.DataLink              => None
        case JdbcType.Date                  => Some(sc.Type.LocalDate)
        case JdbcType.Decimal               => Some(sc.Type.BigDecimal)
        case JdbcType.Distinct              => None
        case JdbcType.Double                => Some(sc.Type.Double)
        case JdbcType.Float                 => Some(sc.Type.Double)
        case JdbcType.Integer               => Some(sc.Type.Int)
        case JdbcType.JavaObject            => Some(other)
        case JdbcType.LongnVarChar          => Some(sc.Type.String)
        case JdbcType.LongVarBinary         => Some(sc.Type.Array.of(sc.Type.Byte))
        case JdbcType.LongVarChar           => Some(sc.Type.String)
        case JdbcType.NChar                 => Some(sc.Type.String)
        case JdbcType.NClob                 => None
        case JdbcType.Null                  => None
        case JdbcType.Numeric               => Some(sc.Type.BigDecimal)
        case JdbcType.NVarChar              => Some(sc.Type.String)
        case JdbcType.Other                 => Some(other)
        case JdbcType.Real                  => Some(sc.Type.Float)
        case JdbcType.Ref                   => None
        case JdbcType.RefCursor             => None
        case JdbcType.RowId                 => None
        case JdbcType.SmallInt              => Some(sc.Type.Int)
        case JdbcType.SqlXml                => None
        case JdbcType.Struct                => None
        case JdbcType.Time                  => Some(sc.Type.LocalTime)
        case JdbcType.TimeWithTimezone      => None
        case JdbcType.Timestamp             => Some(sc.Type.LocalDateTime)
        case JdbcType.TimestampWithTimezone => Some(sc.Type.ZonedDateTime)
        case JdbcType.TinyInt               => Some(sc.Type.Byte)
        case JdbcType.VarBinary             => Some(sc.Type.Array.of(sc.Type.Byte))
        case JdbcType.VarChar               => Some(sc.Type.String)
        case JdbcType.Unknown(_)            => None
      }

    baseType.map { baseType =>
      col.isNullable match {
        case ColumnNullable.NoNulls         => baseType
        case ColumnNullable.Nullable        => sc.Type.Option.of(baseType)
        case ColumnNullable.NullableUnknown => sc.Type.Option.of(baseType).withComment("unknown nullability")
      }
    }
  }
}
