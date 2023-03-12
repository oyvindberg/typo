package typo

import typo.Gen.RepoMethod
import typo.sc.syntax.*

trait DbLib {
  def sig(repoMethod: RepoMethod): sc.Code
}

object DbLib {
  object anorm extends DbLib {
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
      sc.StringInterpolate(DbLib.anorm.SqlStringInterpolation, sc.Ident("SQL"), content)

    def sig(repoMethod: RepoMethod): sc.Code = repoMethod match {
      case RepoMethod.SelectAll(rowType) =>
        code"def selectAll(implicit c: ${sc.Type.Connection}): ${sc.Type.List(rowType)}"
      case RepoMethod.SelectById(idParam, rowType) =>
        code"def selectById($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Option(rowType)}"
      case RepoMethod.SelectAllByIds(idParam, rowType) =>
        code"def selectByIds($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.List(rowType)}"
      case RepoMethod.SelectByUnique(params, rowType) =>
        code"def selectByUnique(${params.map(_.code).mkCode(", ")})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option(rowType)}"
      case RepoMethod.SelectByFieldValues(param, rowType) =>
        code"def selectByFieldValues($param)(implicit c: ${sc.Type.Connection}): ${sc.Type.List(rowType)}"
      case RepoMethod.UpdateFieldValues(idParam, param) =>
        code"def updateFieldValues($idParam, $param)(implicit c: ${sc.Type.Connection}): ${sc.Type.Int}"
      case RepoMethod.InsertDbGeneratedKey(param, idType) =>
        code"def insert($param)(implicit c: ${sc.Type.Connection}): $idType"
      case RepoMethod.InsertProvidedKey(idParam, unsavedParam) =>
        code"def insert($idParam, $unsavedParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Unit}"
      case RepoMethod.InsertOnlyKey(idParam) =>
        code"def insert($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Unit}"
      case RepoMethod.Delete(idParam) =>
        code"def delete($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    }

  }
}
