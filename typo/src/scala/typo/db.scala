package typo

import typo.internal.DebugJson
import typo.internal.analysis.{DecomposedSql, ParsedName}

import scala.collection.immutable.SortedSet

/** Describes what tables look like in postgres
  */
object db {
  sealed trait Type
  object Type {
    case class Array(tpe: Type) extends Type
    case object Boolean extends Type
    case class Bpchar(maxLength: Option[Int]) extends Type // blank padded character
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
    case object Jsonb extends Type
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
    case object aclitem extends Type
    case object anyarray extends Type
    case object int2vector extends Type
    case object oidvector extends Type
    case object pg_node_tree extends Type
    case object record extends Type
    case object regclass extends Type
    case object regconfig extends Type
    case object regdictionary extends Type
    case object regnamespace extends Type
    case object regoper extends Type
    case object regoperator extends Type
    case object regproc extends Type
    case object regprocedure extends Type
    case object regrole extends Type
    case object regtype extends Type
    case object xid extends Type
    case class EnumRef(name: RelationName) extends Type
    case object Text extends Type
    case object Time extends Type
    case object TimeTz extends Type
    case object Timestamp extends Type
    case object TimestampTz extends Type
    case object UUID extends Type
    case object Xml extends Type
    case class VarChar(maxLength: Option[Int]) extends Type
    case class Unknown(sqlType: String) extends Type
    case object Vector extends Type
  }

  case class Domain(name: RelationName, tpe: Type, isNotNull: Nullability, hasDefault: Boolean, constraintDefinition: Option[String])
  case class StringEnum(name: RelationName, values: List[String])
  case class ColName(value: String) extends AnyVal
  object ColName {
    implicit val ordering: Ordering[ColName] = Ordering.by(_.value)
  }

  case class Constraint(name: String, columns: SortedSet[ColName], checkClause: String)

  case class Col(
      parsedName: ParsedName,
      tpe: Type,
      udtName: Option[String],
      nullability: Nullability,
      columnDefault: Option[String],
      comment: Option[String],
      constraints: List[Constraint],
      jsonDescription: DebugJson
  ) {
    def name = parsedName.name
  }
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
      decomposedSql: DecomposedSql,
      cols: NonEmptyList[(db.Col, ParsedName)],
      deps: Map[db.ColName, (db.RelationName, db.ColName)],
      isMaterialized: Boolean
  ) extends Relation
}
