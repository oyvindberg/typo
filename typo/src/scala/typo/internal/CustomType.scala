package typo
package internal

/** Basically a mapping from whatever the postgres JDBC driver accepts or returns to a newtype we control
  */
case class CustomType(
    comment: String,
    sqlType: String,
    typoType: sc.Type.Qualified,
    params: NonEmptyList[sc.Param],
    toTypo: CustomType.ToTypo,
    fromTypo: CustomType.FromTypo,
    // some types is just very difficult to get right inside arrays using jdbc
    forbidArray: Boolean = false,
    toText: CustomType.Text,
    toTypoInArray: Option[CustomType.ToTypo] = None,
    fromTypoInArray: Option[CustomType.FromTypo] = None,
    objBody: Option[sc.Type.Qualified => sc.Code] = None
) {
  def withComment(newComment: String): CustomType = copy(comment = comment + newComment)
  def objBody0 = objBody.map(f => f(typoType))
  def toTypo0(expr: sc.Code): sc.Code = toTypo.toTypo(expr, typoType)
  def fromTypo0(expr: sc.Code): sc.Code = fromTypo.fromTypo0(expr)
}

object CustomType {
  case class Text(
      /* delegates to `Text` instance for this type */
      textType: sc.Type,
      toTextType: sc.Code => sc.Code
  )
  object Text {
    def string(toTextType: sc.Code => sc.Code): Text =
      Text(sc.Type.String, toTextType)
  }

  case class ToTypo(
      jdbcType: sc.Type,
      toTypo: (sc.Code, sc.Type.Qualified) => sc.Code
  )

  case class FromTypo(
      jdbcType: sc.Type,
      fromTypo: (sc.Code, sc.Type) => sc.Code
  ) {
    def fromTypo0(expr: sc.Code): sc.Code = fromTypo(expr, jdbcType)
  }
}
