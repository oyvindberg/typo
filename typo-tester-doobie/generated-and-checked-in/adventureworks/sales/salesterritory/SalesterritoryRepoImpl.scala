/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.countryregion.CountryregionId
import adventureworks.public.Name
import cats.instances.list.catsStdInstancesForList
import doobie.free.connection.ConnectionIO
import doobie.postgres.syntax.FragmentOps
import doobie.syntax.SqlInterpolator.SingleFragment.fromWrite
import doobie.syntax.string.toSqlInterpolator
import doobie.util.Write
import doobie.util.fragment.Fragment
import doobie.util.meta.Meta
import doobie.util.update.Update
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class SalesterritoryRepoImpl extends SalesterritoryRepo {
  override def delete: DeleteBuilder[SalesterritoryFields, SalesterritoryRow] = {
    DeleteBuilder(""""sales"."salesterritory"""", SalesterritoryFields.structure)
  }
  override def deleteById(territoryid: SalesterritoryId): ConnectionIO[Boolean] = {
    sql"""delete from "sales"."salesterritory" where "territoryid" = ${fromWrite(territoryid)(new Write.Single(SalesterritoryId.put))}""".update.run.map(_ > 0)
  }
  override def deleteByIds(territoryids: Array[SalesterritoryId]): ConnectionIO[Int] = {
    sql"""delete from "sales"."salesterritory" where "territoryid" = ANY(${territoryids})""".update.run
  }
  override def insert(unsaved: SalesterritoryRow): ConnectionIO[SalesterritoryRow] = {
    sql"""insert into "sales"."salesterritory"("territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate")
          values (${fromWrite(unsaved.territoryid)(new Write.Single(SalesterritoryId.put))}::int4, ${fromWrite(unsaved.name)(new Write.Single(Name.put))}::varchar, ${fromWrite(unsaved.countryregioncode)(new Write.Single(CountryregionId.put))}, ${fromWrite(unsaved.group)(new Write.Single(Meta.StringMeta.put))}, ${fromWrite(unsaved.salesytd)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric, ${fromWrite(unsaved.saleslastyear)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric, ${fromWrite(unsaved.costytd)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric, ${fromWrite(unsaved.costlastyear)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric, ${fromWrite(unsaved.rowguid)(new Write.Single(TypoUUID.put))}::uuid, ${fromWrite(unsaved.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp)
          returning "territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate"::text
       """.query(using SalesterritoryRow.read).unique
  }
  override def insert(unsaved: SalesterritoryRowUnsaved): ConnectionIO[SalesterritoryRow] = {
    val fs = List(
      Some((Fragment.const0(s""""name""""), fr"${fromWrite(unsaved.name)(new Write.Single(Name.put))}::varchar")),
      Some((Fragment.const0(s""""countryregioncode""""), fr"${fromWrite(unsaved.countryregioncode)(new Write.Single(CountryregionId.put))}")),
      Some((Fragment.const0(s""""group""""), fr"${fromWrite(unsaved.group)(new Write.Single(Meta.StringMeta.put))}")),
      unsaved.territoryid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""territoryid""""), fr"${fromWrite(value: SalesterritoryId)(new Write.Single(SalesterritoryId.put))}::int4"))
      },
      unsaved.salesytd match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""salesytd""""), fr"${fromWrite(value: BigDecimal)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric"))
      },
      unsaved.saleslastyear match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""saleslastyear""""), fr"${fromWrite(value: BigDecimal)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric"))
      },
      unsaved.costytd match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""costytd""""), fr"${fromWrite(value: BigDecimal)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric"))
      },
      unsaved.costlastyear match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""costlastyear""""), fr"${fromWrite(value: BigDecimal)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric"))
      },
      unsaved.rowguid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""rowguid""""), fr"${fromWrite(value: TypoUUID)(new Write.Single(TypoUUID.put))}::uuid"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""modifieddate""""), fr"${fromWrite(value: TypoLocalDateTime)(new Write.Single(TypoLocalDateTime.put))}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into "sales"."salesterritory" default values
            returning "territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate"::text
         """
    } else {
      val CommaSeparate = Fragment.FragmentMonoid.intercalate(fr", ")
      sql"""insert into "sales"."salesterritory"(${CommaSeparate.combineAllOption(fs.map { case (n, _) => n }).get})
            values (${CommaSeparate.combineAllOption(fs.map { case (_, f) => f }).get})
            returning "territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate"::text
         """
    }
    q.query(using SalesterritoryRow.read).unique
    
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, SalesterritoryRow], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "sales"."salesterritory"("territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate") FROM STDIN""").copyIn(unsaved, batchSize)(using SalesterritoryRow.text)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, SalesterritoryRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "sales"."salesterritory"("name", "countryregioncode", "group", "territoryid", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""").copyIn(unsaved, batchSize)(using SalesterritoryRowUnsaved.text)
  }
  override def select: SelectBuilder[SalesterritoryFields, SalesterritoryRow] = {
    SelectBuilderSql(""""sales"."salesterritory"""", SalesterritoryFields.structure, SalesterritoryRow.read)
  }
  override def selectAll: Stream[ConnectionIO, SalesterritoryRow] = {
    sql"""select "territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate"::text from "sales"."salesterritory"""".query(using SalesterritoryRow.read).stream
  }
  override def selectById(territoryid: SalesterritoryId): ConnectionIO[Option[SalesterritoryRow]] = {
    sql"""select "territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate"::text from "sales"."salesterritory" where "territoryid" = ${fromWrite(territoryid)(new Write.Single(SalesterritoryId.put))}""".query(using SalesterritoryRow.read).option
  }
  override def selectByIds(territoryids: Array[SalesterritoryId]): Stream[ConnectionIO, SalesterritoryRow] = {
    sql"""select "territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate"::text from "sales"."salesterritory" where "territoryid" = ANY(${territoryids})""".query(using SalesterritoryRow.read).stream
  }
  override def selectByIdsTracked(territoryids: Array[SalesterritoryId]): ConnectionIO[Map[SalesterritoryId, SalesterritoryRow]] = {
    selectByIds(territoryids).compile.toList.map { rows =>
      val byId = rows.view.map(x => (x.territoryid, x)).toMap
      territoryids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[SalesterritoryFields, SalesterritoryRow] = {
    UpdateBuilder(""""sales"."salesterritory"""", SalesterritoryFields.structure, SalesterritoryRow.read)
  }
  override def update(row: SalesterritoryRow): ConnectionIO[Option[SalesterritoryRow]] = {
    val territoryid = row.territoryid
    sql"""update "sales"."salesterritory"
          set "name" = ${fromWrite(row.name)(new Write.Single(Name.put))}::varchar,
              "countryregioncode" = ${fromWrite(row.countryregioncode)(new Write.Single(CountryregionId.put))},
              "group" = ${fromWrite(row.group)(new Write.Single(Meta.StringMeta.put))},
              "salesytd" = ${fromWrite(row.salesytd)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric,
              "saleslastyear" = ${fromWrite(row.saleslastyear)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric,
              "costytd" = ${fromWrite(row.costytd)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric,
              "costlastyear" = ${fromWrite(row.costlastyear)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric,
              "rowguid" = ${fromWrite(row.rowguid)(new Write.Single(TypoUUID.put))}::uuid,
              "modifieddate" = ${fromWrite(row.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp
          where "territoryid" = ${fromWrite(territoryid)(new Write.Single(SalesterritoryId.put))}
          returning "territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate"::text""".query(using SalesterritoryRow.read).option
  }
  override def upsert(unsaved: SalesterritoryRow): ConnectionIO[SalesterritoryRow] = {
    sql"""insert into "sales"."salesterritory"("territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate")
          values (
            ${fromWrite(unsaved.territoryid)(new Write.Single(SalesterritoryId.put))}::int4,
            ${fromWrite(unsaved.name)(new Write.Single(Name.put))}::varchar,
            ${fromWrite(unsaved.countryregioncode)(new Write.Single(CountryregionId.put))},
            ${fromWrite(unsaved.group)(new Write.Single(Meta.StringMeta.put))},
            ${fromWrite(unsaved.salesytd)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric,
            ${fromWrite(unsaved.saleslastyear)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric,
            ${fromWrite(unsaved.costytd)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric,
            ${fromWrite(unsaved.costlastyear)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric,
            ${fromWrite(unsaved.rowguid)(new Write.Single(TypoUUID.put))}::uuid,
            ${fromWrite(unsaved.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp
          )
          on conflict ("territoryid")
          do update set
            "name" = EXCLUDED."name",
            "countryregioncode" = EXCLUDED."countryregioncode",
            "group" = EXCLUDED."group",
            "salesytd" = EXCLUDED."salesytd",
            "saleslastyear" = EXCLUDED."saleslastyear",
            "costytd" = EXCLUDED."costytd",
            "costlastyear" = EXCLUDED."costlastyear",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate"::text
       """.query(using SalesterritoryRow.read).unique
  }
  override def upsertBatch(unsaved: List[SalesterritoryRow]): Stream[ConnectionIO, SalesterritoryRow] = {
    Update[SalesterritoryRow](
      s"""insert into "sales"."salesterritory"("territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate")
          values (?::int4,?::varchar,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?::uuid,?::timestamp)
          on conflict ("territoryid")
          do update set
            "name" = EXCLUDED."name",
            "countryregioncode" = EXCLUDED."countryregioncode",
            "group" = EXCLUDED."group",
            "salesytd" = EXCLUDED."salesytd",
            "saleslastyear" = EXCLUDED."saleslastyear",
            "costytd" = EXCLUDED."costytd",
            "costlastyear" = EXCLUDED."costlastyear",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate"::text"""
    )(using SalesterritoryRow.write)
    .updateManyWithGeneratedKeys[SalesterritoryRow]("territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate")(unsaved)(using catsStdInstancesForList, SalesterritoryRow.read)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Stream[ConnectionIO, SalesterritoryRow], batchSize: Int = 10000): ConnectionIO[Int] = {
    for {
      _ <- sql"""create temporary table salesterritory_TEMP (like "sales"."salesterritory") on commit drop""".update.run
      _ <- new FragmentOps(sql"""copy salesterritory_TEMP("territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate") from stdin""").copyIn(unsaved, batchSize)(using SalesterritoryRow.text)
      res <- sql"""insert into "sales"."salesterritory"("territoryid", "name", "countryregioncode", "group", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate")
                   select * from salesterritory_TEMP
                   on conflict ("territoryid")
                   do update set
                     "name" = EXCLUDED."name",
                     "countryregioncode" = EXCLUDED."countryregioncode",
                     "group" = EXCLUDED."group",
                     "salesytd" = EXCLUDED."salesytd",
                     "saleslastyear" = EXCLUDED."saleslastyear",
                     "costytd" = EXCLUDED."costytd",
                     "costlastyear" = EXCLUDED."costlastyear",
                     "rowguid" = EXCLUDED."rowguid",
                     "modifieddate" = EXCLUDED."modifieddate"
                   ;
                   drop table salesterritory_TEMP;""".update.run
    } yield res
  }
}
