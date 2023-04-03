package typo
package internal
package codegen

case class TableFiles(table: TableComputed, options: InternalOptions) {
  val relation = RelationFiles(table.naming, table.relation, options)

  val UnsavedRowFile: Option[sc.File] = table.RowUnsavedName.zip(table.colsNotId).headOption.map { case (qident, colsUnsaved) =>
    val rowType = sc.Type.Qualified(qident)

    val str =
      code"""|case class ${qident.name}(
             |  ${colsUnsaved.map(_.param.code).mkCode(",\n")}
             |)
             |object ${qident.name} {
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
        val str =
          code"""case class ${id.qident.name}(value: ${id.underlying}) extends AnyVal
                |object ${id.qident.name} {
                |  implicit val ordering: ${sc.Type.Ordering.of(id.tpe)} = ${sc.Type.Ordering}.by(_.value)
                |  ${options.jsonLib.anyValInstances(wrapperType = id.tpe, underlying = id.underlying).mkCode("\n")}
                |  ${options.dbLib.anyValInstances(wrapperType = id.tpe, underlying = id.underlying, id.col.dbName).mkCode("\n")}
                |}
""".stripMargin

        Some(sc.File(id.tpe, str, secondaryTypes = Nil))

      case _: IdComputed.UnaryUserSpecified =>
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

        val str =
          code"""case class ${qident.name}(${cols.map(_.param.code).mkCode(", ")})
                |object ${qident.name} {
                |  implicit def ordering$orderingImplicits: $ordering = ${sc.Type.Ordering}.by(x => (${cols.map(col => code"x.${col.name.code}").mkCode(", ")}))
                |  ${options.jsonLib.instances(tpe = id.tpe, cols = cols).mkCode("\n")}
                |  ${options.dbLib.instances(tpe = id.tpe, cols = cols).mkCode("\n")}
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
