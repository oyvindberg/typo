package typo
package internal

/** Basically a mapping from whatever the postgres JDBC driver accepts or returns to a newtype we control
  */
case class CustomType(
    comment: String,
    sqlType: String,
    typoType: sc.Type.Qualified,
    params: List[sc.Param],
    isNull: sc.Ident => sc.Code,
    toTypo: CustomType.ToTypo,
    fromTypo: CustomType.FromTypo,
    toTypoInArray: Option[CustomType.ToTypo] = None,
    fromTypoInArray: Option[CustomType.FromTypo] = None
) {
  def toTypo0(expr: sc.Code): sc.Code = toTypo.toTypo(expr, typoType)
  def fromTypo0(expr: sc.Code): sc.Code = fromTypo.fromTypo0(expr)
}

object CustomType {
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
