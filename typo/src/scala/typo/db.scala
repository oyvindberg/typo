package typo

import play.api.libs.json.JsValue

/** Describes what tables look like in postgres
  */
object db {
  sealed trait Type
  object Type {
    case class Array(tpe: Type) extends Type
    case object BigInt extends Type
    case object Boolean extends Type
    case object Bytea extends Type
    case object Char extends Type
    case object Float4 extends Type
    case object Float8 extends Type
    case object Hstore extends Type
    case object Inet extends Type
    case object Int2 extends Type
    case object Int4 extends Type
    case object Int8 extends Type
    case object Json extends Type
    case object Name extends Type
    case object Numeric extends Type
    case object Oid extends Type
    case object PGInterval extends Type
    case object PGbox extends Type
    case object PGcircle extends Type
    case object PGline extends Type
    case object PGlsn extends Type
    case object PGlseg extends Type
    case object PGmoney extends Type
    case object PGpath extends Type
    case object PGpoint extends Type
    case object PGpolygon extends Type
    case class PgObject(value: String) extends Type
    case class StringEnum(name: RelationName) extends Type
    case object Text extends Type
    case object Timestamp extends Type
    case object TimestampTz extends Type
    case object UUID extends Type
    case class VarChar(maxLength: Option[Int]) extends Type
  }

  case class StringEnum(name: db.RelationName, values: List[String])
  case class ColName(value: String) extends AnyVal
  case class Col(name: ColName, tpe: Type, nullability: Nullability, hasDefault: Boolean, jsonDescription: JsValue)
  case class RelationName(schema: Option[String], name: String) {
    def value = s"${schema.map(_ + ".").getOrElse("")}$name"
  }
  object RelationName {
    implicit val ordering: Ordering[RelationName] = Ordering.by(_.value)
  }

  case class PrimaryKey(colNames: List[ColName], constraintName: RelationName)
  case class ForeignKey(cols: List[ColName], otherTable: RelationName, otherCols: List[ColName], constraintName: RelationName) {
    require(cols.size == otherCols.size)
  }
  case class UniqueKey(cols: List[ColName], constraintName: RelationName)

  sealed trait Relation {
    def name: RelationName
    def cols: List[Col]
  }

  case class Table(
      name: RelationName,
      cols: List[Col],
      primaryKey: Option[PrimaryKey],
      uniqueKeys: List[UniqueKey],
      foreignKeys: List[ForeignKey]
  ) extends Relation

  case class View(
      name: db.RelationName,
      cols: List[Col],
      sql: String,
      isMaterialized: Boolean,
      dependencies: Map[ColName, (RelationName, ColName)]
  ) extends Relation
}
