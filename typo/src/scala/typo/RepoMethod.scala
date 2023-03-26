package typo

sealed trait RepoMethod

object RepoMethod {
  case class SelectAll(rowType: sc.Type) extends RepoMethod
  case class SelectById(id: IdComputed, rowType: sc.Type) extends RepoMethod
  case class SelectAllByIds(unaryId: IdComputed.Unary, idsParam: sc.Param, rowType: sc.Type) extends RepoMethod
  case class SelectByUnique(params: List[ColumnComputed], rowType: sc.Type) extends RepoMethod
  case class SelectByFieldValues(param: sc.Param, rowType: sc.Type) extends RepoMethod
  case class UpdateFieldValues(id: IdComputed, param: sc.Param) extends RepoMethod
  case class InsertDbGeneratedKey(id: IdComputed, colsUnsaved: List[ColumnComputed], unsavedParam: sc.Param, default: DefaultComputed) extends RepoMethod
  case class InsertProvidedKey(id: IdComputed, colsUnsaved: List[ColumnComputed], unsavedParam: sc.Param, default: DefaultComputed) extends RepoMethod
  case class InsertOnlyKey(id: IdComputed) extends RepoMethod
  case class Delete(id: IdComputed) extends RepoMethod
  case class SqlScript(sqlScript: SqlScriptComputed) extends RepoMethod
}
