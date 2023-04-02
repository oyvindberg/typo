package typo
package internal
package metadb

import typo.generated.information_schema.key_column_usage.KeyColumnUsageRow
import typo.generated.information_schema.table_constraints.TableConstraintsRow

object PrimaryKeys {
  def apply(tableConstraints: List[TableConstraintsRow], keyColumnUsage: List[KeyColumnUsageRow]): Map[db.RelationName, db.PrimaryKey] = {
    tableConstraints
      .filter(_.constraintType.contains("PRIMARY KEY"))
      .map { tc =>
        (
          db.RelationName(tc.tableSchema, tc.tableName.get),
          db.PrimaryKey(
            colNames = keyColumnUsage
              .filter(kcu =>
                tc.constraintCatalog == kcu.constraintCatalog
                  && tc.constraintSchema == kcu.constraintSchema
                  && tc.constraintName == kcu.constraintName
              )
              .sortBy(_.ordinalPosition)
              .map(kcu => db.ColName(kcu.columnName.get)),
            constraintName = db.RelationName(tc.constraintSchema, tc.constraintName.get)
          )
        )
      }
      .toMap
  }
}
