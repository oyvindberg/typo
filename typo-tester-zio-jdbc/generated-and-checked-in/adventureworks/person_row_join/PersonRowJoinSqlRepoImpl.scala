/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person_row_join

import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class PersonRowJoinSqlRepoImpl extends PersonRowJoinSqlRepo {
  override def apply(): ZStream[ZConnection, Throwable, PersonRowJoinSqlRow] = {
    val sql =
      sql"""SELECT s.businessentityid,
                   (select array_agg(ROW(a.emailaddress, a.rowguid)) from person.emailaddress a where a.businessentityid = s.businessentityid) as email,
                   (select ARRAY[ROW(a.emailaddress, a.rowguid)] from person.emailaddress a where a.businessentityid = s.businessentityid) as emails
            FROM sales.salesperson s
      """
    sql.query(using PersonRowJoinSqlRow.jdbcDecoder).selectStream()
  }
}