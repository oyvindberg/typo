/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_seclabel

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream

object PgSeclabelRepoImpl extends PgSeclabelRepo {
  override def delete(compositeId: PgSeclabelId): ConnectionIO[Boolean] = {
    sql"delete from pg_catalog.pg_seclabel where objoid = ${compositeId.objoid} AND classoid = ${compositeId.classoid} AND objsubid = ${compositeId.objsubid} AND provider = ${compositeId.provider}".update.run.map(_ > 0)
  }
  override def insert(unsaved: PgSeclabelRow): ConnectionIO[PgSeclabelRow] = {
    sql"""insert into pg_catalog.pg_seclabel(objoid, classoid, objsubid, provider, "label")
          values (${unsaved.objoid}::oid, ${unsaved.classoid}::oid, ${unsaved.objsubid}::int4, ${unsaved.provider}, ${unsaved.label})
          returning objoid, classoid, objsubid, provider, "label"
       """.query[PgSeclabelRow].unique
  }
  override def selectAll: Stream[ConnectionIO, PgSeclabelRow] = {
    sql"""select objoid, classoid, objsubid, provider, "label" from pg_catalog.pg_seclabel""".query[PgSeclabelRow].stream
  }
  override def selectById(compositeId: PgSeclabelId): ConnectionIO[Option[PgSeclabelRow]] = {
    sql"""select objoid, classoid, objsubid, provider, "label" from pg_catalog.pg_seclabel where objoid = ${compositeId.objoid} AND classoid = ${compositeId.classoid} AND objsubid = ${compositeId.objsubid} AND provider = ${compositeId.provider}""".query[PgSeclabelRow].option
  }
  override def update(row: PgSeclabelRow): ConnectionIO[Boolean] = {
    val compositeId = row.compositeId
    sql"""update pg_catalog.pg_seclabel
          set "label" = ${row.label}
          where objoid = ${compositeId.objoid} AND classoid = ${compositeId.classoid} AND objsubid = ${compositeId.objsubid} AND provider = ${compositeId.provider}
       """
      .update
      .run
      .map(_ > 0)
  }
  override def upsert(unsaved: PgSeclabelRow): ConnectionIO[PgSeclabelRow] = {
    sql"""insert into pg_catalog.pg_seclabel(objoid, classoid, objsubid, provider, "label")
          values (
            ${unsaved.objoid}::oid,
            ${unsaved.classoid}::oid,
            ${unsaved.objsubid}::int4,
            ${unsaved.provider},
            ${unsaved.label}
          )
          on conflict (objoid, classoid, objsubid, provider)
          do update set
            "label" = EXCLUDED."label"
          returning objoid, classoid, objsubid, provider, "label"
       """.query[PgSeclabelRow].unique
  }
}