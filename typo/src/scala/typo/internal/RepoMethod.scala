package typo
package internal

sealed trait RepoMethod

object RepoMethod {
  case class SelectAll(rowType: sc.Type) extends RepoMethod
  case class SelectById(id: IdComputed, rowType: sc.Type) extends RepoMethod
  case class SelectAllByIds(unaryId: IdComputed.Unary, idsParam: sc.Param, rowType: sc.Type) extends RepoMethod
  case class SelectByUnique(params: NonEmptyList[ColumnComputed], rowType: sc.Type) extends RepoMethod
  case class SelectByFieldValues(param: sc.Param, rowType: sc.Type) extends RepoMethod
  case class UpdateFieldValues(id: IdComputed, param: sc.Param, cases: NonEmptyList[ColumnComputed], rowType: sc.Type) extends RepoMethod
  case class Update(id: IdComputed, param: sc.Param, colsUnsaved: NonEmptyList[ColumnComputed]) extends RepoMethod
  case class Insert(cols: NonEmptyList[ColumnComputed], unsavedParam: sc.Param, default: DefaultComputed, rowType: sc.Type) extends RepoMethod
  case class Delete(id: IdComputed) extends RepoMethod
  case class SqlFile(sqlFile: SqlFileComputed) extends RepoMethod
}
