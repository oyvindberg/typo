package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgEnumRepoImpl extends PgEnumRepo {
  override def selectAll(implicit c: Connection): List[PgEnumRow] = {
    SQL"""select oid, enumtypid, enumsortorder, enumlabel from pg_catalog.pg_enum""".as(PgEnumRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgEnumFieldValue[_]])(implicit c: Connection): List[PgEnumRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgEnumFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgEnumFieldValue.enumtypid(value) => NamedParameter("enumtypid", ParameterValue.from(value))
          case PgEnumFieldValue.enumsortorder(value) => NamedParameter("enumsortorder", ParameterValue.from(value))
          case PgEnumFieldValue.enumlabel(value) => NamedParameter("enumlabel", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_enum where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgEnumRow.rowParser.*)
    }

  }
}
