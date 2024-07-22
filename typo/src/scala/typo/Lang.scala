package typo

trait Lang {
  val BigDecimal: sc.Type
  val Boolean: sc.Type
  val Byte: sc.Type
  val Double: sc.Type
  val Float: sc.Type
  val Function1: sc.Type
  val Int: sc.Type
  val ListType: sc.Type
  val Long: sc.Type
  val MapType: sc.Type
  val MapImpl: sc.Type
  val Short: sc.Type

  def docLink(cls: sc.QIdent, value: sc.Ident): String
  val Optional: OptionalSupport
  val BuiltIn: Map[sc.Ident, sc.Type.Qualified]
  def extension: String
  def renderTree(tree: sc.Tree): sc.Code
  val isKeyword: Set[String]
}

trait OptionalSupport {
  val tpe: sc.Type

  def unapply(t: sc.Type): Option[sc.Type] =
    t match {
      case sc.Type.ArrayOf(_)               => None
      case sc.Type.Wildcard                 => None
      case sc.Type.TApply(`tpe`, List(one)) => Some(one)
      case sc.Type.TApply(underlying, _)    => unapply(underlying)
      case sc.Type.Qualified(_)             => None
      case sc.Type.Abstract(_)              => None
      case sc.Type.Commented(underlying, _) => unapply(underlying)
      case sc.Type.ByName(underlying)       => unapply(underlying)
      case sc.Type.UserDefined(underlying)  => unapply(underlying)
    }
}
