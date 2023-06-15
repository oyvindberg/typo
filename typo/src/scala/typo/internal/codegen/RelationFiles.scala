package typo
package internal
package codegen

import play.api.libs.json.{JsNull, Json}

case class RelationFiles(naming: Naming, names: ComputedNames, options: InternalOptions) {
  val RowFile: sc.File = {
    val compositeId = names.maybeId match {
      case Some(x: IdComputed.Composite) =>
        code"""|{
               |  val ${x.paramName}: ${x.tpe} = ${x.tpe}(${x.cols.map(x => x.name.code).mkCode(", ")})
               |}""".stripMargin
      case _ => code""
    }

    val formattedCols = names.cols.map { col =>
      val commentPieces = List(
        col.dbCol.comment,
        col.pointsTo map { case (relationName, columnName) =>
          val shortened = sc.QIdent(dropCommonPrefix(naming.rowName(relationName).idents, names.RowName.value.idents))
          s"Points to [[${sc.renderTree(shortened)}.${naming.field(columnName).value}]]"
        },
        col.dbCol.jsonDescription match {
          case JsNull => None
          case other  => if (options.debugTypes) Some(s"debug: ${Json.stringify(other)}") else None
        }
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
    val str =
      code"""case class ${names.RowName.name}(
            |  ${formattedCols.mkCode(",\n")}
            |)$compositeId
            |
            |${obj(names.RowName.name, options.jsonLibs.flatMap(_.instances(names.RowName, names.cols)))}
            |""".stripMargin

    sc.File(names.RowName, str, secondaryTypes = Nil)
  }

  val FieldValueFile: sc.File = {
    val members = {
      names.cols.map { col =>
        val parent = if (names.isIdColumn(col.dbName)) names.FieldOrIdValueName else names.FieldValueName
        code"case class ${col.name}(override val value: ${col.tpe}) extends $parent(${sc.StrLit(col.dbName.value)}, value)"
      }
    }
    val str =
      code"""sealed abstract class ${names.FieldOrIdValueName.name}[T](val name: String, val value: T)
            |sealed abstract class ${names.FieldValueName.name}[T](name: String, value: T) extends ${names.FieldOrIdValueName.name}(name, value)
            |
            |${obj(names.FieldValueName.name, members.toList)}
            |""".stripMargin

    sc.File(names.FieldValueName, str, secondaryTypes = List(names.FieldOrIdValueName))
  }

  def RepoTraitFile(dbLib: DbLib, repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val str =
      code"""trait ${names.RepoName.name} {
            |  ${repoMethods.sortedBy(dbLib.repoSig).map(dbLib.repoSig).mkCode("\n")}
            |}
            |""".stripMargin

    sc.File(names.RepoName, str, secondaryTypes = Nil)
  }

  def RepoImplFile(dbLib: DbLib, repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val renderedMethods: NonEmptyList[sc.Code] = repoMethods.sortedBy { dbLib.repoSig }.map { repoMethod =>
      code"""|override ${dbLib.repoSig(repoMethod)} = {
             |  ${dbLib.repoImpl(repoMethod)}
             |}""".stripMargin
    }
    val allMethods = renderedMethods ++
      dbLib.repoAdditionalMembers(names.maybeId, names.RowName, names.cols)

    val str =
      code"""|object ${names.RepoImplName.name} extends ${names.RepoName} {
             |  ${allMethods.mkCode("\n")}
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
      repoMethods.sortedBy { dbLib.repoSig }.map { repoMethod =>
        code"""|override ${dbLib.repoSig(repoMethod)} = {
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
