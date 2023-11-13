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
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.SqlFragment
import zio.jdbc.SqlFragment.Segment
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

object CountryregioncurrencyRepoImpl extends CountryregioncurrencyRepo {
  override def delete(compositeId: CountryregioncurrencyId): ZIO[ZConnection, Throwable, Boolean] = {
    sql"""delete from sales.countryregioncurrency where "countryregioncode" = ${Segment.paramSegment(compositeId.countryregioncode)(CountryregionId.setter)} AND "currencycode" = ${Segment.paramSegment(compositeId.currencycode)(CurrencyId.setter)}""".delete.map(_ > 0)
  }
  override def delete: DeleteBuilder[CountryregioncurrencyFields, CountryregioncurrencyRow] = {
    DeleteBuilder("sales.countryregioncurrency", CountryregioncurrencyFields)
  }
  override def insert(unsaved: CountryregioncurrencyRow): ZIO[ZConnection, Throwable, CountryregioncurrencyRow] = {
    sql"""insert into sales.countryregioncurrency("countryregioncode", "currencycode", "modifieddate")
          values (${Segment.paramSegment(unsaved.countryregioncode)(CountryregionId.setter)}, ${Segment.paramSegment(unsaved.currencycode)(CurrencyId.setter)}::bpchar, ${Segment.paramSegment(unsaved.modifieddate)(TypoLocalDateTime.setter)}::timestamp)
          returning "countryregioncode", "currencycode", "modifieddate"::text
       """.insertReturning(CountryregioncurrencyRow.jdbcDecoder).map(_.updatedKeys.head)
  }
  override def insert(unsaved: CountryregioncurrencyRowUnsaved): ZIO[ZConnection, Throwable, CountryregioncurrencyRow] = {
    val fs = List(
      Some((sql""""countryregioncode"""", sql"${Segment.paramSegment(unsaved.countryregioncode)(CountryregionId.setter)}")),
      Some((sql""""currencycode"""", sql"${Segment.paramSegment(unsaved.currencycode)(CurrencyId.setter)}::bpchar")),
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""modifieddate"""", sql"${Segment.paramSegment(value: TypoLocalDateTime)(TypoLocalDateTime.setter)}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into sales.countryregioncurrency default values
            returning "countryregioncode", "currencycode", "modifieddate"::text
         """
    } else {
      val names  = fs.map { case (n, _) => n }.mkFragment(SqlFragment(", "))
      val values = fs.map { case (_, f) => f }.mkFragment(SqlFragment(", "))
      sql"""insert into sales.countryregioncurrency($names) values ($values) returning "countryregioncode", "currencycode", "modifieddate"::text"""
    }
    q.insertReturning(CountryregioncurrencyRow.jdbcDecoder).map(_.updatedKeys.head)
    
  }
  override def select: SelectBuilder[CountryregioncurrencyFields, CountryregioncurrencyRow] = {
    SelectBuilderSql("sales.countryregioncurrency", CountryregioncurrencyFields, CountryregioncurrencyRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, CountryregioncurrencyRow] = {
    sql"""select "countryregioncode", "currencycode", "modifieddate"::text from sales.countryregioncurrency""".query(CountryregioncurrencyRow.jdbcDecoder).selectStream
  }
  override def selectById(compositeId: CountryregioncurrencyId): ZIO[ZConnection, Throwable, Option[CountryregioncurrencyRow]] = {
    sql"""select "countryregioncode", "currencycode", "modifieddate"::text from sales.countryregioncurrency where "countryregioncode" = ${Segment.paramSegment(compositeId.countryregioncode)(CountryregionId.setter)} AND "currencycode" = ${Segment.paramSegment(compositeId.currencycode)(CurrencyId.setter)}""".query(CountryregioncurrencyRow.jdbcDecoder).selectOne
  }
  override def update(row: CountryregioncurrencyRow): ZIO[ZConnection, Throwable, Boolean] = {
    val compositeId = row.compositeId
    sql"""update sales.countryregioncurrency
          set "modifieddate" = ${Segment.paramSegment(row.modifieddate)(TypoLocalDateTime.setter)}::timestamp
          where "countryregioncode" = ${Segment.paramSegment(compositeId.countryregioncode)(CountryregionId.setter)} AND "currencycode" = ${Segment.paramSegment(compositeId.currencycode)(CurrencyId.setter)}""".update.map(_ > 0)
  }
  override def update: UpdateBuilder[CountryregioncurrencyFields, CountryregioncurrencyRow] = {
    UpdateBuilder("sales.countryregioncurrency", CountryregioncurrencyFields, CountryregioncurrencyRow.jdbcDecoder)
  }
  override def upsert(unsaved: CountryregioncurrencyRow): ZIO[ZConnection, Throwable, UpdateResult[CountryregioncurrencyRow]] = {
    sql"""insert into sales.countryregioncurrency("countryregioncode", "currencycode", "modifieddate")
          values (
            ${Segment.paramSegment(unsaved.countryregioncode)(CountryregionId.setter)},
            ${Segment.paramSegment(unsaved.currencycode)(CurrencyId.setter)}::bpchar,
            ${Segment.paramSegment(unsaved.modifieddate)(TypoLocalDateTime.setter)}::timestamp
          )
          on conflict ("countryregioncode", "currencycode")
          do update set
            "modifieddate" = EXCLUDED."modifieddate"
          returning "countryregioncode", "currencycode", "modifieddate"::text""".insertReturning(CountryregioncurrencyRow.jdbcDecoder)
  }
}