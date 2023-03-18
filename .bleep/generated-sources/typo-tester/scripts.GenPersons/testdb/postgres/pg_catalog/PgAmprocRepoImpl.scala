package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgAmprocRepoImpl extends PgAmprocRepo {
  override def selectAll(implicit c: Connection): List[PgAmprocRow] = {
    SQL"""select oid, amprocfamily, amproclefttype, amprocrighttype, amprocnum, amproc from pg_catalog.pg_amproc""".as(PgAmprocRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgAmprocFieldValue[_]])(implicit c: Connection): List[PgAmprocRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgAmprocFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgAmprocFieldValue.amprocfamily(value) => NamedParameter("amprocfamily", ParameterValue.from(value))
          case PgAmprocFieldValue.amproclefttype(value) => NamedParameter("amproclefttype", ParameterValue.from(value))
          case PgAmprocFieldValue.amprocrighttype(value) => NamedParameter("amprocrighttype", ParameterValue.from(value))
          case PgAmprocFieldValue.amprocnum(value) => NamedParameter("amprocnum", ParameterValue.from(value))
          case PgAmprocFieldValue.amproc(value) => NamedParameter("amproc", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_amproc where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgAmprocRow.rowParser.*)
    }

  }
}
