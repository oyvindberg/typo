/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package pcc

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

class PccViewRepoImpl extends PccViewRepo {
  override def select: SelectBuilder[PccViewFields, PccViewRow] = {
    SelectBuilderSql("sa.pcc", PccViewFields.structure, PccViewRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PccViewRow] = {
    SQL"""select "id", "businessentityid", "creditcardid", "modifieddate"::text
          from sa.pcc
       """.as(PccViewRow.rowParser(1).*)
  }
}