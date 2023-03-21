package typo
package metadb

import typo.generated.information_schema.{ColumnsRow, TablesRow}

class Tables(
    tableRows: List[TablesRow],
    columns: List[ColumnsRow],
    primaryKeys: Map[db.RelationName, db.PrimaryKey],
    uniqueKeys: Map[db.RelationName, List[db.UniqueKey]],
    foreignKeys: Map[db.RelationName, List[db.ForeignKey]],
    enums: Map[String, db.StringEnum]
) {

  lazy val getAsList: List[db.Table] = {
    tableRows.map { table =>
      val cols: List[db.Col] =
        columns
          .filter(c => c.tableCatalog == table.tableCatalog && c.tableSchema == table.tableSchema && c.tableName == table.tableName)
          .sortBy(_.ordinalPosition)
          .map { c =>
            db.Col(
              name = db.ColName(c.columnName.get),
              hasDefault = c.columnDefault.isDefined,
              isNotNull = c.isNullable.contains("NO"),
              tpe = typeFromUdtName(c.udtName.get, c.characterMaximumLength)
            )
          }

      val tableName = db.RelationName(
        schema = table.tableSchema.get,
        name = table.tableName.get
      )
      db.Table(
        name = tableName,
        cols = cols,
        primaryKey = primaryKeys.get(tableName),
        uniqueKeys = uniqueKeys.getOrElse(tableName, List.empty),
        foreignKeys = foreignKeys.getOrElse(tableName, List.empty)
      )
    }
  }

  private def typeFromUdtName(udtName: String, characterMaximumLength: Option[Int]): db.Type = {
    udtName match {
      case "aclitem"                  => db.Type.Text // i.e. "postgres=arwdDxt/postgres"
      case "anyarray"                 => db.Type.AnyArray
      case "bool"                     => db.Type.Boolean
      case "char"                     => db.Type.Char
      case "float4"                   => db.Type.Float4
      case "float8"                   => db.Type.Float8
      case "hstore"                   => db.Type.Hstore
      case "inet"                     => db.Type.Inet
      case "int2"                     => db.Type.Int2
      case "int4"                     => db.Type.Int4
      case "int8"                     => db.Type.Int8
      case "json"                     => db.Type.Json
      case "name"                     => db.Type.Name
      case "numeric"                  => db.Type.Numeric
      case "pg_node_tree"             => db.Type.Text // Expression trees (in nodeToString() representation)
      case "oid"                      => db.Type.Oid
      case "text"                     => db.Type.Text
      case "timestamp"                => db.Type.Timestamp
      case "timestamptz"              => db.Type.TimestampTz
      case "varchar"                  => db.Type.VarChar(characterMaximumLength)
      case str if str.startsWith("_") => db.Type.Array(typeFromUdtName(udtName.drop(1), characterMaximumLength))
      case typeName =>
        enums.get(typeName) match {
          case Some(enum) =>
            db.Type.StringEnum(enum.name)
          case None =>
            System.err.println(s"Couldn't translate type from column $udtName")
            db.Type.Text
        }
    }
  }

}
