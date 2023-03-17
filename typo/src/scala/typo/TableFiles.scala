package typo

import typo.sc.syntax._

case class TableFiles(table: TableComputed, dbLib: DbLib, jsonLib: JsonLib) {
  val relation = RelationFiles(table.relation, dbLib, jsonLib)

  val UnsavedRowFile: Option[sc.File] = table.RowUnsavedName.map { qident =>
    val rowType = sc.Type.Qualified(qident)

    val str =
      code"""case class ${qident.name}(
            |  ${table.colsUnsaved.map(_.param.code).mkCode(",\n  ")}
            |)
            |object ${qident.name} {
            |  ${jsonLib.instances(rowType, table.colsUnsaved).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(rowType, str)
  }
  val IdFile: Option[sc.File] = {
    table.maybeId.map {
      case id: IdComputed.Unary =>
        val str =
          code"""case class ${id.name}(value: ${id.underlying}) extends AnyVal
                |object ${id.name} {
                |  implicit val ordering: ${sc.Type.Ordering.of(id.tpe)} = ${sc.Type.Ordering}.by(_.value)
                |  ${jsonLib.anyValInstances(wrapperType = id.tpe, underlying = id.underlying).mkCode("\n  ")}
                |  ${dbLib.anyValInstances(wrapperType = id.tpe, underlying = id.underlying, id.col.dbName).mkCode("\n  ")}
                |}
""".stripMargin

        sc.File(id.tpe, str)

      case id @ IdComputed.Composite(cols, qident, _) =>
        val ordering = sc.Type.Ordering.of(id.tpe)
        val str =
          code"""case class ${qident.name}(${cols.map(_.param.code).mkCode(", ")})
                |object ${qident.name} {
                |  implicit val ordering: $ordering = ${sc.Type.Ordering}.by(x => (${cols.map(col => code"x.${col.name.code}").mkCode(", ")}))
                |  ${jsonLib.instances(tpe = id.tpe, cols = cols).mkCode("\n  ")}
                |  ${dbLib.instances(tpe = id.tpe, cols = cols).mkCode("\n  ")}
                |}
""".stripMargin
        sc.File(id.tpe, str)
    }
  }

  val all: List[sc.File] = List(
    Some(relation.RowFile),
    UnsavedRowFile,
    table.repoMethods.map(relation.RepoTraitFile),
    table.repoMethods.map(relation.RepoImplTraitFile),
    Some(relation.FieldValueFile),
    IdFile
  ).flatten
}
