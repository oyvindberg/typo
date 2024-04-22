package typo
package internal

sealed trait IdComputed {
  def paramName: sc.Ident
  def cols: NonEmptyList[ComputedColumn]
  def tpe: sc.Type
  final def param: sc.Param = sc.Param(paramName, tpe, None)
}

object IdComputed {

  sealed trait Unary extends IdComputed {
    def col: ComputedColumn
    override def cols: NonEmptyList[ComputedColumn] = NonEmptyList(col, Nil)
    override def paramName: sc.Ident = col.name
  }

  // normal generated code for a normal single-column id
  case class UnaryNormal(col: ComputedColumn, tpe: sc.Type.Qualified) extends Unary {
    def underlying: sc.Type = col.tpe
  }

  // normal generated code for a normal single-column id, we won't generate extra code for this usage of it
  case class UnaryInherited(col: ComputedColumn, tpe: sc.Type) extends Unary {
    def underlying: sc.Type = col.tpe
  }

  // // user specified they don't want a primary key type for this
  case class UnaryNoIdType(col: ComputedColumn, tpe: sc.Type) extends Unary {
    def underlying: sc.Type = col.tpe
  }

  // if user supplied a type override for an id column
  case class UnaryUserSpecified(col: ComputedColumn, tpe: sc.Type) extends Unary {
    override def paramName: sc.Ident = col.name
  }

  case class Composite(cols: NonEmptyList[ComputedColumn], tpe: sc.Type.Qualified, paramName: sc.Ident) extends IdComputed
}
