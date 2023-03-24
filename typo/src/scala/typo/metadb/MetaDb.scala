package typo
package metadb

import play.api.libs.json.Json
import typo.generated.information_schema._

import java.sql.Connection

object MetaDb {
  case class Input(
      tableConstraints: List[TableConstraintsRow],
      keyColumnUsage: List[KeyColumnUsageRow],
      referentialConstraints: List[ReferentialConstraintsRow],
      pgEnums: List[PgEnum.Row],
      tablesRows: List[TablesRow],
      columnsRows: List[ColumnsRow],
      viewRows: List[ViewRow]
  )

  object Input {
    def fromDb(implicit c: Connection): Input = {
      Input(
        tableConstraints = TableConstraintsRepoImpl.selectAll,
        keyColumnUsage = KeyColumnUsageRepoImpl.selectAll,
        referentialConstraints = ReferentialConstraintsRepoImpl.selectAll,
        pgEnums = PgEnum.all(c),
        tablesRows = TablesRepoImpl.selectAll(c),
        columnsRows = ColumnsRepoImpl.selectAll,
        viewRows = ViewsRepo.all(c)
      )
    }
  }
  case class Output(
      tables: List[db.Table],
      views: List[db.View],
      enums: List[db.StringEnum]
  )
  def apply(input: Input): Output = {

    val groupedViewRows: Map[db.RelationName, ViewRow] =
      input.viewRows.map { view => (db.RelationName(view.table_schema, view.table_name), view) }.toMap

    val foreignKeys = ForeignKeys(input.tableConstraints, input.keyColumnUsage, input.referentialConstraints)
    val primaryKeys = PrimaryKeys(input.tableConstraints, input.keyColumnUsage)
    val uniqueKeys = UniqueKeys(input.tableConstraints, input.keyColumnUsage)
    val enums = Enums(input.pgEnums)
    val enumsByName = enums.map(e => (e.name.name, e)).toMap

    val relations: List[db.Relation] = {
      input.tablesRows.map { table =>
        val cols: List[db.Col] =
          input.columnsRows
            .filter(c => c.tableCatalog == table.tableCatalog && c.tableSchema == table.tableSchema && c.tableName == table.tableName)
            .sortBy(_.ordinalPosition)
            .map { c =>
              db.Col(
                name = db.ColName(c.columnName.get),
                hasDefault = c.columnDefault.isDefined,
                nullability = c.isNullable match {
                  case Some("YES") => doobie.Nullability.Nullable
                  case Some("NO")  => doobie.Nullability.NoNulls
                  case None        => doobie.Nullability.NullableUnknown
                  case _           => throw new Exception(s"Unknown nullability: ${c.isNullable}")
                },
                tpe = typeMapper.typeFromUdtName(enumsByName, c.udtName.get, c.characterMaximumLength),
                jsonDescription = Json.toJson(c)
              )
            }

        val relationName = db.RelationName(
          schema = table.tableSchema.get,
          name = table.tableName.get
        )

        groupedViewRows.get(relationName) match {
          case Some(view) =>
            db.View(relationName, cols, view.view_definition, isMaterialized = view.relkind == "m")
          case None =>
            db.Table(
              name = relationName,
              cols = cols,
              primaryKey = primaryKeys.get(relationName),
              uniqueKeys = uniqueKeys.getOrElse(relationName, List.empty),
              foreignKeys = foreignKeys.getOrElse(relationName, List.empty)
            )
        }
      }
    }

    Output(
      relations.collect { case x: db.Table => x },
      relations.collect { case x: db.View => x },
      enums
    )
  }
}
