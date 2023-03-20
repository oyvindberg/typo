package typo
package metadb

import typo.generated.information_schema.{KeyColumnUsageRow, TableConstraintsRow}

class PrimaryKeys(
    tableConstraints: List[TableConstraintsRow],
    keyColumnUsage: List[KeyColumnUsageRow]
) {
  lazy val getAsMap: Map[db.RelationName, db.PrimaryKey] = {
    tableConstraints
      .filter(_.constraintType.contains("PRIMARY KEY"))
      .map { tc =>
        (
          db.RelationName(tc.tableSchema.get, tc.tableName.get),
          db.PrimaryKey(
            colNames = keyColumnUsage
              .filter(kcu =>
                tc.constraintCatalog == kcu.constraintCatalog
                  && tc.constraintSchema == kcu.constraintSchema
                  && tc.constraintName == kcu.constraintName
              )
              .sortBy(_.ordinalPosition)
              .map(kcu => db.ColName(kcu.columnName.get)),
            constraintName = db.RelationName(tc.constraintSchema.get, tc.constraintName.get)
          )
        )
      }
      .toMap
  }
}
