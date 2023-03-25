package typo
package metadb

import typo.generated.information_schema.{KeyColumnUsageRow, TableConstraintsRow}

object UniqueKeys {
  def apply(tableConstraints: List[TableConstraintsRow], keyColumnUsage: List[KeyColumnUsageRow]): Map[db.RelationName, List[db.UniqueKey]] = {

    def toUniqueKey(tc: TableConstraintsRow): db.UniqueKey = {
      val columnsInKey: List[db.ColName] =
        keyColumnUsage
          .filter { kcu =>
            kcu.constraintCatalog == tc.constraintCatalog && kcu.constraintSchema == tc.constraintSchema && kcu.constraintName == tc.constraintName
          }
          .sortBy(_.ordinalPosition)
          .map(kcu => db.ColName(kcu.columnName.get))

      db.UniqueKey(cols = columnsInKey, constraintName = db.RelationName(tc.constraintSchema, tc.constraintName.get))
    }

    val allUniqueConstraintsByTable: Map[db.RelationName, List[TableConstraintsRow]] =
      tableConstraints
        .filter(_.constraintType.contains("UNIQUE"))
        .groupBy(uc => db.RelationName(uc.tableSchema, uc.tableName.get))

    allUniqueConstraintsByTable
      .map { case (tableName, tcs) => (tableName, tcs.map(toUniqueKey)) }
  }

}
