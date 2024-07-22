package typo
package internal
package codegen

object FileDomain {
  def apply(domain: ComputedDomain, options: InternalOptions): sc.File = {
    val comments = scaladoc(
      List(
        s"Domain `${domain.underlying.name.value}`",
        domain.underlying.constraintDefinition match {
          case Some(definition) => s"Constraint: $definition"
          case None             => "No constraint"
        }
      )
    )
    val value = sc.Ident("value")

    val bijection =
      if (options.enableDsl)
        Some {
          val thisBijection = sc.Type.dsl.Bijection.of(domain.tpe, domain.underlyingType)
          sc.Given(Nil, sc.Ident("bijection"), Nil, thisBijection, code"$thisBijection(_.$value)(${domain.tpe}.apply)")
        }
      else None
    val instances = List(
      bijection.toList,
      options.jsonLibs.flatMap(_.wrapperTypeInstances(wrapperType = domain.tpe, fieldName = value, underlying = domain.underlyingType)),
      options.dbLib.toList.flatMap(_.wrapperTypeInstances(wrapperType = domain.tpe, underlying = domain.underlyingType, overrideDbType = Some(domain.underlying.name.quotedValue)))
    ).flatten

    val cls = sc.Adt.Record(
      isWrapper = false,
      comments = comments,
      name = domain.tpe,
      tparams = Nil,
      params = List(sc.Param(value, domain.underlyingType)),
      implicitParams = Nil,
      `extends` = None,
      implements = Nil,
      members = Nil,
      staticMembers = instances
    )

    sc.File(domain.tpe, cls, secondaryTypes = Nil, scope = Scope.Main)
  }
}
