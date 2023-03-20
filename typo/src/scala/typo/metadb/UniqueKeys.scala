package typo
package metadb

import typo.generated.information_schema.{KeyColumnUsageRow, TableConstraintsRow}

class UniqueKeys(
    tableConstraints: List[TableConstraintsRow],
    keyColumnUsage: List[KeyColumnUsageRow]
) {
  lazy val getAsMap: Map[db.RelationName, List[db.UniqueKey]] = {

    val allUniqueConstraintsByTable: Map[db.RelationName, List[TableConstraintsRow]] =
      tableConstraints
        .filter(_.constraintType.contains("UNIQUE"))
        .groupBy(uc => db.RelationName(uc.tableSchema.get, uc.tableName.get))

    allUniqueConstraintsByTable
      .map { case (tableName, tcs) => (tableName, tcs.map(toUniqueKey)) }
  }

  private def toUniqueKey(tc: TableConstraintsRow): db.UniqueKey = {
    val columnsInKey: List[db.ColName] =
      keyColumnUsage
        .filter { kcu =>
          kcu.constraintCatalog == tc.constraintCatalog && kcu.constraintSchema == tc.constraintSchema && kcu.constraintName == tc.constraintName
        }
        .sortBy(_.ordinalPosition)
        .map(kcu => db.ColName(kcu.columnName.get))

    db.UniqueKey(cols = columnsInKey, constraintName = db.RelationName(tc.constraintSchema.get, tc.constraintName.get))
  }
}
