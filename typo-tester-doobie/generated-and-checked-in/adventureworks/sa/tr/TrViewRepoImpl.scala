/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package tr

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

class TrViewRepoImpl extends TrViewRepo {
  override def select: SelectBuilder[TrViewFields, TrViewRow] = {
    SelectBuilderSql(""""sa"."tr"""", TrViewFields.structure, TrViewRow.read)
  }
  override def selectAll: Stream[ConnectionIO, TrViewRow] = {
    sql"""select "id", "salestaxrateid", "stateprovinceid", "taxtype", "taxrate", "name", "rowguid", "modifieddate"::text from "sa"."tr"""".query(using TrViewRow.read).stream
  }
}
