package typo
package internal

case class DefaultComputed(naming: Naming) {
  val Defaulted = naming.className(List(sc.Ident("Defaulted")))
  val DefaultedType = sc.Type.Qualified(Defaulted)
  val Provided = Defaulted / sc.Ident("Provided")
  val UseDefault = Defaulted / sc.Ident("UseDefault")
}
