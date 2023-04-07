package typo
package internal
package codegen

trait DbLib {
  def repoSig(repoMethod: RepoMethod): sc.Code
  def repoImpl(table: RelationComputed, repoMethod: RepoMethod): sc.Code
  def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code]
  def instances(tpe: sc.Type, cols: NonEmptyList[ColumnComputed]): List[sc.Code]
  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code]
  def missingInstances: List[sc.Code]
}
