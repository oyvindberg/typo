package typo
package internal

sealed trait IdComputed {
  def paramName: sc.Ident
  def cols: NonEmptyList[ColumnComputed]
  def tpe: sc.Type
  final def param: sc.Param = sc.Param(paramName, tpe, None)
}

object IdComputed {

  sealed trait Unary extends IdComputed {
    def col: ColumnComputed
    override def cols: NonEmptyList[ColumnComputed] = NonEmptyList(col, Nil)
    override def paramName: sc.Ident = col.name
  }

  // normal generated code for a normal single-column id
  case class UnaryNormal(col: ColumnComputed, tpe: sc.Type.Qualified) extends Unary {
    def underlying: sc.Type = col.tpe
  }

  // normal generated code for a normal single-column id
  case class UnaryInherited(col: ColumnComputed, tpe: sc.Type) extends Unary {
    def underlying: sc.Type = col.tpe
  }

  // if user supplied a type override for an id column
  case class UnaryUserSpecified(col: ColumnComputed, tpe: sc.Type) extends Unary {
    override def paramName: sc.Ident = col.name
  }

  case class Composite(cols: NonEmptyList[ColumnComputed], tpe: sc.Type.Qualified, paramName: sc.Ident) extends IdComputed {
    val colByName: Map[sc.Ident, ColumnComputed] =
      cols.map(c => c.name -> c).toMap
  }
}
