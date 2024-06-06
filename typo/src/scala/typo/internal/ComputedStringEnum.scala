package typo
package internal

case class ComputedStringEnum(
    dbEnum: db.StringEnum,
    tpe: sc.Type.Qualified,
    members: NonEmptyList[(sc.Ident, String)]
)
object ComputedStringEnum {
  def apply(naming: Naming)(enm: db.StringEnum): ComputedStringEnum =
    new ComputedStringEnum(
      enm,
      sc.Type.Qualified(naming.enumName(enm.name)),
      enm.values.map { value => naming.enumValue(value) -> value }
    )

}
