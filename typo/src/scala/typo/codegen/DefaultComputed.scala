package typo
package codegen

case class DefaultComputed(pkg: sc.QIdent) {
  val Defaulted = pkg / sc.Ident("Defaulted")
  val DefaultedType = sc.Type.Qualified(Defaulted)
  val Provided = Defaulted / sc.Ident("Provided")
  val UseDefault = Defaulted / sc.Ident("UseDefault")
}
