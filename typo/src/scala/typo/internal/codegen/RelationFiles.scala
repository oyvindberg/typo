package typo
package internal
package codegen

import play.api.libs.json.{JsNull, Json}

case class RelationFiles(naming: Naming, relation: RelationComputed, options: InternalOptions) {
  val RowFile: sc.File = {
    val compositeId = relation.maybeId match {
      case Some(x: IdComputed.Composite) =>
        code"""|{
               |  val ${x.paramName}: ${x.tpe} = ${x.tpe}(${x.cols.map(x => x.name.code).mkCode(", ")})
               |}""".stripMargin
      case _ => code""
    }

    val formattedCols = relation.cols.map { col =>
      val commentPieces = List(
        col.dbCol.comment,
        col.pointsTo map { case (relationName, columnName) =>
          val shortened = sc.QIdent(dropCommonPrefix(naming.rowName(relationName).idents, relation.RowName.value.idents))
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
      code"""case class ${relation.RowName.name}(
            |  ${formattedCols.mkCode(",\n")}
            |)$compositeId
            |
            |${obj(relation.RowName.name, options.jsonLib.instances(relation.RowName, relation.cols))}
            |""".stripMargin

    sc.File(relation.RowName, str, secondaryTypes = Nil)
  }

  val FieldValueFile: sc.File = {
    val members = {
      relation.cols.map { col =>
        val parent = if (relation.isIdColumn(col.dbName)) relation.FieldOrIdValueName else relation.FieldValueName
        code"case class ${col.name}(override val value: ${col.tpe}) extends $parent(${sc.StrLit(col.dbName.value)}, value)"
      }
    }
    val str =
      code"""sealed abstract class ${relation.FieldOrIdValueName.name}[T](val name: String, val value: T)
            |sealed abstract class ${relation.FieldValueName.name}[T](name: String, value: T) extends ${relation.FieldOrIdValueName.name}(name, value)
            |
            |${obj(relation.FieldValueName.name, members.toList)}
            |""".stripMargin

    sc.File(relation.FieldValueName, str, secondaryTypes = List(relation.FieldOrIdValueName))
  }

  def RepoTraitFile(repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val str =
      code"""trait ${relation.RepoName.name} {
            |  ${repoMethods.sortedBy(options.dbLib.repoSig).map(options.dbLib.repoSig).mkCode("\n")}
            |}
            |""".stripMargin

    sc.File(relation.RepoName, str, secondaryTypes = Nil)
  }

  def RepoImplFile(repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val renderedMethods: NonEmptyList[sc.Code] = repoMethods.sortedBy { options.dbLib.repoSig }.map { repoMethod =>
      code"""|override ${options.dbLib.repoSig(repoMethod)} = {
             |  ${options.dbLib.repoImpl(relation, repoMethod)}
             |}""".stripMargin
    }
    val allMethods = renderedMethods ++
      options.dbLib.repoAdditionalMembers(relation.maybeId, relation.RowName, relation.cols)

    val str =
      code"""|object ${relation.RepoImplName.name} extends ${relation.RepoName} {
             |  ${allMethods.mkCode("\n")}
             |}
             |""".stripMargin

    sc.File(relation.RepoImplName, str, secondaryTypes = Nil)
  }

  def RepoMockFile(idComputed: IdComputed, repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val maybeToRowParam: Option[sc.Param] =
      repoMethods.toList.collectFirst { case RepoMethod.InsertUnsaved(unsaved, _, _, _) =>
        sc.Param(sc.Ident("toRow"), sc.Type.Function1.of(unsaved.tpe, relation.RowName), None)
      }

    val methods: NonEmptyList[sc.Code] =
      repoMethods.sortedBy { options.dbLib.repoSig }.map { repoMethod =>
        code"""|override ${options.dbLib.repoSig(repoMethod)} = {
               |  ${options.dbLib.mockRepoImpl(relation, idComputed, repoMethod, maybeToRowParam)}
               |}""".stripMargin
      }

    val classParams = List(
      maybeToRowParam,
      Some(
        sc.Param(
          sc.Ident("map"),
          sc.Type.mutableMap.of(idComputed.tpe, relation.RowName),
          Some(code"${sc.Type.mutableMap}.empty")
        )
      )
    ).flatten

    val str =
      code"""|class ${relation.RepoMockName.name}(${classParams.map(_.code).mkCode(",\n")}) extends ${relation.RepoName} {
             |  ${methods.mkCode("\n")}
             |}
             |""".stripMargin

    sc.File(relation.RepoMockName, str, secondaryTypes = Nil)
  }

  def dropCommonPrefix[T](a: List[T], b: List[T]): List[T] =
    (a, b) match {
      case (x :: xs, y :: ys) if x == y && xs.nonEmpty => dropCommonPrefix(xs, ys)
      case _                                           => a
    }
}
