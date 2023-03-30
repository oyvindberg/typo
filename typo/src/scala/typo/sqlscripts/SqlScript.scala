package typo
package sqlscripts

import java.nio.file.Path

case class SqlScript(
    relPath: Path,
    decomposedSql: DecomposedSql,
    params: List[SqlScript.Param],
    cols: List[db.Col],
    dependencies: Map[db.ColName, (db.RelationName, db.ColName)]
)

object SqlScript {
  case class Param(maybeName: DecomposedSql.Param, indices: List[Int], tpe: db.Type, nullability: Nullability)
}
