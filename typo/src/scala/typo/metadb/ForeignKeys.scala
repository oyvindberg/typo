package typo
package metadb
import typo.information_schema.TableConstraints

class ForeignKeys(
    tableConstraints: List[information_schema.TableConstraints.Row],
    keyColumnUsage: List[information_schema.KeyColumnUsage.Row],
    referentialConstraints: List[information_schema.ReferentialConstraints.Row]
) {
  lazy val fks: List[TableConstraints.Row] = tableConstraints.filter(_.constraint_type == "FOREIGN KEY")

  lazy val getAsMap: Map[db.RelationName, List[db.ForeignKey]] = {
    fks
      .map { fk =>
        (
          db.RelationName(fk.table_schema, fk.table_name),
          getReferringColumns(fk),
          getReferredTable(fk),
          getReferredColumns(fk),
          db.RelationName(fk.constraint_schema, fk.constraint_name)
        )
      }
      .collect {
        case (tableName, referringColumns, Some(referredTable), referredColumns, constraintName) =>
          tableName -> db.ForeignKey(referringColumns, referredTable, referredColumns, constraintName)
      }
      .groupMap(_._1)(_._2)
  }

  private def getReferringColumns(fk: information_schema.TableConstraints.Row): List[db.ColName] = {
    val kcus =
      keyColumnUsage.filter { kcu =>
        fk.constraint_catalog == kcu.constraint_catalog &&
        fk.constraint_schema == kcu.constraint_schema &&
        fk.constraint_name == kcu.constraint_name
      }

    kcus
      .sortBy(_.ordinal_position)
      .map(kcu => db.ColName(kcu.column_name))
  }

  private def getReferredTable(fk: information_schema.TableConstraints.Row): Option[db.RelationName] = {
    referentialConstraints
      .find { rc =>
        fk.constraint_catalog == rc.constraint_catalog &&
        fk.constraint_schema == rc.constraint_schema &&
        fk.constraint_name == rc.constraint_name
      }
      .flatMap { rc =>
        tableConstraints.find { tc =>
          tc.constraint_catalog == rc.unique_constraint_catalog &&
          tc.constraint_schema == rc.unique_constraint_schema &&
          tc.constraint_name == rc.unique_constraint_name

        }
      }
      .map { tc =>
        db.RelationName(tc.table_schema, tc.table_name)
      }
  }

  private def getReferredColumns(fk: information_schema.TableConstraints.Row): List[db.ColName] = {
    referentialConstraints
      .filter { rc =>
        fk.constraint_catalog == rc.constraint_catalog &&
        fk.constraint_schema == rc.constraint_schema &&
        fk.constraint_name == rc.constraint_name
      }
      .flatMap { rc =>
        keyColumnUsage
          .filter { kcu =>
            kcu.constraint_catalog == rc.unique_constraint_catalog &&
            kcu.constraint_schema == rc.unique_constraint_schema &&
            kcu.constraint_name == rc.unique_constraint_name
          }
          .sortBy(_.ordinal_position)
          .map(kcu => db.ColName(kcu.column_name))
      }
  }

}
