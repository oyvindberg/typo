/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package so

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

object SoViewRepoImpl extends SoViewRepo {
  override def select: SelectBuilder[SoViewFields, SoViewRow] = {
    SelectBuilderSql("sa.so", SoViewFields, SoViewRow.read)
  }
  override def selectAll: Stream[ConnectionIO, SoViewRow] = {
    sql"""select "id", specialofferid, description, discountpct, "type", category, startdate::text, enddate::text, minqty, maxqty, rowguid, modifieddate::text from sa.so""".query(SoViewRow.read).stream
  }
}