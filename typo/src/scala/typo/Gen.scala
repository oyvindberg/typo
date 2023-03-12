package typo

import typo.sc.syntax.*

import java.nio.file.{Files, Path}

object Gen {
  def scalaType(pkg: sc.QIdent, col: db.Col): sc.Type = {
    val baseTpe = col.tpe match {
      case db.Type.BigInt              => sc.Type.Long
      case db.Type.VarChar(_)          => sc.Type.String
      case db.Type.Boolean             => sc.Type.Boolean
      case db.Type.StringEnum(name, _) => sc.Type.Qualified(pkg / sc.Ident.`enum`(name))
    }
    if (col.isNotNull) baseTpe else sc.Type.Option(baseTpe)
  }

  sealed trait Repo
  object Repo {
    case class Direct(repoMethods: List[RepoMethod], table: db.Table) extends Repo
    case class Cached(repoMethods: List[RepoMethod], table: db.Table) extends Repo
  }

  sealed trait RepoMethod

  object RepoMethod {
    case class SelectAll(rowType: sc.Type) extends RepoMethod
    case class SelectById(idParam: sc.Param, rowType: sc.Type) extends RepoMethod
    case class SelectAllByIds(idsParam: sc.Param, rowType: sc.Type) extends RepoMethod
    case class SelectByUnique(params: List[sc.Param], rowType: sc.Type) extends RepoMethod
    case class SelectByFieldValues(param: sc.Param, rowType: sc.Type) extends RepoMethod
    case class UpdateFieldValues(idParam: sc.Param, param: sc.Param) extends RepoMethod
    case class InsertDbGeneratedKey(unsavedParam: sc.Param, idType: sc.Type) extends RepoMethod
    case class InsertProvidedKey(idParam: sc.Param, unsavedParam: sc.Param) extends RepoMethod
    case class InsertOnlyKey(idParam: sc.Param) extends RepoMethod
    case class Delete(idParam: sc.Param) extends RepoMethod
  }

  def stringEnumClass(pkg: sc.QIdent, `enum`: db.Type.StringEnum, jsonLib: JsonLib): sc.File = {
    val EnumName = sc.Ident.`enum`(`enum`.name)
    val EnumType = sc.Type.Qualified(pkg / EnumName)

    val members = `enum`.values.map { value =>
      val name = sc.Ident.enumValue(value)
      name -> code"case object $name extends $EnumName(${sc.StrLit(value)})"
    }
    val ByName = sc.Ident("ByName")
    val str =
      code"""sealed abstract class $EnumName(val value: ${sc.Type.String})
            |object $EnumName {
            |  ${members.map { case (_, definition) => definition }.mkCode("\n  ")}
            |
            |  val All: ${sc.Type.List(EnumType)} = ${sc.Type.ListName}(${members.map { case (ident, _) => ident.code }.mkCode(", ")})
            |  val Names: ${sc.Type.String} = All.map(_.value).mkString(", ")
            |  val ByName: ${sc.Type.Map(sc.Type.String, EnumType)} = All.map(x => (x.value, x)).toMap
            |
            |  implicit val column: ${DbLib.anorm.Column(EnumType)} =
            |    implicitly[${DbLib.anorm.Column(sc.Type.String)}].mapResult { str =>
            |      $ByName.get(str).toRight(${DbLib.anorm.SqlMappingError}(s"$$str was not among $$Names"))
            |    }
            |
            |  implicit val toStatement: ${DbLib.anorm.ToStatement(EnumType)} = implicitly[${DbLib.anorm.ToStatement(sc.Type.String)}].contramap(_.value)
            |  ${jsonLib.stringEnumInstances(EnumType, sc.Type.String, lookup = ByName).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(EnumType, str)
  }

  case class TableFiles(pkg: sc.QIdent, table: db.Table, dbLib: DbLib, jsonLib: JsonLib) {
    val scalaFields: Seq[(sc.Ident, sc.Type, db.Col)] = {
      table.cols.map {
        case col @ db.Col(colName, _, isNotNull, _) if table.primaryKey.exists(_.colName == colName) =>
          if (!isNotNull) {
            sys.error(s"assumption: id column in ${table.name.value} should be not null")
          }
          (sc.Ident.field(colName), sc.Type.Qualified(pkg / sc.Ident.id(table.name)), col)
        case col =>
          val finalType: sc.Type = scalaType(pkg, col)
          (sc.Ident.field(col.name), finalType, col)
      }
    }

    val RowFile: sc.File = {
      val name = sc.Ident.row(table.name)
      val rowType = sc.Type.Qualified(pkg / name)
      val fields = scalaFields
      val fetchValues = fields.map { case (name, tpe, col) => code"$name = row[$tpe](${sc.StrLit(col.name.value)})" }
      val str =
        code"""case class $name(
              |  ${fields.map { case (name, tpe, _) => code"$name: $tpe" }.mkCode(",\n  ")}
              |)
              |object $name {
              |  implicit val rowParser: ${DbLib.anorm.RowParser(rowType)} = { row =>
              |    ${DbLib.anorm.Success}(
              |      $name(
              |        ${fetchValues.mkCode(",\n        ")}
              |      )
              |    )
              |  }
              |  ${jsonLib.instances(rowType).mkCode("\n  ")}
              |}
              |""".stripMargin

      sc.File(rowType, str)
    }

    val UnsavedRowFile: Option[sc.File] = {
      val name = sc.Ident.rowUnsaved(table.name)
      val rowType = sc.Type.Qualified(pkg / name)

      scalaFields.filterNot { case (_, _, col) => table.primaryKey.exists(_.colName == col.name) } match {
        case Nil => None
        case fields =>
          val str =
            code"""case class $name(
                  |  ${fields.map { case (name, tpe, _) => code"$name: $tpe" }.mkCode(",\n  ")}
                  |)
                  |object $name {
                  |  ${jsonLib.instances(rowType).mkCode("\n  ")}
                  |}
                  |""".stripMargin
          Some(sc.File(rowType, str))
      }
    }

    val FieldValueFile: sc.File = {
      val fieldValueName = sc.Ident.fieldValue(table.name)
      val fieldValueType = sc.Type.Qualified(pkg / fieldValueName)

      val members = scalaFields.map { case (name, tpe, col) =>
        name -> code"case class $name(override val value: $tpe) extends $fieldValueType(${sc.StrLit(col.name.value)}, value)"
      }
      val str =
        code"""sealed abstract class $fieldValueName[T: ${DbLib.anorm.ToStatementName}](val name: String, val value: T) {
              |  def toNamedParameter: ${DbLib.anorm.NamedParameter} = ${DbLib.anorm.NamedParameter}(name, ${DbLib.anorm.ParameterValue}.toParameterValue(value))
              |}
              |
              |object $fieldValueName {
              |  ${members.map { case (_, definition) => definition }.mkCode("\n  ")}
              |}
              |""".stripMargin

      sc.File(fieldValueType, str)
    }

    val IdFile: Option[sc.File] = {
      table.idCol.map { idCol =>
        val name = sc.Ident.id(table.name)
        val tpe = sc.Type.Qualified(pkg / name)
        val underlying = scalaType(pkg, idCol)
        val str =
          code"""case class $name(value: $underlying) extends AnyVal
                |object $name {
                |  implicit val column: ${DbLib.anorm.Column(tpe)} = implicitly[${DbLib.anorm.Column(underlying)}].map($name.apply)
                |  implicit val ordering: ${sc.Type.Ordering(tpe)} = Ordering.by(_.value)
                |  implicit val toStatement: ${DbLib.anorm.ToStatement(tpe)} = implicitly[${DbLib.anorm.ToStatement(underlying)}].contramap(_.value)
                |  ${jsonLib.anyValInstances(wrapperType = tpe, underlying = underlying).mkCode("\n")}
                |}
                |""".stripMargin

        sc.File(tpe, str)
      }
    }

    val repoMethods: Option[List[RepoMethod]] = Some {
      List(
        table.idCol.zip(IdFile) match {
          case Some((idCol, idFile)) =>
            val idParam = sc.Param(sc.Ident.field(idCol.name), idFile.tpe)

            val updateMethod = UnsavedRowFile.map { unsavedRowFile =>
              val unsavedParam = sc.Param(sc.Ident("unsaved"), unsavedRowFile.tpe)

              if (idCol.hasDefault) RepoMethod.InsertDbGeneratedKey(unsavedParam, idFile.tpe)
              else RepoMethod.InsertProvidedKey(idParam, unsavedParam)

            }

            val fieldValuesParam = sc.Param(
              sc.Ident("fieldValues"),
              sc.Type.List(sc.Type.TApply(sc.Type.Qualified(pkg / sc.Ident.fieldValue(table.name)), List(sc.Type.Wildcard)))
            )

            List(
              Some(RepoMethod.SelectAll(RowFile.tpe)),
              Some(RepoMethod.SelectById(idParam, RowFile.tpe)),
              Some(RepoMethod.SelectAllByIds(sc.Param(sc.Ident.field(idCol.name).appended("s"), sc.Type.List(idFile.tpe)), RowFile.tpe)),
              Some(RepoMethod.SelectByFieldValues(fieldValuesParam, RowFile.tpe)),
              Some(RepoMethod.UpdateFieldValues(idParam, fieldValuesParam)),
              updateMethod,
              Some(RepoMethod.Delete(idParam))
            ).flatten
          case None => Nil
        },
        table.uniqueKeys.map { uk =>
          val params = uk.cols.map(colName => sc.Param(sc.Ident.field(colName), scalaType(pkg, table.colsByName(colName))))
          RepoMethod.SelectByUnique(params, RowFile.tpe)
        }
      ).flatten
    }.filter(_.nonEmpty)

    val RepoTraitFile: Option[sc.File] = repoMethods.map { repoMethods =>
      val Repo = sc.Ident.repo(table.name)
      val tpe = sc.Type.Qualified(pkg / Repo)
      val str =
        code"""trait $Repo {
              |  ${repoMethods.map(dbLib.sig).mkCode("\n  ")}
              |}
              |""".stripMargin

      sc.File(tpe, str)
    }

    val TripleQuote = "\"" * 3

    val RepoImplTraitFile: Option[sc.File] =
      RepoTraitFile.zip(repoMethods).map { case (repoTrait, repoMethods) =>
        val name = sc.Ident.repoImpl(table.name)
        val renderedMethods: List[sc.Code] = repoMethods.map { repoMethod =>
          val joinedColNames = table.cols.map(_.name.value).mkString(", ")
          val impl: sc.Code = repoMethod match {
            case RepoMethod.SelectAll(_) =>
              val sql = DbLib.anorm.sql(code"""select $joinedColNames from ${table.name.value}""")
              code"""$sql.as(${RowFile.tpe}.rowParser.*)"""
            case RepoMethod.SelectById(idParam, _) =>
              val sql = DbLib.anorm.sql(code"""select $joinedColNames from ${table.name.value} where ${table.idCol.get.name.value} = $$${idParam.name}""")
              code"""$sql.as(${RowFile.tpe}.rowParser.singleOpt)"""
            case RepoMethod.SelectAllByIds(idsParam, _) =>
              val sql = DbLib.anorm.sql(code"""select $joinedColNames from ${table.name.value} where ${table.idCol.get.name.value} in $$${idsParam.name}""")
              code"""$sql.as(${RowFile.tpe}.rowParser.*)"""
            case RepoMethod.SelectByUnique(_, _) => "???"
            case RepoMethod.SelectByFieldValues(param, _) =>
              code"""${param.name} match {
                  |      case Nil => selectAll
                  |      case nonEmpty =>
                  |        SQL"select * from ${table.name.value} where $${nonEmpty.map(x => s"{$${x.name}}")}"
                  |          .on(nonEmpty.map(_.toNamedParameter): _*)
                  |          .as(${RowFile.tpe}.rowParser.*)
                  |    }
                  |""".stripMargin

            case RepoMethod.UpdateFieldValues(idParam, param) =>
              code"""${param.name} match {
                  |      case Nil => 0
                  |      case nonEmpty =>
                  |        SQL${TripleQuote}update ${table.name.value} set $${nonEmpty.map(x => s"$${x.name} = {$${x.name}}").mkString(", ")} where ${table.idCol.get.name.value} = $${${idParam.name}}}$TripleQuote
                  |          .on(nonEmpty.map(_.toNamedParameter): _*)
                  |          .executeUpdate()
                  |    }
                  |""".stripMargin

            case RepoMethod.InsertDbGeneratedKey(_, _) => code"???"
            case RepoMethod.InsertProvidedKey(_, _)    => code"???"
            case RepoMethod.InsertOnlyKey(_)           => code"???"
            case RepoMethod.Delete(_)                  => code"???"
          }
          code"override ${dbLib.sig(repoMethod)} = \n    $impl"
        }

        val str =
          code"""trait $name extends ${repoTrait.tpe} {
              |  ${renderedMethods.mkCode("\n  ")}
              |}
              |""".stripMargin

        sc.File(sc.Type.Qualified(pkg / name), str)
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

  def allTables(pkg: sc.QIdent, tables: List[db.Table], jsonLib: JsonLib): List[sc.File] = {
    val enums: List[db.Type.StringEnum] =
      tables.flatMap(_.cols.map(_.tpe)).collect { case x: db.Type.StringEnum => x }.distinct

    val enumFiles: List[sc.File] =
      enums.map(stringEnumClass(pkg, _, jsonLib))
    val tableFiles: List[sc.File] =
      tables.flatMap(table => TableFiles(pkg, table, DbLib.anorm, jsonLib).all)
    val allFiles: List[sc.File] =
      enumFiles ++ tableFiles
    val knownNames = allFiles.map { f => (f.name, f.tpe.value) }.toMap
    allFiles.map(file => addPackageAndImports(pkg, knownNames, file))
  }
}
