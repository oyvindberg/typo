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
  def apply(from: OverrideFrom, colName: db.ColName): Option[Nullability]

  final def orElse(other: NullabilityOverride): NullabilityOverride =
    (from, colName) => apply(from, colName).orElse(other(from, colName))
}

object NullabilityOverride {
  val Empty: NullabilityOverride = (_, _) => None

  def of(pf: PartialFunction[(OverrideFrom, db.ColName), Nullability]): NullabilityOverride =
    (from, colName) => pf.lift((from, colName))

  def relation(pf: PartialFunction[( /* name without schema*/ String, /* column name*/ String), Nullability]): NullabilityOverride =
    (from, colName) => {
      from match {
        case rel: OverrideFrom.Relation => pf.lift((rel.name.value, colName.value))
        case _                          => None
      }
    }

  def sqlScript(pf: PartialFunction[(RelPath, /* column name */ String), Nullability]): NullabilityOverride =
    (from, colName) => {
      from match {
        case OverrideFrom.SqlScript(relPath) => pf.lift((relPath, colName.value))
        case _                               => None
      }
    }

  def sqlFileParam(pf: PartialFunction[(RelPath, /* column name */ String), Nullability]): NullabilityOverride =
    (from, colName) => {
      from match {
        case OverrideFrom.SqlFileParam(relPath) => pf.lift((relPath, colName.value))
        case _                                  => None
      }
    }
}
