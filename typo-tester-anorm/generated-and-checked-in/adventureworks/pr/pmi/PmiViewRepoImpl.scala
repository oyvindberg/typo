/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pmi

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

class PmiViewRepoImpl extends PmiViewRepo {
  override def select: SelectBuilder[PmiViewFields, PmiViewRow] = {
    SelectBuilderSql("pr.pmi", PmiViewFields.structure, PmiViewRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PmiViewRow] = {
    SQL"""select "productmodelid", "illustrationid", "modifieddate"::text
          from pr.pmi
       """.as(PmiViewRow.rowParser(1).*)
  }
}