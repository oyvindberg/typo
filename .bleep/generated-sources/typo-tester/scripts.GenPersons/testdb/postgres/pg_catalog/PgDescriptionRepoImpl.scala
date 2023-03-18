package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgDescriptionRepoImpl extends PgDescriptionRepo {
  override def selectAll(implicit c: Connection): List[PgDescriptionRow] = {
    SQL"""select objoid, classoid, objsubid, description from pg_catalog.pg_description""".as(PgDescriptionRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgDescriptionFieldValue[_]])(implicit c: Connection): List[PgDescriptionRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgDescriptionFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgDescriptionFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgDescriptionFieldValue.objsubid(value) => NamedParameter("objsubid", ParameterValue.from(value))
          case PgDescriptionFieldValue.description(value) => NamedParameter("description", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_description where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgDescriptionRow.rowParser.*)
    }

  }
}
