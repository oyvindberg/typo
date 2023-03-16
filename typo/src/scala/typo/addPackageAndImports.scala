package typo

import typo.sc.syntax._

import scala.collection.mutable

/** imports are automatically written based on the qualified idents found in the code
  */
object addPackageAndImports {
  def apply(knownNamesByPkg: Map[sc.QIdent, Map[sc.Ident, sc.QIdent]], file: sc.File): sc.File = {
    val newImports = mutable.Map.empty[sc.Ident, sc.QIdent]

    val withShortenedNames = file.contents.mapTrees { tree =>
      shortenNames(tree) { currentQName =>
        val currentName = currentQName.name
        val shortenedQName = sc.QIdent(List(currentName))
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
      code"""package ${file.pkg}
            |
            |${newImports.values.toList.sorted.map { i => code"import $i" }.mkCode("\n")}
            |
            |$withShortenedNames""".stripMargin

    file.copy(contents = withPrefix)
  }

  // traverse tree and rewrite qualified names
  def shortenNames(tree: sc.Tree)(f: sc.QIdent => sc.QIdent): sc.Tree =
    tree match {
      case x: sc.Ident                              => x
      case x: sc.QIdent                             => f(x)
      case sc.Param(name, tpe)                      => sc.Param(name, shortenNames(tpe, f))
      case x: sc.StrLit                             => x
      case tpe: sc.Type                             => shortenNames(tpe, f)
      case sc.StringInterpolate(i, prefix, content) => sc.StringInterpolate(shortenNames(i, f), prefix, content)
    }

  // traverse type tree and rewrite qualified names
  def shortenNames(tpe: sc.Type, f: sc.QIdent => sc.QIdent): sc.Type =
    tpe match {
      case sc.Type.Abstract(value)           => sc.Type.Abstract(value)
      case sc.Type.Wildcard                  => sc.Type.Wildcard
      case sc.Type.TApply(underlying, targs) => sc.Type.TApply(shortenNames(underlying, f), targs.map(targ => shortenNames(targ, f)))
      case sc.Type.Qualified(value)          => sc.Type.Qualified(f(value))
    }
}
