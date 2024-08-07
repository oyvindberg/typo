/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package s

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class SViewRepoImpl extends SViewRepo {
  override def select: SelectBuilder[SViewFields, SViewRow] = {
    SelectBuilderSql(""""sa"."s"""", SViewFields.structure, SViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, SViewRow] = {
    sql"""select "id", "businessentityid", "name", "salespersonid", "demographics", "rowguid", "modifieddate"::text from "sa"."s"""".query(using SViewRow.jdbcDecoder).selectStream()
  }
}
