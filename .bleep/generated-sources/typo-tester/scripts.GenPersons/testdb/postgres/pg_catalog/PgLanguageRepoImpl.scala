package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgLanguageRepoImpl extends PgLanguageRepo {
  override def selectAll(implicit c: Connection): List[PgLanguageRow] = {
    SQL"""select oid, lanname, lanowner, lanispl, lanpltrusted, lanplcallfoid, laninline, lanvalidator, lanacl from pg_catalog.pg_language""".as(PgLanguageRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgLanguageFieldValue[_]])(implicit c: Connection): List[PgLanguageRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgLanguageFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgLanguageFieldValue.lanname(value) => NamedParameter("lanname", ParameterValue.from(value))
          case PgLanguageFieldValue.lanowner(value) => NamedParameter("lanowner", ParameterValue.from(value))
          case PgLanguageFieldValue.lanispl(value) => NamedParameter("lanispl", ParameterValue.from(value))
          case PgLanguageFieldValue.lanpltrusted(value) => NamedParameter("lanpltrusted", ParameterValue.from(value))
          case PgLanguageFieldValue.lanplcallfoid(value) => NamedParameter("lanplcallfoid", ParameterValue.from(value))
          case PgLanguageFieldValue.laninline(value) => NamedParameter("laninline", ParameterValue.from(value))
          case PgLanguageFieldValue.lanvalidator(value) => NamedParameter("lanvalidator", ParameterValue.from(value))
          case PgLanguageFieldValue.lanacl(value) => NamedParameter("lanacl", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_language where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgLanguageRow.rowParser.*)
    }

  }
}
