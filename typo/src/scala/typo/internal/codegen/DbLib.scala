package typo
package internal
package codegen

trait DbLib {
  def repoSig(repoMethod: RepoMethod): sc.Code
  def repoImpl(repoMethod: RepoMethod): sc.Code
  def mockRepoImpl(id: IdComputed, repoMethod: RepoMethod, maybeToRow: Option[sc.Param]): sc.Code
  def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code]
  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code]
  def missingInstances: List[sc.Code]
  def repoAdditionalMembers(maybeId: Option[IdComputed], tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Code]
  def customTypeInstances(ct: CustomType): List[sc.Code]
}
