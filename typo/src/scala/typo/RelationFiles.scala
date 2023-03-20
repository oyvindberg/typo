package typo

import typo.sc.syntax._

case class RelationFiles(relation: RelationComputed, dbLib: DbLib, jsonLib: JsonLib) {
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
      col.pointsTo match {
        case Some((relationName, columnName)) =>
          val row = names.titleCase(relation.pkg, relationName, "Row")
          code"""/** Points to [[$row.${names.field(columnName)}]] */
                |  ${col.param}""".stripMargin
        case None => col.param.code
      }
    }
    val str =
      code"""case class ${relation.RowName.name}(
            |  ${formattedCols.mkCode(",\n  ")}
            |)$compositeId
            |
            |object ${relation.RowName.name} {
            |  ${dbLib.instances(rowType, relation.cols).mkCode("\n  ")}
            |  ${jsonLib.instances(rowType, relation.cols).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(rowType, str)
  }

  val FieldValueFile: sc.File = {
    val fieldValueType = sc.Type.Qualified(relation.FieldValueName)

    val members = relation.cols.map { col =>
      col.name -> code"case class ${col.name}(override val value: ${col.tpe}) extends $fieldValueType(${sc.StrLit(col.dbName.value)}, value)"
    }
    val str =
      code"""sealed abstract class ${relation.FieldValueName.name}[T](val name: String, val value: T)
            |
            |object ${relation.FieldValueName} {
            |  ${members.map { case (_, definition) => definition }.mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(fieldValueType, str)
  }

  def RepoTraitFile(repoMethods: List[RepoMethod]): sc.File = {
    val tpe = sc.Type.Qualified(relation.RepoName)
    val str =
      code"""trait ${relation.RepoName.name} {
            |  ${repoMethods.map(dbLib.repoSig).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(tpe, str)
  }

  def RepoImplFile(repoMethods: List[RepoMethod]): sc.File = {
    val renderedMethods: List[sc.Code] = repoMethods.map { repoMethod =>
      val impl: sc.Code = dbLib.repoImpl(relation, repoMethod)
      code"""|override ${dbLib.repoSig(repoMethod)} = {
               |    $impl
               |  }""".stripMargin
    }

    val str =
      code"""object ${relation.RepoImplName.name} extends ${sc.Type.Qualified(relation.RepoName)} {
              |  ${renderedMethods.mkCode("\n  ")}
              |}
              |""".stripMargin

    sc.File(sc.Type.Qualified(relation.RepoImplName), str)
  }
}
