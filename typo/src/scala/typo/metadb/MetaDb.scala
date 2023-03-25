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
        pgEnums = PgEnum.all,
        tablesRows = TablesRepoImpl.selectAll,
        columnsRows = ColumnsRepoImpl.selectAll,
        viewRows = ViewsRepo.all
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
        val relationName = db.RelationName(
          schema = table.tableSchema.get,
          name = table.tableName.get
        )

        val columns = input.columnsRows
          .filter(c => c.tableCatalog == table.tableCatalog && c.tableSchema == table.tableSchema && c.tableName == table.tableName)
          .sortBy(_.ordinalPosition)

        def mappedCols: List[db.Col] =
          columns.map { c =>
            db.Col(
              name = db.ColName(c.columnName.get),
              hasDefault = c.columnDefault.isDefined,
              nullability = c.isNullable match {
                case Some("YES") => Nullability.Nullable
                case Some("NO")  => Nullability.NoNulls
                case None        => Nullability.NullableUnknown
                case other       => throw new Exception(s"Unknown nullability: $other")
              },
              tpe = typeMapper.dbTypeFrom(enumsByName, c),
              jsonDescription = Json.toJson(c)
            )
          }

        groupedViewRows.get(relationName) match {
          case Some(view) =>
            db.View(relationName, mappedCols, view.view_definition, isMaterialized = view.relkind == "m")
          case None =>
            db.Table(
              name = relationName,
              cols = mappedCols,
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
