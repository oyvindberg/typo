package typo
package internal.sqlfiles

object parseName {
  def apply(name: String): ParsedName = {
    val (shortened, nullability) =
      if (name.endsWith("?")) (name.dropRight(1), Some(Nullability.Nullable))
      else if (name.endsWith("!")) (name.dropRight(1), Some(Nullability.NoNulls))
      else (name, None)

    val (dbName, overriddenType) = shortened.split(":").toList match {
      case Nil                 => sys.error("shouldn't happen (tm)")
      case name :: Nil         => (db.ColName(name), None)
      case name :: tpeStr :: _ => (db.ColName(name), Some(sc.Type.UserDefined(sc.Type.Qualified(tpeStr))))
    }
    ParsedName(dbName, nullability, overriddenType)
  }
}
