package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgInitPrivsRepoImpl extends PgInitPrivsRepo {
  override def selectAll(implicit c: Connection): List[PgInitPrivsRow] = {
    SQL"""select objoid, classoid, objsubid, privtype, initprivs from pg_catalog.pg_init_privs""".as(PgInitPrivsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgInitPrivsFieldValue[_]])(implicit c: Connection): List[PgInitPrivsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgInitPrivsFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgInitPrivsFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgInitPrivsFieldValue.objsubid(value) => NamedParameter("objsubid", ParameterValue.from(value))
          case PgInitPrivsFieldValue.privtype(value) => NamedParameter("privtype", ParameterValue.from(value))
          case PgInitPrivsFieldValue.initprivs(value) => NamedParameter("initprivs", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_init_privs where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgInitPrivsRow.rowParser.*)
    }

  }
}
