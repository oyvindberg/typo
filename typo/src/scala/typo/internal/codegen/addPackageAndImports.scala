package typo
package internal
package codegen

import scala.collection.mutable

/** imports are automatically written based on the qualified idents found in the code
  */
object addPackageAndImports {
  def apply(knownNamesByPkg: Map[sc.QIdent, Map[sc.Ident, sc.Type.Qualified]], file: sc.File): sc.File = {
    val newImports = mutable.Map.empty[sc.Ident, sc.Type.Qualified]

    val contents = file.contents
    val withShortenedNames = contents.mapTrees { tree =>
      shortenNames(tree) {
        case currentQName if currentQName.value.idents.length <= 1 => currentQName
        case currentQName =>
          val currentName = currentQName.value.name
          val shortenedQName = sc.Type.Qualified(currentName)
          knownNamesByPkg(file.pkg).get(currentName).orElse(sc.Type.BuiltIn.get(currentName)).orElse(newImports.get(currentName)) match {
            case Some(alreadyAvailable) =>
              if (alreadyAvailable == currentQName) shortenedQName else currentQName
            case None =>
              newImports += ((currentName, currentQName))
              shortenedQName
          }
      }
    }

    val withPrefix =
      code"""${NonEmptyList.fromList(file.pkg.idents).fold(sc.Code.Empty)(is => is.map(i => code"package $i").mkCode("\n"))}
            |
            |${newImports.values.toList.sorted.map { i => code"import $i" }.mkCode("\n")}
            |
            |$withShortenedNames""".stripMargin

    file.copy(contents = withPrefix)
  }

  // traverse tree and rewrite qualified names
  def shortenNames(tree: sc.Tree)(f: sc.Type.Qualified => sc.Type.Qualified): sc.Tree =
    tree match {
      case x: sc.Ident                              => x
      case x: sc.QIdent                             => x
      case sc.Param(name, tpe, maybeDefault)        => sc.Param(name, shortenNames(tpe, f), maybeDefault.map(code => code.mapTrees(shortenNames(_)(f))))
      case x: sc.StrLit                             => x
      case tpe: sc.Type                             => shortenNames(tpe, f)
      case sc.StringInterpolate(i, prefix, content) => sc.StringInterpolate(shortenNames(i, f), prefix, content)
    }

  // traverse type tree and rewrite qualified names
  def shortenNames(tpe: sc.Type, f: sc.Type.Qualified => sc.Type.Qualified): sc.Type =
    tpe match {
      case sc.Type.Abstract(value)                => sc.Type.Abstract(value)
      case sc.Type.Wildcard                       => sc.Type.Wildcard
      case sc.Type.TApply(underlying, targs)      => sc.Type.TApply(shortenNames(underlying, f), targs.map(targ => shortenNames(targ, f)))
      case sc.Type.Qualified(value)               => f(sc.Type.Qualified(value))
      case sc.Type.Commented(underlying, comment) => sc.Type.Commented(shortenNames(underlying, f), comment)
      case sc.Type.ByName(underlying)             => sc.Type.ByName(shortenNames(underlying, f))
      case sc.Type.UserDefined(underlying)        => sc.Type.UserDefined(shortenNames(underlying, f))
    }
}
