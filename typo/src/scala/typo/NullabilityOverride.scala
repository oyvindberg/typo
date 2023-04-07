package typo

/** Here you can override the inferred nullability of a column.
  *
  * Postgres have multiple inferring nullability for both views and prepared statements (which are used for sql scripts)
  */
trait NullabilityOverride {

  /** @param relation
    *   either the relative path of a script, or the name of a relation
    * @param colName
    *   name of column
    * @return
    */
  def apply(relation: Either[RelPath, db.RelationName], colName: db.ColName): Option[Nullability]
}

object NullabilityOverride {
  val Empty: NullabilityOverride = (_, _) => None
}
