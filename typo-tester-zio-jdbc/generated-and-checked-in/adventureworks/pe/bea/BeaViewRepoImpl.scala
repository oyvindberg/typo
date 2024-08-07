/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package bea

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class BeaViewRepoImpl extends BeaViewRepo {
  override def select: SelectBuilder[BeaViewFields, BeaViewRow] = {
    SelectBuilderSql(""""pe"."bea"""", BeaViewFields.structure, BeaViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, BeaViewRow] = {
    sql"""select "id", "businessentityid", "addressid", "addresstypeid", "rowguid", "modifieddate"::text from "pe"."bea"""".query(using BeaViewRow.jdbcDecoder).selectStream()
  }
}
