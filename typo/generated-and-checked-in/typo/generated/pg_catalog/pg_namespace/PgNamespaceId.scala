/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources`)
 */
package typo
package generated
package pg_catalog
package pg_namespace

import anorm.Column
import anorm.ParameterMetaData
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgNamespaceId(value: /* oid */ Long) extends AnyVal
object PgNamespaceId {
  implicit val ordering: Ordering[PgNamespaceId] = Ordering.by(_.value)
  implicit val format: Format[PgNamespaceId] = implicitly[Format[/* oid */ Long]].bimap(PgNamespaceId.apply, _.value)
  implicit val toStatement: ToStatement[PgNamespaceId] = implicitly[ToStatement[/* oid */ Long]].contramap(_.value)
  implicit val column: Column[PgNamespaceId] = implicitly[Column[/* oid */ Long]].map(PgNamespaceId.apply)
  def rowParser(prefix: String): RowParser[PgNamespaceId] = SqlParser.get[PgNamespaceId](prefix + "oid")
  implicit val parameterMetadata: ParameterMetaData[PgNamespaceId] = new ParameterMetaData[PgNamespaceId] {
    override def sqlType: String = implicitly[ParameterMetaData[/* oid */ Long]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[/* oid */ Long]].jdbcType
  }

}