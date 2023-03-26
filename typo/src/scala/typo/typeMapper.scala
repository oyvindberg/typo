package typo

import typo.generated.information_schema.ColumnsRow

object typeMapper {
  def scalaType(pkg: sc.QIdent, col: db.Col): sc.Type = {
    def go(tpe: db.Type): sc.Type = tpe match {
      case db.Type.PgObject(tpe)    => sc.Type.PGobject.withComment(tpe)
      case db.Type.BigInt           => sc.Type.Long
      case db.Type.Text             => sc.Type.String
      case db.Type.Boolean          => sc.Type.Boolean
      case db.Type.Char             => sc.Type.String
      case db.Type.Name             => sc.Type.String
      case db.Type.StringEnum(name) => sc.Type.Qualified(names.EnumName(pkg, name))
      case db.Type.Hstore           => sc.Type.JavaMap.of(sc.Type.String, sc.Type.String)
      case db.Type.Inet             => sc.Type.PGobject.withComment("inet") // wip
      case db.Type.Oid              => sc.Type.Long.withComment("oid")
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
      case db.Type.UUID             => sc.Type.UUID
    }

    val baseTpe = go(col.tpe)

    col.nullability match {
      case Nullability.NoNulls         => baseTpe
      case Nullability.Nullable        => sc.Type.Option.of(baseTpe)
      case Nullability.NullableUnknown => sc.Type.Option.of(baseTpe).withComment("nullability unknown")
    }
  }

  def dbTypeFrom(enums: Map[String, db.StringEnum], c: ColumnsRow): db.Type =
    dbTypeFrom(enums, c.udtName.get, c.characterMaximumLength).getOrElse {
      System.err.println(s"Couldn't translate type from column $c")
      db.Type.Text
    }

  val pgObjectTypes = Set(
    "aclitem", // i.e. "postgres=arwdDxt/postgres"
    "anyarray",
    "int2vector",
    "oidvector", // space separated oids referencing i.e. pg_collation.oid
    "pg_node_tree", // Expression trees (in nodeToString() representation
    "regclass",
    "regconfig",
    "regdictionary",
    "regnamespace",
    "regoper",
    "regoperator",
    "regproc",
    "regproc",
    "regprocedure",
    "regrole",
    "regtype",
    "regtype",
    "xid" // transaction ID
  )

  def dbTypeFrom(enums: Map[String, db.StringEnum], udtName: String, characterMaximumLength: Option[Int]): Option[db.Type] = {
    udtName match {
      case "bool"                     => Some(db.Type.Boolean)
      case "box"                      => Some(db.Type.PGbox)
      case "bytea"                    => Some(db.Type.Bytea)
      case "char"                     => Some(db.Type.Char)
      case "cid"                      => Some(db.Type.Int4) // command identifier
      case "circle"                   => Some(db.Type.PGcircle)
      case "float4"                   => Some(db.Type.Float4)
      case "float8"                   => Some(db.Type.Float8)
      case "hstore"                   => Some(db.Type.Hstore)
      case "inet"                     => Some(db.Type.Inet)
      case "int4"                     => Some(db.Type.Int4)
      case "int8"                     => Some(db.Type.Int8)
      case "interval"                 => Some(db.Type.PGInterval)
      case "json"                     => Some(db.Type.Json)
      case "line"                     => Some(db.Type.PGline)
      case "lseg"                     => Some(db.Type.PGlseg)
      case "money"                    => Some(db.Type.PGmoney)
      case "name"                     => Some(db.Type.Name)
      case "numeric"                  => Some(db.Type.Numeric)
      case "oid"                      => Some(db.Type.Oid) // numeric object identifier
      case "path"                     => Some(db.Type.PGpath)
      case "point"                    => Some(db.Type.PGpoint)
      case "polygon"                  => Some(db.Type.PGpolygon)
      case "smallint" | "int2"        => Some(db.Type.Int2)
      case "text"                     => Some(db.Type.Text)
      case "timestamp"                => Some(db.Type.Timestamp)
      case "timestamptz"              => Some(db.Type.TimestampTz)
      case "uuid"                     => Some(db.Type.UUID)
      case "varchar"                  => Some(db.Type.VarChar(characterMaximumLength))
      case str if pgObjectTypes(str)  => Some(db.Type.PgObject(str))
      case str if str.startsWith("_") => dbTypeFrom(enums, udtName.drop(1), characterMaximumLength).map(tpe => db.Type.Array(tpe))
      case typeName                   => enums.get(typeName).map(enum => db.Type.StringEnum(enum.name))
    }
  }
}
