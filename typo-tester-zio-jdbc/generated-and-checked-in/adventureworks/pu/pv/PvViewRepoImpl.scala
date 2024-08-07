/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pu
package pv

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class PvViewRepoImpl extends PvViewRepo {
  override def select: SelectBuilder[PvViewFields, PvViewRow] = {
    SelectBuilderSql(""""pu"."pv"""", PvViewFields.structure, PvViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, PvViewRow] = {
    sql"""select "id", "productid", "businessentityid", "averageleadtime", "standardprice", "lastreceiptcost", "lastreceiptdate"::text, "minorderqty", "maxorderqty", "onorderqty", "unitmeasurecode", "modifieddate"::text from "pu"."pv"""".query(using PvViewRow.jdbcDecoder).selectStream()
  }
}
