package typo

sealed trait IdComputed {
  def qident: sc.QIdent
  def paramName: sc.Ident
  def cols: List[ColumnComputed]

  final def name: sc.Ident = qident.name
  final def tpe: sc.Type.Qualified = sc.Type.Qualified(qident)
}

object IdComputed {
  case class Unary(col: ColumnComputed, qident: sc.QIdent) extends IdComputed {
    override def cols: List[ColumnComputed] = List(col)
    def underlying: sc.Type = col.tpe
    override def paramName: sc.Ident = col.name
  }

  case class Composite(cols: List[ColumnComputed], qident: sc.QIdent, paramName: sc.Ident) extends IdComputed
}
