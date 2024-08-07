/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package spqh

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class SpqhViewRepoImpl extends SpqhViewRepo {
  override def select: SelectBuilder[SpqhViewFields, SpqhViewRow] = {
    SelectBuilderSql(""""sa"."spqh"""", SpqhViewFields.structure, SpqhViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, SpqhViewRow] = {
    sql"""select "id", "businessentityid", "quotadate"::text, "salesquota", "rowguid", "modifieddate"::text from "sa"."spqh"""".query(using SpqhViewRow.jdbcDecoder).selectStream()
  }
}
