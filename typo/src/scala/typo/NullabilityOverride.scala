package typo

/** Here you can override the inferred nullability of a column.
  *
  * Postgres have multiple inferring nullability for both views and prepared statements (which are used for sql scripts)
  */
trait NullabilityOverride {

  /** @param from
    *   inside the ADT you can find the name of the relation of the location of the sql script
    * @param colName
    *   name of column
    * @return
    */
  def apply(from: Source, colName: db.ColName): Option[Nullability]

  final def orElse(other: NullabilityOverride): NullabilityOverride =
    (from, colName) => apply(from, colName).orElse(other(from, colName))
}

object NullabilityOverride {
  val Empty: NullabilityOverride = (_, _) => None

  def of(pf: PartialFunction[(Source, db.ColName), Nullability]): NullabilityOverride =
    (from, colName) => pf.lift((from, colName))

  def relation(pf: PartialFunction[( /* name without schema*/ String, /* column name*/ String), Nullability]): NullabilityOverride =
    (from, colName) => {
      from match {
        case rel: Source.Relation => pf.lift((rel.name.value, colName.value))
        case _                    => None
      }
    }

  def sqlFile(pf: PartialFunction[(RelPath, /* column name */ String), Nullability]): NullabilityOverride =
    (from, colName) => {
      from match {
        case Source.SqlFile(relPath) => pf.lift((relPath, colName.value))
        case _                       => None
      }
    }

  def sqlFileParam(pf: PartialFunction[(RelPath, /* column name */ String), Nullability]): NullabilityOverride =
    (from, colName) => {
      from match {
        case Source.SqlFileParam(relPath) => pf.lift((relPath, colName.value))
        case _                            => None
      }
    }
}
