/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package shoppingcartitem

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.SqlFragment
import zio.jdbc.SqlFragment.Segment
import zio.jdbc.SqlFragment.Setter
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class ShoppingcartitemRepoImpl extends ShoppingcartitemRepo {
  override def delete: DeleteBuilder[ShoppingcartitemFields, ShoppingcartitemRow] = {
    DeleteBuilder(""""sales"."shoppingcartitem"""", ShoppingcartitemFields.structure)
  }
  override def deleteById(shoppingcartitemid: ShoppingcartitemId): ZIO[ZConnection, Throwable, Boolean] = {
    sql"""delete from "sales"."shoppingcartitem" where "shoppingcartitemid" = ${Segment.paramSegment(shoppingcartitemid)(ShoppingcartitemId.setter)}""".delete.map(_ > 0)
  }
  override def deleteByIds(shoppingcartitemids: Array[ShoppingcartitemId]): ZIO[ZConnection, Throwable, Long] = {
    sql"""delete from "sales"."shoppingcartitem" where "shoppingcartitemid" = ANY(${shoppingcartitemids})""".delete
  }
  override def insert(unsaved: ShoppingcartitemRow): ZIO[ZConnection, Throwable, ShoppingcartitemRow] = {
    sql"""insert into "sales"."shoppingcartitem"("shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated", "modifieddate")
          values (${Segment.paramSegment(unsaved.shoppingcartitemid)(ShoppingcartitemId.setter)}::int4, ${Segment.paramSegment(unsaved.shoppingcartid)(Setter.stringSetter)}, ${Segment.paramSegment(unsaved.quantity)(Setter.intSetter)}::int4, ${Segment.paramSegment(unsaved.productid)(ProductId.setter)}::int4, ${Segment.paramSegment(unsaved.datecreated)(TypoLocalDateTime.setter)}::timestamp, ${Segment.paramSegment(unsaved.modifieddate)(TypoLocalDateTime.setter)}::timestamp)
          returning "shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated"::text, "modifieddate"::text
       """.insertReturning(using ShoppingcartitemRow.jdbcDecoder).map(_.updatedKeys.head)
  }
  override def insert(unsaved: ShoppingcartitemRowUnsaved): ZIO[ZConnection, Throwable, ShoppingcartitemRow] = {
    val fs = List(
      Some((sql""""shoppingcartid"""", sql"${Segment.paramSegment(unsaved.shoppingcartid)(Setter.stringSetter)}")),
      Some((sql""""productid"""", sql"${Segment.paramSegment(unsaved.productid)(ProductId.setter)}::int4")),
      unsaved.shoppingcartitemid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""shoppingcartitemid"""", sql"${Segment.paramSegment(value: ShoppingcartitemId)(ShoppingcartitemId.setter)}::int4"))
      },
      unsaved.quantity match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""quantity"""", sql"${Segment.paramSegment(value: Int)(Setter.intSetter)}::int4"))
      },
      unsaved.datecreated match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""datecreated"""", sql"${Segment.paramSegment(value: TypoLocalDateTime)(TypoLocalDateTime.setter)}::timestamp"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""modifieddate"""", sql"${Segment.paramSegment(value: TypoLocalDateTime)(TypoLocalDateTime.setter)}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into "sales"."shoppingcartitem" default values
            returning "shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated"::text, "modifieddate"::text
         """
    } else {
      val names  = fs.map { case (n, _) => n }.mkFragment(SqlFragment(", "))
      val values = fs.map { case (_, f) => f }.mkFragment(SqlFragment(", "))
      sql"""insert into "sales"."shoppingcartitem"($names) values ($values) returning "shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated"::text, "modifieddate"::text"""
    }
    q.insertReturning(using ShoppingcartitemRow.jdbcDecoder).map(_.updatedKeys.head)
    
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, ShoppingcartitemRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY "sales"."shoppingcartitem"("shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated", "modifieddate") FROM STDIN""", batchSize, unsaved)(ShoppingcartitemRow.text)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, ShoppingcartitemRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY "sales"."shoppingcartitem"("shoppingcartid", "productid", "shoppingcartitemid", "quantity", "datecreated", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""", batchSize, unsaved)(ShoppingcartitemRowUnsaved.text)
  }
  override def select: SelectBuilder[ShoppingcartitemFields, ShoppingcartitemRow] = {
    SelectBuilderSql(""""sales"."shoppingcartitem"""", ShoppingcartitemFields.structure, ShoppingcartitemRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, ShoppingcartitemRow] = {
    sql"""select "shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated"::text, "modifieddate"::text from "sales"."shoppingcartitem"""".query(using ShoppingcartitemRow.jdbcDecoder).selectStream()
  }
  override def selectById(shoppingcartitemid: ShoppingcartitemId): ZIO[ZConnection, Throwable, Option[ShoppingcartitemRow]] = {
    sql"""select "shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated"::text, "modifieddate"::text from "sales"."shoppingcartitem" where "shoppingcartitemid" = ${Segment.paramSegment(shoppingcartitemid)(ShoppingcartitemId.setter)}""".query(using ShoppingcartitemRow.jdbcDecoder).selectOne
  }
  override def selectByIds(shoppingcartitemids: Array[ShoppingcartitemId]): ZStream[ZConnection, Throwable, ShoppingcartitemRow] = {
    sql"""select "shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated"::text, "modifieddate"::text from "sales"."shoppingcartitem" where "shoppingcartitemid" = ANY(${Segment.paramSegment(shoppingcartitemids)(ShoppingcartitemId.arraySetter)})""".query(using ShoppingcartitemRow.jdbcDecoder).selectStream()
  }
  override def selectByIdsTracked(shoppingcartitemids: Array[ShoppingcartitemId]): ZIO[ZConnection, Throwable, Map[ShoppingcartitemId, ShoppingcartitemRow]] = {
    selectByIds(shoppingcartitemids).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.shoppingcartitemid, x)).toMap
      shoppingcartitemids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[ShoppingcartitemFields, ShoppingcartitemRow] = {
    UpdateBuilder(""""sales"."shoppingcartitem"""", ShoppingcartitemFields.structure, ShoppingcartitemRow.jdbcDecoder)
  }
  override def update(row: ShoppingcartitemRow): ZIO[ZConnection, Throwable, Option[ShoppingcartitemRow]] = {
    val shoppingcartitemid = row.shoppingcartitemid
    sql"""update "sales"."shoppingcartitem"
          set "shoppingcartid" = ${Segment.paramSegment(row.shoppingcartid)(Setter.stringSetter)},
              "quantity" = ${Segment.paramSegment(row.quantity)(Setter.intSetter)}::int4,
              "productid" = ${Segment.paramSegment(row.productid)(ProductId.setter)}::int4,
              "datecreated" = ${Segment.paramSegment(row.datecreated)(TypoLocalDateTime.setter)}::timestamp,
              "modifieddate" = ${Segment.paramSegment(row.modifieddate)(TypoLocalDateTime.setter)}::timestamp
          where "shoppingcartitemid" = ${Segment.paramSegment(shoppingcartitemid)(ShoppingcartitemId.setter)}
          returning "shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated"::text, "modifieddate"::text"""
      .query(ShoppingcartitemRow.jdbcDecoder)
      .selectOne
  }
  override def upsert(unsaved: ShoppingcartitemRow): ZIO[ZConnection, Throwable, UpdateResult[ShoppingcartitemRow]] = {
    sql"""insert into "sales"."shoppingcartitem"("shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated", "modifieddate")
          values (
            ${Segment.paramSegment(unsaved.shoppingcartitemid)(ShoppingcartitemId.setter)}::int4,
            ${Segment.paramSegment(unsaved.shoppingcartid)(Setter.stringSetter)},
            ${Segment.paramSegment(unsaved.quantity)(Setter.intSetter)}::int4,
            ${Segment.paramSegment(unsaved.productid)(ProductId.setter)}::int4,
            ${Segment.paramSegment(unsaved.datecreated)(TypoLocalDateTime.setter)}::timestamp,
            ${Segment.paramSegment(unsaved.modifieddate)(TypoLocalDateTime.setter)}::timestamp
          )
          on conflict ("shoppingcartitemid")
          do update set
            "shoppingcartid" = EXCLUDED."shoppingcartid",
            "quantity" = EXCLUDED."quantity",
            "productid" = EXCLUDED."productid",
            "datecreated" = EXCLUDED."datecreated",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated"::text, "modifieddate"::text""".insertReturning(using ShoppingcartitemRow.jdbcDecoder)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, ShoppingcartitemRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    val created = sql"""create temporary table shoppingcartitem_TEMP (like "sales"."shoppingcartitem") on commit drop""".execute
    val copied = streamingInsert(s"""copy shoppingcartitem_TEMP("shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated", "modifieddate") from stdin""", batchSize, unsaved)(ShoppingcartitemRow.text)
    val merged = sql"""insert into "sales"."shoppingcartitem"("shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated", "modifieddate")
                       select * from shoppingcartitem_TEMP
                       on conflict ("shoppingcartitemid")
                       do update set
                         "shoppingcartid" = EXCLUDED."shoppingcartid",
                         "quantity" = EXCLUDED."quantity",
                         "productid" = EXCLUDED."productid",
                         "datecreated" = EXCLUDED."datecreated",
                         "modifieddate" = EXCLUDED."modifieddate"
                       ;
                       drop table shoppingcartitem_TEMP;""".update
    created *> copied *> merged
  }
}
