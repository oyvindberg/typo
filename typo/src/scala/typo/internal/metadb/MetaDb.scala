package typo
package internal
package metadb

import typo.generated.custom.domains.{DomainsRepoImpl, DomainsRow}
import typo.generated.custom.view_column_dependencies.*
import typo.generated.custom.view_find_all.*
import typo.generated.information_schema.YesOrNoDomain
import typo.generated.information_schema.columns.{ColumnsRepoImpl, ColumnsRow}
import typo.generated.information_schema.key_column_usage.{KeyColumnUsageRepoImpl, KeyColumnUsageRow}
import typo.generated.information_schema.referential_constraints.{ReferentialConstraintsRepoImpl, ReferentialConstraintsRow}
import typo.generated.information_schema.table_constraints.{TableConstraintsRepoImpl, TableConstraintsRow}
import typo.generated.information_schema.tables.{TablesRepoImpl, TablesRow}

import java.sql.Connection

case class MetaDb(
    relations: List[db.Relation],
    enums: List[db.StringEnum],
    domains: List[db.Domain],
    typeMapperDb: TypeMapperDb
)

object MetaDb {
  case class Input(
      tableConstraints: List[TableConstraintsRow],
      keyColumnUsage: List[KeyColumnUsageRow],
      referentialConstraints: List[ReferentialConstraintsRow],
      pgEnums: List[PgEnum.Row],
      tablesRows: List[TablesRow],
      columnsRows: List[ColumnsRow],
      viewRows: List[ViewFindAllRow],
      viewColumnDeps: List[ViewColumnDependenciesRow],
      domains: List[DomainsRow]
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
        viewRows = ViewFindAllRepoImpl(),
        viewColumnDeps = ViewColumnDependenciesRepoImpl(),
        domains = DomainsRepoImpl()
      )
    }
  }

  def apply(input: Input): MetaDb = {

    val groupedViewRows: Map[db.RelationName, ViewFindAllRow] =
      input.viewRows.map { view => (db.RelationName(view.tableSchema, view.tableName.get), view) }.toMap

    val foreignKeys = ForeignKeys(input.tableConstraints, input.keyColumnUsage, input.referentialConstraints)
    val primaryKeys = PrimaryKeys(input.tableConstraints, input.keyColumnUsage)
    val uniqueKeys = UniqueKeys(input.tableConstraints, input.keyColumnUsage)
    val enums = Enums(input.pgEnums)
    val enumsByName = enums.map(e => (e.name.name, e)).toMap
    val domains = input.domains.map { d =>
      val tpe = TypeMapperDb(enumsByName, Map.empty)
        .dbTypeFrom(
          d.`type`,
          characterMaximumLength = None // todo: this can likely be set
        )
        .getOrElse {
          System.err.println(s"Couldn't translate type from domain $d")
          db.Type.Text
        }

      db.Domain(
        name = db.RelationName(Some(d.schema), d.name),
        tpe = tpe,
        isNotNull = if (d.isNotNull) Nullability.NoNulls else Nullability.Nullable,
        hasDefault = d.default.isDefined,
        constraintDefinition = d.constraintDefinition
      )
    }

    val typeMapperDb = TypeMapperDb(
      enumsByName,
      domains.map(e => (e.name.name, e)).toMap
    )

    val relations: List[db.Relation] = {
      input.tablesRows.flatMap { table =>
        val relationName = db.RelationName(
          schema = table.tableSchema.map(_.value),
          name = table.tableName.get.value
        )

        val columns = input.columnsRows
          .filter(c => c.tableCatalog == table.tableCatalog && c.tableSchema == table.tableSchema && c.tableName == table.tableName)
          .sortBy(_.ordinalPosition)

        def mappedCols: List[db.Col] =
          columns.map { c =>
            val jsonDescription = minimalJson(c)
            db.Col(
              name = db.ColName(c.columnName.get.value),
              hasDefault = c.columnDefault.isDefined,
              nullability = c.isNullable match {
                case Some(YesOrNoDomain("YES")) => Nullability.Nullable
                case Some(YesOrNoDomain("NO"))  => Nullability.NoNulls
                case None                       => Nullability.NullableUnknown
                case other                      => throw new Exception(s"Unknown nullability: $other")
              },
              tpe = typeMapperDb.col(c).getOrElse {
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
                  (colName, (db.RelationName(x.tableSchema.map(_.getValue), x.tableName), colName))
              }.toMap

            for {
              mappedCols <- NonEmptyList.fromList(mappedCols)
            } yield db.View(relationName, mappedCols, view.viewDefinition.get, isMaterialized = view.relkind == "m", deps)

          case None =>
            for {
              mappedCols <- NonEmptyList.fromList(mappedCols)
            } yield db.Table(
              name = relationName,
              cols = mappedCols,
              primaryKey = primaryKeys.get(relationName),
              uniqueKeys = uniqueKeys.getOrElse(relationName, List.empty),
              foreignKeys = foreignKeys.getOrElse(relationName, List.empty)
            )
        }
      }
    }
    MetaDb(relations, enums, domains, typeMapperDb)
  }
}
