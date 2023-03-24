package typo

import typo.doobie.Nullability

object typeMapper {
  def apply(pkg: sc.QIdent, col: db.Col): sc.Type = {
    def go(tpe: db.Type): sc.Type = tpe match {
      case db.Type.PgObject         => sc.Type.PGobject
      case db.Type.BigInt           => sc.Type.Long
      case db.Type.Text             => sc.Type.String
      case db.Type.AnyArray         => sc.Type.PGobject
      case db.Type.Boolean          => sc.Type.Boolean
      case db.Type.Char             => sc.Type.String
      case db.Type.Name             => sc.Type.String
      case db.Type.StringEnum(name) => sc.Type.Qualified(names.EnumName(pkg, name))
      case db.Type.Hstore           => sc.Type.JavaMap.of(sc.Type.String, sc.Type.String)
      case db.Type.Inet             => sc.Type.PGobject
      case db.Type.Oid              => sc.Type.Long // wip
      case db.Type.VarChar(_)       => sc.Type.String
      case db.Type.Float4           => sc.Type.Float
      case db.Type.Float8           => sc.Type.Double
      case db.Type.Bytea            => sc.Type.Array.of(sc.Type.Byte)
      case db.Type.Int2             => sc.Type.Int // jdbc driver seems to return ints instead of floats
      case db.Type.Int4             => sc.Type.Int
      case db.Type.Int8             => sc.Type.Long
      case db.Type.Json             => sc.Type.String // wip
      case db.Type.Numeric          => sc.Type.BigDecimal
      case db.Type.Timestamp        => sc.Type.LocalDateTime
      case db.Type.TimestampTz      => sc.Type.ZonedDateTime
      case db.Type.Array(tpe)       => sc.Type.Array.of(go(tpe))
      case db.Type.PGbox            => sc.Type.PGbox
      case db.Type.PGcircle         => sc.Type.PGcircle
      case db.Type.PGline           => sc.Type.PGline
      case db.Type.PGlseg           => sc.Type.PGlseg
      case db.Type.PGpath           => sc.Type.PGpath
      case db.Type.PGpoint          => sc.Type.PGpoint
      case db.Type.PGpolygon        => sc.Type.PGpolygon
      case db.Type.PGInterval       => sc.Type.PGInterval
      case db.Type.PGmoney          => sc.Type.PGmoney
    }

    val baseTpe = go(col.tpe)

    col.nullability match {
      case Nullability.NoNulls         => baseTpe
      case Nullability.Nullable        => sc.Type.Option.of(baseTpe)
      case Nullability.NullableUnknown => sc.Type.Option.of(baseTpe).withComment("nullability unknown")
    }
  }

  def typeFromUdtName(enums: Map[String, db.StringEnum], udtName: String, characterMaximumLength: Option[Int]): db.Type = {
    udtName match {
      case "aclitem" => db.Type.PgObject // i.e. "postgres=arwdDxt/postgres"
      case "anyarray" => db.Type.AnyArray
      case "bool" => db.Type.Boolean
      case "char" => db.Type.Char
      case "float4" => db.Type.Float4
      case "float8" => db.Type.Float8
      case "hstore" => db.Type.Hstore
      case "inet" => db.Type.Inet
      case "int2" => db.Type.Int2
      case "int2vector" => db.Type.PgObject
      case "int4" => db.Type.Int4
      case "int8" => db.Type.Int8
      case "json" => db.Type.Json
      case "name" => db.Type.Name
      case "numeric" => db.Type.Numeric
      case "oid" => db.Type.Oid
      case "oidvector" => db.Type.PgObject // space separated oids referencing i.e. pg_collation.oid
      case "pg_node_tree" => db.Type.PgObject // Expression trees (in nodeToString() representation)
      case "regproc" => db.Type.PgObject
      case "text" => db.Type.Text
      case "timestamp" => db.Type.Timestamp
      case "timestamptz" => db.Type.TimestampTz
      case "varchar" => db.Type.VarChar(characterMaximumLength)
      case "xid" => db.Type.PgObject // transaction ID
      case "regtype" => db.Type.PgObject
      case "bytea" => db.Type.Bytea
      case "box" => db.Type.PGbox
      case "circle" => db.Type.PGcircle
      case "line" => db.Type.PGline
      case "lseg" => db.Type.PGlseg
      case "path" => db.Type.PGpath
      case "point" => db.Type.PGpoint
      case "polygon" => db.Type.PGpolygon
      case "interval" => db.Type.PGInterval
      case "money" => db.Type.PGmoney

      case str if str.startsWith("_") => db.Type.Array(typeFromUdtName(enums, udtName.drop(1), characterMaximumLength))
      case typeName =>
        enums.get(typeName) match {
          case Some(enum) =>
            db.Type.StringEnum(enum.name)
          case None =>
            System.err.println(s"Couldn't translate type from column $udtName")
            db.Type.Text
        }
    }
  }

}
