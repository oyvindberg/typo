package typo
package internal
package codegen

object DomainFile {
  def apply(naming: Naming, options: InternalOptions, typeMapperScala: TypeMapperScala, genOrdering: GenOrdering)(dom: db.Domain): sc.File = {
    val qident = naming.domainName(dom.name)
    val tpe = sc.Type.Qualified(qident)

    val comments = scaladoc(s"Domain `${dom.name.value}`")(
      dom.constraintDefinition match {
        case Some(definition) => List(s"Constraint: $definition")
        case None             => List("No constraint")
      }
    )
    val value = sc.Ident("value")

    val underlying: sc.Type = typeMapperScala.domain(dom.tpe)
    val instances = List(
      List(
        genOrdering.ordering(tpe, NonEmptyList(sc.Param(value, underlying, None)))
      ),
      options.jsonLibs.flatMap(_.anyValInstances(wrapperType = tpe, fieldName = value, underlying = underlying)),
      options.dbLib.toList.flatMap(_.anyValInstances(wrapperType = tpe, underlying = underlying))
    ).flatten

    val str =
      code"""$comments
            |case class ${qident.name}($value: $underlying) extends AnyVal
            |${genObject(qident, instances)}
            |""".stripMargin

    sc.File(tpe, str, secondaryTypes = Nil)
  }
}
