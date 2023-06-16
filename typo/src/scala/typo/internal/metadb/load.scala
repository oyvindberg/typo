package typo
package internal
package metadb

import typo.generated.custom.comments.{CommentsSqlRepoImpl, CommentsSqlRow}
import typo.generated.custom.domains.{DomainsSqlRepoImpl, DomainsSqlRow}
import typo.generated.custom.view_column_dependencies.*
import typo.generated.custom.view_find_all.*
import typo.generated.information_schema.columns.{ColumnsViewRepoImpl, ColumnsViewRow}
import typo.generated.information_schema.key_column_usage.{KeyColumnUsageViewRepoImpl, KeyColumnUsageViewRow}
import typo.generated.information_schema.referential_constraints.{ReferentialConstraintsViewRepoImpl, ReferentialConstraintsViewRow}
import typo.generated.information_schema.table_constraints.{TableConstraintsViewRepoImpl, TableConstraintsViewRow}
import typo.generated.information_schema.tables.{TablesViewRepoImpl, TablesViewRow}
import typo.generated.information_schema.{SqlIdentifier, YesOrNo}

import java.nio.file.Path
import java.sql.Connection

object load {
  case class Input(
      tableConstraints: List[TableConstraintsViewRow],
      keyColumnUsage: List[KeyColumnUsageViewRow],
      referentialConstraints: List[ReferentialConstraintsViewRow],
      pgEnums: List[PgEnum.Row],
      TablesViewRows: List[TablesViewRow],
      ColumnsViewRows: List[ColumnsViewRow],
      viewRows: List[ViewFindAllSqlRow],
      viewColumnDeps: List[ViewColumnDependenciesSqlRow],
      domains: List[DomainsSqlRow],
      comments: List[CommentsSqlRow]
  )

  object Input {
    def fromDb(implicit c: Connection): Input = {
      Input(
        tableConstraints = TableConstraintsViewRepoImpl.selectAll,
        keyColumnUsage = KeyColumnUsageViewRepoImpl.selectAll,
        referentialConstraints = ReferentialConstraintsViewRepoImpl.selectAll,
        pgEnums = PgEnum.all,
        TablesViewRows = TablesViewRepoImpl.selectAll,
        ColumnsViewRows = ColumnsViewRepoImpl.selectAll,
        viewRows = ViewFindAllSqlRepoImpl(),
        viewColumnDeps = ViewColumnDependenciesSqlRepoImpl(None),
        domains = DomainsSqlRepoImpl(),
        comments = CommentsSqlRepoImpl()
      )
    }
  }

  def apply(maybeScriptPath: Option[Path])(implicit c: Connection): MetaDb = {
    val input = load.Input.fromDb
    val groupedViewRows: Map[db.RelationName, ViewFindAllSqlRow] =
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

    val comments: Map[(db.RelationName, db.ColName), String] =
      input.comments.collect { case CommentsSqlRow(maybeSchema, Some(SqlIdentifier(table)), Some(SqlIdentifier(column)), description) =>
        (db.RelationName(maybeSchema.map(_.value), table), db.ColName(column)) -> description
      }.toMap

    val relations: List[db.Relation] = {
      input.TablesViewRows.flatMap { table =>
        val relationName = db.RelationName(
          schema = table.tableSchema.map(_.value),
          name = table.tableName.get.value
        )

        val columns = input.ColumnsViewRows
          .filter(c => c.tableCatalog == table.tableCatalog && c.tableSchema == table.tableSchema && c.tableName == table.tableName)
          .sortBy(_.ordinalPosition)

        def mappedCols: List[db.Col] =
          columns.map { c =>
            val jsonDescription = minimalJson(c)

            val colName = db.ColName(c.columnName.get.value)
            db.Col(
              name = colName,
              columnDefault = c.columnDefault.map(_.value),
              nullability = c.isNullable match {
                case Some(YesOrNo("YES")) => Nullability.Nullable
                case Some(YesOrNo("NO"))  => Nullability.NoNulls
                case None                 => Nullability.NullableUnknown
                case other                => throw new Exception(s"Unknown nullability: $other")
              },
              tpe = typeMapperDb.col(c).getOrElse {
                System.err.println(s"Couldn't translate type from column $jsonDescription")
                db.Type.Text
              },
              udtName = c.udtName.map(_.value),
              comment = comments.get((relationName, colName)),
              jsonDescription = jsonDescription
            )
          }

        groupedViewRows.get(relationName) match {
          case Some(view) =>
            val deps: Map[db.ColName, (db.RelationName, db.ColName)] =
              input.viewColumnDeps.collect {
                case x if x.viewSchema.map(_.value) == relationName.schema && x.viewName == relationName.name =>
                  // TODO: I'm only able to get the column name from one of the two tables involved in the dependency.
                  // I suppose this means that we'll only find the dependency if the column name is the same in both tables.
                  val colName = db.ColName(x.columnName)
                  val relName = db.RelationName(x.tableSchema.map(_.value), x.tableName)
                  (colName, (relName, colName))
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
    val sqlScripts = maybeScriptPath match {
      case Some(scriptPath) => sqlfiles.Load(scriptPath, typeMapperDb)
      case None             => Nil
    }

    MetaDb(relations, enums, domains, sqlScripts)
  }
}
