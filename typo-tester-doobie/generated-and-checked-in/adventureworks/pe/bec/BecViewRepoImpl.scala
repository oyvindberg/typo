/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package bec

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

class BecViewRepoImpl extends BecViewRepo {
  override def select: SelectBuilder[BecViewFields, BecViewRow] = {
    SelectBuilderSql("pe.bec", BecViewFields.structure, BecViewRow.read)
  }
  override def selectAll: Stream[ConnectionIO, BecViewRow] = {
    sql"""select "id", "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text from pe.bec""".query(using BecViewRow.read).stream
  }
}