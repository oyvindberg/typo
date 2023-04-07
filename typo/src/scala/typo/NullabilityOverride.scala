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
}

object NullabilityOverride {
  val Empty: NullabilityOverride = (_, _) => None
}
