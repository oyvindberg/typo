package typo
package internal
package codegen

case class FileDefault(default: ComputedDefault, jsonLibs: List[JsonLib], dbLib: Option[DbLib], lang: Lang) {
  val cls = {
    val instances: List[sc.Given] =
      jsonLibs.flatMap(_.defaultedInstance(default)) ++
        dbLib.toList.flatMap(_.defaultedInstance)

    val T = sc.Type.Abstract(sc.Ident("T"))
    val U = sc.Type.Abstract(sc.Ident("U"))
    val onDefaultParam = sc.Param(sc.Ident("onDefault"), sc.Type.ByName(U))
    val onProvidedParam = sc.Param(sc.Ident("onProvided"), lang.Function1.of(T, U))

    val foldAbstract =
      sc.Method(
        comments = sc.Comments.Empty,
        name = sc.Ident("fold"),
        tparams = List(U),
        params = List(onDefaultParam, onProvidedParam),
        implicitParams = Nil,
        tpe = U,
        body = None
      )

    val getOrElseAbstract =
      sc.Method(
        comments = sc.Comments.Empty,
        name = sc.Ident("getOrElse"),
        tparams = Nil,
        params = List(sc.Param(sc.Ident("onDefault"), sc.Type.ByName(T))),
        implicitParams = Nil,
        tpe = T,
        body = None
      )
    val valueParam = sc.Param(sc.Ident("value"), T)

    sc.Adt.Sum(
      comments = sc.Comments(List("This signals a value where if you don't provide it, postgres will generate it for you")),
      name = default.Defaulted,
      tparams = List(T),
      members = List(foldAbstract, getOrElseAbstract),
      implements = Nil,
      subtypes = List(
        sc.Adt.Record(
          isWrapper = false,
          comments = sc.Comments.Empty,
          name = sc.Type.Qualified(default.Provided),
          tparams = List(T),
          params = List(valueParam),
          implicitParams = Nil,
          `extends` = None,
          implements = List(default.Defaulted.of(T)),
          members = List(
            foldAbstract.copy(body = Some(sc.ApplyFunction1(onProvidedParam.name.code, valueParam.name.code))),
            getOrElseAbstract.copy(body = Some(valueParam.name.code))
          ),
          staticMembers = Nil
        ),
        sc.Adt.Record(
          isWrapper = false,
          comments = sc.Comments.Empty,
          name = sc.Type.Qualified(default.UseDefault),
          tparams = List(T),
          params = Nil,
          implicitParams = Nil,
          `extends` = None,
          implements = List(default.Defaulted.of(T)),
          members = List(
            foldAbstract.copy(body = Some(sc.ApplyByName(onDefaultParam.name))),
            getOrElseAbstract.copy(body = Some(sc.ApplyByName(onDefaultParam.name)))
          ),
          staticMembers = Nil
        )
      ),
      staticMembers = instances
    )
  }

  val file = sc.File(default.Defaulted, cls, secondaryTypes = Nil, scope = Scope.Main)
}
