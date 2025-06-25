package typo

import typo.internal.codegen.ToCode

sealed abstract class ImplicitOrUsing extends Product with Serializable

object ImplicitOrUsing {
  case object Implicit extends ImplicitOrUsing {
    override def toString: String = "implicit"
  }
  case object Using extends ImplicitOrUsing {
    override def toString: String = "using"
  }

  implicit def toCode: ToCode[ImplicitOrUsing] = {
    case Implicit => sc.Code.Str("implicit")
    case Using    => sc.Code.Str("using")
  }
}
