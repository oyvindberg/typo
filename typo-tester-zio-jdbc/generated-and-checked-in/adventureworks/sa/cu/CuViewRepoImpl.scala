/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package cu

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class CuViewRepoImpl extends CuViewRepo {
  override def select: SelectBuilder[CuViewFields, CuViewRow] = {
    SelectBuilderSql(""""sa"."cu"""", CuViewFields.structure, CuViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, CuViewRow] = {
    sql"""select "id", "currencycode", "name", "modifieddate"::text from "sa"."cu"""".query(using CuViewRow.jdbcDecoder).selectStream()
  }
}
