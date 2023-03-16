package typo

/** Describes what tables look like in postgres
  */
object db {
  case class EnumName(schema: String, name: String)
  sealed trait Type
  object Type {
    case object BigInt extends Type
    case class VarChar(n: Int) extends Type
    case object Text extends Type
    case object Boolean extends Type
    case class StringEnum(name: EnumName, values: List[String]) extends Type
  }

  case class ColName(value: String) extends AnyVal
  case class Col(name: ColName, tpe: Type, isNotNull: Boolean, hasDefault: Boolean)
  case class TableName(schema: String, name: String)
  case class PrimaryKey(colNames: List[ColName])
  case class ForeignKey(col: ColName, otherTable: TableName, otherColumn: ColName)
  case class UniqueKey(cols: List[ColName])
  case class Table(
      name: TableName,
      cols: Seq[Col],
      primaryKey: Option[PrimaryKey],
      uniqueKeys: List[UniqueKey],
      foreignKeys: List[ForeignKey]
  )
}
