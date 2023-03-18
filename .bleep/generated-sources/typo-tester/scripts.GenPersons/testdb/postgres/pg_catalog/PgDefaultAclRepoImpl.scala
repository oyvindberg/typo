package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgDefaultAclRepoImpl extends PgDefaultAclRepo {
  override def selectAll(implicit c: Connection): List[PgDefaultAclRow] = {
    SQL"""select oid, defaclrole, defaclnamespace, defaclobjtype, defaclacl from pg_catalog.pg_default_acl""".as(PgDefaultAclRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgDefaultAclFieldValue[_]])(implicit c: Connection): List[PgDefaultAclRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgDefaultAclFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgDefaultAclFieldValue.defaclrole(value) => NamedParameter("defaclrole", ParameterValue.from(value))
          case PgDefaultAclFieldValue.defaclnamespace(value) => NamedParameter("defaclnamespace", ParameterValue.from(value))
          case PgDefaultAclFieldValue.defaclobjtype(value) => NamedParameter("defaclobjtype", ParameterValue.from(value))
          case PgDefaultAclFieldValue.defaclacl(value) => NamedParameter("defaclacl", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_default_acl where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgDefaultAclRow.rowParser.*)
    }

  }
}
