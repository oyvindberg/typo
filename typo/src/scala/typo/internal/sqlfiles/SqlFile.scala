package typo
package internal
package sqlfiles

case class SqlFile(
    relPath: RelPath,
    decomposedSql: DecomposedSql,
    jdbcMetadata: JdbcMetadata,
    nullableColumnsFromJoins: Option[NullabilityFromExplain.NullableColumns],
    depsFromView: Map[db.ColName, (db.RelationName, db.ColName)]
)
