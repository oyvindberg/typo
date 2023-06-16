package typo
package internal

import typo.generated.information_schema.columns.ColumnsViewRow

case class TypeMapperDb(enums: Map[String, db.StringEnum], domains: Map[String, db.Domain]) {
  def col(c: ColumnsViewRow): Option[db.Type] = {
    val fromDomain: Option[db.Type.DomainRef] =
      c.domainName.map(domainName => db.Type.DomainRef(db.RelationName(c.domainSchema.map(_.value), domainName.value)))

    fromDomain.orElse(dbTypeFrom(c.udtName.get.value, c.characterMaximumLength.map(_.value)))
  }

  def dbTypeFrom(udtName: String, characterMaximumLength: Option[Int]): Option[db.Type] = {
    udtName match {
      case "bool"                              => Some(db.Type.Boolean)
      case "box"                               => Some(db.Type.PGbox)
      case "bpchar"                            => Some(db.Type.Bpchar)
      case "bytea"                             => Some(db.Type.Bytea)
      case "char"                              => Some(db.Type.Char)
      case "cid"                               => Some(db.Type.Int4) // command identifier
      case "circle"                            => Some(db.Type.PGcircle)
      case "date"                              => Some(db.Type.Date)
      case "float4"                            => Some(db.Type.Float4)
      case "float8"                            => Some(db.Type.Float8)
      case "hstore"                            => Some(db.Type.Hstore)
      case "inet"                              => Some(db.Type.Inet)
      case "int2" | "smallint" | "smallserial" => Some(db.Type.Int2)
      case "int4" | "integer" | "serial"       => Some(db.Type.Int4)
      case "int8" | "bigint" | "bigserial"     => Some(db.Type.Int8)
      case "interval"                          => Some(db.Type.PGInterval)
      case "json"                              => Some(db.Type.Json)
      case "jsonb"                             => Some(db.Type.Jsonb)
      case "line"                              => Some(db.Type.PGline)
      case "lseg"                              => Some(db.Type.PGlseg)
      case "money"                             => Some(db.Type.PGmoney)
      case "name"                              => Some(db.Type.Name)
      case "numeric" | "decimal"               => Some(db.Type.Numeric)
      case "oid"                               => Some(db.Type.Oid) // numeric object identifier
      case "path"                              => Some(db.Type.PGpath)
      case "pg_lsn"                            => Some(db.Type.PGlsn)
      case "point"                             => Some(db.Type.PGpoint)
      case "polygon"                           => Some(db.Type.PGpolygon)
      case "text"                              => Some(db.Type.Text)
      case "time"                              => Some(db.Type.Time)
      case "timestamp"                         => Some(db.Type.Timestamp)
      case "timestamptz"                       => Some(db.Type.TimestampTz)
      case "uuid"                              => Some(db.Type.UUID)
      case "varchar"                           => Some(db.Type.VarChar(characterMaximumLength))
      case "xml"                               => Some(db.Type.Xml)
      case "aclitem"                           => Some(db.Type.aclitem)
      case "anyarray"                          => Some(db.Type.anyarray)
      case "int2vector"                        => Some(db.Type.int2vector)
      case "oidvector"                         => Some(db.Type.oidvector)
      case "pg_node_tree"                      => Some(db.Type.pg_node_tree)
      case "regclass"                          => Some(db.Type.regclass)
      case "regconfig"                         => Some(db.Type.regconfig)
      case "regdictionary"                     => Some(db.Type.regdictionary)
      case "regnamespace"                      => Some(db.Type.regnamespace)
      case "regoper"                           => Some(db.Type.regoper)
      case "regoperator"                       => Some(db.Type.regoperator)
      case "regproc"                           => Some(db.Type.regproc)
      case "regprocedure"                      => Some(db.Type.regprocedure)
      case "regrole"                           => Some(db.Type.regrole)
      case "regtype"                           => Some(db.Type.regtype)
      case "xid"                               => Some(db.Type.xid)
      case str if str.startsWith("_")          => dbTypeFrom(udtName.drop(1), characterMaximumLength).map(tpe => db.Type.Array(tpe))
      case typeName =>
        enums
          .get(typeName)
          .map(`enum` => db.Type.EnumRef(`enum`.name))
          .orElse(domains.get(typeName).map(domain => db.Type.DomainRef(domain.name)))
    }
  }
}
