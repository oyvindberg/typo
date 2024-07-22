package typo
package internal

/** Keep those files among `allFiles` which are part of or reachable (through type references) from `entryPoints`. */
object minimize {
  def apply(allFiles: List[sc.File], entryPoints: Iterable[sc.File]): List[sc.File] = {
    val filesByQident = allFiles.iterator.map(x => (x.tpe.value, x)).toMap
    val toKeep: Set[sc.QIdent] = {
      val b = collection.mutable.HashSet.empty[sc.QIdent]
      b ++= entryPoints.map(_.tpe.value)
      entryPoints.foreach { f =>
        def goTree(tree: sc.Tree): Unit = {
          tree match {
            case sc.Ident(_) => ()
            case x: sc.QIdent =>
              if (!b(x)) {
                b += x
                filesByQident.get(x).foreach(f => go(f.contents))
              }
            case sc.New(target, args) =>
              go(target)
              args.foreach(goTree)
            case sc.ApplyFunction1(target, arg) =>
              go(target)
              go(arg)
            case sc.ApplyByName(target) =>
              go(target)
            case sc.ApplyNullary(target) =>
              go(target)
            case sc.Arg.Pos(value) =>
              go(value)
            case sc.Arg.Named(_, value) =>
              go(value)
            case sc.Param(_, _, tpe, maybeCode) =>
              goTree(tpe)
              maybeCode.foreach(go)
            case sc.StrLit(_) => ()
            case sc.Summon(tpe) =>
              goTree(tpe)
            case sc.StringInterpolate(i, prefix, content) =>
              goTree(i)
              goTree(prefix)
              go(content)
            case sc.Given(tparams, name, implicitParams, tpe, body) =>
              tparams.foreach(goTree)
              goTree(name)
              implicitParams.foreach(goTree)
              goTree(tpe)
              go(body)
            case sc.Value(name, tpe, body) =>
              goTree(name)
              goTree(tpe)
              body.foreach(go)
            case sc.Method(_, tparams, name, params, implicitParams, tpe, body) =>
              tparams.foreach(goTree)
              goTree(name)
              params.foreach(goTree)
              implicitParams.foreach(goTree)
              goTree(tpe)
              body.foreach(go)
            case sc.Enum(_, tpe, _, instances) =>
              goTree(tpe)
              instances.foreach(goTree)
            case sc.Class(_, _, _, tparams, params, implicitParams, extends_, implements, members, staticMembers) =>
              tparams.foreach(goTree)
              params.foreach(goTree)
              implicitParams.foreach(goTree)
              extends_.foreach(goTree)
              implements.foreach(goTree)
              members.foreach(goTree)
              staticMembers.foreach(goTree)
            case sc.Adt.Record(_, _, _, tparams, params, implicitParams, extends_, implements, members, staticMembers) =>
              tparams.foreach(goTree)
              params.foreach(goTree)
              implicitParams.foreach(goTree)
              extends_.foreach(goTree)
              implements.foreach(goTree)
              members.foreach(goTree)
              staticMembers.foreach(goTree)
            case sc.Adt.Sum(_, _, tparams, members, implements, subtypes, staticMembers) =>
              tparams.foreach(goTree)
              members.foreach(goTree)
              implements.foreach(goTree)
              subtypes.foreach(goTree)
              staticMembers.foreach(goTree)
            case sc.Type.Wildcard =>
              ()
            case sc.Type.TApply(underlying, targs) =>
              goTree(underlying)
              targs.foreach(goTree)
            case sc.Type.ArrayOf(value)           => goTree(value)
            case sc.Type.Qualified(value)         => goTree(value)
            case sc.Type.Abstract(_)              => ()
            case sc.Type.Commented(underlying, _) => goTree(underlying)
            case sc.Type.UserDefined(underlying)  => goTree(underlying)
            case sc.Type.ByName(underlying)       => goTree(underlying)
          }
        }

        def go(code: sc.Code): Unit = {
          code match {
            case sc.Code.Interpolated(_, args) =>
              args.foreach(go)
            case sc.Code.Combined(codes) =>
              codes.foreach(go)
            case sc.Code.Str(_) =>
              ()
            case sc.Code.Tree(tree) =>
              goTree(tree)
          }
        }

        go(f.contents)
      }

      b.toSet
    }

    allFiles.filter(f => toKeep(f.tpe.value))
  }
}
