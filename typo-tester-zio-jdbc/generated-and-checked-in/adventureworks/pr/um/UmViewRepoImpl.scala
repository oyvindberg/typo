/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package um

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class UmViewRepoImpl extends UmViewRepo {
  override def select: SelectBuilder[UmViewFields, UmViewRow] = {
    SelectBuilderSql(""""pr"."um"""", UmViewFields.structure, UmViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, UmViewRow] = {
    sql"""select "id", "unitmeasurecode", "name", "modifieddate"::text from "pr"."um"""".query(using UmViewRow.jdbcDecoder).selectStream()
  }
}
