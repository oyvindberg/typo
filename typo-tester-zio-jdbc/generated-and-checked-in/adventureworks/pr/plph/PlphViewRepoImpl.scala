/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package plph

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class PlphViewRepoImpl extends PlphViewRepo {
  override def select: SelectBuilder[PlphViewFields, PlphViewRow] = {
    SelectBuilderSql(""""pr"."plph"""", PlphViewFields.structure, PlphViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, PlphViewRow] = {
    sql"""select "id", "productid", "startdate"::text, "enddate"::text, "listprice", "modifieddate"::text from "pr"."plph"""".query(using PlphViewRow.jdbcDecoder).selectStream()
  }
}
