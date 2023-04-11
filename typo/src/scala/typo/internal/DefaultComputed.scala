package typo
package internal

case class DefaultComputed(naming: Naming) {
  val Defaulted = sc.Type.Qualified(naming.className(List(sc.Ident("Defaulted"))))
  val Provided = sc.Ident("Provided")
  val UseDefault = sc.Ident("UseDefault")
}
