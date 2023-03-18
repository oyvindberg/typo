package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgForeignTableRepoImpl extends PgForeignTableRepo {
  override def selectAll(implicit c: Connection): List[PgForeignTableRow] = {
    SQL"""select ftrelid, ftserver, ftoptions from pg_catalog.pg_foreign_table""".as(PgForeignTableRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgForeignTableFieldValue[_]])(implicit c: Connection): List[PgForeignTableRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgForeignTableFieldValue.ftrelid(value) => NamedParameter("ftrelid", ParameterValue.from(value))
          case PgForeignTableFieldValue.ftserver(value) => NamedParameter("ftserver", ParameterValue.from(value))
          case PgForeignTableFieldValue.ftoptions(value) => NamedParameter("ftoptions", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_foreign_table where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgForeignTableRow.rowParser.*)
    }

  }
}
