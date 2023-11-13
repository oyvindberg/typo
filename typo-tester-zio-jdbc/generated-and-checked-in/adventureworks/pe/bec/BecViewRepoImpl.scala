/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package bec

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

object BecViewRepoImpl extends BecViewRepo {
  override def select: SelectBuilder[BecViewFields, BecViewRow] = {
    SelectBuilderSql("pe.bec", BecViewFields, BecViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, BecViewRow] = {
    sql"""select "id", "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text from pe.bec""".query(BecViewRow.jdbcDecoder).selectStream
  }
}