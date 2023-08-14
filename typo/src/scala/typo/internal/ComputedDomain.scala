package typo
package internal

case class ComputedDomain(tpe: sc.Type.Qualified, underlyingType: sc.Type, underlying: db.Domain)

object ComputedDomain {
  def apply(naming: Naming, typeMapperScala: TypeMapperScala)(dom: db.Domain): ComputedDomain =
    ComputedDomain(
      tpe = sc.Type.Qualified(naming.domainName(dom.name)),
      underlyingType = typeMapperScala.domain(dom.tpe),
      underlying = dom
    )
}
