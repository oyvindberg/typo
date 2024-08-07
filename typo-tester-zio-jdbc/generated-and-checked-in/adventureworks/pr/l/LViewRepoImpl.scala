/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package l

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class LViewRepoImpl extends LViewRepo {
  override def select: SelectBuilder[LViewFields, LViewRow] = {
    SelectBuilderSql(""""pr"."l"""", LViewFields.structure, LViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, LViewRow] = {
    sql"""select "id", "locationid", "name", "costrate", "availability", "modifieddate"::text from "pr"."l"""".query(using LViewRow.jdbcDecoder).selectStream()
  }
}
