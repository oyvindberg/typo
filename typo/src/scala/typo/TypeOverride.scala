package typo

/** Here you can override the type of a column. You specify either a fully qualified type name or something which is in scope, either by default in scala or is
  * exposed in a super package of the generated code.
  *
  * Note that you cannot override `Array` or `Option`.
  */
trait TypeOverride {

  /** @param from
    *   inside the ADT you can find the name of the relation or the location of the sql script
    * @param colName
    *   name of column
    * @return
    */
  def apply(from: Source, colName: db.ColName): Option[String]

  final def orElse(other: TypeOverride): TypeOverride =
    (from, colName) => apply(from, colName).orElse(other(from, colName))
}

object TypeOverride {
  val Empty: TypeOverride = (_, _) => None

  def of(pf: PartialFunction[(Source, db.ColName), String]): TypeOverride =
    (from, colName) => pf.lift((from, colName))

  def relation(pf: PartialFunction[(String, String), String]): TypeOverride =
    (from, colName) => {
      from match {
        case rel: Source.Relation => pf.lift((rel.name.value, colName.value))
        case _                    => None
      }
    }

  def sqlFile(pf: PartialFunction[(RelPath, /* column name */ String), String]): TypeOverride =
    (from, colName) => {
      from match {
        case sql: Source.SqlFile => pf.lift((sql.relPath, colName.value))
        case _                   => None
      }
    }

  def sqlFileParam(pf: PartialFunction[(RelPath, /* column name */ String), String]): TypeOverride =
    (from, colName) => {
      from match {
        case Source.SqlFileParam(relPath) => pf.lift((relPath, colName.value))
        case _                            => None
      }
    }

}
