package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgOpclassRepoImpl extends PgOpclassRepo {
  override def selectAll(implicit c: Connection): List[PgOpclassRow] = {
    SQL"""select oid, opcmethod, opcname, opcnamespace, opcowner, opcfamily, opcintype, opcdefault, opckeytype from pg_catalog.pg_opclass""".as(PgOpclassRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgOpclassFieldValue[_]])(implicit c: Connection): List[PgOpclassRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgOpclassFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgOpclassFieldValue.opcmethod(value) => NamedParameter("opcmethod", ParameterValue.from(value))
          case PgOpclassFieldValue.opcname(value) => NamedParameter("opcname", ParameterValue.from(value))
          case PgOpclassFieldValue.opcnamespace(value) => NamedParameter("opcnamespace", ParameterValue.from(value))
          case PgOpclassFieldValue.opcowner(value) => NamedParameter("opcowner", ParameterValue.from(value))
          case PgOpclassFieldValue.opcfamily(value) => NamedParameter("opcfamily", ParameterValue.from(value))
          case PgOpclassFieldValue.opcintype(value) => NamedParameter("opcintype", ParameterValue.from(value))
          case PgOpclassFieldValue.opcdefault(value) => NamedParameter("opcdefault", ParameterValue.from(value))
          case PgOpclassFieldValue.opckeytype(value) => NamedParameter("opckeytype", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_opclass where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgOpclassRow.rowParser.*)
    }

  }
}
