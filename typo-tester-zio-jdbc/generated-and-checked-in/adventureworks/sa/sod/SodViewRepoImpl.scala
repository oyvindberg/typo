/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sod

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

object SodViewRepoImpl extends SodViewRepo {
  override def select: SelectBuilder[SodViewFields, SodViewRow] = {
    SelectBuilderSql("sa.sod", SodViewFields, SodViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, SodViewRow] = {
    sql"""select "id", "salesorderid", "salesorderdetailid", "carriertrackingnumber", "orderqty", "productid", "specialofferid", "unitprice", "unitpricediscount", "rowguid", "modifieddate"::text from sa.sod""".query(SodViewRow.jdbcDecoder).selectStream
  }
}