package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgTypeRepoImpl extends PgTypeRepo {
  override def selectAll(implicit c: Connection): List[PgTypeRow] = {
    SQL"""select oid, typname, typnamespace, typowner, typlen, typbyval, typtype, typcategory, typispreferred, typisdefined, typdelim, typrelid, typsubscript, typelem, typarray, typinput, typoutput, typreceive, typsend, typmodin, typmodout, typanalyze, typalign, typstorage, typnotnull, typbasetype, typtypmod, typndims, typcollation, typdefaultbin, typdefault, typacl from pg_catalog.pg_type""".as(PgTypeRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgTypeFieldValue[_]])(implicit c: Connection): List[PgTypeRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTypeFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgTypeFieldValue.typname(value) => NamedParameter("typname", ParameterValue.from(value))
          case PgTypeFieldValue.typnamespace(value) => NamedParameter("typnamespace", ParameterValue.from(value))
          case PgTypeFieldValue.typowner(value) => NamedParameter("typowner", ParameterValue.from(value))
          case PgTypeFieldValue.typlen(value) => NamedParameter("typlen", ParameterValue.from(value))
          case PgTypeFieldValue.typbyval(value) => NamedParameter("typbyval", ParameterValue.from(value))
          case PgTypeFieldValue.typtype(value) => NamedParameter("typtype", ParameterValue.from(value))
          case PgTypeFieldValue.typcategory(value) => NamedParameter("typcategory", ParameterValue.from(value))
          case PgTypeFieldValue.typispreferred(value) => NamedParameter("typispreferred", ParameterValue.from(value))
          case PgTypeFieldValue.typisdefined(value) => NamedParameter("typisdefined", ParameterValue.from(value))
          case PgTypeFieldValue.typdelim(value) => NamedParameter("typdelim", ParameterValue.from(value))
          case PgTypeFieldValue.typrelid(value) => NamedParameter("typrelid", ParameterValue.from(value))
          case PgTypeFieldValue.typsubscript(value) => NamedParameter("typsubscript", ParameterValue.from(value))
          case PgTypeFieldValue.typelem(value) => NamedParameter("typelem", ParameterValue.from(value))
          case PgTypeFieldValue.typarray(value) => NamedParameter("typarray", ParameterValue.from(value))
          case PgTypeFieldValue.typinput(value) => NamedParameter("typinput", ParameterValue.from(value))
          case PgTypeFieldValue.typoutput(value) => NamedParameter("typoutput", ParameterValue.from(value))
          case PgTypeFieldValue.typreceive(value) => NamedParameter("typreceive", ParameterValue.from(value))
          case PgTypeFieldValue.typsend(value) => NamedParameter("typsend", ParameterValue.from(value))
          case PgTypeFieldValue.typmodin(value) => NamedParameter("typmodin", ParameterValue.from(value))
          case PgTypeFieldValue.typmodout(value) => NamedParameter("typmodout", ParameterValue.from(value))
          case PgTypeFieldValue.typanalyze(value) => NamedParameter("typanalyze", ParameterValue.from(value))
          case PgTypeFieldValue.typalign(value) => NamedParameter("typalign", ParameterValue.from(value))
          case PgTypeFieldValue.typstorage(value) => NamedParameter("typstorage", ParameterValue.from(value))
          case PgTypeFieldValue.typnotnull(value) => NamedParameter("typnotnull", ParameterValue.from(value))
          case PgTypeFieldValue.typbasetype(value) => NamedParameter("typbasetype", ParameterValue.from(value))
          case PgTypeFieldValue.typtypmod(value) => NamedParameter("typtypmod", ParameterValue.from(value))
          case PgTypeFieldValue.typndims(value) => NamedParameter("typndims", ParameterValue.from(value))
          case PgTypeFieldValue.typcollation(value) => NamedParameter("typcollation", ParameterValue.from(value))
          case PgTypeFieldValue.typdefaultbin(value) => NamedParameter("typdefaultbin", ParameterValue.from(value))
          case PgTypeFieldValue.typdefault(value) => NamedParameter("typdefault", ParameterValue.from(value))
          case PgTypeFieldValue.typacl(value) => NamedParameter("typacl", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_type where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgTypeRow.rowParser.*)
    }

  }
}
