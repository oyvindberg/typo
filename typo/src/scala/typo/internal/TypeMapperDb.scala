package typo
package internal

import typo.generated.information_schema.columns.ColumnsViewRow

case class TypeMapperDb(enums: List[db.StringEnum], domains: List[db.Domain]) {
  val domainsByName: Map[String, db.Domain] = domains.flatMap(e => List((e.name.name, e), (e.name.value, e))).toMap
  val enumsByName = enums.flatMap(e => List((e.name.name, e), (e.name.value, e))).toMap
  
  def col(c: ColumnsViewRow)(logWarning: () => Unit): db.Type = {
    val fromDomain: Option[db.Type.DomainRef] =
      c.domainName.map { domainName =>
        val domainRelationName = db.RelationName(c.domainSchema, domainName)
        val d = domainsByName(domainRelationName.value)
        db.Type.DomainRef(d.name, d.tpe)
      }

    fromDomain.getOrElse(dbTypeFrom(c.udtName.get, c.characterMaximumLength)(logWarning))
  }
  object ArrayName {
    def unapply(udtName: String): Option[String] = {
      udtName.split('.') match {
        case Array(schema, name) if name.startsWith("_") => Some(s"$schema.${name.drop(1)}")
        case Array(name) if name.startsWith("_")         => Some(name.drop(1))
        case _                                           => None
      }
    }
  }
  def dbTypeFrom(udtName: String, characterMaximumLength: Option[Int])(logWarning: () => Unit): db.Type = {
    udtName.replaceAll("\"", "") match {
      case "bool"                              => db.Type.Boolean
      case "box"                               => db.Type.PGbox
      case "bpchar"                            => db.Type.Bpchar(characterMaximumLength)
      case "bytea"                             => db.Type.Bytea
      case "char"                              => db.Type.Char
      case "cid"                               => db.Type.Int4 // command identifier
      case "circle"                            => db.Type.PGcircle
      case "date"                              => db.Type.Date
      case "float4"                            => db.Type.Float4
      case "float8"                            => db.Type.Float8
      case "hstore"                            => db.Type.Hstore
      case "inet"                              => db.Type.Inet
      case "int2" | "smallint" | "smallserial" => db.Type.Int2
      case "int4" | "integer" | "serial"       => db.Type.Int4
      case "int8" | "bigint" | "bigserial"     => db.Type.Int8
      case "interval"                          => db.Type.PGInterval
      case "json"                              => db.Type.Json
      case "jsonb"                             => db.Type.Jsonb
      case "line"                              => db.Type.PGline
      case "lseg"                              => db.Type.PGlseg
      case "money"                             => db.Type.PGmoney
      case "name"                              => db.Type.Name
      case "numeric" | "decimal"               => db.Type.Numeric
      case "oid"                               => db.Type.Oid // numeric object identifier
      case "path"                              => db.Type.PGpath
      case "pg_lsn"                            => db.Type.PGlsn
      case "point"                             => db.Type.PGpoint
      case "polygon"                           => db.Type.PGpolygon
      case "text"                              => db.Type.Text
      case "time"                              => db.Type.Time
      case "timetz"                            => db.Type.TimeTz
      case "timestamp"                         => db.Type.Timestamp
      case "timestamptz"                       => db.Type.TimestampTz
      case "uuid"                              => db.Type.UUID
      case "varchar"                           => db.Type.VarChar(characterMaximumLength)
      case "xml"                               => db.Type.Xml
      case "aclitem"                           => db.Type.aclitem
      case "anyarray"                          => db.Type.anyarray
      case "int2vector"                        => db.Type.int2vector
      case "oidvector"                         => db.Type.oidvector
      case "pg_node_tree"                      => db.Type.pg_node_tree
      case "record"                            => db.Type.record
      case "regclass"                          => db.Type.regclass
      case "regconfig"                         => db.Type.regconfig
      case "regdictionary"                     => db.Type.regdictionary
      case "regnamespace"                      => db.Type.regnamespace
      case "regoper"                           => db.Type.regoper
      case "regoperator"                       => db.Type.regoperator
      case "regproc"                           => db.Type.regproc
      case "regprocedure"                      => db.Type.regprocedure
      case "regrole"                           => db.Type.regrole
      case "regtype"                           => db.Type.regtype
      case "xid"                               => db.Type.xid
      case "vector"                            => db.Type.Vector
      case ArrayName(underlying) =>
        db.Type.Array(dbTypeFrom(underlying, characterMaximumLength)(logWarning))
      case typeName =>
        enumsByName
          .get(typeName)
          .map(`enum` => db.Type.EnumRef(`enum`.name))
          .orElse(domainsByName.get(typeName).map(domain => db.Type.DomainRef(domain.name, domain.tpe)))
          .getOrElse {
            logWarning()
            db.Type.Unknown(udtName)
          }
    }
  }
}
