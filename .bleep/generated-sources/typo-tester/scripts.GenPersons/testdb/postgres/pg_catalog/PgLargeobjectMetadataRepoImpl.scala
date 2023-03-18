package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgLargeobjectMetadataRepoImpl extends PgLargeobjectMetadataRepo {
  override def selectAll(implicit c: Connection): List[PgLargeobjectMetadataRow] = {
    SQL"""select oid, lomowner, lomacl from pg_catalog.pg_largeobject_metadata""".as(PgLargeobjectMetadataRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgLargeobjectMetadataFieldValue[_]])(implicit c: Connection): List[PgLargeobjectMetadataRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgLargeobjectMetadataFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgLargeobjectMetadataFieldValue.lomowner(value) => NamedParameter("lomowner", ParameterValue.from(value))
          case PgLargeobjectMetadataFieldValue.lomacl(value) => NamedParameter("lomacl", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_largeobject_metadata where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgLargeobjectMetadataRow.rowParser.*)
    }

  }
}
