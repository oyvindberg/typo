package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgAttributeRepoImpl extends PgAttributeRepo {
  override def selectAll(implicit c: Connection): List[PgAttributeRow] = {
    SQL"""select attrelid, attname, atttypid, attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attalign, attstorage, attcompression, attnotnull, atthasdef, atthasmissing, attidentity, attgenerated, attisdropped, attislocal, attinhcount, attcollation, attacl, attoptions, attfdwoptions, attmissingval from pg_catalog.pg_attribute""".as(PgAttributeRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgAttributeFieldValue[_]])(implicit c: Connection): List[PgAttributeRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgAttributeFieldValue.attrelid(value) => NamedParameter("attrelid", ParameterValue.from(value))
          case PgAttributeFieldValue.attname(value) => NamedParameter("attname", ParameterValue.from(value))
          case PgAttributeFieldValue.atttypid(value) => NamedParameter("atttypid", ParameterValue.from(value))
          case PgAttributeFieldValue.attstattarget(value) => NamedParameter("attstattarget", ParameterValue.from(value))
          case PgAttributeFieldValue.attlen(value) => NamedParameter("attlen", ParameterValue.from(value))
          case PgAttributeFieldValue.attnum(value) => NamedParameter("attnum", ParameterValue.from(value))
          case PgAttributeFieldValue.attndims(value) => NamedParameter("attndims", ParameterValue.from(value))
          case PgAttributeFieldValue.attcacheoff(value) => NamedParameter("attcacheoff", ParameterValue.from(value))
          case PgAttributeFieldValue.atttypmod(value) => NamedParameter("atttypmod", ParameterValue.from(value))
          case PgAttributeFieldValue.attbyval(value) => NamedParameter("attbyval", ParameterValue.from(value))
          case PgAttributeFieldValue.attalign(value) => NamedParameter("attalign", ParameterValue.from(value))
          case PgAttributeFieldValue.attstorage(value) => NamedParameter("attstorage", ParameterValue.from(value))
          case PgAttributeFieldValue.attcompression(value) => NamedParameter("attcompression", ParameterValue.from(value))
          case PgAttributeFieldValue.attnotnull(value) => NamedParameter("attnotnull", ParameterValue.from(value))
          case PgAttributeFieldValue.atthasdef(value) => NamedParameter("atthasdef", ParameterValue.from(value))
          case PgAttributeFieldValue.atthasmissing(value) => NamedParameter("atthasmissing", ParameterValue.from(value))
          case PgAttributeFieldValue.attidentity(value) => NamedParameter("attidentity", ParameterValue.from(value))
          case PgAttributeFieldValue.attgenerated(value) => NamedParameter("attgenerated", ParameterValue.from(value))
          case PgAttributeFieldValue.attisdropped(value) => NamedParameter("attisdropped", ParameterValue.from(value))
          case PgAttributeFieldValue.attislocal(value) => NamedParameter("attislocal", ParameterValue.from(value))
          case PgAttributeFieldValue.attinhcount(value) => NamedParameter("attinhcount", ParameterValue.from(value))
          case PgAttributeFieldValue.attcollation(value) => NamedParameter("attcollation", ParameterValue.from(value))
          case PgAttributeFieldValue.attacl(value) => NamedParameter("attacl", ParameterValue.from(value))
          case PgAttributeFieldValue.attoptions(value) => NamedParameter("attoptions", ParameterValue.from(value))
          case PgAttributeFieldValue.attfdwoptions(value) => NamedParameter("attfdwoptions", ParameterValue.from(value))
          case PgAttributeFieldValue.attmissingval(value) => NamedParameter("attmissingval", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_attribute where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgAttributeRow.rowParser.*)
    }

  }
}
