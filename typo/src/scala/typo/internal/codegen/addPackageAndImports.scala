package typo
package internal
package codegen

import scala.collection.mutable

/** imports are automatically written based on the qualified idents found in the code
  */
object addPackageAndImports {
  def apply(language: Lang, knownNamesByPkg: Map[sc.QIdent, Map[sc.Ident, sc.Type.Qualified]], file: sc.File): sc.File = {
    val newImports = mutable.Map.empty[sc.Ident, sc.Type.Qualified]

    val contents = file.contents
    val withShortenedNames = contents.mapTrees { tree =>
      shortenNames(
        tree,
        {
          case currentQName if currentQName.value.idents.length <= 1 => currentQName
          case currentQName =>
            val currentName = currentQName.value.name
            val shortenedQName = sc.Type.Qualified(currentName)
            knownNamesByPkg.get(file.pkg).flatMap(_.get(currentName)).orElse(language.BuiltIn.get(currentName)).orElse(newImports.get(currentName)) match {
              case Some(alreadyAvailable) =>
                if (alreadyAvailable == currentQName) shortenedQName else currentQName
              case None =>
                newImports += ((currentName, currentQName))
                shortenedQName
            }
        }
      )
    }

    val withPrefix =
      code"""|package ${file.pkg};
             |
             |${newImports.values.toList.sorted.map { i => code"import $i;" }.mkCode("\n")}
             |
             |$withShortenedNames""".stripMargin

    file.copy(contents = withPrefix)
  }

  // traverse tree and rewrite qualified names
  def shortenNames(tree: sc.Tree, f: sc.Type.Qualified => sc.Type.Qualified): sc.Tree =
    tree match {
      case sc.New(target, args) =>
        sc.New(target.mapTrees(t => shortenNames(t, f)), args.map(shortenNamesArg(_, f)))
      case sc.ApplyFunction1(target, arg) =>
        sc.ApplyFunction1(target.mapTrees(t => shortenNames(t, f)), arg.mapTrees(t => shortenNames(t, f)))
      case sc.ApplyByName(target) =>
        sc.ApplyByName(target.mapTrees(t => shortenNames(t, f)))
      case sc.ApplyNullary(target) =>
        sc.ApplyNullary(target.mapTrees(t => shortenNames(t, f)))
      case sc.Arg.Pos(value) =>
        sc.Arg.Pos(value.mapTrees(t => shortenNames(t, f)))
      case sc.Arg.Named(name, value) =>
        sc.Arg.Named(name, value.mapTrees(t => shortenNames(t, f)))
      case x: sc.Ident =>
        x
      case x: sc.QIdent =>
        x
      case x: sc.Param =>
        shortenNamesParam(x, f)
      case x: sc.StrLit =>
        x
      case x: sc.Summon =>
        sc.Summon(shortenNamesType(x.tpe, f))
      case tpe: sc.Type =>
        shortenNamesType(tpe, f)
      case sc.StringInterpolate(i, prefix, content) =>
        sc.StringInterpolate(shortenNamesType(i, f), prefix, content.mapTrees(t => shortenNames(t, f)))
      case x: sc.ClassMember =>
        shortenNamesClassMember(x, f)
      case sc.Enum(comments, tpe, members, instances) =>
        sc.Enum(comments, f(tpe), members, instances.map(shortenNamesClassMember(_, f)))
      case cls: sc.Class =>
        shortenNamesClass(cls, f)
      case adt: sc.Adt =>
        shortenNamesAdt(adt, f)
    }

  def shortenNamesParam(param: sc.Param, f: sc.Type.Qualified => sc.Type.Qualified): sc.Param =
    sc.Param(param.comments, param.name, shortenNamesType(param.tpe, f), param.default.map(code => code.mapTrees(t => shortenNames(t, f))))

  def shortenNamesArg(arg: sc.Arg, f: sc.Type.Qualified => sc.Type.Qualified): sc.Arg =
    arg match {
      case sc.Arg.Pos(value)         => sc.Arg.Pos(value.mapTrees(t => shortenNames(t, f)))
      case sc.Arg.Named(name, value) => sc.Arg.Named(name, value.mapTrees(t => shortenNames(t, f)))
    }

  def shortenNamesClass(cls: sc.Class, f: sc.Type.Qualified => sc.Type.Qualified): sc.Class =
    sc.Class(
      comments = cls.comments,
      classType = cls.classType,
      name = cls.name,
      tparams = cls.tparams,
      params = cls.params.map(shortenNamesParam(_, f)),
      implicitParams = cls.implicitParams.map(shortenNamesParam(_, f)),
      `extends` = cls.`extends`.map(shortenNamesType(_, f)),
      implements = cls.implements.map(shortenNamesType(_, f)),
      members = cls.members.map(shortenNamesClassMember(_, f)),
      staticMembers = cls.staticMembers.map(shortenNamesClassMember(_, f))
    )

  def shortenNamesAdt(x: sc.Adt, f: sc.Type.Qualified => sc.Type.Qualified): sc.Adt =
    x match {
      case x: sc.Adt.Record => shortenNamesAdtRecord(x, f)
      case x: sc.Adt.Sum    => shortenNamesAdtSum(x, f)
    }

  def shortenNamesAdtRecord(x: sc.Adt.Record, f: sc.Type.Qualified => sc.Type.Qualified): sc.Adt.Record =
    sc.Adt.Record(
      isWrapper = x.isWrapper,
      comments = x.comments,
      name = x.name,
      tparams = x.tparams,
      params = x.params.map(shortenNamesParam(_, f)),
      implicitParams = x.implicitParams.map(shortenNamesParam(_, f)),
      `extends` = x.`extends`.map(shortenNamesType(_, f)),
      implements = x.implements.map(shortenNamesType(_, f)),
      members = x.members.map(shortenNamesClassMember(_, f)),
      staticMembers = x.staticMembers.map(shortenNamesClassMember(_, f))
    )

  def shortenNamesAdtSum(x: sc.Adt.Sum, f: sc.Type.Qualified => sc.Type.Qualified): sc.Adt.Sum =
    sc.Adt.Sum(
      comments = x.comments,
      name = x.name,
      tparams = x.tparams,
      implements = x.implements.map(shortenNamesType(_, f)),
      members = x.members.map(shortenNamesMethod(_, f)),
      staticMembers = x.staticMembers.map(shortenNamesClassMember(_, f)),
      subtypes = x.subtypes.map(shortenNamesAdt(_, f))
    )

  def shortenNamesClassMember(cm: sc.ClassMember, f: sc.Type.Qualified => sc.Type.Qualified): sc.ClassMember =
    cm match {
      case sc.Given(tparams, name, implicitParams, tpe, body) =>
        sc.Given(tparams, name, implicitParams.map(p => shortenNamesParam(p, f)), shortenNamesType(tpe, f), body.mapTrees(t => shortenNames(t, f)))
      case sc.Value(name, tpe, body) =>
        sc.Value(
          name,
          shortenNamesType(tpe, f),
          body.map(_.mapTrees(t => shortenNames(t, f)))
        )
      case x: sc.Method =>
        shortenNamesMethod(x, f)
    }

  def shortenNamesMethod(x: sc.Method, f: sc.Type.Qualified => sc.Type.Qualified): sc.Method =
    sc.Method(
      x.comments,
      x.tparams,
      x.name,
      x.params.map(p => shortenNamesParam(p, f)),
      x.implicitParams.map(p => shortenNamesParam(p, f)),
      shortenNamesType(x.tpe, f),
      x.body.map(_.mapTrees(t => shortenNames(t, f)))
    )

  // traverse type tree and rewrite qualified names
  def shortenNamesType(tpe: sc.Type, f: sc.Type.Qualified => sc.Type.Qualified): sc.Type =
    tpe match {
      case sc.Type.ArrayOf(value)                 => sc.Type.ArrayOf(shortenNamesType(value, f))
      case sc.Type.Abstract(value)                => sc.Type.Abstract(value)
      case sc.Type.Wildcard                       => sc.Type.Wildcard
      case sc.Type.TApply(underlying, targs)      => sc.Type.TApply(shortenNamesType(underlying, f), targs.map(targ => shortenNamesType(targ, f)))
      case q @ sc.Type.Qualified(_)               => f(q)
      case sc.Type.Commented(underlying, comment) => sc.Type.Commented(shortenNamesType(underlying, f), comment)
      case sc.Type.ByName(underlying)             => sc.Type.ByName(shortenNamesType(underlying, f))
      case sc.Type.UserDefined(underlying)        => sc.Type.UserDefined(shortenNamesType(underlying, f))
    }
}
