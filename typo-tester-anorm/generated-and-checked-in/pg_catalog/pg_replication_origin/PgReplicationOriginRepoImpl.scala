/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_replication_origin

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

object PgReplicationOriginRepoImpl extends PgReplicationOriginRepo {
  override def delete(roident: PgReplicationOriginId)(implicit c: Connection): Boolean = {
    SQL"delete from pg_catalog.pg_replication_origin where roident = $roident".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[PgReplicationOriginFields, PgReplicationOriginRow] = {
    DeleteBuilder("pg_catalog.pg_replication_origin", PgReplicationOriginFields)
  }
  override def insert(unsaved: PgReplicationOriginRow)(implicit c: Connection): PgReplicationOriginRow = {
    SQL"""insert into pg_catalog.pg_replication_origin(roident, roname)
          values (${unsaved.roident}::oid, ${unsaved.roname})
          returning roident, roname
       """
      .executeInsert(PgReplicationOriginRow.rowParser(1).single)
    
  }
  override def select: SelectBuilder[PgReplicationOriginFields, PgReplicationOriginRow] = {
    SelectBuilderSql("pg_catalog.pg_replication_origin", PgReplicationOriginFields, PgReplicationOriginRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PgReplicationOriginRow] = {
    SQL"""select roident, roname
          from pg_catalog.pg_replication_origin
       """.as(PgReplicationOriginRow.rowParser(1).*)
  }
  override def selectById(roident: PgReplicationOriginId)(implicit c: Connection): Option[PgReplicationOriginRow] = {
    SQL"""select roident, roname
          from pg_catalog.pg_replication_origin
          where roident = $roident
       """.as(PgReplicationOriginRow.rowParser(1).singleOpt)
  }
  override def selectByIds(roidents: Array[PgReplicationOriginId])(implicit c: Connection): List[PgReplicationOriginRow] = {
    SQL"""select roident, roname
          from pg_catalog.pg_replication_origin
          where roident = ANY($roidents)
       """.as(PgReplicationOriginRow.rowParser(1).*)
    
  }
  override def update(row: PgReplicationOriginRow)(implicit c: Connection): Boolean = {
    val roident = row.roident
    SQL"""update pg_catalog.pg_replication_origin
          set roname = ${row.roname}
          where roident = $roident
       """.executeUpdate() > 0
  }
  override def update: UpdateBuilder[PgReplicationOriginFields, PgReplicationOriginRow] = {
    UpdateBuilder("pg_catalog.pg_replication_origin", PgReplicationOriginFields, PgReplicationOriginRow.rowParser)
  }
  override def upsert(unsaved: PgReplicationOriginRow)(implicit c: Connection): PgReplicationOriginRow = {
    SQL"""insert into pg_catalog.pg_replication_origin(roident, roname)
          values (
            ${unsaved.roident}::oid,
            ${unsaved.roname}
          )
          on conflict (roident)
          do update set
            roname = EXCLUDED.roname
          returning roident, roname
       """
      .executeInsert(PgReplicationOriginRow.rowParser(1).single)
    
  }
}