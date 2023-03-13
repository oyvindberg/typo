package typo

import typo.sc.syntax.*

import java.nio.file.{Files, Path}

case class TableFiles(table: TableComputed, dbLib: DbLib, jsonLib: JsonLib) {

  val RowFile: sc.File = {
    val rowType = sc.Type.Qualified(table.RowName)
    val str =
      code"""case class ${table.RowName.last}(
            |  ${table.scalaFields.map { case (name, tpe, _) => code"$name: $tpe" }.mkCode(",\n  ")}
            |)
            |object ${table.RowName.last} {
            |  ${dbLib.instances(rowType, table.scalaFields).mkCode("\n  ")}
            |  ${jsonLib.instances(rowType, table.scalaFields).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(rowType, str)
  }

  val UnsavedRowFile: Option[sc.File] = table.RowUnsavedName.map { qident =>
    val rowType = sc.Type.Qualified(qident)

    val str =
      code"""case class ${qident.last}(
            |  ${table.scalaFieldsUnsaved.map { case (name, tpe, _) => code"$name: $tpe" }.mkCode(",\n  ")}
            |)
            |object ${qident.last} {
            |  ${jsonLib.instances(rowType, table.scalaFieldsUnsaved).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(rowType, str)
  }

  val FieldValueFile: sc.File = {
    val fieldValueType = sc.Type.Qualified(table.FieldValueName)

    val members = table.scalaFields.map { case (name, tpe, col) =>
      name -> code"case class $name(override val value: $tpe) extends $fieldValueType(${sc.StrLit(col.name.value)}, value)"
    }
    val str =
      code"""sealed abstract class ${table.FieldValueName.last}[T](val name: String, val value: T)
            |
            |object ${table.FieldValueName} {
            |  ${members.map { case (_, definition) => definition }.mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(fieldValueType, str)
  }

  val IdFile: Option[sc.File] = {
    table.maybeId.map { id =>
      val str =
        code"""case class ${id.name}(value: ${id.underlying}) extends AnyVal
              |object ${id.name} {
              |  implicit val ordering: ${sc.Type.Ordering(id.tpe)} = Ordering.by(_.value)
              |  ${jsonLib.anyValInstances(wrapperType = id.tpe, underlying = id.underlying).mkCode("\n  ")}
              |  ${dbLib.anyValInstances(wrapperType = id.tpe, underlying = id.underlying).mkCode("\n  ")}
              |}
""".stripMargin

      sc.File(id.tpe, str)
    }
  }

  val RepoTraitFile: Option[sc.File] = table.repoMethods.map { repoMethods =>
    val tpe = sc.Type.Qualified(table.RepoName)
    val str =
      code"""trait ${table.RepoName.last} {
            |  ${repoMethods.map(dbLib.repoSig).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(tpe, str)
  }

  val RepoImplTraitFile: Option[sc.File] =
    RepoTraitFile.zip(table.repoMethods).map { case (repoTrait, repoMethods) =>
      val renderedMethods: List[sc.Code] = repoMethods.map { repoMethod =>
        val impl: sc.Code = dbLib.repoImpl(table, repoMethod)
        code"override ${dbLib.repoSig(repoMethod)} = \n    $impl"
      }

      val str =
        code"""trait ${table.RepoImplName.last} extends ${repoTrait.tpe} {
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
