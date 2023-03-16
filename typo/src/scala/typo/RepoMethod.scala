package typo

sealed trait RepoMethod

object RepoMethod {
  case class SelectAll(rowType: sc.Type) extends RepoMethod
  case class SelectById(idParam: sc.Param, rowType: sc.Type) extends RepoMethod
  case class SelectAllByIds(unaryId: IdComputed.Unary, idsParam: sc.Param, rowType: sc.Type) extends RepoMethod
  case class SelectByUnique(params: List[sc.Param], rowType: sc.Type) extends RepoMethod
  case class SelectByFieldValues(param: sc.Param, rowType: sc.Type) extends RepoMethod
  case class UpdateFieldValues(idParam: sc.Param, param: sc.Param) extends RepoMethod
  case class InsertDbGeneratedKey(unsavedParam: sc.Param, idType: sc.Type) extends RepoMethod
  case class InsertProvidedKey(idParam: sc.Param, unsavedParam: sc.Param) extends RepoMethod
  case class InsertOnlyKey(idParam: sc.Param) extends RepoMethod
  case class Delete(idParam: sc.Param) extends RepoMethod
}
