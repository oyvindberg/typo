package typo
package metadb

import typo.generated.information_schema._

import java.sql.Connection

class MetaDb(implicit c: Connection) {
  private lazy val tableConstraints = TableConstraintsRepoImpl.selectAll
  private lazy val keyColumnUsage = KeyColumnUsageRepoImpl.selectAll
  private lazy val referentialConstraints = ReferentialConstraintsRepoImpl.selectAll
  private lazy val pgEnums = PgEnum.all(c)
  private lazy val tablesRows = TablesRepoImpl.selectByFieldValues(List(TablesFieldValue.tableType(Some("BASE TABLE"))))(c)
  private lazy val columnsRows = ColumnsRepoImpl.selectAll

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
