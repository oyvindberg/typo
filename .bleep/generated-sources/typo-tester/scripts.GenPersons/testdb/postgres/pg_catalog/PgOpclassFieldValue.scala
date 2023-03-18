package testdb
package postgres
package pg_catalog



sealed abstract class PgOpclassFieldValue[T](val name: String, val value: T)

object PgOpclassFieldValue {
  case class oid(override val value: Long) extends PgOpclassFieldValue("oid", value)
  case class opcmethod(override val value: Long) extends PgOpclassFieldValue("opcmethod", value)
  case class opcname(override val value: String) extends PgOpclassFieldValue("opcname", value)
  case class opcnamespace(override val value: Long) extends PgOpclassFieldValue("opcnamespace", value)
  case class opcowner(override val value: Long) extends PgOpclassFieldValue("opcowner", value)
  case class opcfamily(override val value: Long) extends PgOpclassFieldValue("opcfamily", value)
  case class opcintype(override val value: Long) extends PgOpclassFieldValue("opcintype", value)
  case class opcdefault(override val value: Boolean) extends PgOpclassFieldValue("opcdefault", value)
  case class opckeytype(override val value: Long) extends PgOpclassFieldValue("opckeytype", value)
}
