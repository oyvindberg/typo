package typo

import typo.sc.syntax._

case class TableFiles(table: TableComputed, dbLib: DbLib, jsonLib: JsonLib) {

  val RowFile: sc.File = {
    val rowType = sc.Type.Qualified(table.RowName)

    val compositeId = table.maybeId match {
      case Some(x: IdComputed.Composite) =>
        code"""|{
               |  val ${x.paramName}: ${x.tpe} = ${x.tpe}(${x.cols.map(x => x.name.code).mkCode(", ")})
               |}""".stripMargin
      case _ => code""
    }

    val str =
      code"""case class ${table.RowName.name}(
            |  ${table.cols.map { case ColumnComputed(name, tpe, _) => code"$name: $tpe" }.mkCode(",\n  ")}
            |)$compositeId
            |
            |object ${table.RowName.name} {
            |  ${dbLib.instances(rowType, table.cols).mkCode("\n  ")}
            |  ${jsonLib.instances(rowType, table.cols).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(rowType, str)
  }

  val UnsavedRowFile: Option[sc.File] = table.RowUnsavedName.map { qident =>
    val rowType = sc.Type.Qualified(qident)

    val str =
      code"""case class ${qident.name}(
            |  ${table.colsUnsaved.map { case ColumnComputed(name, tpe, _) => code"$name: $tpe" }.mkCode(",\n  ")}
            |)
            |object ${qident.name} {
            |  ${jsonLib.instances(rowType, table.colsUnsaved).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(rowType, str)
  }

  val FieldValueFile: sc.File = {
    val fieldValueType = sc.Type.Qualified(table.FieldValueName)

    val members = table.cols.map { case ColumnComputed(name, tpe, col) =>
      name -> code"case class $name(override val value: $tpe) extends $fieldValueType(${sc.StrLit(col.name.value)}, value)"
    }
    val str =
      code"""sealed abstract class ${table.FieldValueName.name}[T](val name: String, val value: T)
            |
            |object ${table.FieldValueName} {
            |  ${members.map { case (_, definition) => definition }.mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(fieldValueType, str)
  }

  val IdFile: Option[sc.File] = {
    table.maybeId.map {
      case id: IdComputed.Unary =>
        val str =
          code"""case class ${id.name}(value: ${id.underlying}) extends AnyVal
              |object ${id.name} {
              |  implicit val ordering: ${sc.Type.Ordering(id.tpe)} = ${sc.Type.OrderingName}.by(_.value)
              |  ${jsonLib.anyValInstances(wrapperType = id.tpe, underlying = id.underlying).mkCode("\n  ")}
              |  ${dbLib.anyValInstances(wrapperType = id.tpe, underlying = id.underlying, id.col.dbCol.name).mkCode("\n  ")}
              |}
""".stripMargin

        sc.File(id.tpe, str)

      case id @ IdComputed.Composite(cols, qident, _) =>
        val str = code"""case class ${qident.name}(${cols.map { case ColumnComputed(i, tpe, _) => code"$i: $tpe" }.mkCode(", ")})
              |object ${qident.name} {
              |  implicit val ordering: ${sc.Type
                         .Ordering(id.tpe)} = ${sc.Type.OrderingName}.by(x => (${cols.map { case ColumnComputed(i, _, _) => code"x.${i.code}" }.mkCode(", ")}))
              |  ${jsonLib.instances(tpe = id.tpe, cols = cols).mkCode("\n  ")}
              |  ${dbLib.instances(tpe = id.tpe, cols = cols).mkCode("\n  ")}
              |}
""".stripMargin
        sc.File(id.tpe, str)
    }
  }

  val RepoTraitFile: Option[sc.File] = table.repoMethods.map { repoMethods =>
    val tpe = sc.Type.Qualified(table.RepoName)
    val str =
      code"""trait ${table.RepoName.name} {
            |  ${repoMethods.map(dbLib.repoSig).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(tpe, str)
  }

  val RepoImplTraitFile: Option[sc.File] =
    RepoTraitFile.zip(table.repoMethods).map { case (repoTrait, repoMethods) =>
      val renderedMethods: List[sc.Code] = repoMethods.map { repoMethod =>
        val impl: sc.Code = dbLib.repoImpl(table, table.default, repoMethod)
        code"""|override ${dbLib.repoSig(repoMethod)} = {
               |    $impl
               |  }""".stripMargin
      }

      val str =
        code"""trait ${table.RepoImplName.name} extends ${repoTrait.tpe} {
              |  ${renderedMethods.mkCode("\n  ")}
              |}
              |""".stripMargin

      sc.File(sc.Type.Qualified(table.RepoImplName), str)
    }

  val all: List[sc.File] = List(
    Some(RowFile),
    UnsavedRowFile,
    RepoTraitFile,
    RepoImplTraitFile,
    Some(FieldValueFile),
    IdFile
  ).flatten
}
