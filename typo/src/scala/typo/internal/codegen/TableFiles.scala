package typo
package internal
package codegen

import play.api.libs.json.{JsNull, Json}

case class TableFiles(table: TableComputed, options: InternalOptions) {
  val relation = RelationFiles(table.naming, table.relation, options)

  val UnsavedRowFile: Option[sc.File] = table.maybeUnsavedRow.map { unsaved =>
    val comments = scaladoc(s"This class corresponds to a row in table `${table.dbTable.name.value}` which has not been persisted yet")(Nil)

    val toRow: sc.Code = {
      def mkDefaultParamName(col: ColumnComputed): sc.Ident =
        sc.Ident(col.name.value).appended("Default")

      val params: NonEmptyList[sc.Param] =
        unsaved.defaultCols.map { case (col, originalType) =>
          sc.Param(mkDefaultParamName(col), sc.Type.ByName(originalType))
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

      code"""|def toRow(${params.map(_.code).mkCode(", ")}): ${table.relation.RowName} =
             |  ${table.relation.RowName}(
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
             |${obj(unsaved.tpe.name, options.jsonLib.instances(unsaved.tpe, unsaved.allCols))}
             |""".stripMargin

    sc.File(unsaved.tpe, str, secondaryTypes = Nil)
  }

  val JoinedRowFile: Option[sc.File] = table.RowJoined.map { rowJoined =>
    val str =
      code"""|case class ${rowJoined.name.name}(
             |  ${rowJoined.params.map(_.param.code).mkCode(",\n")}
             |)
             |""".stripMargin

    sc.File(sc.Type.Qualified(rowJoined.name), str, secondaryTypes = Nil)
  }

  val IdFile: Option[sc.File] = {
    table.maybeId.flatMap {
      case id: IdComputed.UnaryNormal =>
        val comments = scaladoc(s"Type for the primary key of table `${table.dbTable.name.value}`")(Nil)

        val str =
          code"""$comments
                |case class ${id.tpe.name}(value: ${id.underlying}) extends AnyVal
                |object ${id.tpe.name} {
                |  implicit val ordering: ${sc.Type.Ordering.of(id.tpe)} = ${sc.Type.Ordering}.by(_.value)
                |  ${options.jsonLib.anyValInstances(wrapperType = id.tpe, underlying = id.underlying).mkCode("\n")}
                |  ${options.dbLib.anyValInstances(wrapperType = id.tpe, underlying = id.underlying).mkCode("\n")}
                |}
""".stripMargin

        Some(sc.File(id.tpe, str, secondaryTypes = Nil))

      case _: IdComputed.UnaryUserSpecified =>
        None
      case _: IdComputed.UnaryInherited =>
        None
      case id @ IdComputed.Composite(cols, qident, _) =>
        val ordering = sc.Type.Ordering.of(id.tpe)

        // don't demand that user-specified types are ordered, but compositive key will be if they are
        val orderingImplicits = cols.toList.filter(x => sc.Type.containsUserDefined(x.tpe)) match {
          case Nil => sc.Code.Empty
          case nonEmpty =>
            val orderingParams = nonEmpty.map(_.tpe).distinct.zipWithIndex.map { case (colTpe, idx) => code"O$idx: ${sc.Type.Ordering.of(colTpe)}" }
            code"(implicit ${orderingParams.mkCode(", ")})"
        }
        val comments = scaladoc(s"Type for the composite primary key of table `${table.dbTable.name.value}`")(Nil)

        val str =
          code"""$comments
                |case class ${qident.name}(${cols.map(_.param.code).mkCode(", ")})
                |object ${qident.name} {
                |  implicit def ordering$orderingImplicits: $ordering = ${sc.Type.Ordering}.by(x => (${cols.map(col => code"x.${col.name.code}").mkCode(", ")}))
                |  ${options.jsonLib.instances(tpe = id.tpe, cols = cols).mkCode("\n")}
                |}
""".stripMargin
        Some(sc.File(id.tpe, str, secondaryTypes = Nil))
    }
  }

  val all: List[sc.File] = List(
    Some(relation.RowFile),
    UnsavedRowFile,
    table.repoMethods.map(relation.RepoTraitFile),
    table.repoMethods.map(relation.RepoImplFile),
    Some(relation.FieldValueFile),
    IdFile
    // JoinedRowFile
  ).flatten
}
