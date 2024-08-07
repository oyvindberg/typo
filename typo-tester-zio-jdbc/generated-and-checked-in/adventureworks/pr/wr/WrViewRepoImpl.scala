/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package wr

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class WrViewRepoImpl extends WrViewRepo {
  override def select: SelectBuilder[WrViewFields, WrViewRow] = {
    SelectBuilderSql(""""pr"."wr"""", WrViewFields.structure, WrViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, WrViewRow] = {
    sql"""select "id", "workorderid", "productid", "operationsequence", "locationid", "scheduledstartdate"::text, "scheduledenddate"::text, "actualstartdate"::text, "actualenddate"::text, "actualresourcehrs", "plannedcost", "actualcost", "modifieddate"::text from "pr"."wr"""".query(using WrViewRow.jdbcDecoder).selectStream()
  }
}
