package typo.internal.sqlfiles

import typo.{NonEmptyList, Nullability, RelPath, db}

case class SqlFile(
    relPath: RelPath,
    decomposedSql: DecomposedSql,
    params: List[SqlFile.Param],
    cols: NonEmptyList[db.Col],
    dependencies: Map[db.ColName, (db.RelationName, db.ColName)]
)

object SqlFile {
  case class Param(maybeName: DecomposedSql.Param, indices: List[Int], tpe: db.Type, nullability: Nullability)
}
