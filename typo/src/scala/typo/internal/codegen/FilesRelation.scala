package typo
package internal
package codegen

import play.api.libs.json.Json

case class FilesRelation(naming: Naming, names: ComputedNames, maybeCols: Option[NonEmptyList[ComputedColumn]], options: InternalOptions) {
  def RowFile(rowType: DbLib.RowType): Option[sc.File] = maybeCols.map { cols =>
    val compositeId = names.maybeId match {
      case Some(x: IdComputed.Composite) =>
        code"""|{
               |  val ${x.paramName}: ${x.tpe} = ${x.tpe}(${x.cols.map(x => x.name.code).mkCode(", ")})
               |}""".stripMargin
      case _ => code""
    }

    val formattedCols = cols.map { col =>
      val commentPieces = List[Iterable[String]](
        col.dbCol.comment,
        col.pointsTo.map { case (relationName, columnName) =>
          val shortened = sc.QIdent(dropCommonPrefix(naming.rowName(relationName).idents, names.RowName.value.idents))
          s"Points to [[${shortened.dotName}.${naming.field(columnName).value}]]"
        },
        col.dbCol.constraints.map(c => s"Constraint ${c.name} affecting columns ${c.columns.map(_.value).mkString(", ")}: ${c.checkClause}"),
        if (options.debugTypes)
          col.dbCol.jsonDescription.maybeJson.map(other => s"debug: ${Json.stringify(other)}")
        else None
      ).flatten

      val comment = commentPieces match {
        case Nil => sc.Code.Empty
        case nonEmpty =>
          val lines = nonEmpty.flatMap(_.linesIterator).map(_.code)
          code"""|/** ${lines.mkCode("\n")} */
                 |""".stripMargin
      }

      code"$comment${col.param.code}"
    }
    val instances =
      options.jsonLibs.flatMap(_.instances(names.RowName, cols)) ++
        options.dbLib.toList.flatMap(_.rowInstances(names.RowName, cols, rowType = rowType))

    val str =
      code"""case class ${names.RowName.name}(
            |  ${formattedCols.mkCode(",\n")}
            |)$compositeId
            |
            |${genObject(names.RowName.value, instances)}
            |""".stripMargin

    sc.File(names.RowName, str, secondaryTypes = Nil)
  }

  val FieldValueFile: Option[sc.File] =
    for {
      fieldOrIdValueName <- names.FieldOrIdValueName
      fieldValueName <- names.FieldValueName
      cols <- maybeCols
    } yield {
      val members = {
        cols.map { col =>
          val parent = if (names.isIdColumn(col.dbName)) fieldOrIdValueName else fieldValueName
          code"case class ${col.name}(override val value: ${col.tpe}) extends $parent(${sc.StrLit(col.dbName.value)}, value)"
        }
      }
      val str =
        code"""sealed abstract class ${fieldOrIdValueName.name}[T](val name: String, val value: T)
              |sealed abstract class ${fieldValueName.name}[T](name: String, value: T) extends ${fieldOrIdValueName.name}(name, value)
              |
              |${sc.Obj(fieldValueName.value, Nil, Some(members.mkCode("\n")))}
              |""".stripMargin

      sc.File(fieldValueName, str, secondaryTypes = List(fieldOrIdValueName))
    }

  val FieldsFile: Option[sc.File] =
    for {
      structureName <- names.StructureName
      fieldsName <- names.FieldsName
      cols <- maybeCols
    } yield {
      val Row = sc.Type.Abstract(sc.Ident("Row"))
      val members =
        cols
          .map { col =>
            val (cls, tpe) =
              if (names.isIdColumn(col.dbName)) (sc.Type.dsl.IdField, col.tpe)
              else
                col.tpe match {
                  case sc.Type.Optional(underlying) => (sc.Type.dsl.OptField, underlying)
                  case _                            => (sc.Type.dsl.Field, col.tpe)
                }
            code"val ${col.name}: ${cls.of(tpe, Row)}"
          }
          .mkCode("\n")

      val str =
        code"""trait ${fieldsName.name}[$Row] {
            |  $members
            |}
            |object ${fieldsName.name} extends ${structureName.of(names.RowName)}(None, identity, (_, x) => x)
            |
            |""".stripMargin

      sc.File(fieldsName, str, Nil)
    }

  val StructureFile: Option[sc.File] =
    for {
      structureName <- names.StructureName
      fieldsName <- names.FieldsName
      cols <- maybeCols
    } yield {
      val Row = sc.Type.Abstract(sc.Ident("Row"))
      val NewRow = sc.Type.Abstract(sc.Ident("NewRow"))
      val members =
        cols
          .map { col =>
            val (cls, tpe) =
              if (names.isIdColumn(col.dbName)) (sc.Type.dsl.IdField, col.tpe)
              else
                col.tpe match {
                  case sc.Type.Optional(underlying) => (sc.Type.dsl.OptField, underlying)
                  case _                            => (sc.Type.dsl.Field, col.tpe)
                }

            val readSqlCast = SqlCast.fromPg(col.dbCol) match {
              case Some(sqlCast) => code"${sc.Type.Some}(${sc.StrLit(sqlCast.typeName)})"
              case None          => sc.Type.None.code
            }
            val writeSqlCast = SqlCast.toPg(col.dbCol) match {
              case Some(sqlCast) => code"${sc.Type.Some}(${sc.StrLit(sqlCast.typeName)})"
              case None          => sc.Type.None.code
            }
            code"override val ${col.name} = new ${cls.of(tpe, Row)}(prefix, ${sc
                .StrLit(col.dbName.value)}, $readSqlCast, $writeSqlCast)(x => extract(x).${col.name}, (row, value) => merge(row, extract(row).copy(${col.name} = value)))"
          }
          .mkCode("\n")

      val optString = sc.Type.Option.of(sc.Type.String)
      val generalizedColumn = sc.Type.dsl.FieldLikeNoHkt.of(sc.Type.Wildcard, Row)
      val columnsList = sc.Type.List.of(generalizedColumn)
      val str =
        code"""class ${structureName.name}[$Row](val prefix: $optString, val extract: $Row => ${names.RowName}, val merge: ($Row, ${names.RowName}) => $Row)
            |  extends ${sc.Type.dsl.StructureRelation.of(fieldsName, names.RowName, Row)}
            |    with ${fieldsName.of(Row)} { outer =>
            |
            |  $members
            |
            |  override val columns: $columnsList =
            |    $columnsList(${cols.map(x => x.name.code).mkCode(", ")})
            |
            |  override def copy[$NewRow](prefix: $optString, extract: $NewRow => ${names.RowName}, merge: ($NewRow, ${names.RowName}) => $NewRow): ${structureName.of(NewRow)} =
            |    new ${structureName.name}(prefix, extract, merge)
            |}
              |""".stripMargin

      sc.File(structureName, str, Nil)
    }

  def RepoTraitFile(dbLib: DbLib, repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val renderedMethods = repoMethods.map { repoMethod =>
      code"${repoMethod.comment.fold("")(c => c + "\n")}${dbLib.repoSig(repoMethod)}"
    }
    val str =
      code"""trait ${names.RepoName.name} {
            |  ${renderedMethods.mkCode("\n")}
            |}
            |""".stripMargin

    sc.File(names.RepoName, str, secondaryTypes = Nil)
  }

  def RepoImplFile(dbLib: DbLib, repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val renderedMethods: NonEmptyList[sc.Code] = repoMethods.map { repoMethod =>
      code"""|${repoMethod.comment.fold("")(c => c + "\n")}override ${dbLib.repoSig(repoMethod)} = {
             |  ${dbLib.repoImpl(repoMethod)}
             |}""".stripMargin
    }
    val str =
      code"""|class ${names.RepoImplName.name} extends ${names.RepoName} {
             |  ${renderedMethods.mkCode("\n")}
             |}
             |""".stripMargin

    sc.File(names.RepoImplName, str, secondaryTypes = Nil)
  }

  def RepoMockFile(dbLib: DbLib, idComputed: IdComputed, repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val maybeToRowParam: Option[sc.Param] =
      repoMethods.toList.collectFirst { case RepoMethod.InsertUnsaved(_, _, unsaved, _, _, _) =>
        sc.Param(sc.Ident("toRow"), sc.Type.Function1.of(unsaved.tpe, names.RowName), None)
      }

    val methods: NonEmptyList[sc.Code] =
      repoMethods.map { repoMethod =>
        code"""|${repoMethod.comment.fold("")(c => c + "\n")}override ${dbLib.repoSig(repoMethod)} = {
               |  ${dbLib.mockRepoImpl(idComputed, repoMethod, maybeToRowParam)}
               |}""".stripMargin
      }

    val classParams = List(
      maybeToRowParam,
      Some(
        sc.Param(
          sc.Ident("map"),
          sc.Type.mutableMap.of(idComputed.tpe, names.RowName),
          Some(code"${sc.Type.mutableMap}.empty")
        )
      )
    ).flatten

    val str =
      code"""|class ${names.RepoMockName.name}(${classParams.map(_.code).mkCode(",\n")}) extends ${names.RepoName} {
             |  ${methods.mkCode("\n")}
             |}
             |""".stripMargin

    sc.File(names.RepoMockName, str, secondaryTypes = Nil)
  }

  def dropCommonPrefix[T](a: List[T], b: List[T]): List[T] =
    (a, b) match {
      case (x :: xs, y :: ys) if x == y && xs.nonEmpty => dropCommonPrefix(xs, ys)
      case _                                           => a
    }
}
