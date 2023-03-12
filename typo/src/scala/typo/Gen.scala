package typo

import typo.Show.*

import java.nio.file.{Files, Path}

object Gen {
  def scalaType(pkg: s.QIdent, col: db.Col): s.Type = {
    val baseTpe = col.tpe match {
      case db.Type.BigInt              => s.Type.Long
      case db.Type.VarChar(_)          => s.Type.String
      case db.Type.Boolean             => s.Type.Boolean
      case db.Type.StringEnum(name, _) => s.Type.Qualified(pkg / s.Ident.`enum`(name))
    }
    if (col.isNotNull) baseTpe else s.Type.Option(baseTpe)
  }

  sealed trait Repo
  object Repo {
    case class Direct(repoMethods: List[RepoMethod], table: db.Table) extends Repo
    case class Cached(repoMethods: List[RepoMethod], table: db.Table) extends Repo
  }

  sealed trait RepoMethod {
    def sig: String = this match {
      case RepoMethod.SelectAll(rowType) =>
        show"def selectAll(implicit c: Connection): List[$rowType]"
      case RepoMethod.SelectById(idParam, rowType) =>
        show"def selectById($idParam)(implicit c: Connection): Option[$rowType]"
      case RepoMethod.SelectAllByIds(idParam, rowType) =>
        show"def selectByIds($idParam)(implicit c: Connection): List[$rowType]"
      case RepoMethod.SelectByUnique(params, rowType) =>
        show"def selectByUnique(${params.map(_.show).mkString(", ")})(implicit c: Connection): Option[$rowType]"
      case RepoMethod.SelectByFieldValues(param, rowType) =>
        show"def selectByFieldValues($param)(implicit c: Connection): List[$rowType]"
      case RepoMethod.UpdateFieldValues(idParam, param) =>
        show"def updateFieldValues($idParam, $param)(implicit c: Connection): Int"
      case RepoMethod.InsertDbGeneratedKey(param, idType) =>
        show"def insert(${param.show})(implicit c: Connection): $idType"
      case RepoMethod.InsertProvidedKey(idParam, unsavedParam) =>
        show"def insert($idParam, $unsavedParam)(implicit c: Connection): Unit"
      case RepoMethod.InsertOnlyKey(idParam) =>
        show"def insert($idParam)(implicit c: Connection): Unit"
      case RepoMethod.Delete(idParam) =>
        show"def delete($idParam)(implicit c: Connection): Boolean"
    }
  }

  object RepoMethod {
    case class SelectAll(rowType: s.Type) extends RepoMethod
    case class SelectById(idParam: s.Param, rowType: s.Type) extends RepoMethod
    case class SelectAllByIds(idsParam: s.Param, rowType: s.Type) extends RepoMethod
    case class SelectByUnique(params: List[s.Param], rowType: s.Type) extends RepoMethod
    case class SelectByFieldValues(param: s.Param, rowType: s.Type) extends RepoMethod
    case class UpdateFieldValues(idParam: s.Param, param: s.Param) extends RepoMethod
    case class InsertDbGeneratedKey(unsavedParam: s.Param, idType: s.Type) extends RepoMethod
    case class InsertProvidedKey(idParam: s.Param, unsavedParam: s.Param) extends RepoMethod
    case class InsertOnlyKey(idParam: s.Param) extends RepoMethod
    case class Delete(idParam: s.Param) extends RepoMethod
  }

  case class File(name: s.Ident, contents: String)

  def stringEnumClass(pkg: s.QIdent, `enum`: db.Type.StringEnum): File = {
    val EnumName = s.Ident.`enum`(`enum`.name)

    val members = `enum`.values.map { value =>
      val name = s.Ident.enumValue(value)
      name -> show"case object $name extends $EnumName(${s.StrLit(value)})"
    }
    val str =
      show"""package $pkg
            |
            |import anorm.{Column, SqlMappingError, ToStatement}
            |import play.api.libs.json._
            |
            |sealed abstract class $EnumName(val value: String)
            |object $EnumName {
            |  ${members.map { case (_, definition) => definition }.mkString("\n  ")}
            |
            |  val All: List[$EnumName] = ${members.map { case (ident, _) => ident.show }.mkString("List(", ", ", ")")}
            |  val Names: String = All.map(_.value).mkString(", ")
            |  val ByName: Map[String, $EnumName] = All.map(x => (x.value, x)).toMap
            |
            |  implicit val column: Column[$EnumName] =
            |    implicitly[Column[String]].mapResult { str =>
            |      ByName.get(str).toRight(SqlMappingError(s"$$str was not among $$Names"))
            |    }
            |
            |  implicit val reads: Reads[$EnumName] = (value: JsValue) =>
            |    value.validate[String].flatMap { str =>
            |      ByName.get(str) match {
            |        case Some(value) => JsSuccess(value)
            |        case None => JsError(s"'$$str' does not match any of the following legal values: $$Names")
            |      }
            |    }
            |  implicit val toStatement: ToStatement[$EnumName] = implicitly[ToStatement[String]].contramap(_.value)
            |  implicit val writes: Writes[$EnumName] = enumValue => JsString(enumValue.value)
            |}
            |""".stripMargin

    File(EnumName, str)
  }

  case class TableFiles(pkg: s.QIdent, table: db.Table) {
    val scalaFields: Seq[(s.Ident, s.Type, db.Col)] = {
      table.cols.map {
        case col @ db.Col(colName, _, isNotNull, _) if table.primaryKey.exists(_.colName == colName) =>
          if (!isNotNull) {
            sys.error(s"assumption: id column in ${table.name.value} should be not null")
          }
          (s.Ident.field(colName), s.Type.Qualified(pkg / s.Ident.id(table.name)), col)
        case col =>
          val finalType: s.Type = scalaType(pkg, col)
          (s.Ident.field(col.name), finalType, col)
      }
    }

    val caseClass: File = {
      val name = s.Ident.row(table.name)
      val fields = scalaFields
      val fetchValues = fields.map { case (name, tpe, col) => show"$name = row[$tpe](${s.StrLit(col.name.value)})" }
      val str =
        show"""package $pkg
              |
              |import anorm.{RowParser, Success}
              |import play.api.libs.json.{Json, OFormat}
              |
              |case class $name(
              |  ${fields.map { case (name, tpe, _) => show"$name: $tpe" }.mkString(",\n  ")}
              |)
              |object $name {
              |  implicit val rowParser: RowParser[$name] = { row =>
              |    Success(
              |      $name(
              |        ${fetchValues.mkString(",\n        ")}
              |      )
              |    )
              |  }
              |  implicit val oFormat: OFormat[$name] = Json.format
              |
              |}
              |""".stripMargin

      File(name, str)
    }

    val caseClassUnsaved: Option[File] = {
      val Unsaved = s.Ident.rowUnsaved(table.name)
      scalaFields.filterNot { case (_, _, col) => table.primaryKey.exists(_.colName == col.name) } match {
        case Nil => None
        case fields =>
          val str =
            show"""package $pkg
                  |
                  |import anorm.{RowParser, Success}
                  |import play.api.libs.json.{Json, OFormat}
                  |
                  |case class $Unsaved(
                  |  ${fields.map { case (name, tpe, _) => show"$name: $tpe" }.mkString(",\n  ")}
                  |)
                  |object $Unsaved {
                  |  implicit val oFormat: OFormat[$Unsaved] = Json.format
                  |}
                  |""".stripMargin
          Some(File(Unsaved, str))
      }
    }

    val fieldValue: File = {
      val FieldValue = s.Ident.fieldValue(table.name)

      val members = scalaFields.map { case (name, tpe, col) =>
        name -> show"case class $name(override val value: $tpe) extends $FieldValue(${s.StrLit(col.name.value)}, value)"
      }
      val str =
        show"""package $pkg
              |
              |import anorm.{NamedParameter, ParameterValue, ToStatement }
              |
              |sealed abstract class $FieldValue[T: ToStatement](val name: String, val value: T) {
              |  def toNamedParameter: NamedParameter = NamedParameter(name, ParameterValue.toParameterValue(value))
              |}
              |
              |object $FieldValue {
              |  ${members.map { case (_, definition) => definition }.mkString("\n  ")}
              |}
              |""".stripMargin

      File(FieldValue, str)
    }

    val idFile: Option[File] = {
      table.idCol.map { idCol =>
        val Id = s.Ident.id(table.name)
        val underlying = scalaType(pkg, idCol)
        val str =
          show"""package $pkg
                |
                |import anorm.{Column, ToStatement}
                |import play.api.libs.json.{Format, Reads, Writes}
                |
                |case class $Id(value: $underlying) extends AnyVal
                |object $Id {
                |  implicit val column: Column[$Id] = implicitly[Column[$underlying]].map($Id.apply)
                |  implicit val format: Format[$Id] = implicitly[Format[$underlying]].bimap($Id.apply, _.value)
                |  implicit val ordering: Ordering[$Id] = Ordering.by(_.value)
                |  implicit val reads: Reads[$Id] = implicitly[Reads[$underlying]].map($Id.apply)
                |  implicit val toStatement: ToStatement[$Id] = implicitly[ToStatement[$underlying]].contramap(_.value)
                |  implicit val writes: Writes[$Id] = implicitly[Writes[$underlying]].contramap(_.value)
                |}
                |""".stripMargin

        File(Id, str)
      }
    }

    val repoMethods: Option[List[RepoMethod]] = Some {
      val rowType = s.Type.Qualified(caseClass.name)

      List(
        table.idCol.zip(idFile) match {
          case Some((idCol, idFile)) =>
            val idType = s.Type.Qualified(s.QIdent(List(idFile.name)))
            val idParam = s.Param(s.Ident.field(idCol.name), idType)

            val updateMethod = caseClassUnsaved.map { unsavedRowFile =>
              val unsavedRow = s.Type.Qualified(unsavedRowFile.name)
              val unsavedParam = s.Param(s.Ident("unsaved"), unsavedRow)

              if (idCol.hasDefault) RepoMethod.InsertDbGeneratedKey(unsavedParam, idType)
              else RepoMethod.InsertProvidedKey(idParam, unsavedParam)

            }

            val fieldValuesParam = s.Param(
              s.Ident("fieldValues"),
              s.Type.List(s.Type.TApply(s.Type.Qualified(pkg / s.Ident.fieldValue(table.name)), List(s.Type.Wildcard)))
            )

            List(
              Some(RepoMethod.SelectAll(rowType)),
              Some(RepoMethod.SelectById(idParam, rowType)),
              Some(RepoMethod.SelectAllByIds(s.Param(s.Ident.field(idCol.name).appended("s"), s.Type.List(idType)), rowType)),
              Some(RepoMethod.SelectByFieldValues(fieldValuesParam, rowType)),
              Some(RepoMethod.UpdateFieldValues(idParam, fieldValuesParam)),
              updateMethod,
              Some(RepoMethod.Delete(idParam))
            ).flatten
          case None => Nil
        },
        table.uniqueKeys.map { uk =>
          val params = uk.cols.map(colName => s.Param(s.Ident.field(colName), scalaType(pkg, table.colsByName(colName))))
          RepoMethod.SelectByUnique(params, rowType)
        }
      ).flatten
    }.filter(_.nonEmpty)

    val repoTrait: Option[File] = repoMethods.map { repoMethods =>
      val Repo = s.Ident.repo(table.name)

      val str =
        show"""package $pkg
              |
              |import java.sql.Connection
              |
              |trait $Repo {
              |  ${repoMethods.map(_.sig).mkString("\n  ")}
              |}
              |""".stripMargin

      File(Repo, str)
    }

    val repoImplTrait: Option[File] = repoTrait.zip(repoMethods).map { case (repoTrait, repoMethods) =>
      val Repo = s.Ident.repoImpl(table.name)

      val tripleQuote = "\"" * 3
      val renderedMethods = repoMethods.map { repoMethod =>
        val impl = repoMethod match {
          case RepoMethod.SelectAll(_) =>
            show"""SQL${tripleQuote}select ${table.cols
                .map(_.name.value)
                .mkString(", ")} from ${table.name.value}${tripleQuote}.as(${caseClass.name}.rowParser.*)"""
          case RepoMethod.SelectById(idParam, _) =>
            show"""SQL${tripleQuote}select ${table.cols
                .map(_.name.value)
                .mkString(
                  ", "
                )} from ${table.name.value} where ${table.idCol.get.name.value} = $$${idParam.name}${tripleQuote}.as(${caseClass.name}.rowParser.singleOpt)"""
          case RepoMethod.SelectAllByIds(idsParam, _) =>
            show"""SQL${tripleQuote}select ${table.cols
                .map(_.name.value)
                .mkString(
                  ", "
                )} from ${table.name.value} where ${table.idCol.get.name.value} in $$${idsParam.name}${tripleQuote}.as(${caseClass.name}.rowParser.*)"""
          case RepoMethod.SelectByUnique(_, _) => "???"
          case RepoMethod.SelectByFieldValues(param, rowType) =>
            show"""${param.name} match {
                  |      case Nil => selectAll
                  |      case nonEmpty =>
                  |        SQL"select * from ${table.name.value} where $${nonEmpty.map(x => s"{$${x.name}")}"
                  |          .on(nonEmpty.map(_.toNamedParameter): _*)
                  |          .as($rowType.rowParser.*)
                  |    }
                  |""".stripMargin

          case RepoMethod.UpdateFieldValues(idParam, param) =>
            show"""${param.name} match {
                  |      case Nil => 0
                  |      case nonEmpty =>
                  |        SQL${tripleQuote}update ${table.name.value} set $${nonEmpty.map(x => s"$${x.name} = {$${x.name}").mkString(", ")} where ${table.idCol.get.name.value} = $${${idParam.name}}}${tripleQuote}
                  |          .on(nonEmpty.map(_.toNamedParameter): _*)
                  |          .executeUpdate()
                  |    }
                  |""".stripMargin

          case RepoMethod.InsertDbGeneratedKey(_, _) => "???"
          case RepoMethod.InsertProvidedKey(_, _)    => "???"
          case RepoMethod.InsertOnlyKey(_)           => "???"
          case RepoMethod.Delete(_)                  => "???"
        }
        show"override ${repoMethod.sig} = \n    $impl"
      }

      val str =
        show"""package $pkg
              |
              |import anorm._
              |import java.sql.Connection
              |
              |trait $Repo extends ${repoTrait.name} {
              |  ${renderedMethods.mkString("\n  ")}
              |}
              |""".stripMargin

      File(Repo, str)
    }

    //  def repoMockTrait(pkg: ScalaQIdent, table: Table): (ScalaIdent, String) = {
    //    val Repo = ScalaIdent.repo(table.name)
    //    val MockRepo = ScalaIdent.repoMock(table.name)
    //    val idType = scalaType(pkg, table.idCol)
    //    val str =
    //      show"""package $pkg
    //         |import java.sql.Connection
    //         |
    //         |class $MockRepo(initial: Map[$idType, ]) extends Repo {
    //         |  val map
    //         |  def selectById(id: $idType)(implicit c: Connection): Option[${ScalaIdent.row(table.name)}]
    //         |  def selectByIds(id: List[$idType])(implicit c: Connection): List[Option[${ScalaIdent.row(table.name)}]]
    //         |  def insert(value: ${ScalaIdent.rowUnsaved(table.name)})(implicit c: Connection): ${ScalaIdent.id(table.name)}
    //         |}
    //         |""".stripMargin
    //
    //    Repo -> str
    //  }
    val all: List[File] = List(
      Some(caseClass),
      caseClassUnsaved,
      repoTrait,
      repoImplTrait,
      Some(fieldValue),
      idFile
    ).flatten
  }

  def all(pkg: s.QIdent, tables: List[db.Table]): List[File] = {
    val enums: List[db.Type.StringEnum] =
      tables.flatMap(_.cols.map(_.tpe)).collect { case x: db.Type.StringEnum => x }.distinct

    val enumFiles = enums.map(stringEnumClass(pkg, _))
    val tableFiles: List[File] = tables.flatMap(table => TableFiles(pkg, table).all)
    enumFiles ++ tableFiles
  }
}
