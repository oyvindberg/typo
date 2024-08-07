/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pm

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

class PmViewRepoImpl extends PmViewRepo {
  override def select: SelectBuilder[PmViewFields, PmViewRow] = {
    SelectBuilderSql(""""pr"."pm"""", PmViewFields.structure, PmViewRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PmViewRow] = {
    SQL"""select "id", "productmodelid", "name", "catalogdescription", "instructions", "rowguid", "modifieddate"::text
          from "pr"."pm"
       """.as(PmViewRow.rowParser(1).*)
  }
}
