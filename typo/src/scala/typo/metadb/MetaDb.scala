package typo
package metadb

import java.sql.Connection

class MetaDb(c: Connection) {
  private val tableConstraints = information_schema.TableConstraints.all(c)
  private val keyColumnUsage = information_schema.KeyColumnUsage.all(c)
  private val referentialConstraints = information_schema.ReferentialConstraints.all(c)

  val foreignKeys = new ForeignKeys(tableConstraints, keyColumnUsage, referentialConstraints)
  val primaryKeys = new PrimaryKeys(tableConstraints, keyColumnUsage)
  val uniqueKeys = new UniqueKeys(tableConstraints, keyColumnUsage)
}
