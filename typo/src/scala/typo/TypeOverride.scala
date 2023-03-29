package typo

import java.nio.file.Path

/** Here you can override the type of a column. You specify either a fully qualified type name or something which is in scope, either by default in scala or is
  * exposed in a super package of the generated code.
  *
  * Note that you cannot override `Array` or `Option`.
  */
trait TypeOverride {

  /** @param relation
    *   either the relative path of a script, or the name of a relation
    * @param colName
    *   name of column
    * @return
    */
  def apply(relation: Either[Path, db.RelationName], colName: db.ColName): Option[String]
}

object TypeOverride {
  val Empty: TypeOverride = (_, _) => None

  def of(pf: PartialFunction[(Either[Path, db.RelationName], db.ColName), String]): TypeOverride =
    (relation, colName) => pf.lift((relation, colName))

  def simplified(pf: PartialFunction[(String, String), String]): TypeOverride =
    (relation, colName) => {
      val str = relation match {
        case Left(relPath)  => relPath.toString
        case Right(relName) => relName.value
      }
      pf.lift((str, colName.value))
    }
}
