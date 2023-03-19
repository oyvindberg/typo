package typo
package metadb

class PrimaryKeys(
    tableConstraints: List[information_schema.TableConstraints.Row],
    keyColumnUsage: List[information_schema.KeyColumnUsage.Row]
) {
  lazy val getAsMap: Map[db.RelationName, db.PrimaryKey] = {
    tableConstraints
      .filter(_.constraint_type == "PRIMARY KEY")
      .map { tc =>
        (
          db.RelationName(tc.table_schema, tc.table_name),
          db.PrimaryKey(
            colNames = keyColumnUsage
              .filter(kcu =>
                tc.constraint_catalog == kcu.constraint_catalog
                  && tc.constraint_schema == kcu.constraint_schema
                  && tc.constraint_name == kcu.constraint_name
              )
              .sortBy(_.ordinal_position)
              .map(kcu => db.ColName(kcu.column_name)),
            constraintName = db.RelationName(tc.constraint_schema, tc.constraint_name)
          )
        )
      }
      .toMap
  }
}
