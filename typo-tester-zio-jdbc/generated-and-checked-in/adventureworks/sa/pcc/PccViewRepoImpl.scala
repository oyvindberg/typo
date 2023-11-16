/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package pcc

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class PccViewRepoImpl extends PccViewRepo {
  override def select: SelectBuilder[PccViewFields, PccViewRow] = {
    SelectBuilderSql("sa.pcc", PccViewFields, PccViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, PccViewRow] = {
    sql"""select "id", "businessentityid", "creditcardid", "modifieddate"::text from sa.pcc""".query(PccViewRow.jdbcDecoder).selectStream
  }
}