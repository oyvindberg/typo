package typo

import typo.Show.*
import typo.db.*

import java.nio.file.{Files, Path}

object Gen {

  case class ScalaIdent(value: String) extends AnyVal {
    def appended(suffix: String) = new ScalaIdent(value + suffix)
  }

  object ScalaIdent {
    implicit val shows: Show[ScalaIdent] = _.value
    private def camelCase(name: ColName): ScalaIdent =
      ScalaIdent(
        name.value
          .split('_')
          .zipWithIndex
          .map {
            case (s, 0) => s
            case (s, _) => s.capitalize
          }
          .mkString("")
      )
    def titleCase(name: String): ScalaIdent =
      ScalaIdent(name.split('_').map(_.capitalize).mkString(""))
    def field(name: ColName): ScalaIdent =
      camelCase(name)
    def id(name: TableName): ScalaIdent =
      titleCase(name.value).appended("Id")
    def repo(name: TableName): ScalaIdent =
      titleCase(name.value).appended("Repo")
    def repoImpl(name: TableName): ScalaIdent =
      titleCase(name.value).appended("RepoImpl")
    def repoMock(name: TableName): ScalaIdent =
      titleCase(name.value).appended("RepoMock")
    def row(name: TableName): ScalaIdent =
      titleCase(name.value).appended("Row")
    def rowUnsaved(name: TableName): ScalaIdent =
      titleCase(name.value).appended("RowUnsaved")
    def fieldValue(name: TableName): ScalaIdent =
      titleCase(name.value).appended("FieldValue")
    def `enum`(name: EnumName): ScalaIdent =
      titleCase(name.value).appended("Enum")
    def enumValue(name: String): ScalaIdent = ScalaIdent(name)
  }

  case class ScalaQIdent(value: List[ScalaIdent]) extends AnyVal {
    def /(ident: ScalaIdent): ScalaQIdent = ScalaQIdent(value :+ ident)
  }

  object ScalaQIdent {
    implicit val shows: Show[ScalaQIdent] = _.value.map(_.show).mkString(".")
  }

  case class ScalaParam(name: ScalaIdent, tpe: ScalaType)
  object ScalaParam {
    implicit val shows: Show[ScalaParam] = { case ScalaParam(name, tpe) =>
      show"$name: $tpe"
    }
  }

  case class StringLiteral(str: String)
  object StringLiteral {
    implicit val shows: Show[StringLiteral] = { case StringLiteral(str) =>
      '"' + str + '"'
    }
  }
  sealed trait ScalaType

  object ScalaType {
    case object Wildcard extends ScalaType
    case object Int extends ScalaType
    case object Long extends ScalaType
    case object String extends ScalaType
    case object Boolean extends ScalaType
    case class Option(underlying: ScalaType) extends ScalaType
    case class List(underlying: ScalaType) extends ScalaType
    case class TApply(underlying: ScalaType, targs: scala.List[ScalaType]) extends ScalaType
    case class Qualified(value: ScalaQIdent) extends ScalaType

    object Qualified {
      def apply(value: ScalaIdent): Qualified =
        Qualified(ScalaQIdent(scala.List(value)))
    }

    implicit val shows: Show[ScalaType] = {
      case Wildcard                  => "_"
      case Int                       => "Int"
      case Long                      => "Long"
      case String                    => "String"
      case Boolean                   => "Boolean"
      case Option(underlying)        => show"Option[$underlying]"
      case List(underlying)          => show"List[$underlying]"
      case Qualified(qname)          => qname.show
      case TApply(underlying, targs) => show"$underlying[${targs.map(shows.show).mkString(", ")}]"
    }
  }

  def scalaType(pkg: ScalaQIdent, col: Col): ScalaType = {
    val baseTpe = col.tpe match {
      case DbType.BigInt              => ScalaType.Long
      case DbType.VarChar(_)          => ScalaType.String
      case DbType.Boolean             => ScalaType.Boolean
      case DbType.StringEnum(name, _) => ScalaType.Qualified(pkg / ScalaIdent.`enum`(name))
    }
    if (col.isNotNull) baseTpe else ScalaType.Option(baseTpe)
  }

  def scalaFields(pkg: ScalaQIdent, table: Table): Seq[(ScalaIdent, ScalaType, Col)] = {
    table.cols.map {
      case col @ Col(colName, _, isNotNull, _) if table.primaryKey.exists(_.colName == colName) =>
        if (!isNotNull) {
          sys.error(s"assumption: id column in ${table.name.value} should be not null")
        }
        (ScalaIdent.field(colName), ScalaType.Qualified(pkg / ScalaIdent.id(table.name)), col)
      case col =>
        val finalType: ScalaType = scalaType(pkg, col)
        (ScalaIdent.field(col.name), finalType, col)
    }
  }
  sealed trait Repo
  object Repo {
    case class Direct(repoMethods: List[RepoMethod], table: Table) extends Repo
    case class Cached(repoMethods: List[RepoMethod], table: Table) extends Repo
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
    case class SelectAll(rowType: ScalaType) extends RepoMethod
    case class SelectById(idParam: ScalaParam, rowType: ScalaType) extends RepoMethod
    case class SelectAllByIds(idsParam: ScalaParam, rowType: ScalaType) extends RepoMethod
    case class SelectByUnique(params: List[ScalaParam], rowType: ScalaType) extends RepoMethod
    case class SelectByFieldValues(param: ScalaParam, rowType: ScalaType) extends RepoMethod
    case class UpdateFieldValues(idParam: ScalaParam, param: ScalaParam) extends RepoMethod
    case class InsertDbGeneratedKey(unsavedParam: ScalaParam, idType: ScalaType) extends RepoMethod
    case class InsertProvidedKey(idParam: ScalaParam, unsavedParam: ScalaParam) extends RepoMethod
    case class InsertOnlyKey(idParam: ScalaParam) extends RepoMethod
    case class Delete(idParam: ScalaParam) extends RepoMethod
  }

  case class File(name: ScalaIdent, contents: String)

  def stringEnumClass(pkg: ScalaQIdent, `enum`: DbType.StringEnum): File = {
    val EnumName = ScalaIdent.`enum`(`enum`.name)

    val members = `enum`.values.map { value =>
      val name = ScalaIdent.enumValue(value)
      name -> show"case object $name extends $EnumName(${StringLiteral(value)})"
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

  case class TableFiles(pkg: ScalaQIdent, table: Table) {
    val caseClass: File = {
      val name = ScalaIdent.row(table.name)
      val fields = scalaFields(pkg, table)
      val fetchValues = fields.map { case (name, tpe, col) => show"$name = row[$tpe](${StringLiteral(col.name.value)})" }
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
      val Unsaved = ScalaIdent.rowUnsaved(table.name)
      scalaFields(pkg, table).filterNot { case (_, _, col) => table.primaryKey.exists(_.colName == col.name) } match {
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
      val FieldValue = ScalaIdent.fieldValue(table.name)

      val members = scalaFields(pkg, table).map { case (name, tpe, col) =>
        name -> show"case class $name(override val value: $tpe) extends $FieldValue(${StringLiteral(col.name.value)}, value)"
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
        val Id = ScalaIdent.id(table.name)
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
      val rowType = ScalaType.Qualified(caseClass.name)

      List(
        table.idCol.zip(idFile) match {
          case Some((idCol, idFile)) =>
            val idType = ScalaType.Qualified(ScalaQIdent(List(idFile.name)))
            val idParam = ScalaParam(ScalaIdent.field(idCol.name), idType)

            val updateMethod = caseClassUnsaved.map { unsavedRowFile =>
              val unsavedRow = ScalaType.Qualified(unsavedRowFile.name)
              val unsavedParam = ScalaParam(ScalaIdent("unsaved"), unsavedRow)

              if (idCol.hasDefault) RepoMethod.InsertDbGeneratedKey(unsavedParam, idType)
              else RepoMethod.InsertProvidedKey(idParam, unsavedParam)

            }

            val fieldValuesParam = ScalaParam(
              ScalaIdent("fieldValues"),
              ScalaType.List(ScalaType.TApply(ScalaType.Qualified(pkg / ScalaIdent.fieldValue(table.name)), List(ScalaType.Wildcard)))
            )

            List(
              Some(RepoMethod.SelectAll(rowType)),
              Some(RepoMethod.SelectById(idParam, rowType)),
              Some(RepoMethod.SelectAllByIds(ScalaParam(ScalaIdent.field(idCol.name).appended("s"), ScalaType.List(idType)), rowType)),
              Some(RepoMethod.SelectByFieldValues(fieldValuesParam, rowType)),
              Some(RepoMethod.UpdateFieldValues(idParam, fieldValuesParam)),
              updateMethod,
              Some(RepoMethod.Delete(idParam))
            ).flatten
          case None => Nil
        },
        table.uniqueKeys.map { uk =>
          val params = uk.cols.map(colName => ScalaParam(ScalaIdent.field(colName), scalaType(pkg, table.colsByName(colName))))
          RepoMethod.SelectByUnique(params, rowType)
        }
      ).flatten
    }.filter(_.nonEmpty)

    val repoTrait: Option[File] = repoMethods.map { repoMethods =>
      val Repo = ScalaIdent.repo(table.name)

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
      val Repo = ScalaIdent.repoImpl(table.name)

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

  def all(pkg: ScalaQIdent, tables: List[Table]): List[File] = {
    val enums: List[DbType.StringEnum] =
      tables.flatMap(_.cols.map(_.tpe)).collect { case x: DbType.StringEnum => x }.distinct

    val enumFiles = enums.map(stringEnumClass(pkg, _))
    val tableFiles: List[File] = tables.flatMap(table => TableFiles(pkg, table).all)
    enumFiles ++ tableFiles
  }
}
