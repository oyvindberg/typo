package typo
package sqlscripts

case class SqlScript(
    relPath: RelPath,
    decomposedSql: DecomposedSql,
    params: List[SqlScript.Param],
    cols: List[db.Col],
    dependencies: Map[db.ColName, (db.RelationName, db.ColName)]
)

object SqlScript {
  case class Param(maybeName: DecomposedSql.Param, indices: List[Int], tpe: db.Type, nullability: Nullability)
}
