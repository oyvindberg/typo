/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sop

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class SopViewRepoImpl extends SopViewRepo {
  override def select: SelectBuilder[SopViewFields, SopViewRow] = {
    SelectBuilderSql("sa.sop", SopViewFields.structure, SopViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, SopViewRow] = {
    sql"""select "id", "specialofferid", "productid", "rowguid", "modifieddate"::text from sa.sop""".query(using SopViewRow.jdbcDecoder).selectStream()
  }
}