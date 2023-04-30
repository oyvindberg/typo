package typo
package internal

case class RowJoinedComputed(name: sc.QIdent, params: NonEmptyList[RowJoinedComputed.Param])

object RowJoinedComputed {
  case class Param(name: sc.Ident, tpe: sc.Type, isOptional: Boolean, table: TableComputed) {
    def param: sc.Param = sc.Param(name, tpe, None)
  }
}
