package testdb
package postgres
package pg_catalog



sealed abstract class PgOpfamilyFieldValue[T](val name: String, val value: T)

object PgOpfamilyFieldValue {
  case class oid(override val value: PgOpfamilyId) extends PgOpfamilyFieldValue("oid", value)
  case class opfmethod(override val value: Long) extends PgOpfamilyFieldValue("opfmethod", value)
  case class opfname(override val value: String) extends PgOpfamilyFieldValue("opfname", value)
  case class opfnamespace(override val value: Long) extends PgOpfamilyFieldValue("opfnamespace", value)
  case class opfowner(override val value: Long) extends PgOpfamilyFieldValue("opfowner", value)
}
