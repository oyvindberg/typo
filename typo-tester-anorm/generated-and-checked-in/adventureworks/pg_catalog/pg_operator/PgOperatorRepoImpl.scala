/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_operator

import adventureworks.customtypes.TypoRegproc
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import anorm.ToStatement
import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

object PgOperatorRepoImpl extends PgOperatorRepo {
  override def delete(oid: PgOperatorId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_operator where "oid" = ${ParameterValue(oid, null, PgOperatorId.toStatement)}""".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[PgOperatorFields, PgOperatorRow] = {
    DeleteBuilder("pg_catalog.pg_operator", PgOperatorFields)
  }
  override def insert(unsaved: PgOperatorRow)(implicit c: Connection): PgOperatorRow = {
    SQL"""insert into pg_catalog.pg_operator("oid", "oprname", "oprnamespace", "oprowner", "oprkind", "oprcanmerge", "oprcanhash", "oprleft", "oprright", "oprresult", "oprcom", "oprnegate", "oprcode", "oprrest", "oprjoin")
          values (${ParameterValue(unsaved.oid, null, PgOperatorId.toStatement)}::oid, ${ParameterValue(unsaved.oprname, null, ToStatement.stringToStatement)}::name, ${ParameterValue(unsaved.oprnamespace, null, ToStatement.longToStatement)}::oid, ${ParameterValue(unsaved.oprowner, null, ToStatement.longToStatement)}::oid, ${ParameterValue(unsaved.oprkind, null, ToStatement.stringToStatement)}::char, ${ParameterValue(unsaved.oprcanmerge, null, ToStatement.booleanToStatement)}, ${ParameterValue(unsaved.oprcanhash, null, ToStatement.booleanToStatement)}, ${ParameterValue(unsaved.oprleft, null, ToStatement.longToStatement)}::oid, ${ParameterValue(unsaved.oprright, null, ToStatement.longToStatement)}::oid, ${ParameterValue(unsaved.oprresult, null, ToStatement.longToStatement)}::oid, ${ParameterValue(unsaved.oprcom, null, ToStatement.longToStatement)}::oid, ${ParameterValue(unsaved.oprnegate, null, ToStatement.longToStatement)}::oid, ${ParameterValue(unsaved.oprcode, null, TypoRegproc.toStatement)}::regproc, ${ParameterValue(unsaved.oprrest, null, TypoRegproc.toStatement)}::regproc, ${ParameterValue(unsaved.oprjoin, null, TypoRegproc.toStatement)}::regproc)
          returning "oid", "oprname", "oprnamespace", "oprowner", "oprkind", "oprcanmerge", "oprcanhash", "oprleft", "oprright", "oprresult", "oprcom", "oprnegate", "oprcode", "oprrest", "oprjoin"
       """
      .executeInsert(PgOperatorRow.rowParser(1).single)
    
  }
  override def select: SelectBuilder[PgOperatorFields, PgOperatorRow] = {
    SelectBuilderSql("pg_catalog.pg_operator", PgOperatorFields, PgOperatorRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PgOperatorRow] = {
    SQL"""select "oid", "oprname", "oprnamespace", "oprowner", "oprkind", "oprcanmerge", "oprcanhash", "oprleft", "oprright", "oprresult", "oprcom", "oprnegate", "oprcode", "oprrest", "oprjoin"
          from pg_catalog.pg_operator
       """.as(PgOperatorRow.rowParser(1).*)
  }
  override def selectById(oid: PgOperatorId)(implicit c: Connection): Option[PgOperatorRow] = {
    SQL"""select "oid", "oprname", "oprnamespace", "oprowner", "oprkind", "oprcanmerge", "oprcanhash", "oprleft", "oprright", "oprresult", "oprcom", "oprnegate", "oprcode", "oprrest", "oprjoin"
          from pg_catalog.pg_operator
          where "oid" = ${ParameterValue(oid, null, PgOperatorId.toStatement)}
       """.as(PgOperatorRow.rowParser(1).singleOpt)
  }
  override def selectByIds(oids: Array[PgOperatorId])(implicit c: Connection): List[PgOperatorRow] = {
    SQL"""select "oid", "oprname", "oprnamespace", "oprowner", "oprkind", "oprcanmerge", "oprcanhash", "oprleft", "oprright", "oprresult", "oprcom", "oprnegate", "oprcode", "oprrest", "oprjoin"
          from pg_catalog.pg_operator
          where "oid" = ANY(${oids})
       """.as(PgOperatorRow.rowParser(1).*)
    
  }
  override def selectByUnique(oprname: String, oprleft: /* oid */ Long, oprright: /* oid */ Long, oprnamespace: /* oid */ Long)(implicit c: Connection): Option[PgOperatorRow] = {
    SQL"""select "oprname", "oprleft", "oprright", "oprnamespace"
          from pg_catalog.pg_operator
          where "oprname" = ${ParameterValue(oprname, null, ToStatement.stringToStatement)} AND "oprleft" = ${ParameterValue(oprleft, null, ToStatement.longToStatement)} AND "oprright" = ${ParameterValue(oprright, null, ToStatement.longToStatement)} AND "oprnamespace" = ${ParameterValue(oprnamespace, null, ToStatement.longToStatement)}
       """.as(PgOperatorRow.rowParser(1).singleOpt)
    
  }
  override def update(row: PgOperatorRow)(implicit c: Connection): Boolean = {
    val oid = row.oid
    SQL"""update pg_catalog.pg_operator
          set "oprname" = ${ParameterValue(row.oprname, null, ToStatement.stringToStatement)}::name,
              "oprnamespace" = ${ParameterValue(row.oprnamespace, null, ToStatement.longToStatement)}::oid,
              "oprowner" = ${ParameterValue(row.oprowner, null, ToStatement.longToStatement)}::oid,
              "oprkind" = ${ParameterValue(row.oprkind, null, ToStatement.stringToStatement)}::char,
              "oprcanmerge" = ${ParameterValue(row.oprcanmerge, null, ToStatement.booleanToStatement)},
              "oprcanhash" = ${ParameterValue(row.oprcanhash, null, ToStatement.booleanToStatement)},
              "oprleft" = ${ParameterValue(row.oprleft, null, ToStatement.longToStatement)}::oid,
              "oprright" = ${ParameterValue(row.oprright, null, ToStatement.longToStatement)}::oid,
              "oprresult" = ${ParameterValue(row.oprresult, null, ToStatement.longToStatement)}::oid,
              "oprcom" = ${ParameterValue(row.oprcom, null, ToStatement.longToStatement)}::oid,
              "oprnegate" = ${ParameterValue(row.oprnegate, null, ToStatement.longToStatement)}::oid,
              "oprcode" = ${ParameterValue(row.oprcode, null, TypoRegproc.toStatement)}::regproc,
              "oprrest" = ${ParameterValue(row.oprrest, null, TypoRegproc.toStatement)}::regproc,
              "oprjoin" = ${ParameterValue(row.oprjoin, null, TypoRegproc.toStatement)}::regproc
          where "oid" = ${ParameterValue(oid, null, PgOperatorId.toStatement)}
       """.executeUpdate() > 0
  }
  override def update: UpdateBuilder[PgOperatorFields, PgOperatorRow] = {
    UpdateBuilder("pg_catalog.pg_operator", PgOperatorFields, PgOperatorRow.rowParser)
  }
  override def upsert(unsaved: PgOperatorRow)(implicit c: Connection): PgOperatorRow = {
    SQL"""insert into pg_catalog.pg_operator("oid", "oprname", "oprnamespace", "oprowner", "oprkind", "oprcanmerge", "oprcanhash", "oprleft", "oprright", "oprresult", "oprcom", "oprnegate", "oprcode", "oprrest", "oprjoin")
          values (
            ${ParameterValue(unsaved.oid, null, PgOperatorId.toStatement)}::oid,
            ${ParameterValue(unsaved.oprname, null, ToStatement.stringToStatement)}::name,
            ${ParameterValue(unsaved.oprnamespace, null, ToStatement.longToStatement)}::oid,
            ${ParameterValue(unsaved.oprowner, null, ToStatement.longToStatement)}::oid,
            ${ParameterValue(unsaved.oprkind, null, ToStatement.stringToStatement)}::char,
            ${ParameterValue(unsaved.oprcanmerge, null, ToStatement.booleanToStatement)},
            ${ParameterValue(unsaved.oprcanhash, null, ToStatement.booleanToStatement)},
            ${ParameterValue(unsaved.oprleft, null, ToStatement.longToStatement)}::oid,
            ${ParameterValue(unsaved.oprright, null, ToStatement.longToStatement)}::oid,
            ${ParameterValue(unsaved.oprresult, null, ToStatement.longToStatement)}::oid,
            ${ParameterValue(unsaved.oprcom, null, ToStatement.longToStatement)}::oid,
            ${ParameterValue(unsaved.oprnegate, null, ToStatement.longToStatement)}::oid,
            ${ParameterValue(unsaved.oprcode, null, TypoRegproc.toStatement)}::regproc,
            ${ParameterValue(unsaved.oprrest, null, TypoRegproc.toStatement)}::regproc,
            ${ParameterValue(unsaved.oprjoin, null, TypoRegproc.toStatement)}::regproc
          )
          on conflict ("oid")
          do update set
            "oprname" = EXCLUDED."oprname",
            "oprnamespace" = EXCLUDED."oprnamespace",
            "oprowner" = EXCLUDED."oprowner",
            "oprkind" = EXCLUDED."oprkind",
            "oprcanmerge" = EXCLUDED."oprcanmerge",
            "oprcanhash" = EXCLUDED."oprcanhash",
            "oprleft" = EXCLUDED."oprleft",
            "oprright" = EXCLUDED."oprright",
            "oprresult" = EXCLUDED."oprresult",
            "oprcom" = EXCLUDED."oprcom",
            "oprnegate" = EXCLUDED."oprnegate",
            "oprcode" = EXCLUDED."oprcode",
            "oprrest" = EXCLUDED."oprrest",
            "oprjoin" = EXCLUDED."oprjoin"
          returning "oid", "oprname", "oprnamespace", "oprowner", "oprkind", "oprcanmerge", "oprcanhash", "oprleft", "oprright", "oprresult", "oprcom", "oprnegate", "oprcode", "oprrest", "oprjoin"
       """
      .executeInsert(PgOperatorRow.rowParser(1).single)
    
  }
}