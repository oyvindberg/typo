/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package billofmaterials

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.production.product.ProductId
import adventureworks.production.unitmeasure.UnitmeasureId
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

class BillofmaterialsRepoImpl extends BillofmaterialsRepo {
  override def delete: DeleteBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    DeleteBuilder(""""production"."billofmaterials"""", BillofmaterialsFields.structure)
  }
  override def deleteById(billofmaterialsid: Int): ConnectionIO[Boolean] = {
    sql"""delete from "production"."billofmaterials" where "billofmaterialsid" = ${fromWrite(billofmaterialsid)(new Write.Single(Meta.IntMeta.put))}""".update.run.map(_ > 0)
  }
  override def deleteByIds(billofmaterialsids: Array[Int]): ConnectionIO[Int] = {
    sql"""delete from "production"."billofmaterials" where "billofmaterialsid" = ANY(${billofmaterialsids})""".update.run
  }
  override def insert(unsaved: BillofmaterialsRow): ConnectionIO[BillofmaterialsRow] = {
    sql"""insert into "production"."billofmaterials"("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate")
          values (${fromWrite(unsaved.billofmaterialsid)(new Write.Single(Meta.IntMeta.put))}::int4, ${fromWrite(unsaved.productassemblyid)(new Write.SingleOpt(ProductId.put))}::int4, ${fromWrite(unsaved.componentid)(new Write.Single(ProductId.put))}::int4, ${fromWrite(unsaved.startdate)(new Write.Single(TypoLocalDateTime.put))}::timestamp, ${fromWrite(unsaved.enddate)(new Write.SingleOpt(TypoLocalDateTime.put))}::timestamp, ${fromWrite(unsaved.unitmeasurecode)(new Write.Single(UnitmeasureId.put))}::bpchar, ${fromWrite(unsaved.bomlevel)(new Write.Single(TypoShort.put))}::int2, ${fromWrite(unsaved.perassemblyqty)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric, ${fromWrite(unsaved.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp)
          returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
       """.query(using BillofmaterialsRow.read).unique
  }
  override def insert(unsaved: BillofmaterialsRowUnsaved): ConnectionIO[BillofmaterialsRow] = {
    val fs = List(
      Some((Fragment.const0(s""""productassemblyid""""), fr"${fromWrite(unsaved.productassemblyid)(new Write.SingleOpt(ProductId.put))}::int4")),
      Some((Fragment.const0(s""""componentid""""), fr"${fromWrite(unsaved.componentid)(new Write.Single(ProductId.put))}::int4")),
      Some((Fragment.const0(s""""enddate""""), fr"${fromWrite(unsaved.enddate)(new Write.SingleOpt(TypoLocalDateTime.put))}::timestamp")),
      Some((Fragment.const0(s""""unitmeasurecode""""), fr"${fromWrite(unsaved.unitmeasurecode)(new Write.Single(UnitmeasureId.put))}::bpchar")),
      Some((Fragment.const0(s""""bomlevel""""), fr"${fromWrite(unsaved.bomlevel)(new Write.Single(TypoShort.put))}::int2")),
      unsaved.billofmaterialsid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""billofmaterialsid""""), fr"${fromWrite(value: Int)(new Write.Single(Meta.IntMeta.put))}::int4"))
      },
      unsaved.startdate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""startdate""""), fr"${fromWrite(value: TypoLocalDateTime)(new Write.Single(TypoLocalDateTime.put))}::timestamp"))
      },
      unsaved.perassemblyqty match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""perassemblyqty""""), fr"${fromWrite(value: BigDecimal)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""modifieddate""""), fr"${fromWrite(value: TypoLocalDateTime)(new Write.Single(TypoLocalDateTime.put))}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into "production"."billofmaterials" default values
            returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
         """
    } else {
      val CommaSeparate = Fragment.FragmentMonoid.intercalate(fr", ")
      sql"""insert into "production"."billofmaterials"(${CommaSeparate.combineAllOption(fs.map { case (n, _) => n }).get})
            values (${CommaSeparate.combineAllOption(fs.map { case (_, f) => f }).get})
            returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
         """
    }
    q.query(using BillofmaterialsRow.read).unique
    
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, BillofmaterialsRow], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "production"."billofmaterials"("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate") FROM STDIN""").copyIn(unsaved, batchSize)(using BillofmaterialsRow.text)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, BillofmaterialsRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "production"."billofmaterials"("productassemblyid", "componentid", "enddate", "unitmeasurecode", "bomlevel", "billofmaterialsid", "startdate", "perassemblyqty", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""").copyIn(unsaved, batchSize)(using BillofmaterialsRowUnsaved.text)
  }
  override def select: SelectBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    SelectBuilderSql(""""production"."billofmaterials"""", BillofmaterialsFields.structure, BillofmaterialsRow.read)
  }
  override def selectAll: Stream[ConnectionIO, BillofmaterialsRow] = {
    sql"""select "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text from "production"."billofmaterials"""".query(using BillofmaterialsRow.read).stream
  }
  override def selectById(billofmaterialsid: Int): ConnectionIO[Option[BillofmaterialsRow]] = {
    sql"""select "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text from "production"."billofmaterials" where "billofmaterialsid" = ${fromWrite(billofmaterialsid)(new Write.Single(Meta.IntMeta.put))}""".query(using BillofmaterialsRow.read).option
  }
  override def selectByIds(billofmaterialsids: Array[Int]): Stream[ConnectionIO, BillofmaterialsRow] = {
    sql"""select "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text from "production"."billofmaterials" where "billofmaterialsid" = ANY(${billofmaterialsids})""".query(using BillofmaterialsRow.read).stream
  }
  override def selectByIdsTracked(billofmaterialsids: Array[Int]): ConnectionIO[Map[Int, BillofmaterialsRow]] = {
    selectByIds(billofmaterialsids).compile.toList.map { rows =>
      val byId = rows.view.map(x => (x.billofmaterialsid, x)).toMap
      billofmaterialsids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    UpdateBuilder(""""production"."billofmaterials"""", BillofmaterialsFields.structure, BillofmaterialsRow.read)
  }
  override def update(row: BillofmaterialsRow): ConnectionIO[Option[BillofmaterialsRow]] = {
    val billofmaterialsid = row.billofmaterialsid
    sql"""update "production"."billofmaterials"
          set "productassemblyid" = ${fromWrite(row.productassemblyid)(new Write.SingleOpt(ProductId.put))}::int4,
              "componentid" = ${fromWrite(row.componentid)(new Write.Single(ProductId.put))}::int4,
              "startdate" = ${fromWrite(row.startdate)(new Write.Single(TypoLocalDateTime.put))}::timestamp,
              "enddate" = ${fromWrite(row.enddate)(new Write.SingleOpt(TypoLocalDateTime.put))}::timestamp,
              "unitmeasurecode" = ${fromWrite(row.unitmeasurecode)(new Write.Single(UnitmeasureId.put))}::bpchar,
              "bomlevel" = ${fromWrite(row.bomlevel)(new Write.Single(TypoShort.put))}::int2,
              "perassemblyqty" = ${fromWrite(row.perassemblyqty)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric,
              "modifieddate" = ${fromWrite(row.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp
          where "billofmaterialsid" = ${fromWrite(billofmaterialsid)(new Write.Single(Meta.IntMeta.put))}
          returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text""".query(using BillofmaterialsRow.read).option
  }
  override def upsert(unsaved: BillofmaterialsRow): ConnectionIO[BillofmaterialsRow] = {
    sql"""insert into "production"."billofmaterials"("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate")
          values (
            ${fromWrite(unsaved.billofmaterialsid)(new Write.Single(Meta.IntMeta.put))}::int4,
            ${fromWrite(unsaved.productassemblyid)(new Write.SingleOpt(ProductId.put))}::int4,
            ${fromWrite(unsaved.componentid)(new Write.Single(ProductId.put))}::int4,
            ${fromWrite(unsaved.startdate)(new Write.Single(TypoLocalDateTime.put))}::timestamp,
            ${fromWrite(unsaved.enddate)(new Write.SingleOpt(TypoLocalDateTime.put))}::timestamp,
            ${fromWrite(unsaved.unitmeasurecode)(new Write.Single(UnitmeasureId.put))}::bpchar,
            ${fromWrite(unsaved.bomlevel)(new Write.Single(TypoShort.put))}::int2,
            ${fromWrite(unsaved.perassemblyqty)(new Write.Single(Meta.ScalaBigDecimalMeta.put))}::numeric,
            ${fromWrite(unsaved.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp
          )
          on conflict ("billofmaterialsid")
          do update set
            "productassemblyid" = EXCLUDED."productassemblyid",
            "componentid" = EXCLUDED."componentid",
            "startdate" = EXCLUDED."startdate",
            "enddate" = EXCLUDED."enddate",
            "unitmeasurecode" = EXCLUDED."unitmeasurecode",
            "bomlevel" = EXCLUDED."bomlevel",
            "perassemblyqty" = EXCLUDED."perassemblyqty",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
       """.query(using BillofmaterialsRow.read).unique
  }
  override def upsertBatch(unsaved: List[BillofmaterialsRow]): Stream[ConnectionIO, BillofmaterialsRow] = {
    Update[BillofmaterialsRow](
      s"""insert into "production"."billofmaterials"("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate")
          values (?::int4,?::int4,?::int4,?::timestamp,?::timestamp,?::bpchar,?::int2,?::numeric,?::timestamp)
          on conflict ("billofmaterialsid")
          do update set
            "productassemblyid" = EXCLUDED."productassemblyid",
            "componentid" = EXCLUDED."componentid",
            "startdate" = EXCLUDED."startdate",
            "enddate" = EXCLUDED."enddate",
            "unitmeasurecode" = EXCLUDED."unitmeasurecode",
            "bomlevel" = EXCLUDED."bomlevel",
            "perassemblyqty" = EXCLUDED."perassemblyqty",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text"""
    )(using BillofmaterialsRow.write)
    .updateManyWithGeneratedKeys[BillofmaterialsRow]("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate")(unsaved)(using catsStdInstancesForList, BillofmaterialsRow.read)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Stream[ConnectionIO, BillofmaterialsRow], batchSize: Int = 10000): ConnectionIO[Int] = {
    for {
      _ <- sql"""create temporary table billofmaterials_TEMP (like "production"."billofmaterials") on commit drop""".update.run
      _ <- new FragmentOps(sql"""copy billofmaterials_TEMP("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate") from stdin""").copyIn(unsaved, batchSize)(using BillofmaterialsRow.text)
      res <- sql"""insert into "production"."billofmaterials"("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate")
                   select * from billofmaterials_TEMP
                   on conflict ("billofmaterialsid")
                   do update set
                     "productassemblyid" = EXCLUDED."productassemblyid",
                     "componentid" = EXCLUDED."componentid",
                     "startdate" = EXCLUDED."startdate",
                     "enddate" = EXCLUDED."enddate",
                     "unitmeasurecode" = EXCLUDED."unitmeasurecode",
                     "bomlevel" = EXCLUDED."bomlevel",
                     "perassemblyqty" = EXCLUDED."perassemblyqty",
                     "modifieddate" = EXCLUDED."modifieddate"
                   ;
                   drop table billofmaterials_TEMP;""".update.run
    } yield res
  }
}
