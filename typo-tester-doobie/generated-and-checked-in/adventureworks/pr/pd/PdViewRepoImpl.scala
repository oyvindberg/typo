/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pd

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

class PdViewRepoImpl extends PdViewRepo {
  override def select: SelectBuilder[PdViewFields, PdViewRow] = {
    SelectBuilderSql("pr.pd", PdViewFields.structure, PdViewRow.read)
  }
  override def selectAll: Stream[ConnectionIO, PdViewRow] = {
    sql"""select "id", "productdescriptionid", "description", "rowguid", "modifieddate"::text from pr.pd""".query(using PdViewRow.read).stream
  }
}