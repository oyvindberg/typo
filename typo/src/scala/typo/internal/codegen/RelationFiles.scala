package typo
package internal
package codegen

import play.api.libs.json.{JsNull, Json}

case class RelationFiles(naming: Naming, relation: RelationComputed, options: InternalOptions) {
  val RowFile: sc.File = {
    val rowType = sc.Type.Qualified(relation.RowName)

    val compositeId = relation.maybeId match {
      case Some(x: IdComputed.Composite) =>
        code"""|{
               |  val ${x.paramName}: ${x.tpe} = ${x.tpe}(${x.cols.map(x => x.name.code).mkCode(", ")})
               |}""".stripMargin
      case _ => code""
    }

    val formattedCols = relation.cols.map { col =>
      val commentPieces = List(
        col.comment,
        col.pointsTo map { case (relationName, columnName) =>
          val shortened = sc.QIdent(dropCommonPrefix(naming.rowName(relationName).idents, relation.RowName.idents))
          s"Points to [[${sc.renderTree(shortened)}.${naming.field(columnName).value}]]"
        },
        col.jsonDescription match {
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
            |object ${relation.RowName.name} {
            |  ${options.dbLib.instances(rowType, relation.cols).mkCode("\n")}
            |  ${options.jsonLib.instances(rowType, relation.cols).mkCode("\n")}
            |}
            |""".stripMargin

    sc.File(rowType, str, secondaryTypes = Nil)
  }

  val FieldValueFile: sc.File = {
    val fieldValueType = sc.Type.Qualified(relation.FieldValueName)
    val fieldValueIdType = sc.Type.Qualified(relation.FieldOrIdValueName)

    val members = {
      relation.cols.map { col =>
        val parent = if (relation.isIdColumn(col.dbName)) fieldValueIdType else fieldValueType
        code"case class ${col.name}(override val value: ${col.tpe}) extends $parent(${sc.StrLit(col.dbName.value)}, value)"
      }
    }
    val str =
      code"""sealed abstract class ${relation.FieldOrIdValueName.name}[T](val name: String, val value: T)
            |sealed abstract class ${relation.FieldValueName.name}[T](name: String, value: T) extends ${relation.FieldOrIdValueName.name}(name, value)
            |
            |object ${relation.FieldValueName} {
            |  ${members.mkCode("\n")}
            |}
            |""".stripMargin

    sc.File(fieldValueType, str, secondaryTypes = List(fieldValueIdType))
  }

  def RepoTraitFile(repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val tpe = sc.Type.Qualified(relation.RepoName)
    val str =
      code"""trait ${relation.RepoName.name} {
            |  ${repoMethods.sortedBy(options.dbLib.repoSig).map(options.dbLib.repoSig).mkCode("\n")}
            |}
            |""".stripMargin

    sc.File(tpe, str, secondaryTypes = Nil)
  }

  def RepoImplFile(repoMethods: NonEmptyList[RepoMethod]): sc.File = {
    val renderedMethods: NonEmptyList[sc.Code] = repoMethods.sortedBy { options.dbLib.repoSig }.map { repoMethod =>
      code"""|override ${options.dbLib.repoSig(repoMethod)} = {
             |  ${options.dbLib.repoImpl(relation, repoMethod)}
             |}""".stripMargin
    }

    val str =
      code"""|object ${relation.RepoImplName.name} extends ${sc.Type.Qualified(relation.RepoName)} {
             |  ${renderedMethods.mkCode("\n")}
             |}
             |""".stripMargin

    sc.File(sc.Type.Qualified(relation.RepoImplName), str, secondaryTypes = Nil)
  }

  def dropCommonPrefix[T](a: List[T], b: List[T]): List[T] =
    (a, b) match {
      case (x :: xs, y :: ys) if x == y && xs.nonEmpty => dropCommonPrefix(xs, ys)
      case _                                           => a
    }
}
