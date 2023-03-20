package typo
package metadb

import typo.generated.information_schema.{TablesFieldValue, TablesRepoImpl}

import java.sql.Connection

class MetaDb(c: Connection) {
  private lazy val tableConstraints = information_schema.TableConstraints.all(c)
  private lazy val keyColumnUsage = information_schema.KeyColumnUsage.all(c)
  private lazy val referentialConstraints = information_schema.ReferentialConstraints.all(c)
  private lazy val pgEnums = information_schema.PgEnum.all(c)
  private lazy val tablesRows = TablesRepoImpl.selectByFieldValues(List(TablesFieldValue.tableType(Some("BASE TABLE"))))(c)
  private lazy val columnsRows = information_schema.Columns.all(c)

  lazy val foreignKeys = new ForeignKeys(tableConstraints, keyColumnUsage, referentialConstraints)
  lazy val primaryKeys = new PrimaryKeys(tableConstraints, keyColumnUsage)
  lazy val uniqueKeys = new UniqueKeys(tableConstraints, keyColumnUsage)
  lazy val enums = new Enums(pgEnums)
  lazy val tables = new Tables(
    tablesRows,
    columnsRows,
    primaryKeys.getAsMap,
    uniqueKeys.getAsMap,
    foreignKeys.getAsMap,
    enums.getAsMap
  )

}
