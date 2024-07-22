package typo

/** Simplified model of the scala language.
  *
  * The generated code is stored in the `Code` data structure. For full flexibility, some parts are stored as text and other parts in trees. Most notably *all type and term references* which need an
  * import to work should be in a tree.
  *
  * You'll mainly use this module with the `code"..."` interpolator.
  *
  * (It should rather be called `scala`, but let's avoid that name clash)
  */
object sc {
  sealed trait Tree

  case class Ident(value: String) extends Tree {
    def map(f: String => String): Ident = Ident(f(value))
    def appended(suffix: String) = new Ident(value + suffix)
    def prepended(prefix: String) = new Ident(prefix + value)
  }

  object Ident {
    implicit val ordering: Ordering[Ident] = Ordering.by(_.value)
  }

  case class QIdent(idents: List[Ident]) extends Tree {
    lazy val dotName = idents.map(_.value).mkString(".")
    def parentOpt: Option[QIdent] = if (idents.size <= 1) None else Some(QIdent(idents.dropRight(1)))
    require(idents.nonEmpty)
    def /(ident: Ident): QIdent = QIdent(idents :+ ident)
    def /(newIdents: List[Ident]): QIdent = QIdent(idents ++ newIdents)
    def name = idents.last
  }
  object QIdent {
    def apply(str: String): QIdent =
      sc.QIdent(str.split('.').toList.map(Ident.apply))
    def of(idents: Ident*): QIdent =
      QIdent(idents.toList)
  }

  case class Param(comments: Comments, name: Ident, tpe: Type, default: Option[sc.Code]) extends Tree
  object Param {
    def apply(name: Ident, tpe: Type): Param = Param(Comments.Empty, name, tpe, None)
  }

  case class StrLit(str: String) extends Tree

  case class StringInterpolate(`import`: sc.Type, prefix: sc.Ident, content: sc.Code) extends Tree

  sealed trait StaticMember extends Tree

  sealed trait ClassMember extends StaticMember {
    def name: sc.Ident
  }

  case class Summon(tpe: Type) extends Tree

  case class Given(
      tparams: List[Type.Abstract],
      name: sc.Ident,
      implicitParams: List[Param],
      tpe: sc.Type,
      body: sc.Code
  ) extends ClassMember

  case class Comments(lines: List[String])
  object Comments {
    val Empty = Comments(Nil)
  }

  sealed trait ClassType
  object ClassType {
    case object Class extends ClassType
    case object Interface extends ClassType
  }

  case class Enum(
      comments: Comments,
      tpe: sc.Type.Qualified,
      members: List[(sc.Ident, String)],
      instances: List[sc.ClassMember]
  ) extends Tree

  case class New(target: sc.Code, args: List[Arg]) extends Tree
  case class ApplyFunction1(target: sc.Code, arg: sc.Code) extends Tree
  case class ApplyNullary(target: sc.Code) extends Tree
  case class ApplyByName(target: sc.Code) extends Tree

  sealed trait Arg extends Tree
  object Arg {
    case class Pos(value: sc.Code) extends Arg
    case class Named(name: sc.Ident, value: sc.Code) extends Arg
  }

  sealed trait Adt extends Tree {
    def name: sc.Type.Qualified
  }
  object Adt {
    case class Sum(
        comments: Comments,
        name: sc.Type.Qualified,
        tparams: List[Type.Abstract],
        members: List[Method],
        implements: List[Type],
        subtypes: List[Adt],
        staticMembers: List[ClassMember]
    ) extends Adt {
      def flattenedSubtypes: List[sc.Adt] = {
        val b = List.newBuilder[sc.Adt]
        def go(subtype: sc.Adt): Unit = subtype match {
          case x: sc.Adt.Sum =>
            b += x.copy(subtypes = Nil)
            x.subtypes.foreach(go)
          case x: sc.Adt.Record =>
            b += x
        }
        subtypes.foreach(go)
        b.result()
      }
    }
    case class Record(
        isWrapper: Boolean,
        comments: Comments,
        name: sc.Type.Qualified,
        tparams: List[Type.Abstract],
        params: List[Param],
        implicitParams: List[Param],
        `extends`: Option[sc.Type],
        implements: List[Type],
        members: List[ClassMember],
        staticMembers: List[ClassMember]
    ) extends Adt
  }
  case class Class(
      comments: Comments,
      classType: ClassType,
      name: sc.Type.Qualified,
      tparams: List[Type.Abstract],
      params: List[Param],
      implicitParams: List[Param],
      `extends`: Option[sc.Type],
      implements: List[Type],
      members: List[ClassMember],
      staticMembers: List[ClassMember]
  ) extends StaticMember

  case class Value(
      name: sc.Ident,
      tpe: sc.Type,
      body: Option[sc.Code]
  ) extends ClassMember

  case class Method(
      comments: Comments,
      tparams: List[Type.Abstract],
      name: sc.Ident,
      params: List[Param],
      implicitParams: List[Param],
      tpe: sc.Type,
      body: Option[sc.Code]
  ) extends ClassMember

  sealed trait Type extends Tree {
    def of(targs: Type*): Type = Type.TApply(this, targs.toList)
    def withComment(str: String): Type = Type.Commented(this, s"/* $str */")
  }

  object Type {
    case object Wildcard extends Type
    case class TApply(underlying: Type, targs: List[Type]) extends Type
    case class Qualified(value: QIdent) extends Type {
      def /(ident: Ident): Qualified = Qualified(value / ident)
      lazy val dotName = value.dotName
      def name = value.name
    }
    case class Abstract(value: Ident) extends Type
    case class Commented(underlying: Type, comment: String) extends Type
    case class UserDefined(underlying: Type) extends Type
    case class ByName(underlying: Type) extends Type
    case class ArrayOf(underlying: Type) extends Type

    object Qualified {
      implicit val ordering: Ordering[Qualified] = scala.Ordering.by(x => x.dotName)

      def apply(str: String): Qualified =
        Qualified(QIdent(str))
      def apply(value: Ident): Qualified =
        Qualified(QIdent(scala.List(value)))
    }

    object dsl {
      val Bijection = Qualified("typo.dsl.Bijection")
      val CompositeIn = Qualified("typo.dsl.SqlExpr.CompositeIn")
      val CompositeTuplePart = Qualified("typo.dsl.SqlExpr.CompositeIn.TuplePart")
      val ConstAs = Qualified("typo.dsl.SqlExpr.Const.As")
      val ConstAsAs = ConstAs / sc.Ident("as")
      val ConstAsAsOpt = ConstAs / sc.Ident("asOpt")
      val DeleteBuilder = Qualified("typo.dsl.DeleteBuilder")
      val DeleteBuilderMock = Qualified("typo.dsl.DeleteBuilder.DeleteBuilderMock")
      val DeleteParams = Qualified("typo.dsl.DeleteParams")
      val Field = Qualified("typo.dsl.SqlExpr.Field")
      val FieldLikeNoHkt = Qualified("typo.dsl.SqlExpr.FieldLike")
      val ForeignKey = Qualified("typo.dsl.ForeignKey")
      val IdField = Qualified("typo.dsl.SqlExpr.IdField")
      val OptField = Qualified("typo.dsl.SqlExpr.OptField")
      val Path = Qualified("typo.dsl.Path")
      val SelectBuilder = Qualified("typo.dsl.SelectBuilder")
      val SelectBuilderMock = Qualified("typo.dsl.SelectBuilderMock")
      val SelectBuilderSql = Qualified("typo.dsl.SelectBuilderSql")
      val SelectParams = Qualified("typo.dsl.SelectParams")
      val SqlExpr = Qualified("typo.dsl.SqlExpr")
      val StructureRelation = Qualified("typo.dsl.Structure.Relation")
      val UpdatedValue = Qualified("typo.dsl.UpdatedValue")
      val UpdateBuilder = Qualified("typo.dsl.UpdateBuilder")
      val UpdateBuilderMock = Qualified("typo.dsl.UpdateBuilder.UpdateBuilderMock")
      val UpdateParams = Qualified("typo.dsl.UpdateParams")
    }

    def containsUserDefined(tpe: sc.Type): Boolean = tpe match {
      case ArrayOf(targ)             => containsUserDefined(targ)
      case Wildcard                  => false
      case TApply(underlying, targs) => containsUserDefined(underlying) || targs.exists(containsUserDefined)
      case Qualified(_)              => false
      case Abstract(_)               => false
      case Commented(underlying, _)  => containsUserDefined(underlying)
      case ByName(underlying)        => containsUserDefined(underlying)
      case UserDefined(_)            => true
    }

    def base(tpe: sc.Type): sc.Type = tpe match {
      case Wildcard                  => tpe
      case TApply(underlying, targs) => TApply(base(underlying), targs.map(base))
      case ArrayOf(targ)             => sc.Type.ArrayOf(base(targ))
      case Qualified(_)              => tpe
      case Abstract(_)               => tpe
      case Commented(underlying, _)  => base(underlying)
      case ByName(underlying)        => base(underlying)
      case UserDefined(tpe)          => base(tpe)
    }
  }

  case class File(tpe: Type.Qualified, contents: Code, secondaryTypes: List[Type.Qualified], scope: Scope) {
    val name: Ident = tpe.value.name
    val pkg = QIdent(tpe.value.idents.dropRight(1))
  }

  /** Semi-structured generated code. We keep all `Tree`s as long as possible so we can write imports based on what is used
    */
  sealed trait Code {
//    override def toString: String = sys.error("stringifying code too early loses structure")

    def stripMargin: Code =
      this match {
        case Code.Combined(codes) => Code.Combined(codes.map(_.stripMargin))
        case Code.Str(value)      => Code.Str(value.stripMargin)
        case tree @ Code.Tree(_)  => tree
        case Code.Interpolated(parts, args) =>
          Code.Interpolated(parts.map(_.stripMargin), args.map(_.stripMargin))
      }

    // render tree as a string in such a way that newlines inside interpolated strings preserves outer indentation
    def render(language: Lang): Lines =
      this match {
        case Code.Interpolated(parts, args) =>
          val lines = Array.newBuilder[String]
          val currentLine = new StringBuilder()

          def consume(str: Lines, indent: Int): Unit =
            // @unchecked because scala 2 and 3 disagree on exhaustivity
            (str.lines: @unchecked) match {
              case Array() => ()
              case Array(one) if one.endsWith("\n") =>
                currentLine.append(one)
                lines += currentLine.result()
                currentLine.clear()
              case Array(one) =>
                currentLine.append(one)
                ()
              case Array(first, rest*) =>
                currentLine.append(first)
                lines += currentLine.result()
                currentLine.clear()
                val indentedRest = rest.map(str => (" " * indent) + str)
                if (indentedRest.lastOption.exists(_.endsWith("\n"))) {
                  lines ++= indentedRest
                } else {
                  lines ++= indentedRest.init
                  currentLine.append(indentedRest.last)
                  ()
                }
            }

          // do the string interpolation
          parts.iterator.zipWithIndex.foreach { case (str, n) =>
            if (n > 0) {
              val rendered = args(n - 1).render(language)
              // consider the current indentation level when interpolating in multiline strings
              consume(rendered, indent = currentLine.length)
            }
            val escaped = StringContext.processEscapes(str)
            consume(Lines(escaped), indent = 0)
          }
          // commit last line
          lines += currentLine.result()
          // recombine lines back into one string
          Lines(lines.result())
        case Code.Combined(codes) => codes.iterator.map(_.render(language)).reduceOption(_ ++ _).getOrElse(Lines.Empty)
        case Code.Str(str)        => Lines(str)
        case Code.Tree(tree)      => language.renderTree(tree).render(language)
      }

    def mapTrees(f: Tree => Tree): Code =
      this match {
        case Code.Interpolated(parts, args) => Code.Interpolated(parts, args.map(_.mapTrees(f)))
        case Code.Combined(codes)           => Code.Combined(codes.map(_.mapTrees(f)))
        case str @ Code.Str(_)              => str
        case Code.Tree(tree)                => Code.Tree(f(tree))
      }

    def ++(other: Code): Code = Code.Combined(List(this, other))
  }

  case class Lines(lines: Array[String]) {
    def ++(other: Lines): Lines =
      if (this == Lines.Empty) other
      else if (other == Lines.Empty) this
      else if (lines.last.endsWith("\n")) new Lines(lines ++ other.lines)
      else {
        val newArray = lines.init.iterator ++ Iterator(lines.last + other.lines.head) ++ other.lines.iterator.drop(1)
        new Lines(newArray.toArray)
      }

    def asString: String = lines.mkString
    override def toString: String = asString
  }

  object Lines {
    val Empty = new Lines(Array())
    def apply(str: String): Lines = if (str.isEmpty) Empty else new Lines(str.linesWithSeparators.toArray)
  }

  object Code {
    val Empty: Code = Str("")
    case class Interpolated(parts: Seq[String], args: Seq[Code]) extends Code
    case class Combined(codes: List[Code]) extends Code
    case class Str(value: String) extends Code
    case class Tree(value: sc.Tree) extends Code
  }

  // `s"..." interpolator
  def s(content: sc.Code) =
    sc.StringInterpolate(TypesScala.StringContext, sc.Ident("s"), content)
}
