/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources`)
 */
package typo
package generated
package information_schema

import anorm.Column
import anorm.ParameterMetaData
import anorm.ToStatement
import play.api.libs.json.Reads
import play.api.libs.json.Writes

/** Domain `information_schema.sql_identifier`
  * No constraint
  */
case class SqlIdentifier(value: String) extends AnyVal
object SqlIdentifier {
  implicit lazy val arrayColumn: Column[Array[SqlIdentifier]] = Column.columnToArray(column, implicitly)
  implicit lazy val arrayToStatement: ToStatement[Array[SqlIdentifier]] = implicitly[ToStatement[Array[String]]].contramap(_.map(_.value))
  implicit lazy val column: Column[SqlIdentifier] = implicitly[Column[String]].map(SqlIdentifier.apply)
  implicit lazy val ordering: Ordering[SqlIdentifier] = Ordering.by(_.value)
  implicit lazy val parameterMetadata: ParameterMetaData[SqlIdentifier] = new ParameterMetaData[SqlIdentifier] {
    override def sqlType: String = implicitly[ParameterMetaData[String]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[String]].jdbcType
  }
  implicit lazy val reads: Reads[SqlIdentifier] = Reads.StringReads.map(SqlIdentifier.apply)
  implicit lazy val toStatement: ToStatement[SqlIdentifier] = implicitly[ToStatement[String]].contramap(_.value)
  implicit lazy val writes: Writes[SqlIdentifier] = Writes.StringWrites.contramap(_.value)
}