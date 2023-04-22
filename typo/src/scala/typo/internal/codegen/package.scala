package typo
package internal

package object codegen {
  implicit class ToCodeOps[T](private val t: T) extends AnyVal {
    def code(implicit toCode: ToCode[T]): sc.Code = toCode.toCode(t)
  }

  implicit final class CodeInterpolator(private val stringContext: StringContext) extends AnyVal {
    def code(args: sc.Code*): sc.Code = {
      sc.Code.Interpolated(stringContext.parts, args)
    }
  }

  // magnet pattern
  implicit def toCode[T: ToCode](x: T): sc.Code = ToCode[T].toCode(x)

  implicit class CodeOps[C <: sc.Code](private val codes: List[C]) extends AnyVal {
    def mkCode(sep: sc.Code): sc.Code = {
      val interspersed = codes.zipWithIndex.map {
        case (c, 0) => c
        case (c, _) => sc.Code.Combined(List(sep, c))
      }
      sc.Code.Combined(interspersed)
    }
  }

  implicit class CodeOpsNel[C <: sc.Code](private val codes: NonEmptyList[C]) extends AnyVal {
    def mkCode(sep: sc.Code): sc.Code = {
      val interspersed = codes.toList.zipWithIndex.map {
        case (c, 0) => c
        case (c, _) => sc.Code.Combined(List(sep, c))
      }
      sc.Code.Combined(interspersed)
    }
  }

  implicit val tableName: ToCode[db.RelationName] = {
    case db.RelationName(Some(schema), name) => code"${maybeQuotedDb(schema)}.${maybeQuotedDb(name)}"
    case db.RelationName(None, name)         => maybeQuotedDb(name)
  }

  def maybeQuoted(colName: db.ColName): sc.Code =
    maybeQuotedDb(colName.value)

  private def maybeQuotedDb(str: String): sc.Code =
    if (pgKeyword(str) || str.exists(x => !x.isUnicodeIdentifierPart))
      sc.StrLit(str)
    else sc.Code.Str(str)

  def scaladoc(title: String)(lines: List[String]): sc.Code = {
    lines match {
      case Nil =>
        code"""|/** $title */""".stripMargin
      case nonEmpty =>
        code"""|/** $title
               |${nonEmpty.flatMap(_.linesIterator).map(line => code"  * $line").mkCode("\n")}
               |  */""".stripMargin
    }
  }

  def obj(name: sc.Ident, members: List[sc.Code]): sc.Code =
    members match {
      case Nil => sc.Code.Empty
      case nonEmpty =>
        code"""|object $name {
               |  ${nonEmpty.mkCode("\n")}
               |}""".stripMargin
    }
}
