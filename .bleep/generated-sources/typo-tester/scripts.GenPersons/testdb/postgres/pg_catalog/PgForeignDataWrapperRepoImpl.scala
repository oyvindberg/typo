package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgForeignDataWrapperRepoImpl extends PgForeignDataWrapperRepo {
  override def selectAll(implicit c: Connection): List[PgForeignDataWrapperRow] = {
    SQL"""select oid, fdwname, fdwowner, fdwhandler, fdwvalidator, fdwacl, fdwoptions from pg_catalog.pg_foreign_data_wrapper""".as(PgForeignDataWrapperRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgForeignDataWrapperFieldValue[_]])(implicit c: Connection): List[PgForeignDataWrapperRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgForeignDataWrapperFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgForeignDataWrapperFieldValue.fdwname(value) => NamedParameter("fdwname", ParameterValue.from(value))
          case PgForeignDataWrapperFieldValue.fdwowner(value) => NamedParameter("fdwowner", ParameterValue.from(value))
          case PgForeignDataWrapperFieldValue.fdwhandler(value) => NamedParameter("fdwhandler", ParameterValue.from(value))
          case PgForeignDataWrapperFieldValue.fdwvalidator(value) => NamedParameter("fdwvalidator", ParameterValue.from(value))
          case PgForeignDataWrapperFieldValue.fdwacl(value) => NamedParameter("fdwacl", ParameterValue.from(value))
          case PgForeignDataWrapperFieldValue.fdwoptions(value) => NamedParameter("fdwoptions", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_foreign_data_wrapper where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgForeignDataWrapperRow.rowParser.*)
    }

  }
}
