/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package anorm
package adventureworks

import java.sql.Connection
import resource.managed

object ExecuteReturningSyntax {
  /* add executeReturning to anorm. it needs to be inside the package, because everything is hidden */
  implicit class Ops(batchSql: BatchSql) {
    def executeReturning[T](parser: ResultSetParser[T])(implicit c: Connection): T =
      managed(batchSql.getFilledStatement(c, getGeneratedKeys = true))(using StatementResource, statementClassTag).acquireAndGet { ps =>
        ps.executeBatch()
        Sql
          .asTry(
            parser,
            managed(ps.getGeneratedKeys)(using ResultSetResource, resultSetClassTag),
            onFirstRow = false,
            ColumnAliaser.empty
          )
          .get
      }
  }
}
