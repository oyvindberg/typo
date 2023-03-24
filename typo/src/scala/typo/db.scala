package typo

import play.api.libs.json.JsValue

/** Describes what tables look like in postgres
  */
object db {
  sealed trait Type
  object Type {
    case object BigInt extends Type
    case object Text extends Type
    case object AnyArray extends Type
    case object Boolean extends Type
    case object Char extends Type
    case object Name extends Type
    case class StringEnum(name: RelationName) extends Type
    case object Hstore extends Type
    case object Inet extends Type
    case object Oid extends Type
    case object PgObject extends Type
    case class VarChar(maxLength: Option[Int]) extends Type
    case object Float4 extends Type
    case object Float8 extends Type
    case object Int2 extends Type
    case object Int4 extends Type
    case object Int8 extends Type
    case object Json extends Type
    case object Numeric extends Type
    case object Timestamp extends Type
    case object TimestampTz extends Type
    case class Array(tpe: Type) extends Type
    case class Vector(tpe: Type) extends Type
  }

  case class StringEnum(name: db.RelationName, values: List[String])
  case class ColName(value: String) extends AnyVal
  case class Col(name: ColName, tpe: Type, isNotNull: Boolean, hasDefault: Boolean, jsonDescription: JsValue)
  case class RelationName(schema: String, name: String)
  case class PrimaryKey(colNames: List[ColName], constraintName: RelationName = RelationName("", ""))
  case class ForeignKey(cols: List[ColName], otherTable: RelationName, otherCols: List[ColName], constraintName: RelationName = RelationName("", "")) {
    require(cols.size == otherCols.size)
  }
  case class UniqueKey(cols: List[ColName], constraintName: RelationName = RelationName("", ""))
  case class Table(
      name: RelationName,
      cols: List[Col],
      primaryKey: Option[PrimaryKey],
      uniqueKeys: List[UniqueKey],
      foreignKeys: List[ForeignKey]
  )
}
