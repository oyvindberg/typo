/**
 * File automatically generated by `typo` for its own test suite.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN
 */
package testdb
package hardcoded
package compositepk
package person

import testdb.hardcoded.customtypes.Defaulted
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder
import zio.NonEmptyChunk
import zio.ZIO
import zio.jdbc.SqlFragment
import zio.jdbc.SqlFragment.Segment
import zio.jdbc.SqlFragment.Setter
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class PersonRepoImpl extends PersonRepo {
  override def delete: DeleteBuilder[PersonFields, PersonRow] = {
    DeleteBuilder(""""compositepk"."person"""", PersonFields.structure)
  }
  override def deleteById(compositeId: PersonId): ZIO[ZConnection, Throwable, Boolean] = {
    sql"""delete from "compositepk"."person" where "one" = ${Segment.paramSegment(compositeId.one)(Setter.longSetter)} AND "two" = ${Segment.paramSegment(compositeId.two)(Setter.optionParamSetter(Setter.stringSetter))}""".delete.map(_ > 0)
  }
  override def insert(unsaved: PersonRow): ZIO[ZConnection, Throwable, PersonRow] = {
    sql"""insert into "compositepk"."person"("one", "two", "name")
          values (${Segment.paramSegment(unsaved.one)(Setter.longSetter)}::int8, ${Segment.paramSegment(unsaved.two)(Setter.optionParamSetter(Setter.stringSetter))}, ${Segment.paramSegment(unsaved.name)(Setter.optionParamSetter(Setter.stringSetter))})
          returning "one", "two", "name"
       """.insertReturning(using PersonRow.jdbcDecoder).map(_.updatedKeys.head)
  }
  override def insert(unsaved: PersonRowUnsaved): ZIO[ZConnection, Throwable, PersonRow] = {
    val fs = List(
      Some((sql""""name"""", sql"${Segment.paramSegment(unsaved.name)(Setter.optionParamSetter(Setter.stringSetter))}")),
      unsaved.one match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""one"""", sql"${Segment.paramSegment(value: Long)(Setter.longSetter)}::int8"))
      },
      unsaved.two match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""two"""", sql"${Segment.paramSegment(value: Option[String])(Setter.optionParamSetter(Setter.stringSetter))}"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into "compositepk"."person" default values
            returning "one", "two", "name"
         """
    } else {
      val names  = fs.map { case (n, _) => n }.mkFragment(SqlFragment(", "))
      val values = fs.map { case (_, f) => f }.mkFragment(SqlFragment(", "))
      sql"""insert into "compositepk"."person"($names) values ($values) returning "one", "two", "name""""
    }
    q.insertReturning(using PersonRow.jdbcDecoder).map(_.updatedKeys.head)
    
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, PersonRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY "compositepk"."person"("one", "two", "name") FROM STDIN""", batchSize, unsaved)(PersonRow.text)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, PersonRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY "compositepk"."person"("name", "one", "two") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""", batchSize, unsaved)(PersonRowUnsaved.text)
  }
  override def select: SelectBuilder[PersonFields, PersonRow] = {
    SelectBuilderSql(""""compositepk"."person"""", PersonFields.structure, PersonRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, PersonRow] = {
    sql"""select "one", "two", "name" from "compositepk"."person"""".query(using PersonRow.jdbcDecoder).selectStream()
  }
  override def selectByFieldValues(fieldValues: List[PersonFieldOrIdValue[?]]): ZStream[ZConnection, Throwable, PersonRow] = {
    fieldValues match {
      case Nil      => selectAll
      case nonEmpty =>
        val wheres = SqlFragment.empty.and(
          nonEmpty.map {
            case PersonFieldValue.one(value) => sql""""one" = ${Segment.paramSegment(value)(Setter.longSetter)}"""
            case PersonFieldValue.two(value) => sql""""two" = ${Segment.paramSegment(value)(Setter.optionParamSetter(Setter.stringSetter))}"""
            case PersonFieldValue.name(value) => sql""""name" = ${Segment.paramSegment(value)(Setter.optionParamSetter(Setter.stringSetter))}"""
          }
        )
        sql"""select "one", "two", "name" from "compositepk"."person" where $wheres""".query(using PersonRow.jdbcDecoder).selectStream()
    }
  }
  override def selectById(compositeId: PersonId): ZIO[ZConnection, Throwable, Option[PersonRow]] = {
    sql"""select "one", "two", "name" from "compositepk"."person" where "one" = ${Segment.paramSegment(compositeId.one)(Setter.longSetter)} AND "two" = ${Segment.paramSegment(compositeId.two)(Setter.optionParamSetter(Setter.stringSetter))}""".query(using PersonRow.jdbcDecoder).selectOne
  }
  override def update: UpdateBuilder[PersonFields, PersonRow] = {
    UpdateBuilder(""""compositepk"."person"""", PersonFields.structure, PersonRow.jdbcDecoder)
  }
  override def update(row: PersonRow): ZIO[ZConnection, Throwable, Option[PersonRow]] = {
    val compositeId = row.compositeId
    sql"""update "compositepk"."person"
          set "name" = ${Segment.paramSegment(row.name)(Setter.optionParamSetter(Setter.stringSetter))}
          where "one" = ${Segment.paramSegment(compositeId.one)(Setter.longSetter)} AND "two" = ${Segment.paramSegment(compositeId.two)(Setter.optionParamSetter(Setter.stringSetter))}
          returning "one", "two", "name""""
      .query(PersonRow.jdbcDecoder)
      .selectOne
  }
  override def updateFieldValues(compositeId: PersonId, fieldValues: List[PersonFieldValue[?]]): ZIO[ZConnection, Throwable, Boolean] = {
    NonEmptyChunk.fromIterableOption(fieldValues) match {
      case None           => ZIO.succeed(false)
      case Some(nonEmpty) =>
        val updates = nonEmpty.map { case PersonFieldValue.name(value) => sql""""name" = ${Segment.paramSegment(value)(Setter.optionParamSetter(Setter.stringSetter))}""" }.mkFragment(SqlFragment(", "))
        sql"""update "compositepk"."person"
              set $updates
              where "one" = ${Segment.paramSegment(compositeId.one)(Setter.longSetter)} AND "two" = ${Segment.paramSegment(compositeId.two)(Setter.optionParamSetter(Setter.stringSetter))}
           """.update.map(_ > 0)
    }
  }
  override def upsert(unsaved: PersonRow): ZIO[ZConnection, Throwable, UpdateResult[PersonRow]] = {
    sql"""insert into "compositepk"."person"("one", "two", "name")
          values (
            ${Segment.paramSegment(unsaved.one)(Setter.longSetter)}::int8,
            ${Segment.paramSegment(unsaved.two)(Setter.optionParamSetter(Setter.stringSetter))},
            ${Segment.paramSegment(unsaved.name)(Setter.optionParamSetter(Setter.stringSetter))}
          )
          on conflict ("one", "two")
          do update set
            "name" = EXCLUDED."name"
          returning "one", "two", "name"""".insertReturning(using PersonRow.jdbcDecoder)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, PersonRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    val created = sql"""create temporary table person_TEMP (like "compositepk"."person") on commit drop""".execute
    val copied = streamingInsert(s"""copy person_TEMP("one", "two", "name") from stdin""", batchSize, unsaved)(PersonRow.text)
    val merged = sql"""insert into "compositepk"."person"("one", "two", "name")
                       select * from person_TEMP
                       on conflict ("one", "two")
                       do update set
                         "name" = EXCLUDED."name"
                       ;
                       drop table person_TEMP;""".update
    created *> copied *> merged
  }
}
