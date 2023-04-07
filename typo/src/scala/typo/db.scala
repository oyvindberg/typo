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
    case object Bpchar extends Type // blank padded character
    case object Bytea extends Type
    case object Char extends Type
    case object Date extends Type
    case class DomainRef(name: RelationName) extends Type
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
    case class EnumRef(name: RelationName) extends Type
    case object Text extends Type
    case object Time extends Type
    case object Timestamp extends Type
    case object TimestampTz extends Type
    case object UUID extends Type
    case object Xml extends Type
    case class VarChar(maxLength: Option[Int]) extends Type
  }

  case class Domain(name: RelationName, tpe: Type, isNotNull: Nullability, hasDefault: Boolean, constraintDefinition: Option[String])
  case class StringEnum(name: RelationName, values: List[String])
  case class ColName(value: String) extends AnyVal
  case class Col(name: ColName, tpe: Type, nullability: Nullability, hasDefault: Boolean, jsonDescription: JsValue)
  case class RelationName(schema: Option[String], name: String) {
    def value = s"${schema.map(_ + ".").getOrElse("")}$name"
  }
  object RelationName {
    implicit val ordering: Ordering[RelationName] = Ordering.by(_.value)
  }

  case class PrimaryKey(colNames: NonEmptyList[ColName], constraintName: RelationName)
  case class ForeignKey(cols: NonEmptyList[ColName], otherTable: RelationName, otherCols: NonEmptyList[ColName], constraintName: RelationName) {
    require(cols.length == otherCols.length)
  }
  case class UniqueKey(cols: NonEmptyList[ColName], constraintName: RelationName)

  sealed trait Relation {
    def name: RelationName
    def cols: NonEmptyList[Col]
  }

  case class Table(
      name: RelationName,
      cols: NonEmptyList[Col],
      primaryKey: Option[PrimaryKey],
      uniqueKeys: List[UniqueKey],
      foreignKeys: List[ForeignKey]
  ) extends Relation

  case class View(
      name: RelationName,
      cols: NonEmptyList[Col],
      sql: String,
      isMaterialized: Boolean,
      dependencies: Map[ColName, (RelationName, ColName)]
  ) extends Relation
}
