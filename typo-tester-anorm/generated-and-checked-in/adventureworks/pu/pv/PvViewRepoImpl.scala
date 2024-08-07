/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pu
package pv

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

class PvViewRepoImpl extends PvViewRepo {
  override def select: SelectBuilder[PvViewFields, PvViewRow] = {
    SelectBuilderSql(""""pu"."pv"""", PvViewFields.structure, PvViewRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PvViewRow] = {
    SQL"""select "id", "productid", "businessentityid", "averageleadtime", "standardprice", "lastreceiptcost", "lastreceiptdate"::text, "minorderqty", "maxorderqty", "onorderqty", "unitmeasurecode", "modifieddate"::text
          from "pu"."pv"
       """.as(PvViewRow.rowParser(1).*)
  }
}
