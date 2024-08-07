/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pc

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class PcViewRepoImpl extends PcViewRepo {
  override def select: SelectBuilder[PcViewFields, PcViewRow] = {
    SelectBuilderSql(""""pr"."pc"""", PcViewFields.structure, PcViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, PcViewRow] = {
    sql"""select "id", "productcategoryid", "name", "rowguid", "modifieddate"::text from "pr"."pc"""".query(using PcViewRow.jdbcDecoder).selectStream()
  }
}
