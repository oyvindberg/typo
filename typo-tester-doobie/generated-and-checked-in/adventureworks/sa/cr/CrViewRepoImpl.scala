/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package cr

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

class CrViewRepoImpl extends CrViewRepo {
  override def select: SelectBuilder[CrViewFields, CrViewRow] = {
    SelectBuilderSql("sa.cr", CrViewFields.structure, CrViewRow.read)
  }
  override def selectAll: Stream[ConnectionIO, CrViewRow] = {
    sql"""select "currencyrateid", "currencyratedate"::text, "fromcurrencycode", "tocurrencycode", "averagerate", "endofdayrate", "modifieddate"::text from sa.cr""".query(using CrViewRow.read).stream
  }
}