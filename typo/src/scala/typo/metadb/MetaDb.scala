package typo
package metadb

import play.api.libs.json.Json
import typo.generated.information_schema._
import typo.generated.views.{FindAllViewsRepoImpl, FindAllViewsRow}

import java.sql.Connection

case class MetaDb(
    tables: List[db.Table],
    views: List[db.View],
    enums: List[db.StringEnum]
)

object MetaDb {
  case class Input(
      tableConstraints: List[TableConstraintsRow],
      keyColumnUsage: List[KeyColumnUsageRow],
      referentialConstraints: List[ReferentialConstraintsRow],
      pgEnums: List[PgEnum.Row],
      tablesRows: List[TablesRow],
      columnsRows: List[ColumnsRow],
      viewRows: List[FindAllViewsRow]
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
        viewRows = FindAllViewsRepoImpl()
      )
    }
  }

  def apply(input: Input, selector: Selector): MetaDb = {

    val groupedViewRows: Map[db.RelationName, FindAllViewsRow] =
      input.viewRows.map { view => (db.RelationName(view.tableSchema, view.tableName.get), view) }.toMap

    val foreignKeys = ForeignKeys(input.tableConstraints, input.keyColumnUsage, input.referentialConstraints)
    val primaryKeys = PrimaryKeys(input.tableConstraints, input.keyColumnUsage)
    val uniqueKeys = UniqueKeys(input.tableConstraints, input.keyColumnUsage)
    val enums = Enums(input.pgEnums)
    val enumsByName = enums.map(e => (e.name.name, e)).toMap

    val relations: List[db.Relation] = {
      input.tablesRows.map { table =>
        val relationName = db.RelationName(
          schema = table.tableSchema,
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
            db.View(relationName, mappedCols, view.viewDefinition.get, isMaterialized = view.relkind == "m")
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

    // note: we should transitively check references between relations when considering `selector`.
    // especially we'll just include all enums for now.
    MetaDb(
      relations.collect { case x: db.Table if selector.include(x.name) => x },
      relations.collect { case x: db.View if selector.include(x.name) => x },
      enums
    )
  }
}
