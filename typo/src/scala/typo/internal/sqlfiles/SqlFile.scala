package typo
package internal
package sqlfiles

import typo.internal.analysis.{DecomposedSql, JdbcMetadata, NullabilityFromExplain}

case class SqlFile(
    relPath: RelPath,
    decomposedSql: DecomposedSql,
    jdbcMetadata: JdbcMetadata,
    nullableColumnsFromJoins: Option[NullabilityFromExplain.NullableIndices]
)
