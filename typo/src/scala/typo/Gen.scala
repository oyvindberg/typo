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

  sealed trait RepoMethod {
    def sig: sc.Code = this match {
      case RepoMethod.SelectAll(rowType) =>
        code"def selectAll(implicit c: ${sc.Type.Connection}): ${sc.Type.List(rowType)}"
      case RepoMethod.SelectById(idParam, rowType) =>
        code"def selectById($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Option(rowType)}"
      case RepoMethod.SelectAllByIds(idParam, rowType) =>
        code"def selectByIds($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.List(rowType)}"
      case RepoMethod.SelectByUnique(params, rowType) =>
        code"def selectByUnique(${params.map(_.code).mkCode(", ")})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option(rowType)}"
      case RepoMethod.SelectByFieldValues(param, rowType) =>
        code"def selectByFieldValues($param)(implicit c: ${sc.Type.Connection}): ${sc.Type.List(rowType)}"
      case RepoMethod.UpdateFieldValues(idParam, param) =>
        code"def updateFieldValues($idParam, $param)(implicit c: ${sc.Type.Connection}): ${sc.Type.Int}"
      case RepoMethod.InsertDbGeneratedKey(param, idType) =>
        code"def insert($param)(implicit c: ${sc.Type.Connection}): $idType"
      case RepoMethod.InsertProvidedKey(idParam, unsavedParam) =>
        code"def insert($idParam, $unsavedParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Unit}"
      case RepoMethod.InsertOnlyKey(idParam) =>
        code"def insert($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Unit}"
      case RepoMethod.Delete(idParam) =>
        code"def delete($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    }
  }

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

  def stringEnumClass(pkg: sc.QIdent, `enum`: db.Type.StringEnum): sc.File = {
    val EnumName = sc.Ident.`enum`(`enum`.name)
    val EnumType = sc.Type.Qualified(pkg / EnumName)

    val members = `enum`.values.map { value =>
      val name = sc.Ident.enumValue(value)
      name -> code"case object $name extends $EnumName(${sc.StrLit(value)})"
    }

    val str =
      code"""sealed abstract class $EnumName(val value: ${sc.Type.String})
            |object $EnumName {
            |  ${members.map { case (_, definition) => definition }.mkCode("\n  ")}
            |
            |  val All: ${sc.Type.List(EnumType)} = ${sc.Type.ListName}(${members.map { case (ident, _) => ident.code }.mkCode(", ")})
            |  val Names: ${sc.Type.String} = All.map(_.value).mkString(", ")
            |  val ByName: ${sc.Type.Map(sc.Type.String, EnumType)} = All.map(x => (x.value, x)).toMap
            |
            |  implicit val column: ${libs.anorm.Column(EnumType)} =
            |    implicitly[${libs.anorm.Column(sc.Type.String)}].mapResult { str =>
            |      ByName.get(str).toRight(${libs.anorm.SqlMappingError}(s"$$str was not among $$Names"))
            |    }
            |
            |  implicit val reads: ${libs.playJson.Reads(EnumType)} = (value: ${libs.playJson.JsValue}) =>
            |    value.validate[${sc.Type.String}].flatMap { str =>
            |      ByName.get(str) match {
            |        case Some(value) => ${libs.playJson.JsSuccess}(value)
            |        case None => ${libs.playJson.JsError}(s"'$$str' does not match any of the following legal values: $$Names")
            |      }
            |    }
            |  implicit val toStatement: ${libs.anorm.ToStatement(EnumType)} = implicitly[${libs.anorm.ToStatement(sc.Type.String)}].contramap(_.value)
            |  implicit val writes: ${libs.playJson.Writes(EnumType)} = enumValue => ${libs.playJson.JsString}(enumValue.value)
            |}
            |""".stripMargin

    sc.File(EnumType, str)
  }

  case class TableFiles(pkg: sc.QIdent, table: db.Table) {
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
              |  implicit val rowParser: ${libs.anorm.RowParser(rowType)} = { row =>
              |    ${libs.anorm.Success}(
              |      $name(
              |        ${fetchValues.mkCode(",\n        ")}
              |      )
              |    )
              |  }
              |  implicit val oFormat: ${libs.playJson.OFormat(rowType)} = ${libs.playJson.Json}.format
              |
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
                  |  implicit val oFormat: ${libs.playJson.OFormat(rowType)} = ${libs.playJson.Json}.format
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
        code"""sealed abstract class $fieldValueName[T: ${libs.anorm.ToStatementName}](val name: String, val value: T) {
              |  def toNamedParameter: ${libs.anorm.NamedParameter} = ${libs.anorm.NamedParameter}(name, ${libs.anorm.ParameterValue}.toParameterValue(value))
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
                |  implicit val column: ${libs.anorm.Column(tpe)} = implicitly[${libs.anorm.Column(underlying)}].map($name.apply)
                |  implicit val format: ${libs.playJson.Format(tpe)} = implicitly[${libs.playJson.Format(underlying)}].bimap($name.apply, _.value)
                |  implicit val ordering: ${sc.Type.Ordering(tpe)} = Ordering.by(_.value)
                |  implicit val reads: ${libs.playJson.Reads(tpe)} = implicitly[${libs.playJson.Reads(underlying)}].map($name.apply)
                |  implicit val toStatement: ${libs.anorm.ToStatement(tpe)} = implicitly[${libs.anorm.ToStatement(underlying)}].contramap(_.value)
                |  implicit val writes: ${libs.playJson.Writes(tpe)} = implicitly[${libs.playJson.Writes(underlying)}].contramap(_.value)
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
              |  ${repoMethods.map(_.sig).mkCode("\n  ")}
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
              val sql = libs.anorm.sql(code"""select $joinedColNames from ${table.name.value}""")
              code"""$sql.as(${RowFile.tpe}.rowParser.*)"""
            case RepoMethod.SelectById(idParam, _) =>
              val sql = libs.anorm.sql(code"""select $joinedColNames from ${table.name.value} where ${table.idCol.get.name.value} = $$${idParam.name}""")
              code"""$sql.as(${RowFile.tpe}.rowParser.singleOpt)"""
            case RepoMethod.SelectAllByIds(idsParam, _) =>
              val sql = libs.anorm.sql(code"""select $joinedColNames from ${table.name.value} where ${table.idCol.get.name.value} in $$${idsParam.name}""")
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
          code"override ${repoMethod.sig} = \n    $impl"
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

  def allTables(pkg: sc.QIdent, tables: List[db.Table]): List[sc.File] = {
    val enums: List[db.Type.StringEnum] =
      tables.flatMap(_.cols.map(_.tpe)).collect { case x: db.Type.StringEnum => x }.distinct

    val enumFiles: List[sc.File] =
      enums.map(stringEnumClass(pkg, _))
    val tableFiles: List[sc.File] =
      tables.flatMap(table => TableFiles(pkg, table).all)
    val allFiles: List[sc.File] =
      enumFiles ++ tableFiles
    val knownNames = allFiles.map { f => (f.name, f.tpe.value) }.toMap
    allFiles.map(file => addPackageAndImports(pkg, knownNames, file))
  }
}
