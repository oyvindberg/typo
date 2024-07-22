package typo
package internal
package codegen

object FileCustomType {
  def apply(options: InternalOptions)(ct: CustomType): sc.File = {

    val maybeBijection = ct.params match {
      case NonEmptyList(sc.Param(_, name, underlying, _), Nil) if options.enableDsl =>
        val bijection = {
          val thisBijection = sc.Type.dsl.Bijection.of(ct.typoType, underlying)
          sc.Given(Nil, sc.Ident("bijection"), Nil, thisBijection, code"$thisBijection(_.$name)(${ct.typoType}.apply)")
        }
        Some(bijection)
      case _ => None
    }

    val instances =
      maybeBijection.toList ++
        options.jsonLibs.flatMap(_.customTypeInstances(ct)) ++
        options.dbLib.toList.flatMap(_.customTypeInstances(ct))

    val cls = sc.Adt.Record(
      isWrapper = false,
      comments = scaladoc(List(ct.comment)),
      name = ct.typoType,
      tparams = Nil,
      params = ct.params.toList,
      implicitParams = Nil,
      `extends` = None,
      implements = Nil,
      members = Nil,
      staticMembers = instances ++ ct.objBody0
    )

    sc.File(ct.typoType, cls, secondaryTypes = Nil, scope = Scope.Main)
  }
}
