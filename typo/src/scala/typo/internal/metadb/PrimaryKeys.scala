package typo
package internal
package metadb

import typo.generated.information_schema.CharacterDataDomain
import typo.generated.information_schema.key_column_usage.KeyColumnUsageRow
import typo.generated.information_schema.table_constraints.TableConstraintsRow

object PrimaryKeys {
  def apply(tableConstraints: List[TableConstraintsRow], keyColumnUsage: List[KeyColumnUsageRow]): Map[db.RelationName, db.PrimaryKey] = {
    tableConstraints
      .filter(_.constraintType.contains(CharacterDataDomain("PRIMARY KEY")))
      .flatMap { tc =>
        val columns = keyColumnUsage
          .filter(kcu =>
            tc.constraintCatalog == kcu.constraintCatalog
              && tc.constraintSchema == kcu.constraintSchema
              && tc.constraintName == kcu.constraintName
          )
          .sortBy(_.ordinalPosition)
          .map(kcu => db.ColName(kcu.columnName.get.value))
        val relName = db.RelationName(tc.tableSchema.map(_.value), tc.tableName.get.value)
        val constraintName = db.RelationName(tc.constraintSchema.map(_.value), tc.constraintName.get.value)
        for {
          columns <- NonEmptyList.fromList(columns)
        } yield (relName, db.PrimaryKey(colNames = columns, constraintName = constraintName))
      }
      .toMap
  }
}
