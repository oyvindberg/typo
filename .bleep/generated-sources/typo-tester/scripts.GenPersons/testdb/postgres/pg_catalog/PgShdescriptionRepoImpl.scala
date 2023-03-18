package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgShdescriptionRepoImpl extends PgShdescriptionRepo {
  override def selectAll(implicit c: Connection): List[PgShdescriptionRow] = {
    SQL"""select objoid, classoid, description from pg_catalog.pg_shdescription""".as(PgShdescriptionRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgShdescriptionFieldValue[_]])(implicit c: Connection): List[PgShdescriptionRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgShdescriptionFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgShdescriptionFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgShdescriptionFieldValue.description(value) => NamedParameter("description", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_shdescription where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgShdescriptionRow.rowParser.*)
    }

  }
}
