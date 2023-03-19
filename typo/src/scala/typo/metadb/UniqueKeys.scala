package typo
package metadb

class UniqueKeys(
    tableConstraints: List[information_schema.TableConstraints.Row],
    keyColumnUsage: List[information_schema.KeyColumnUsage.Row]
) {
  lazy val getAsMap: Map[db.RelationName, List[db.UniqueKey]] = {

    val allUniqueConstraintsByTable: Map[db.RelationName, List[information_schema.TableConstraints.Row]] =
      tableConstraints
        .filter(_.constraint_type == "UNIQUE")
        .groupBy(uc => db.RelationName(uc.table_schema, uc.table_name))

    allUniqueConstraintsByTable
      .map { case (tableName, tcs) =>
        (tableName, tcs.map(toUniqueKey))
      }
  }

  private def toUniqueKey(tc: information_schema.TableConstraints.Row): db.UniqueKey = {
    val columnsInKey: List[db.ColName] =
      keyColumnUsage
        .filter { kcu =>
          kcu.constraint_catalog == tc.constraint_catalog && kcu.constraint_schema == tc.constraint_schema && kcu.constraint_name == tc.constraint_name
        }
        .sortBy(_.ordinal_position)
        .map(kcu => db.ColName(kcu.column_name))

    db.UniqueKey(cols = columnsInKey, constraintName = db.RelationName(tc.constraint_schema, tc.constraint_name))
  }
}
