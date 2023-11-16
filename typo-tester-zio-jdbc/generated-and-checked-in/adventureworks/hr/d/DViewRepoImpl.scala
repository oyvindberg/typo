/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package hr
package d

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class DViewRepoImpl extends DViewRepo {
  override def select: SelectBuilder[DViewFields, DViewRow] = {
    SelectBuilderSql("hr.d", DViewFields, DViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, DViewRow] = {
    sql"""select "id", "departmentid", "name", "groupname", "modifieddate"::text from hr.d""".query(DViewRow.jdbcDecoder).selectStream
  }
}