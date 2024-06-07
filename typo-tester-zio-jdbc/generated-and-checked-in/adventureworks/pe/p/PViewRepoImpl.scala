/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package p

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class PViewRepoImpl extends PViewRepo {
  override def select: SelectBuilder[PViewFields, PViewRow] = {
    SelectBuilderSql("pe.p", PViewFields.structure, PViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, PViewRow] = {
    sql"""select "id", "businessentityid", "persontype", "namestyle", "title", "firstname", "middlename", "lastname", "suffix", "emailpromotion", "additionalcontactinfo", "demographics", "rowguid", "modifieddate"::text from pe.p""".query(using PViewRow.jdbcDecoder).selectStream()
  }
}