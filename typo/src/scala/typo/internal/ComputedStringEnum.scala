package typo.internal

import typo.{Naming, db, sc}

case class ComputedStringEnum(tpe: sc.Type.Qualified, name: db.RelationName, members: List[(sc.Ident, String)])

object ComputedStringEnum {
  def apply(naming: Naming)(enm: db.StringEnum): ComputedStringEnum =
    new ComputedStringEnum(
      sc.Type.Qualified(naming.enumName(enm.name)),
      enm.name,
      enm.values.map { value => naming.enumValue(value) -> value }
    )
}
