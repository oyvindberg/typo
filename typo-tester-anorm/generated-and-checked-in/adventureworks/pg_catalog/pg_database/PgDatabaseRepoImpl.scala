/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_database

import adventureworks.customtypes.TypoAclItem
import adventureworks.customtypes.TypoXid
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import anorm.ToStatement
import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

object PgDatabaseRepoImpl extends PgDatabaseRepo {
  override def delete(oid: PgDatabaseId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_database where "oid" = ${ParameterValue(oid, null, PgDatabaseId.toStatement)}""".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[PgDatabaseFields, PgDatabaseRow] = {
    DeleteBuilder("pg_catalog.pg_database", PgDatabaseFields)
  }
  override def insert(unsaved: PgDatabaseRow)(implicit c: Connection): PgDatabaseRow = {
    SQL"""insert into pg_catalog.pg_database("oid", "datname", "datdba", "encoding", "datcollate", "datctype", "datistemplate", "datallowconn", "datconnlimit", "datlastsysoid", "datfrozenxid", "datminmxid", "dattablespace", "datacl")
          values (${ParameterValue(unsaved.oid, null, PgDatabaseId.toStatement)}::oid, ${ParameterValue(unsaved.datname, null, ToStatement.stringToStatement)}::name, ${ParameterValue(unsaved.datdba, null, ToStatement.longToStatement)}::oid, ${ParameterValue(unsaved.encoding, null, ToStatement.intToStatement)}::int4, ${ParameterValue(unsaved.datcollate, null, ToStatement.stringToStatement)}::name, ${ParameterValue(unsaved.datctype, null, ToStatement.stringToStatement)}::name, ${ParameterValue(unsaved.datistemplate, null, ToStatement.booleanToStatement)}, ${ParameterValue(unsaved.datallowconn, null, ToStatement.booleanToStatement)}, ${ParameterValue(unsaved.datconnlimit, null, ToStatement.intToStatement)}::int4, ${ParameterValue(unsaved.datlastsysoid, null, ToStatement.longToStatement)}::oid, ${ParameterValue(unsaved.datfrozenxid, null, TypoXid.toStatement)}::xid, ${ParameterValue(unsaved.datminmxid, null, TypoXid.toStatement)}::xid, ${ParameterValue(unsaved.dattablespace, null, ToStatement.longToStatement)}::oid, ${ParameterValue(unsaved.datacl, null, ToStatement.optionToStatement(TypoAclItem.arrayToStatement, adventureworks.arrayParameterMetaData(TypoAclItem.parameterMetadata)))}::_aclitem)
          returning "oid", "datname", "datdba", "encoding", "datcollate", "datctype", "datistemplate", "datallowconn", "datconnlimit", "datlastsysoid", "datfrozenxid", "datminmxid", "dattablespace", "datacl"
       """
      .executeInsert(PgDatabaseRow.rowParser(1).single)
    
  }
  override def select: SelectBuilder[PgDatabaseFields, PgDatabaseRow] = {
    SelectBuilderSql("pg_catalog.pg_database", PgDatabaseFields, PgDatabaseRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PgDatabaseRow] = {
    SQL"""select "oid", "datname", "datdba", "encoding", "datcollate", "datctype", "datistemplate", "datallowconn", "datconnlimit", "datlastsysoid", "datfrozenxid", "datminmxid", "dattablespace", "datacl"
          from pg_catalog.pg_database
       """.as(PgDatabaseRow.rowParser(1).*)
  }
  override def selectById(oid: PgDatabaseId)(implicit c: Connection): Option[PgDatabaseRow] = {
    SQL"""select "oid", "datname", "datdba", "encoding", "datcollate", "datctype", "datistemplate", "datallowconn", "datconnlimit", "datlastsysoid", "datfrozenxid", "datminmxid", "dattablespace", "datacl"
          from pg_catalog.pg_database
          where "oid" = ${ParameterValue(oid, null, PgDatabaseId.toStatement)}
       """.as(PgDatabaseRow.rowParser(1).singleOpt)
  }
  override def selectByIds(oids: Array[PgDatabaseId])(implicit c: Connection): List[PgDatabaseRow] = {
    SQL"""select "oid", "datname", "datdba", "encoding", "datcollate", "datctype", "datistemplate", "datallowconn", "datconnlimit", "datlastsysoid", "datfrozenxid", "datminmxid", "dattablespace", "datacl"
          from pg_catalog.pg_database
          where "oid" = ANY(${oids})
       """.as(PgDatabaseRow.rowParser(1).*)
    
  }
  override def selectByUnique(datname: String)(implicit c: Connection): Option[PgDatabaseRow] = {
    SQL"""select "datname"
          from pg_catalog.pg_database
          where "datname" = ${ParameterValue(datname, null, ToStatement.stringToStatement)}
       """.as(PgDatabaseRow.rowParser(1).singleOpt)
    
  }
  override def update(row: PgDatabaseRow)(implicit c: Connection): Boolean = {
    val oid = row.oid
    SQL"""update pg_catalog.pg_database
          set "datname" = ${ParameterValue(row.datname, null, ToStatement.stringToStatement)}::name,
              "datdba" = ${ParameterValue(row.datdba, null, ToStatement.longToStatement)}::oid,
              "encoding" = ${ParameterValue(row.encoding, null, ToStatement.intToStatement)}::int4,
              "datcollate" = ${ParameterValue(row.datcollate, null, ToStatement.stringToStatement)}::name,
              "datctype" = ${ParameterValue(row.datctype, null, ToStatement.stringToStatement)}::name,
              "datistemplate" = ${ParameterValue(row.datistemplate, null, ToStatement.booleanToStatement)},
              "datallowconn" = ${ParameterValue(row.datallowconn, null, ToStatement.booleanToStatement)},
              "datconnlimit" = ${ParameterValue(row.datconnlimit, null, ToStatement.intToStatement)}::int4,
              "datlastsysoid" = ${ParameterValue(row.datlastsysoid, null, ToStatement.longToStatement)}::oid,
              "datfrozenxid" = ${ParameterValue(row.datfrozenxid, null, TypoXid.toStatement)}::xid,
              "datminmxid" = ${ParameterValue(row.datminmxid, null, TypoXid.toStatement)}::xid,
              "dattablespace" = ${ParameterValue(row.dattablespace, null, ToStatement.longToStatement)}::oid,
              "datacl" = ${ParameterValue(row.datacl, null, ToStatement.optionToStatement(TypoAclItem.arrayToStatement, adventureworks.arrayParameterMetaData(TypoAclItem.parameterMetadata)))}::_aclitem
          where "oid" = ${ParameterValue(oid, null, PgDatabaseId.toStatement)}
       """.executeUpdate() > 0
  }
  override def update: UpdateBuilder[PgDatabaseFields, PgDatabaseRow] = {
    UpdateBuilder("pg_catalog.pg_database", PgDatabaseFields, PgDatabaseRow.rowParser)
  }
  override def upsert(unsaved: PgDatabaseRow)(implicit c: Connection): PgDatabaseRow = {
    SQL"""insert into pg_catalog.pg_database("oid", "datname", "datdba", "encoding", "datcollate", "datctype", "datistemplate", "datallowconn", "datconnlimit", "datlastsysoid", "datfrozenxid", "datminmxid", "dattablespace", "datacl")
          values (
            ${ParameterValue(unsaved.oid, null, PgDatabaseId.toStatement)}::oid,
            ${ParameterValue(unsaved.datname, null, ToStatement.stringToStatement)}::name,
            ${ParameterValue(unsaved.datdba, null, ToStatement.longToStatement)}::oid,
            ${ParameterValue(unsaved.encoding, null, ToStatement.intToStatement)}::int4,
            ${ParameterValue(unsaved.datcollate, null, ToStatement.stringToStatement)}::name,
            ${ParameterValue(unsaved.datctype, null, ToStatement.stringToStatement)}::name,
            ${ParameterValue(unsaved.datistemplate, null, ToStatement.booleanToStatement)},
            ${ParameterValue(unsaved.datallowconn, null, ToStatement.booleanToStatement)},
            ${ParameterValue(unsaved.datconnlimit, null, ToStatement.intToStatement)}::int4,
            ${ParameterValue(unsaved.datlastsysoid, null, ToStatement.longToStatement)}::oid,
            ${ParameterValue(unsaved.datfrozenxid, null, TypoXid.toStatement)}::xid,
            ${ParameterValue(unsaved.datminmxid, null, TypoXid.toStatement)}::xid,
            ${ParameterValue(unsaved.dattablespace, null, ToStatement.longToStatement)}::oid,
            ${ParameterValue(unsaved.datacl, null, ToStatement.optionToStatement(TypoAclItem.arrayToStatement, adventureworks.arrayParameterMetaData(TypoAclItem.parameterMetadata)))}::_aclitem
          )
          on conflict ("oid")
          do update set
            "datname" = EXCLUDED."datname",
            "datdba" = EXCLUDED."datdba",
            "encoding" = EXCLUDED."encoding",
            "datcollate" = EXCLUDED."datcollate",
            "datctype" = EXCLUDED."datctype",
            "datistemplate" = EXCLUDED."datistemplate",
            "datallowconn" = EXCLUDED."datallowconn",
            "datconnlimit" = EXCLUDED."datconnlimit",
            "datlastsysoid" = EXCLUDED."datlastsysoid",
            "datfrozenxid" = EXCLUDED."datfrozenxid",
            "datminmxid" = EXCLUDED."datminmxid",
            "dattablespace" = EXCLUDED."dattablespace",
            "datacl" = EXCLUDED."datacl"
          returning "oid", "datname", "datdba", "encoding", "datcollate", "datctype", "datistemplate", "datallowconn", "datconnlimit", "datlastsysoid", "datfrozenxid", "datminmxid", "dattablespace", "datacl"
       """
      .executeInsert(PgDatabaseRow.rowParser(1).single)
    
  }
}