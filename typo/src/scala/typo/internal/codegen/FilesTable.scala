package typo
package internal
package codegen

import play.api.libs.json.Json
import typo.internal.codegen.DbLib.RowType

case class FilesTable(lang: Lang, table: ComputedTable, fkAnalysis: FkAnalysis, options: InternalOptions, domainsByName: Map[db.RelationName, ComputedDomain]) {
  val relation = FilesRelation(lang, table.naming, table.names, Some(table.cols), Some(fkAnalysis), options, table.dbTable.foreignKeys)
  val RowFile = relation.RowFile(RowType.ReadWriteable, table.dbTable.comment, maybeUnsavedRow = table.maybeUnsavedRow.map(u => (u, table.default)))

  val UnsavedRowFile: Option[sc.File] =
    for {
      unsaved <- table.maybeUnsavedRow
    } yield {
      val comments = scaladoc(List(s"This class corresponds to a row in table `${table.dbTable.name.value}` which has not been persisted yet"))

      val toRow: sc.Method = {
        def mkDefaultParamName(col: ComputedColumn): sc.Ident =
          sc.Ident(col.name.value).appended("Default")

        val params: NonEmptyList[sc.Param] =
          unsaved.defaultCols.map { case ComputedRowUnsaved.DefaultedCol(col, originalType) => sc.Param(mkDefaultParamName(col), sc.Type.ByName(originalType)) } ++
            unsaved.alwaysGeneratedCols.map(col => sc.Param(mkDefaultParamName(col), sc.Type.ByName(col.tpe)))

        val keyValues = unsaved.categorizedColumnsOriginalOrder.map {
          case ComputedRowUnsaved.DefaultedCol(col, _) =>
            val defaultParamName = mkDefaultParamName(col)
            val impl = code"${col.name}.getOrElse($defaultParamName)"
            sc.Arg.Named(col.name, impl)
          case ComputedRowUnsaved.AlwaysGeneratedCol(col) =>
            val defaultParamName = mkDefaultParamName(col)
            sc.Arg.Named(col.name, sc.ApplyByName(defaultParamName))
          case ComputedRowUnsaved.NormalCol(col) =>
            sc.Arg.Named(col.name, sc.QIdent.of(col.name).code)
        }

        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("toRow"),
          params = params.toList,
          implicitParams = Nil,
          tpe = table.names.RowName,
          body = Some(sc.New(table.names.RowName, keyValues.toList))
        )
      }

      val colParams = unsaved.allCols.map { col =>
        col.param.copy(
          comments = scaladoc(
            List[Iterable[String]](
              col.dbCol.columnDefault.map(x => s"Default: $x"),
              col.dbCol.identity.map(_.asString),
              col.dbCol.comment,
              col.pointsTo map { case (relationName, columnName) => lang.docLink(table.naming.rowName(relationName), table.naming.field(columnName)) },
              col.dbCol.constraints.map(c => s"Constraint ${c.name} affecting columns ${c.columns.map(_.value).mkString(", ")}:  ${c.checkClause}"),
              if (options.debugTypes)
                col.dbCol.jsonDescription.maybeJson.map(other => s"debug: ${Json.stringify(other)}")
              else None
            ).flatten
          ),
          default = col.dbCol.columnDefault.map { _ => code"${table.default.Defaulted}.${table.default.UseDefault}()" }
        )
      }

      val instances =
        options.jsonLibs.flatMap(_.instances(unsaved.tpe, unsaved.allCols)) ++
          options.dbLib.toList.flatMap(_.rowInstances(unsaved.tpe, unsaved.allCols, rowType = DbLib.RowType.Writable))

      val cls = sc.Adt.Record(
        isWrapper = false,
        comments = comments,
        name = unsaved.tpe,
        tparams = Nil,
        params = colParams.toList,
        implicitParams = Nil,
        `extends` = None,
        implements = Nil,
        members = List(toRow),
        staticMembers = instances
      )

      sc.File(unsaved.tpe, cls.code, secondaryTypes = Nil, scope = Scope.Main)
    }

  val IdFile: Option[sc.File] = {
    table.maybeId.flatMap {
      case id: IdComputed.UnaryNormal =>
        val value = sc.Ident("value")
        val comments = scaladoc(List(s"Type for the primary key of table `${table.dbTable.name.value}`"))
        val bijection =
          if (options.enableDsl)
            Some {
              val thisBijection = sc.Type.dsl.Bijection.of(id.tpe, id.underlying)
              sc.Given(Nil, sc.Ident("bijection"), Nil, thisBijection, code"$thisBijection(_.$value)(${id.tpe}.apply)")
            }
          else None

        // shortcut for id files wrapping a domain
        val maybeFromString: Option[sc.Method] =
          id.col.dbCol.tpe match {
            case db.Type.DomainRef(name, _, _) =>
              domainsByName.get(name).map { domain =>
                val name = domain.underlying.constraintDefinition match {
                  case Some(_) => domain.tpe.name.map(Naming.camelCase)
                  case None    => sc.Ident("apply")
                }
                sc.Method(
                  comments = sc.Comments.Empty,
                  tparams = Nil,
                  name = name,
                  params = List(sc.Param(value, domain.underlyingType)),
                  implicitParams = Nil,
                  tpe = id.tpe,
                  body = Some(sc.New(id.tpe, List(sc.Arg.Pos(sc.New(domain.tpe, List(sc.Arg.Pos(value)))))))
                )
              }
            case _ => None
          }

        val instances = List(
          bijection.toList,
          options.jsonLibs.flatMap(_.wrapperTypeInstances(wrapperType = id.tpe, fieldName = value, underlying = id.underlying)),
          options.dbLib.toList.flatMap(_.wrapperTypeInstances(wrapperType = id.tpe, underlying = id.underlying, overrideDbType = None))
        ).flatten
        Some(
          sc.File(
            id.tpe,
            sc.Adt.Record(
              isWrapper = true,
              comments = comments,
              name = id.tpe,
              tparams = Nil,
              params = List(sc.Param(value, id.underlying)),
              implicitParams = Nil,
              `extends` = None,
              implements = Nil,
              members = Nil,
              staticMembers = instances ++ maybeFromString
            ),
            secondaryTypes = Nil,
            scope = Scope.Main
          )
        )

      case _: IdComputed.UnaryUserSpecified | _: IdComputed.UnaryNoIdType | _: IdComputed.UnaryInherited =>
        None
      case id @ IdComputed.Composite(cols, tpe, _) =>
        val constructorMethod: Option[sc.Method] =
          fkAnalysis.createWithFkIdsId.map { colsFromFks =>
            sc.Method(
              comments = sc.Comments.Empty,
              tparams = Nil,
              name = sc.Ident("from"),
              params = colsFromFks.allParams,
              implicitParams = Nil,
              tpe = tpe,
              Some(sc.New(tpe, colsFromFks.allExpr.map { case (colName, expr) => sc.Arg.Named(colName, expr) }))
            )
          }

        val instanceMethods: List[sc.Method] =
          fkAnalysis.extractFksIdsFromId.map { colsToFk =>
            sc.Method(
              comments = sc.Comments.Empty,
              tparams = Nil,
              name = colsToFk.name.prepended("extract"),
              params = Nil,
              implicitParams = Nil,
              tpe = colsToFk.otherCompositeIdType,
              body = Some(sc.New(colsToFk.otherCompositeIdType, colsToFk.colPairs.map { case (inComposite, inId) => sc.Arg.Named(inComposite.name, inId.name) }))
            )
          }

        val instances: List[sc.Given] =
          options.jsonLibs.flatMap(_.instances(tpe = id.tpe, cols = cols))

        Some(
          sc.File(
            id.tpe,
            sc.Adt.Record(
              isWrapper = false,
              comments = scaladoc(List(s"Type for the composite primary key of table `${table.dbTable.name.value}`")),
              name = tpe,
              tparams = Nil,
              params = cols.map(_.param).toList,
              implicitParams = Nil,
              `extends` = None,
              implements = Nil,
              members = instanceMethods,
              staticMembers = instances ++ constructorMethod.toList
            ),
            secondaryTypes = Nil,
            scope = Scope.Main
          )
        )
    }
  }

  private val maybeMockRepo: Option[sc.File] =
    if (options.generateMockRepos.include(table.dbTable.name))
      for {
        id <- table.maybeId
        repoMethods <- table.repoMethods
        dbLib <- options.dbLib
      } yield relation.RepoMockFile(dbLib, id, repoMethods)
    else None

  val all: List[sc.File] = List(
    RowFile,
    relation.FieldsFile,
    UnsavedRowFile,
    for {
      repoMethods <- table.repoMethods
      dbLib <- options.dbLib
    } yield relation.RepoTraitFile(dbLib, repoMethods),
    for {
      repoMethods <- table.repoMethods
      dbLib <- options.dbLib
    } yield relation.RepoImplFile(dbLib, repoMethods),
    relation.FieldValueFile,
    maybeMockRepo,
    IdFile
  ).flatten
}
