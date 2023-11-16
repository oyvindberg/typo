/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salespersonquotahistory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import doobie.free.connection.ConnectionIO
import doobie.postgres.syntax.FragmentOps
import doobie.syntax.SqlInterpolator.SingleFragment.fromWrite
import doobie.syntax.string.toSqlInterpolator
import doobie.util.Write
import doobie.util.fragment.Fragment
import doobie.util.meta.Meta
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class SalespersonquotahistoryRepoImpl extends SalespersonquotahistoryRepo {
  override def delete(compositeId: SalespersonquotahistoryId): ConnectionIO[Boolean] = {
    sql"""delete from sales.salespersonquotahistory where "businessentityid" = ${fromWrite(compositeId.businessentityid)(Write.fromPut(BusinessentityId.put))} AND "quotadate" = ${fromWrite(compositeId.quotadate)(Write.fromPut(TypoLocalDateTime.put))}""".update.run.map(_ > 0)
  }
  override def delete: DeleteBuilder[SalespersonquotahistoryFields, SalespersonquotahistoryRow] = {
    DeleteBuilder("sales.salespersonquotahistory", SalespersonquotahistoryFields)
  }
  override def insert(unsaved: SalespersonquotahistoryRow): ConnectionIO[SalespersonquotahistoryRow] = {
    sql"""insert into sales.salespersonquotahistory("businessentityid", "quotadate", "salesquota", "rowguid", "modifieddate")
          values (${fromWrite(unsaved.businessentityid)(Write.fromPut(BusinessentityId.put))}::int4, ${fromWrite(unsaved.quotadate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp, ${fromWrite(unsaved.salesquota)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric, ${fromWrite(unsaved.rowguid)(Write.fromPut(TypoUUID.put))}::uuid, ${fromWrite(unsaved.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp)
          returning "businessentityid", "quotadate"::text, "salesquota", "rowguid", "modifieddate"::text
       """.query(SalespersonquotahistoryRow.read).unique
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, SalespersonquotahistoryRow], batchSize: Int): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY sales.salespersonquotahistory("businessentityid", "quotadate", "salesquota", "rowguid", "modifieddate") FROM STDIN""").copyIn(unsaved, batchSize)(SalespersonquotahistoryRow.text)
  }
  override def insert(unsaved: SalespersonquotahistoryRowUnsaved): ConnectionIO[SalespersonquotahistoryRow] = {
    val fs = List(
      Some((Fragment.const(s""""businessentityid""""), fr"${fromWrite(unsaved.businessentityid)(Write.fromPut(BusinessentityId.put))}::int4")),
      Some((Fragment.const(s""""quotadate""""), fr"${fromWrite(unsaved.quotadate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp")),
      Some((Fragment.const(s""""salesquota""""), fr"${fromWrite(unsaved.salesquota)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric")),
      unsaved.rowguid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s""""rowguid""""), fr"${fromWrite(value: TypoUUID)(Write.fromPut(TypoUUID.put))}::uuid"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s""""modifieddate""""), fr"${fromWrite(value: TypoLocalDateTime)(Write.fromPut(TypoLocalDateTime.put))}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into sales.salespersonquotahistory default values
            returning "businessentityid", "quotadate"::text, "salesquota", "rowguid", "modifieddate"::text
         """
    } else {
      val CommaSeparate = Fragment.FragmentMonoid.intercalate(fr", ")
      sql"""insert into sales.salespersonquotahistory(${CommaSeparate.combineAllOption(fs.map { case (n, _) => n }).get})
            values (${CommaSeparate.combineAllOption(fs.map { case (_, f) => f }).get})
            returning "businessentityid", "quotadate"::text, "salesquota", "rowguid", "modifieddate"::text
         """
    }
    q.query(SalespersonquotahistoryRow.read).unique
    
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, SalespersonquotahistoryRowUnsaved], batchSize: Int): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY sales.salespersonquotahistory("businessentityid", "quotadate", "salesquota", "rowguid", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""").copyIn(unsaved, batchSize)(SalespersonquotahistoryRowUnsaved.text)
  }
  override def select: SelectBuilder[SalespersonquotahistoryFields, SalespersonquotahistoryRow] = {
    SelectBuilderSql("sales.salespersonquotahistory", SalespersonquotahistoryFields, SalespersonquotahistoryRow.read)
  }
  override def selectAll: Stream[ConnectionIO, SalespersonquotahistoryRow] = {
    sql"""select "businessentityid", "quotadate"::text, "salesquota", "rowguid", "modifieddate"::text from sales.salespersonquotahistory""".query(SalespersonquotahistoryRow.read).stream
  }
  override def selectById(compositeId: SalespersonquotahistoryId): ConnectionIO[Option[SalespersonquotahistoryRow]] = {
    sql"""select "businessentityid", "quotadate"::text, "salesquota", "rowguid", "modifieddate"::text from sales.salespersonquotahistory where "businessentityid" = ${fromWrite(compositeId.businessentityid)(Write.fromPut(BusinessentityId.put))} AND "quotadate" = ${fromWrite(compositeId.quotadate)(Write.fromPut(TypoLocalDateTime.put))}""".query(SalespersonquotahistoryRow.read).option
  }
  override def update(row: SalespersonquotahistoryRow): ConnectionIO[Boolean] = {
    val compositeId = row.compositeId
    sql"""update sales.salespersonquotahistory
          set "salesquota" = ${fromWrite(row.salesquota)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric,
              "rowguid" = ${fromWrite(row.rowguid)(Write.fromPut(TypoUUID.put))}::uuid,
              "modifieddate" = ${fromWrite(row.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp
          where "businessentityid" = ${fromWrite(compositeId.businessentityid)(Write.fromPut(BusinessentityId.put))} AND "quotadate" = ${fromWrite(compositeId.quotadate)(Write.fromPut(TypoLocalDateTime.put))}"""
      .update
      .run
      .map(_ > 0)
  }
  override def update: UpdateBuilder[SalespersonquotahistoryFields, SalespersonquotahistoryRow] = {
    UpdateBuilder("sales.salespersonquotahistory", SalespersonquotahistoryFields, SalespersonquotahistoryRow.read)
  }
  override def upsert(unsaved: SalespersonquotahistoryRow): ConnectionIO[SalespersonquotahistoryRow] = {
    sql"""insert into sales.salespersonquotahistory("businessentityid", "quotadate", "salesquota", "rowguid", "modifieddate")
          values (
            ${fromWrite(unsaved.businessentityid)(Write.fromPut(BusinessentityId.put))}::int4,
            ${fromWrite(unsaved.quotadate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp,
            ${fromWrite(unsaved.salesquota)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric,
            ${fromWrite(unsaved.rowguid)(Write.fromPut(TypoUUID.put))}::uuid,
            ${fromWrite(unsaved.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp
          )
          on conflict ("businessentityid", "quotadate")
          do update set
            "salesquota" = EXCLUDED."salesquota",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "businessentityid", "quotadate"::text, "salesquota", "rowguid", "modifieddate"::text
       """.query(SalespersonquotahistoryRow.read).unique
  }
}