package typo
package internal
package codegen

import play.api.libs.json.{JsNull, Json}

case class TableFiles(table: ComputedTable, options: InternalOptions) {
  val relation = RelationFiles(table.naming, table.names, options)

  val UnsavedRowFile: Option[sc.File] = table.maybeUnsavedRow.map { unsaved =>
    val comments = scaladoc(s"This class corresponds to a row in table `${table.dbTable.name.value}` which has not been persisted yet")(Nil)

    val toRow: sc.Code = {
      def mkDefaultParamName(col: ComputedColumn): sc.Ident =
        sc.Ident(col.name.value).appended("Default")

      val params: NonEmptyList[sc.Param] =
        unsaved.defaultCols.map { case (col, originalType) =>
          sc.Param(mkDefaultParamName(col), sc.Type.ByName(originalType), None)
        }

      val keyValues1 =
        unsaved.defaultCols.map { case (col, _) =>
          val defaultParamName = mkDefaultParamName(col)
          val impl = code"""|${col.name} match {
                   |  case ${table.default.Defaulted}.${table.default.UseDefault} => $defaultParamName
                   |  case ${table.default.Defaulted}.${table.default.Provided}(value) => value
                   |}""".stripMargin
          (col.name, impl)
        }

      val keyValues2 =
        unsaved.restCols.map { col =>
          (col.name, sc.QIdent.of(col.name).code)
        }

      val keyValues: List[(sc.Ident, sc.Code)] =
        keyValues2 ::: keyValues1.toList

      code"""|def toRow(${params.map(_.code).mkCode(", ")}): ${table.names.RowName} =
             |  ${table.names.RowName}(
             |    ${keyValues.map { case (k, v) => code"$k = $v" }.mkCode(",\n")}
             |  )""".stripMargin
    }

    val formattedCols = unsaved.allCols.map { col =>
      val commentPieces = List(
        col.dbCol.columnDefault.map(x => s"Default: $x"),
        col.dbCol.comment,
        col.pointsTo map { case (relationName, columnName) =>
          val shortened = sc.QIdent(relation.dropCommonPrefix(table.naming.rowName(relationName).idents, relation.RowFile.tpe.value.idents))
          s"Points to [[${sc.renderTree(shortened)}.${table.naming.field(columnName).value}]]"
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

      val default = col.dbCol.columnDefault match {
        case Some(_) => code" = ${table.default.Defaulted}.${table.default.UseDefault}"
        case None    => sc.Code.Empty
      }
      code"$comment${col.param.code}$default"
    }

    val str =
      code"""|$comments
             |case class ${unsaved.tpe.name}(
             |  ${formattedCols.mkCode(",\n")}
             |) {
             |  $toRow
             |}
             |${obj(unsaved.tpe.name, options.jsonLibs.flatMap(_.instances(unsaved.tpe, unsaved.allCols)))}
             |""".stripMargin

    sc.File(unsaved.tpe, str, secondaryTypes = Nil)
  }
  
  def ordering(tpe: sc.Type, constituents: NonEmptyList[sc.Param]) = {
    val ordering = sc.Type.Ordering.of(tpe)

    val impl =
      constituents match {
        case NonEmptyList(col, Nil) =>
          code"${sc.Type.Ordering}.by(_.${col.name.code})"
        case more =>
          code"${sc.Type.Ordering}.by(x => (${more.map(col => code"x.${col.name.code}").mkCode(", ")}))"
      }

    // don't demand that parts of the id are ordered, for instance if they are custom types or user-provided
    val needsImplicits = constituents.toList.filterNot { x =>
      val baseType = sc.Type.base(x.tpe)
      val hasOrdering = sc.Type.HasOrdering(baseType)

      def isInternal = baseType match {
        case sc.Type.Qualified(qident)
            if qident.idents.startsWith(options.pkg.idents)
            // custom types do not have ordering
              && table.scalaTypeMapper.customTypes.All.forall(_.typoType != baseType) =>
          true
        case _ => false
      }

      hasOrdering || isInternal
    }

    needsImplicits match {
      case Nil =>
        code"implicit val ordering: $ordering = $impl"
      case nonEmpty =>
        val orderingParams = nonEmpty.map(_.tpe).distinct.zipWithIndex.map { case (colTpe, idx) => code"O$idx: ${sc.Type.Ordering.of(colTpe)}" }
        code"implicit def ordering(implicit ${orderingParams.mkCode(", ")}): $ordering = $impl"
    }
  }

  val IdFile: Option[sc.File] = {
    table.maybeId.flatMap {
      case id: IdComputed.UnaryNormal =>
        val comments = scaladoc(s"Type for the primary key of table `${table.dbTable.name.value}`")(Nil)

        val str =
          code"""$comments
                |case class ${id.tpe.name}(value: ${id.underlying}) extends AnyVal
                |object ${id.tpe.name} {
                |  ${ordering(id.tpe, NonEmptyList(sc.Param(sc.Ident("value"), id.underlying, None)))}
                |  ${options.jsonLibs.flatMap(_.anyValInstances(wrapperType = id.tpe, underlying = id.underlying)).mkCode("\n")}
                |  ${options.dbLib.toList.flatMap(_.anyValInstances(wrapperType = id.tpe, underlying = id.underlying)).mkCode("\n")}
                |}
""".stripMargin

        Some(sc.File(id.tpe, str, secondaryTypes = Nil))

      case _: IdComputed.UnaryUserSpecified =>
        None
      case _: IdComputed.UnaryInherited =>
        None
      case id @ IdComputed.Composite(cols, qident, _) =>
        val comments = scaladoc(s"Type for the composite primary key of table `${table.dbTable.name.value}`")(Nil)

        val str =
          code"""$comments
                |case class ${qident.name}(${cols.map(_.param.code).mkCode(", ")})
                |object ${qident.name} {
                |  ${ordering(id.tpe, cols.map(cc => sc.Param(cc.param.name, cc.tpe, None)))}
                |  ${options.jsonLibs.flatMap(_.instances(tpe = id.tpe, cols = cols)).mkCode("\n")}
                |}
""".stripMargin
        Some(sc.File(id.tpe, str, secondaryTypes = Nil))
    }
  }

  private val maybeMockRepo: Option[sc.File] =
    for {
      id <- table.maybeId
      repoMethods <- table.repoMethods
      dbLib <- options.dbLib
    } yield relation.RepoMockFile(dbLib, id, repoMethods)

  val all: List[sc.File] = List(
    Some(relation.RowFile),
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
    // JoinedRowFile
  ).flatten
}
