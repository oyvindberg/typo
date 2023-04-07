package typo
package internal
package metadb

import typo.generated.information_schema.CharacterData
import typo.generated.information_schema.key_column_usage.KeyColumnUsageRow
import typo.generated.information_schema.table_constraints.TableConstraintsRow

object UniqueKeys {
  def apply(tableConstraints: List[TableConstraintsRow], keyColumnUsage: List[KeyColumnUsageRow]): Map[db.RelationName, List[db.UniqueKey]] = {

    def toUniqueKey(tc: TableConstraintsRow): Option[db.UniqueKey] = {
      val columnsInKey: List[db.ColName] =
        keyColumnUsage
          .filter { kcu =>
            kcu.constraintCatalog == tc.constraintCatalog && kcu.constraintSchema == tc.constraintSchema && kcu.constraintName == tc.constraintName
          }
          .sortBy(_.ordinalPosition)
          .map(kcu => db.ColName(kcu.columnName.get.value))

      for {
        columnsInKey <- NonEmptyList.fromList(columnsInKey)
      } yield db.UniqueKey(cols = columnsInKey, constraintName = db.RelationName(tc.constraintSchema.map(_.value), tc.constraintName.get.value))
    }

    val allUniqueConstraintsByTable: Map[db.RelationName, List[TableConstraintsRow]] =
      tableConstraints
        .filter(_.constraintType.contains(CharacterData("UNIQUE")))
        .groupBy(uc => db.RelationName(uc.tableSchema.map(_.value), uc.tableName.get.value))

    allUniqueConstraintsByTable
      .map { case (tableName, tcs) => (tableName, tcs.flatMap(toUniqueKey)) }
  }

}
