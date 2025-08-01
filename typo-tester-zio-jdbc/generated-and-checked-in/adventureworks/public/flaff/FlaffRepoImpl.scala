/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package flaff

import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.SqlFragment.Segment
import zio.jdbc.SqlFragment.Setter
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class FlaffRepoImpl extends FlaffRepo {
  override def delete: DeleteBuilder[FlaffFields, FlaffRow] = {
    DeleteBuilder(""""public"."flaff"""", FlaffFields.structure)
  }
  override def deleteById(compositeId: FlaffId): ZIO[ZConnection, Throwable, Boolean] = {
    sql"""delete from "public"."flaff" where "code" = ${Segment.paramSegment(compositeId.code)(ShortText.setter)} AND "another_code" = ${Segment.paramSegment(compositeId.anotherCode)(Setter.stringSetter)} AND "some_number" = ${Segment.paramSegment(compositeId.someNumber)(Setter.intSetter)} AND "specifier" = ${Segment.paramSegment(compositeId.specifier)(ShortText.setter)}""".delete.map(_ > 0)
  }
  override def deleteByIds(compositeIds: Array[FlaffId]): ZIO[ZConnection, Throwable, Long] = {
    val code = compositeIds.map(_.code)
    val anotherCode = compositeIds.map(_.anotherCode)
    val someNumber = compositeIds.map(_.someNumber)
    val specifier = compositeIds.map(_.specifier)
    sql"""delete
          from "public"."flaff"
          where ("code", "another_code", "some_number", "specifier")
          in (select unnest(${code}), unnest(${anotherCode}), unnest(${someNumber}), unnest(${specifier}))
       """.delete
    
  }
  override def insert(unsaved: FlaffRow): ZIO[ZConnection, Throwable, FlaffRow] = {
    sql"""insert into "public"."flaff"("code", "another_code", "some_number", "specifier", "parentspecifier")
          values (${Segment.paramSegment(unsaved.code)(ShortText.setter)}::text, ${Segment.paramSegment(unsaved.anotherCode)(Setter.stringSetter)}, ${Segment.paramSegment(unsaved.someNumber)(Setter.intSetter)}::int4, ${Segment.paramSegment(unsaved.specifier)(ShortText.setter)}::text, ${Segment.paramSegment(unsaved.parentspecifier)(Setter.optionParamSetter(ShortText.setter))}::text)
          returning "code", "another_code", "some_number", "specifier", "parentspecifier"
       """.insertReturning(using FlaffRow.jdbcDecoder).map(_.updatedKeys.head)
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, FlaffRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY "public"."flaff"("code", "another_code", "some_number", "specifier", "parentspecifier") FROM STDIN""", batchSize, unsaved)(FlaffRow.text)
  }
  override def select: SelectBuilder[FlaffFields, FlaffRow] = {
    SelectBuilderSql(""""public"."flaff"""", FlaffFields.structure, FlaffRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, FlaffRow] = {
    sql"""select "code", "another_code", "some_number", "specifier", "parentspecifier" from "public"."flaff"""".query(using FlaffRow.jdbcDecoder).selectStream()
  }
  override def selectById(compositeId: FlaffId): ZIO[ZConnection, Throwable, Option[FlaffRow]] = {
    sql"""select "code", "another_code", "some_number", "specifier", "parentspecifier" from "public"."flaff" where "code" = ${Segment.paramSegment(compositeId.code)(ShortText.setter)} AND "another_code" = ${Segment.paramSegment(compositeId.anotherCode)(Setter.stringSetter)} AND "some_number" = ${Segment.paramSegment(compositeId.someNumber)(Setter.intSetter)} AND "specifier" = ${Segment.paramSegment(compositeId.specifier)(ShortText.setter)}""".query(using FlaffRow.jdbcDecoder).selectOne
  }
  override def selectByIds(compositeIds: Array[FlaffId]): ZStream[ZConnection, Throwable, FlaffRow] = {
    val code = compositeIds.map(_.code)
    val anotherCode = compositeIds.map(_.anotherCode)
    val someNumber = compositeIds.map(_.someNumber)
    val specifier = compositeIds.map(_.specifier)
    sql"""select "code", "another_code", "some_number", "specifier", "parentspecifier"
          from "public"."flaff"
          where ("code", "another_code", "some_number", "specifier")
          in (select unnest(${code}), unnest(${anotherCode}), unnest(${someNumber}), unnest(${specifier}))
       """.query(using FlaffRow.jdbcDecoder).selectStream()
    
  }
  override def selectByIdsTracked(compositeIds: Array[FlaffId]): ZIO[ZConnection, Throwable, Map[FlaffId, FlaffRow]] = {
    selectByIds(compositeIds).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.compositeId, x)).toMap
      compositeIds.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[FlaffFields, FlaffRow] = {
    UpdateBuilder(""""public"."flaff"""", FlaffFields.structure, FlaffRow.jdbcDecoder)
  }
  override def update(row: FlaffRow): ZIO[ZConnection, Throwable, Option[FlaffRow]] = {
    val compositeId = row.compositeId
    sql"""update "public"."flaff"
          set "parentspecifier" = ${Segment.paramSegment(row.parentspecifier)(Setter.optionParamSetter(ShortText.setter))}::text
          where "code" = ${Segment.paramSegment(compositeId.code)(ShortText.setter)} AND "another_code" = ${Segment.paramSegment(compositeId.anotherCode)(Setter.stringSetter)} AND "some_number" = ${Segment.paramSegment(compositeId.someNumber)(Setter.intSetter)} AND "specifier" = ${Segment.paramSegment(compositeId.specifier)(ShortText.setter)}
          returning "code", "another_code", "some_number", "specifier", "parentspecifier""""
      .query(FlaffRow.jdbcDecoder)
      .selectOne
  }
  override def upsert(unsaved: FlaffRow): ZIO[ZConnection, Throwable, UpdateResult[FlaffRow]] = {
    sql"""insert into "public"."flaff"("code", "another_code", "some_number", "specifier", "parentspecifier")
          values (
            ${Segment.paramSegment(unsaved.code)(ShortText.setter)}::text,
            ${Segment.paramSegment(unsaved.anotherCode)(Setter.stringSetter)},
            ${Segment.paramSegment(unsaved.someNumber)(Setter.intSetter)}::int4,
            ${Segment.paramSegment(unsaved.specifier)(ShortText.setter)}::text,
            ${Segment.paramSegment(unsaved.parentspecifier)(Setter.optionParamSetter(ShortText.setter))}::text
          )
          on conflict ("code", "another_code", "some_number", "specifier")
          do update set
            "parentspecifier" = EXCLUDED."parentspecifier"
          returning "code", "another_code", "some_number", "specifier", "parentspecifier"""".insertReturning(using FlaffRow.jdbcDecoder)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, FlaffRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    val created = sql"""create temporary table flaff_TEMP (like "public"."flaff") on commit drop""".execute
    val copied = streamingInsert(s"""copy flaff_TEMP("code", "another_code", "some_number", "specifier", "parentspecifier") from stdin""", batchSize, unsaved)(FlaffRow.text)
    val merged = sql"""insert into "public"."flaff"("code", "another_code", "some_number", "specifier", "parentspecifier")
                       select * from flaff_TEMP
                       on conflict ("code", "another_code", "some_number", "specifier")
                       do update set
                         "parentspecifier" = EXCLUDED."parentspecifier"
                       ;
                       drop table flaff_TEMP;""".update
    created *> copied *> merged
  }
}
