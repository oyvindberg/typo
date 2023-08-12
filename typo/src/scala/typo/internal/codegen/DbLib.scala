package typo
package internal
package codegen

trait DbLib {
  def repoSig(repoMethod: RepoMethod): sc.Code
  def repoFinalImpl(repoMethod: RepoMethod.Final): sc.Code
  def repoVirtualImpl(repoMethod: RepoMethod.Virtual): sc.Code
  def mockVirtualRepoImpl(id: IdComputed, repoMethod: RepoMethod.Virtual, maybeToRow: Option[sc.Param]): sc.Code
  def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type): List[sc.ClassMember]
  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.ClassMember]
  def missingInstances: List[sc.ClassMember]
  def rowInstances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.ClassMember]
  def customTypeInstances(ct: CustomType): List[sc.ClassMember]
}
