/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_statistic_ext_data

import adventureworks.customtypes.TypoUnknownPgDependencies
import adventureworks.customtypes.TypoUnknownPgMcvList
import adventureworks.customtypes.TypoUnknownPgNdistinct
import adventureworks.customtypes.TypoUnknownPgStatistic
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import anorm.ToStatement
import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

object PgStatisticExtDataRepoImpl extends PgStatisticExtDataRepo {
  override def delete(stxoid: PgStatisticExtDataId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_statistic_ext_data where "stxoid" = ${ParameterValue(stxoid, null, PgStatisticExtDataId.toStatement)}""".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[PgStatisticExtDataFields, PgStatisticExtDataRow] = {
    DeleteBuilder("pg_catalog.pg_statistic_ext_data", PgStatisticExtDataFields)
  }
  override def insert(unsaved: PgStatisticExtDataRow)(implicit c: Connection): PgStatisticExtDataRow = {
    SQL"""insert into pg_catalog.pg_statistic_ext_data("stxoid", "stxdndistinct", "stxddependencies", "stxdmcv", "stxdexpr")
          values (${ParameterValue(unsaved.stxoid, null, PgStatisticExtDataId.toStatement)}::oid, ${ParameterValue(unsaved.stxdndistinct, null, ToStatement.optionToStatement(TypoUnknownPgNdistinct.toStatement, TypoUnknownPgNdistinct.parameterMetadata))}::pg_ndistinct, ${ParameterValue(unsaved.stxddependencies, null, ToStatement.optionToStatement(TypoUnknownPgDependencies.toStatement, TypoUnknownPgDependencies.parameterMetadata))}::pg_dependencies, ${ParameterValue(unsaved.stxdmcv, null, ToStatement.optionToStatement(TypoUnknownPgMcvList.toStatement, TypoUnknownPgMcvList.parameterMetadata))}::pg_mcv_list, ${ParameterValue(unsaved.stxdexpr, null, ToStatement.optionToStatement(TypoUnknownPgStatistic.arrayToStatement, adventureworks.arrayParameterMetaData(TypoUnknownPgStatistic.parameterMetadata)))}::_pg_statistic)
          returning "stxoid", "stxdndistinct"::text, "stxddependencies"::text, "stxdmcv"::text, "stxdexpr"::text[]
       """
      .executeInsert(PgStatisticExtDataRow.rowParser(1).single)
    
  }
  override def select: SelectBuilder[PgStatisticExtDataFields, PgStatisticExtDataRow] = {
    SelectBuilderSql("pg_catalog.pg_statistic_ext_data", PgStatisticExtDataFields, PgStatisticExtDataRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PgStatisticExtDataRow] = {
    SQL"""select "stxoid", "stxdndistinct"::text, "stxddependencies"::text, "stxdmcv"::text, "stxdexpr"::text[]
          from pg_catalog.pg_statistic_ext_data
       """.as(PgStatisticExtDataRow.rowParser(1).*)
  }
  override def selectById(stxoid: PgStatisticExtDataId)(implicit c: Connection): Option[PgStatisticExtDataRow] = {
    SQL"""select "stxoid", "stxdndistinct"::text, "stxddependencies"::text, "stxdmcv"::text, "stxdexpr"::text[]
          from pg_catalog.pg_statistic_ext_data
          where "stxoid" = ${ParameterValue(stxoid, null, PgStatisticExtDataId.toStatement)}
       """.as(PgStatisticExtDataRow.rowParser(1).singleOpt)
  }
  override def selectByIds(stxoids: Array[PgStatisticExtDataId])(implicit c: Connection): List[PgStatisticExtDataRow] = {
    SQL"""select "stxoid", "stxdndistinct"::text, "stxddependencies"::text, "stxdmcv"::text, "stxdexpr"::text[]
          from pg_catalog.pg_statistic_ext_data
          where "stxoid" = ANY(${stxoids})
       """.as(PgStatisticExtDataRow.rowParser(1).*)
    
  }
  override def update(row: PgStatisticExtDataRow)(implicit c: Connection): Boolean = {
    val stxoid = row.stxoid
    SQL"""update pg_catalog.pg_statistic_ext_data
          set "stxdndistinct" = ${ParameterValue(row.stxdndistinct, null, ToStatement.optionToStatement(TypoUnknownPgNdistinct.toStatement, TypoUnknownPgNdistinct.parameterMetadata))}::pg_ndistinct,
              "stxddependencies" = ${ParameterValue(row.stxddependencies, null, ToStatement.optionToStatement(TypoUnknownPgDependencies.toStatement, TypoUnknownPgDependencies.parameterMetadata))}::pg_dependencies,
              "stxdmcv" = ${ParameterValue(row.stxdmcv, null, ToStatement.optionToStatement(TypoUnknownPgMcvList.toStatement, TypoUnknownPgMcvList.parameterMetadata))}::pg_mcv_list,
              "stxdexpr" = ${ParameterValue(row.stxdexpr, null, ToStatement.optionToStatement(TypoUnknownPgStatistic.arrayToStatement, adventureworks.arrayParameterMetaData(TypoUnknownPgStatistic.parameterMetadata)))}::_pg_statistic
          where "stxoid" = ${ParameterValue(stxoid, null, PgStatisticExtDataId.toStatement)}
       """.executeUpdate() > 0
  }
  override def update: UpdateBuilder[PgStatisticExtDataFields, PgStatisticExtDataRow] = {
    UpdateBuilder("pg_catalog.pg_statistic_ext_data", PgStatisticExtDataFields, PgStatisticExtDataRow.rowParser)
  }
  override def upsert(unsaved: PgStatisticExtDataRow)(implicit c: Connection): PgStatisticExtDataRow = {
    SQL"""insert into pg_catalog.pg_statistic_ext_data("stxoid", "stxdndistinct", "stxddependencies", "stxdmcv", "stxdexpr")
          values (
            ${ParameterValue(unsaved.stxoid, null, PgStatisticExtDataId.toStatement)}::oid,
            ${ParameterValue(unsaved.stxdndistinct, null, ToStatement.optionToStatement(TypoUnknownPgNdistinct.toStatement, TypoUnknownPgNdistinct.parameterMetadata))}::pg_ndistinct,
            ${ParameterValue(unsaved.stxddependencies, null, ToStatement.optionToStatement(TypoUnknownPgDependencies.toStatement, TypoUnknownPgDependencies.parameterMetadata))}::pg_dependencies,
            ${ParameterValue(unsaved.stxdmcv, null, ToStatement.optionToStatement(TypoUnknownPgMcvList.toStatement, TypoUnknownPgMcvList.parameterMetadata))}::pg_mcv_list,
            ${ParameterValue(unsaved.stxdexpr, null, ToStatement.optionToStatement(TypoUnknownPgStatistic.arrayToStatement, adventureworks.arrayParameterMetaData(TypoUnknownPgStatistic.parameterMetadata)))}::_pg_statistic
          )
          on conflict ("stxoid")
          do update set
            "stxdndistinct" = EXCLUDED."stxdndistinct",
            "stxddependencies" = EXCLUDED."stxddependencies",
            "stxdmcv" = EXCLUDED."stxdmcv",
            "stxdexpr" = EXCLUDED."stxdexpr"
          returning "stxoid", "stxdndistinct"::text, "stxddependencies"::text, "stxdmcv"::text, "stxdexpr"::text[]
       """
      .executeInsert(PgStatisticExtDataRow.rowParser(1).single)
    
  }
}