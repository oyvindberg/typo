/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package countryregioncurrency

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.countryregion.CountryregionId
import adventureworks.sales.currency.CurrencyId
import doobie.free.connection.ConnectionIO
import doobie.postgres.syntax.FragmentOps
import doobie.syntax.SqlInterpolator.SingleFragment.fromWrite
import doobie.syntax.string.toSqlInterpolator
import doobie.util.Write
import doobie.util.fragment.Fragment
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class CountryregioncurrencyRepoImpl extends CountryregioncurrencyRepo {
  override def delete(compositeId: CountryregioncurrencyId): ConnectionIO[Boolean] = {
    sql"""delete from sales.countryregioncurrency where "countryregioncode" = ${fromWrite(compositeId.countryregioncode)(Write.fromPut(CountryregionId.put))} AND "currencycode" = ${fromWrite(compositeId.currencycode)(Write.fromPut(CurrencyId.put))}""".update.run.map(_ > 0)
  }
  override def delete: DeleteBuilder[CountryregioncurrencyFields, CountryregioncurrencyRow] = {
    DeleteBuilder("sales.countryregioncurrency", CountryregioncurrencyFields)
  }
  override def insert(unsaved: CountryregioncurrencyRow): ConnectionIO[CountryregioncurrencyRow] = {
    sql"""insert into sales.countryregioncurrency("countryregioncode", "currencycode", "modifieddate")
          values (${fromWrite(unsaved.countryregioncode)(Write.fromPut(CountryregionId.put))}, ${fromWrite(unsaved.currencycode)(Write.fromPut(CurrencyId.put))}::bpchar, ${fromWrite(unsaved.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp)
          returning "countryregioncode", "currencycode", "modifieddate"::text
       """.query(CountryregioncurrencyRow.read).unique
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, CountryregioncurrencyRow], batchSize: Int): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY sales.countryregioncurrency("countryregioncode", "currencycode", "modifieddate") FROM STDIN""").copyIn(unsaved, batchSize)(CountryregioncurrencyRow.text)
  }
  override def insert(unsaved: CountryregioncurrencyRowUnsaved): ConnectionIO[CountryregioncurrencyRow] = {
    val fs = List(
      Some((Fragment.const(s""""countryregioncode""""), fr"${fromWrite(unsaved.countryregioncode)(Write.fromPut(CountryregionId.put))}")),
      Some((Fragment.const(s""""currencycode""""), fr"${fromWrite(unsaved.currencycode)(Write.fromPut(CurrencyId.put))}::bpchar")),
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s""""modifieddate""""), fr"${fromWrite(value: TypoLocalDateTime)(Write.fromPut(TypoLocalDateTime.put))}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into sales.countryregioncurrency default values
            returning "countryregioncode", "currencycode", "modifieddate"::text
         """
    } else {
      val CommaSeparate = Fragment.FragmentMonoid.intercalate(fr", ")
      sql"""insert into sales.countryregioncurrency(${CommaSeparate.combineAllOption(fs.map { case (n, _) => n }).get})
            values (${CommaSeparate.combineAllOption(fs.map { case (_, f) => f }).get})
            returning "countryregioncode", "currencycode", "modifieddate"::text
         """
    }
    q.query(CountryregioncurrencyRow.read).unique
    
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, CountryregioncurrencyRowUnsaved], batchSize: Int): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY sales.countryregioncurrency("countryregioncode", "currencycode", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""").copyIn(unsaved, batchSize)(CountryregioncurrencyRowUnsaved.text)
  }
  override def select: SelectBuilder[CountryregioncurrencyFields, CountryregioncurrencyRow] = {
    SelectBuilderSql("sales.countryregioncurrency", CountryregioncurrencyFields, CountryregioncurrencyRow.read)
  }
  override def selectAll: Stream[ConnectionIO, CountryregioncurrencyRow] = {
    sql"""select "countryregioncode", "currencycode", "modifieddate"::text from sales.countryregioncurrency""".query(CountryregioncurrencyRow.read).stream
  }
  override def selectById(compositeId: CountryregioncurrencyId): ConnectionIO[Option[CountryregioncurrencyRow]] = {
    sql"""select "countryregioncode", "currencycode", "modifieddate"::text from sales.countryregioncurrency where "countryregioncode" = ${fromWrite(compositeId.countryregioncode)(Write.fromPut(CountryregionId.put))} AND "currencycode" = ${fromWrite(compositeId.currencycode)(Write.fromPut(CurrencyId.put))}""".query(CountryregioncurrencyRow.read).option
  }
  override def update(row: CountryregioncurrencyRow): ConnectionIO[Boolean] = {
    val compositeId = row.compositeId
    sql"""update sales.countryregioncurrency
          set "modifieddate" = ${fromWrite(row.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp
          where "countryregioncode" = ${fromWrite(compositeId.countryregioncode)(Write.fromPut(CountryregionId.put))} AND "currencycode" = ${fromWrite(compositeId.currencycode)(Write.fromPut(CurrencyId.put))}"""
      .update
      .run
      .map(_ > 0)
  }
  override def update: UpdateBuilder[CountryregioncurrencyFields, CountryregioncurrencyRow] = {
    UpdateBuilder("sales.countryregioncurrency", CountryregioncurrencyFields, CountryregioncurrencyRow.read)
  }
  override def upsert(unsaved: CountryregioncurrencyRow): ConnectionIO[CountryregioncurrencyRow] = {
    sql"""insert into sales.countryregioncurrency("countryregioncode", "currencycode", "modifieddate")
          values (
            ${fromWrite(unsaved.countryregioncode)(Write.fromPut(CountryregionId.put))},
            ${fromWrite(unsaved.currencycode)(Write.fromPut(CurrencyId.put))}::bpchar,
            ${fromWrite(unsaved.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp
          )
          on conflict ("countryregioncode", "currencycode")
          do update set
            "modifieddate" = EXCLUDED."modifieddate"
          returning "countryregioncode", "currencycode", "modifieddate"::text
       """.query(CountryregioncurrencyRow.read).unique
  }
}