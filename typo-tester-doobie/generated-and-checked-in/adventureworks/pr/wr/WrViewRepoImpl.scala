/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package wr

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

object WrViewRepoImpl extends WrViewRepo {
  override def select: SelectBuilder[WrViewFields, WrViewRow] = {
    SelectBuilderSql("pr.wr", WrViewFields, WrViewRow.read)
  }
  override def selectAll: Stream[ConnectionIO, WrViewRow] = {
    sql"""select "id", "workorderid", "productid", "operationsequence", "locationid", "scheduledstartdate"::text, "scheduledenddate"::text, "actualstartdate"::text, "actualenddate"::text, "actualresourcehrs", "plannedcost", "actualcost", "modifieddate"::text from pr.wr""".query(WrViewRow.read).stream
  }
}