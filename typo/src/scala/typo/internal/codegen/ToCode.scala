package typo
package internal
package codegen

@FunctionalInterface
trait ToCode[T] {
  def toCode(t: T): sc.Code
}

object ToCode {
  def apply[T: ToCode]: ToCode[T] = implicitly

  implicit def tree[T <: sc.Tree]: ToCode[T] = sc.Code.Tree.apply

  implicit val str: ToCode[String] = sc.Code.Str.apply
  implicit val int: ToCode[Int] = _.toString
  implicit val code: ToCode[sc.Code] = identity
  implicit val dbColName: ToCode[db.ColName] = colName => sc.StrLit(colName.value)

  implicit val tableName: ToCode[db.RelationName] = name => sc.Code.Str(name.value)
}
