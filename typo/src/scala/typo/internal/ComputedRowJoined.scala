package typo
package internal

case class ComputedRowJoined(name: sc.QIdent, params: NonEmptyList[ComputedRowJoined.Param])

object ComputedRowJoined {
  case class Param(name: sc.Ident, tpe: sc.Type, isOptional: Boolean, table: ComputedTable) {
    def param: sc.Param = sc.Param(name, tpe, None)
  }
}
