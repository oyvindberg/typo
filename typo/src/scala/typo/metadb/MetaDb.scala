package typo
package metadb

import typo.generated.information_schema._
import typo.generated.views.{FindAllViewsRepoImpl, FindAllViewsRow, ViewColumnDependenciesRepoImpl, ViewColumnDependenciesRow}
import typo.internal.minimalJson

import java.sql.Connection

case class MetaDb(
    relations: List[db.Relation],
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
      viewRows: List[FindAllViewsRow],
      viewColumnDeps: List[ViewColumnDependenciesRow]
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
        viewRows = FindAllViewsRepoImpl(),
        viewColumnDeps = ViewColumnDependenciesRepoImpl()
      )
    }
  }

  def apply(input: Input): MetaDb = {

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
            val jsonDescription = minimalJson(c)
            db.Col(
              name = db.ColName(c.columnName.get),
              hasDefault = c.columnDefault.isDefined,
              nullability = c.isNullable match {
                case Some("YES") => Nullability.Nullable
                case Some("NO")  => Nullability.NoNulls
                case None        => Nullability.NullableUnknown
                case other       => throw new Exception(s"Unknown nullability: $other")
              },
              tpe = TypeMapperDb.dbTypeFrom(enumsByName, c).getOrElse {
                System.err.println(s"Couldn't translate type from column $jsonDescription")
                db.Type.Text
              },
              jsonDescription = jsonDescription
            )
          }

        groupedViewRows.get(relationName) match {
          case Some(view) =>
            val deps: Map[db.ColName, (db.RelationName, db.ColName)] =
              input.viewColumnDeps.collect {
                case x if x.viewSchema.map(_.getValue) == relationName.schema && x.viewName == relationName.name =>
                  // TODO: I'm only able to get the column name from one of the two tables involved in the dependency.
                  // I suppose this means that we'll only find the dependency if the column name is the same in both tables.
                  val colName = db.ColName(x.columnName)
                  colName -> (db.RelationName(x.tableSchema.map(_.getValue), x.tableName), colName)
              }.toMap

            db.View(relationName, mappedCols, view.viewDefinition.get, isMaterialized = view.relkind == "m", deps)
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

    MetaDb(relations, enums)
  }
}
