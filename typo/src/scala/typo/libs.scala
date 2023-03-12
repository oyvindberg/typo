package typo

/** All references to types/terms in third party libraries go here
  */
object libs {
  object anorm {
    def Column(t: sc.Type) = sc.Type.TApply(sc.Type.Qualified("anorm.Column"), List(t))
    val ToStatementName = sc.Type.Qualified("anorm.ToStatement")
    def ToStatement(t: sc.Type) = sc.Type.TApply(ToStatementName, List(t))
    val NamedParameter = sc.Type.Qualified("anorm.NamedParameter")
    val ParameterValue = sc.Type.Qualified("anorm.ParameterValue")
    def RowParser(t: sc.Type) = sc.Type.TApply(sc.Type.Qualified("anorm.RowParser"), List(t))
    val Success = sc.Type.Qualified("anorm.Success")
    val SqlMappingError = sc.Type.Qualified("anorm.SqlMappingError")
    val SqlStringInterpolation = sc.Type.Qualified("anorm.SqlStringInterpolation")

    def sql(content: sc.Code) =
      sc.StringInterpolate(libs.anorm.SqlStringInterpolation, sc.Ident("SQL"), content)
  }
}
