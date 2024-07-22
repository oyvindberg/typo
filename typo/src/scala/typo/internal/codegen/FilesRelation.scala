package typo
package internal
package codegen

import play.api.libs.json.Json

case class FilesRelation(
    lang: Lang,
    naming: Naming,
    names: ComputedNames,
    maybeCols: Option[NonEmptyList[ComputedColumn]],
    maybeFkAnalysis: Option[FkAnalysis],
    options: InternalOptions,
    fks: List[db.ForeignKey]
) {
  def RowFile(rowType: DbLib.RowType, comment: Option[String], maybeUnsavedRow: Option[(ComputedRowUnsaved, ComputedDefault)]): Option[sc.File] =
    maybeCols.map { cols =>
      val members = List[Iterable[sc.ClassMember]](
        names.maybeId.collect { case x: IdComputed.Composite =>
          sc.Method(sc.Comments.Empty, Nil, x.paramName, Nil, Nil, x.tpe, Some(code"""new ${x.tpe}(${x.cols.map(x => x.name.code).mkCode(", ")})"""))
        },
        // id member which points to either `compositeId` val defined above or id column
        if (maybeCols.exists(_.exists(_.name.value == "id"))) None
        else
          names.maybeId.collect {
            case id: IdComputed.Unary =>
              sc.Method(sc.Comments.Empty, Nil, sc.Ident("id"), Nil, Nil, id.tpe, Some(id.col.name.code))
            case id: IdComputed.Composite =>
              sc.Method(sc.Comments.Empty, Nil, sc.Ident("id"), Nil, Nil, id.tpe, Some(sc.ApplyNullary(id.paramName.code)))
          },
        maybeFkAnalysis.toList.flatMap(_.extractFksIdsFromRowNotId).map { extractFkId =>
          val args = extractFkId.colPairs.map { case (inComposite, inId) => sc.Arg.Named(inComposite.name, inId.name.code) }
          sc.Method(
            sc.Comments.Empty,
            Nil,
            extractFkId.name.prepended("extract"),
            Nil,
            Nil,
            extractFkId.otherCompositeIdType,
            Some(sc.New(extractFkId.otherCompositeIdType, args))
          )
        },
        maybeUnsavedRow.map { case (unsaved, defaults) =>
          val (partOfId, rest) = unsaved.defaultCols.toList.partition { case ComputedRowUnsaved.DefaultedCol(col, _) => names.isIdColumn(col.dbName) }
          val partOfIdParams = partOfId.map { case ComputedRowUnsaved.DefaultedCol(col, _) => sc.Param(col.name, col.tpe) }
          val restParams = rest.map { case ComputedRowUnsaved.DefaultedCol(col, _) =>
            sc.Param(col.name, col.tpe).copy(default = Some(code"${defaults.Defaulted}.${defaults.Provided}(this.${col.name})"))
          }
          val params = partOfIdParams ++ restParams
          sc.Method(
            comments = sc.Comments.Empty,
            tparams = Nil,
            name = sc.Ident("toUnsavedRow"),
            params = params,
            implicitParams = Nil,
            tpe = unsaved.tpe,
            body = Some(sc.New(unsaved.tpe, unsaved.allCols.toList.map(col => sc.Arg.Pos(col.name))))
          )
        }
      ).flatten

      val commentedParams: NonEmptyList[sc.Param] =
        cols.map { col =>
          val commentPieces = List[Iterable[String]](
            col.dbCol.comment,
            col.dbCol.columnDefault.map(x => s"Default: $x"),
            col.dbCol.identity.map(_.asString),
            col.pointsTo.map { case (relationName, columnName) => lang.docLink(naming.rowName(relationName), naming.field(columnName)) },
            col.dbCol.constraints.map(c => s"Constraint ${c.name} affecting columns ${c.columns.map(_.value).mkString(", ")}: ${c.checkClause}"),
            if (options.debugTypes)
              col.dbCol.jsonDescription.maybeJson.map(other => s"debug: ${Json.stringify(other)}")
            else None
          ).flatten

          col.param.copy(comments = sc.Comments(commentPieces))
        }

      val instances: List[sc.ClassMember] =
        options.jsonLibs.flatMap(_.instances(names.RowName, cols)) ++
          options.dbLib.toList.flatMap(_.rowInstances(names.RowName, cols, rowType = rowType))

      val classComment = {
        sc.Comments(
          List[Iterable[String]](
            Some(names.source match {
              case Source.Table(name)       => s"Table: ${name.value}"
              case Source.View(name, true)  => s"Materialized View: ${name.value}"
              case Source.View(name, false) => s"View: ${name.value}"
              case Source.SqlFile(relPath)  => s"SQL file: ${relPath.asString}"
            }),
            comment,
            names.maybeId.map {
              case x: IdComputed.Unary     => s"Primary key: ${x.col.dbName.value}"
              case x: IdComputed.Composite => s"Composite primary key: ${x.cols.map(_.dbName.value).mkString(", ")}"
            }
          ).flatten
        )
      }

      val maybeExtraApply: Option[sc.Method] =
        names.maybeId.collect { case id: IdComputed.Composite =>
          val nonKeyColumns = cols.toList.filter(col => !names.isIdColumn(col.dbCol.name))
          val params = sc.Param(id.paramName, id.tpe) :: nonKeyColumns.map(col => sc.Param(col.name, col.tpe))
          val args = cols.map(col => sc.Arg.Pos(if (names.isIdColumn(col.dbCol.name)) sc.ApplyNullary(code"${id.paramName}.${col.name}") else col.name.code))
          sc.Method(comments = sc.Comments.Empty, Nil, sc.Ident("apply"), params, Nil, names.RowName, Some(sc.New(names.RowName, args.toList)))
        }

      sc.File(
        names.RowName,
        sc.Adt.Record(
          isWrapper = false,
          comments = classComment,
          name = names.RowName,
          tparams = Nil,
          params = commentedParams.toList,
          implicitParams = Nil,
          `extends` = None,
          implements = Nil,
          members = members,
          staticMembers = instances ++ maybeExtraApply.toList
        ),
        secondaryTypes = Nil,
        scope = Scope.Main
      )
    }

  val FieldValueFile: Option[sc.File] =
    for {
      fieldValueName <- names.FieldOrIdValueName
      cols <- maybeCols
    } yield {
      val T = sc.Type.Abstract(sc.Ident("T"))
      val abstractMembers = List(
        sc.Method(sc.Comments.Empty, Nil, sc.Ident("name"), Nil, Nil, TypesJava.String, None),
        sc.Method(sc.Comments.Empty, Nil, sc.Ident("value"), Nil, Nil, T, None)
      )

      val colRecords = cols.toList.map { col =>
        sc.Adt.Record(
          isWrapper = false,
          comments = sc.Comments.Empty,
          name = sc.Type.Qualified(col.name),
          tparams = Nil,
          params = List(sc.Param(sc.Ident("value"), col.tpe)),
          implicitParams = Nil,
          `extends` = None,
          implements = List(fieldValueName.of(col.tpe)),
          members = List(
            sc.Method(
              comments = sc.Comments.Empty,
              tparams = Nil,
              name = sc.Ident("name"),
              params = Nil,
              implicitParams = Nil,
              tpe = TypesJava.String,
              body = Some(sc.StrLit(col.dbName.value))
            )
          ),
          staticMembers = Nil
        )
      }

      val fieldOrIdValue = sc.Adt.Sum(
        comments = sc.Comments.Empty,
        name = fieldValueName,
        tparams = List(T),
        implements = Nil,
        members = abstractMembers,
        staticMembers = Nil,
        subtypes = colRecords
      )
      sc.File(fieldValueName, fieldOrIdValue, secondaryTypes = Nil, scope = Scope.Main)
    }

  val FieldsFile: Option[sc.File] =
    for {
      fieldsName <- names.FieldsName
      cols <- maybeCols
    } yield {
      val colMembers = cols.map { col =>
        val (cls, tpe) =
          if (names.isIdColumn(col.dbName)) (sc.Type.dsl.IdField, col.tpe)
          else
            col.tpe match {
              case lang.Optional(underlying) => (sc.Type.dsl.OptField, underlying)
              case _                         => (sc.Type.dsl.Field, col.tpe)
            }
        code"def ${col.name}: ${cls.of(tpe, names.RowName)}"
      }

      val fkMembers: List[sc.Code] =
        names.source match {
          case relation: Source.Relation =>
            val byOtherTable = fks.groupBy(_.otherTable)
            fks
              .sortBy(_.constraintName.value)
              .map { fk =>
                val otherTableSource = Source.Table(fk.otherTable)
                val fkType = sc.Type.dsl.ForeignKey.of(
                  sc.Type.Qualified(naming.fieldsName(otherTableSource)),
                  sc.Type.Qualified(naming.rowName(otherTableSource))
                )
                val columnPairs = fk.cols.zip(fk.otherCols).map { case (col, otherCol) =>
                  code".withColumnPair(${naming.field(col)}, _.${naming.field(otherCol)})"
                }
                val fkName = naming.fk(relation.name, fk, includeCols = byOtherTable(fk.otherTable).size > 1)
                val body =
                  code"""|def $fkName: $fkType =
                       |  $fkType(${sc.StrLit(fk.constraintName.value)}, Nil)
                       |    ${columnPairs.mkCode("\n")}""".stripMargin
                (fkName, body)
              }
              .distinctBy(_._1)
              .map(_._2)
          case Source.SqlFile(_) => Nil
        }

      val extractFkMember: List[sc.Code] =
        maybeFkAnalysis.toList.flatMap(_.extractFksIdsFromRow).flatMap { (x: FkAnalysis.ExtractFkId) =>
          val predicateType = sc.Type.dsl.SqlExpr.of(TypesScala.Boolean)
          val is = {
            val expr = x.colPairs
              .map { case (otherCol, thisCol) => code"${thisCol.name}.isEqual(id.${otherCol.name})" }
              .reduceLeft[sc.Code] { case (acc, current) => code"$acc.and($current)" }
            code"""|def extract${x.name}Is(id: ${x.otherCompositeIdType}): $predicateType =
                   |  $expr""".stripMargin

          }
          val in = {
            val parts = x.colPairs.map { case (otherCol, thisCol) =>
              code"${sc.Type.dsl.CompositeTuplePart.of(x.otherCompositeIdType)}(${thisCol.name})(_.${otherCol.name})(using ${options.dbLib.get.resolveConstAs(sc.Type.ArrayOf(thisCol.tpe))}, implicitly)"
            }
            code"""|def extract${x.name}In(ids: ${sc.Type.ArrayOf(x.otherCompositeIdType)}): $predicateType =
                   |  new ${sc.Type.dsl.CompositeIn}(ids)(${parts.mkCode(", ")})
                   |""".stripMargin
          }

          List(is, in)

        }
      val compositeIdMembers: List[sc.Code] =
        names.maybeId
          .collect {
            case x: IdComputed.Composite if x.cols.forall(_.dbCol.nullability == Nullability.NoNulls) =>
              val predicateType = sc.Type.dsl.SqlExpr.of(TypesScala.Boolean)
              val is = {
                val id = x.paramName
                val expr = x.cols
                  .map(col => code"${col.name}.isEqual($id.${col.name})")
                  .toList
                  .reduceLeft[sc.Code] { case (acc, current) => code"$acc.and($current)" }
                code"""|def compositeIdIs($id: ${x.tpe}): $predicateType =
                       |  $expr""".stripMargin

              }
              val in = {
                val ids = x.paramName.appended("s")
                val parts =
                  x.cols.map(col => code"${sc.Type.dsl.CompositeTuplePart.of(x.tpe)}(${col.name})(_.${col.name})(using ${options.dbLib.get.resolveConstAs(sc.Type.ArrayOf(col.tpe))}, implicitly)")
                code"""|def compositeIdIn($ids: ${sc.Type.ArrayOf(x.tpe)}): $predicateType =
                       |  new ${sc.Type.dsl.CompositeIn}($ids)(${parts.mkCode(", ")})
                       |""".stripMargin
              }

              List(is, in)
          }
          .getOrElse(Nil)

      val ImplName = sc.Ident("Impl")
      val str =
        code"""|trait ${fieldsName.name} {
               |  ${(colMembers ++ fkMembers ++ compositeIdMembers ++ extractFkMember).mkCode("\n")}
               |}
               |
               |object ${fieldsName.name} {
               |  lazy val structure: ${sc.Type.dsl.StructureRelation}[$fieldsName, ${names.RowName}] =
               |    new $ImplName(${TypesScala.Nil})
               |
               |  ${structureFile(ImplName, fieldsName, cols)}
               |}
               |""".stripMargin

      sc.File(fieldsName, str, Nil, scope = Scope.Main)
    }

  def structureFile(implName: sc.Ident, fieldsName: sc.Type.Qualified, cols: NonEmptyList[ComputedColumn]) = {
    val members =
      cols
        .map { col =>
          val (cls, tpe) =
            if (names.isIdColumn(col.dbName)) (sc.Type.dsl.IdField, col.tpe)
            else
              col.tpe match {
                case lang.Optional(underlying) => (sc.Type.dsl.OptField, underlying)
                case _                         => (sc.Type.dsl.Field, col.tpe)
              }

          val readSqlCast = SqlCast.fromPg(col.dbCol.tpe) match {
            case Some(sqlCast) => code"${TypesScala.Some}(${sc.StrLit(sqlCast.typeName)})"
            case None          => TypesScala.None.code
          }
          val writeSqlCast = SqlCast.toPg(col.dbCol) match {
            case Some(sqlCast) => code"${TypesScala.Some}(${sc.StrLit(sqlCast.typeName)})"
            case None          => TypesScala.None.code
          }
          code"override def ${col.name} = ${cls.of(tpe, names.RowName)}(_path, ${sc.StrLit(col.dbName.value)}, $readSqlCast, $writeSqlCast, x => x.${col.name}, (row, value) => row.copy(${col.name} = value))"
        }
        .mkCode("\n")

    val pathList = TypesScala.List.of(sc.Type.dsl.Path)
    val generalizedColumn = sc.Type.dsl.FieldLikeNoHkt.of(sc.Type.Wildcard, names.RowName)
    val columnsList = TypesScala.List.of(generalizedColumn)
    code"""private final class $implName(val _path: $pathList)
          |  extends ${sc.Type.dsl.StructureRelation.of(fieldsName, names.RowName)} {
          |
          |  override lazy val fields: ${fieldsName.name} = new ${fieldsName.name} {
          |    $members
          |  }
          |
          |  override lazy val columns: $columnsList =
          |    $columnsList(${cols.map(x => code"fields.${x.name}").mkCode(", ")})
          |
          |  override def copy(path: $pathList): $implName =
          |    new $implName(path)
          |}
          |""".stripMargin
  }

  def RepoTraitFile(dbLib: DbLib, repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val maybeSignatures = repoMethods.toList.map(dbLib.repoSig)

    val cls = sc.Class(
      comments = sc.Comments(maybeSignatures.collect { case Left(DbLib.NotImplementedFor(repoMethod, lib)) =>
        s"${repoMethod.methodName}: Not implementable for $lib"
      }),
      classType = sc.ClassType.Interface,
      name = names.RepoName,
      tparams = Nil,
      params = Nil,
      implicitParams = Nil,
      `extends` = None,
      implements = Nil,
      members = maybeSignatures.collect { case Right(signature) => signature },
      staticMembers = Nil
    )

    sc.File(names.RepoName, cls, secondaryTypes = Nil, scope = Scope.Main)
  }

  def RepoImplFile(dbLib: DbLib, repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val methods: List[sc.Method] =
      repoMethods.toList.flatMap { repoMethod =>
        dbLib.repoSig(repoMethod) match {
          case Right(sig @ sc.Method(_, _, _, _, _, _, None)) =>
            Some(sig.copy(body = Some(dbLib.repoImpl(repoMethod))))
          case _ =>
            None
        }
      }
    val cls = sc.Class(
      comments = sc.Comments.Empty,
      classType = sc.ClassType.Class,
      name = names.RepoImplName,
      tparams = Nil,
      params = Nil,
      implicitParams = Nil,
      `extends` = None,
      implements = List(names.RepoName),
      members = methods,
      staticMembers = Nil
    )

    sc.File(names.RepoImplName, cls, secondaryTypes = Nil, scope = Scope.Main)
  }

  def RepoMockFile(dbLib: DbLib, idComputed: IdComputed, repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val maybeToRowParam: Option[sc.Param] =
      repoMethods.toList.collectFirst { case RepoMethod.InsertUnsaved(_, _, unsaved, _, _, _) =>
        sc.Param(sc.Ident("toRow"), TypesScala.Function1.of(unsaved.tpe, names.RowName))
      }

    val methods: List[sc.Method] =
      repoMethods.toList.flatMap { repoMethod =>
        dbLib.repoSig(repoMethod) match {
          case Right(sig @ sc.Method(_, _, _, _, _, _, None)) =>
            Some(sig.copy(body = Some(dbLib.mockRepoImpl(idComputed, repoMethod, maybeToRowParam))))
          case _ =>
            None
        }
      }

    val classParams = List(
      maybeToRowParam,
      Some(
        sc.Param(
          sc.Comments.Empty,
          sc.Ident("map"),
          TypesScala.mutableMap.of(idComputed.tpe, names.RowName),
          Some(code"${TypesScala.mutableMap}.empty")
        )
      )
    ).flatten

    val cls = sc.Class(
      comments = sc.Comments.Empty,
      classType = sc.ClassType.Class,
      name = names.RepoMockName,
      tparams = Nil,
      params = classParams,
      implicitParams = Nil,
      `extends` = None,
      implements = List(names.RepoName),
      members = methods,
      staticMembers = Nil
    )
    sc.File(names.RepoMockName, cls, secondaryTypes = Nil, scope = Scope.Test)
  }
}
