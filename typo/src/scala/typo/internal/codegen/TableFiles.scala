package typo
package internal
package codegen

case class TableFiles(table: TableComputed, options: InternalOptions) {
  val relation = RelationFiles(table.naming, table.relation, options)

  val UnsavedRowFile: Option[sc.File] = table.RowUnsavedName.zip(table.colsNotId).zip(table.maybeId).headOption.map { case ((rowType, colsUnsaved), id) =>
    val comments = scaladoc(s"This class corresponds to a row in table `${table.dbTable.name.value}` which has not been persisted yet")(Nil)

    val maybeToRow: sc.Code = {
      val keyValues = table.cols.map { col =>
        val ref: sc.Code = id match {
          case unary: IdComputed.Unary if unary.col.name == col.name =>
            sc.QIdent.of(unary.paramName)
          case composite: IdComputed.Composite if composite.colByName.contains(col.name) =>
            sc.QIdent.of(composite.paramName, composite.colByName(col.name).name)
          case _ =>
            if (col.hasDefault) {
              code"""|${col.name} match {
                     |  case ${table.default.Defaulted}.${table.default.UseDefault} => sys.error("cannot produce row when you depend on a value which is defaulted in database")
                     |  case ${table.default.Defaulted}.${table.default.Provided}(value) => value
                     |}""".stripMargin
            } else sc.QIdent.of(col.name)
        }

        col.name -> ref
      }
      val name = if (table.cols.exists(_.hasDefault)) sc.Ident("unsafeToRow") else sc.Ident("toRow")

      code"""|def $name(${id.param}): ${table.relation.RowName} =
             |  ${table.relation.RowName}(
             |    ${keyValues.map { case (k, v) => code"$k = $v" }.mkCode(",\n")}
             |  )""".stripMargin
    }
    val str =
      code"""|$comments
             |case class ${rowType.name}(
             |  ${colsUnsaved.map(_.param.code).mkCode(",\n")}
             |) {
             |  $maybeToRow
             |}
             |object ${rowType.name} {
             |  ${options.jsonLib.instances(rowType, colsUnsaved).mkCode("\n")}
             |}
             |""".stripMargin

    sc.File(rowType, str, secondaryTypes = Nil)
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
