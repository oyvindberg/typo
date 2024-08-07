/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pch

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class PchViewRepoImpl extends PchViewRepo {
  override def select: SelectBuilder[PchViewFields, PchViewRow] = {
    SelectBuilderSql(""""pr"."pch"""", PchViewFields.structure, PchViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, PchViewRow] = {
    sql"""select "id", "productid", "startdate"::text, "enddate"::text, "standardcost", "modifieddate"::text from "pr"."pch"""".query(using PchViewRow.jdbcDecoder).selectStream()
  }
}
