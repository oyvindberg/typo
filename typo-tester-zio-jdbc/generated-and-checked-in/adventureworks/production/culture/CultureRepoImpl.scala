/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package culture

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
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

class CultureRepoImpl extends CultureRepo {
  override def delete: DeleteBuilder[CultureFields, CultureRow] = {
    DeleteBuilder(""""production"."culture"""", CultureFields.structure)
  }
  override def deleteById(cultureid: CultureId): ZIO[ZConnection, Throwable, Boolean] = {
    sql"""delete from "production"."culture" where "cultureid" = ${Segment.paramSegment(cultureid)(CultureId.setter)}""".delete.map(_ > 0)
  }
  override def deleteByIds(cultureids: Array[CultureId]): ZIO[ZConnection, Throwable, Long] = {
    sql"""delete from "production"."culture" where "cultureid" = ANY(${cultureids})""".delete
  }
  override def insert(unsaved: CultureRow): ZIO[ZConnection, Throwable, CultureRow] = {
    sql"""insert into "production"."culture"("cultureid", "name", "modifieddate")
          values (${Segment.paramSegment(unsaved.cultureid)(CultureId.setter)}::bpchar, ${Segment.paramSegment(unsaved.name)(Name.setter)}::varchar, ${Segment.paramSegment(unsaved.modifieddate)(TypoLocalDateTime.setter)}::timestamp)
          returning "cultureid", "name", "modifieddate"::text
       """.insertReturning(using CultureRow.jdbcDecoder).map(_.updatedKeys.head)
  }
  override def insert(unsaved: CultureRowUnsaved): ZIO[ZConnection, Throwable, CultureRow] = {
    val fs = List(
      Some((sql""""cultureid"""", sql"${Segment.paramSegment(unsaved.cultureid)(CultureId.setter)}::bpchar")),
      Some((sql""""name"""", sql"${Segment.paramSegment(unsaved.name)(Name.setter)}::varchar")),
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""modifieddate"""", sql"${Segment.paramSegment(value: TypoLocalDateTime)(TypoLocalDateTime.setter)}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into "production"."culture" default values
            returning "cultureid", "name", "modifieddate"::text
         """
    } else {
      val names  = fs.map { case (n, _) => n }.mkFragment(SqlFragment(", "))
      val values = fs.map { case (_, f) => f }.mkFragment(SqlFragment(", "))
      sql"""insert into "production"."culture"($names) values ($values) returning "cultureid", "name", "modifieddate"::text"""
    }
    q.insertReturning(using CultureRow.jdbcDecoder).map(_.updatedKeys.head)
    
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, CultureRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY "production"."culture"("cultureid", "name", "modifieddate") FROM STDIN""", batchSize, unsaved)(CultureRow.text)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, CultureRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY "production"."culture"("cultureid", "name", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""", batchSize, unsaved)(CultureRowUnsaved.text)
  }
  override def select: SelectBuilder[CultureFields, CultureRow] = {
    SelectBuilderSql(""""production"."culture"""", CultureFields.structure, CultureRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, CultureRow] = {
    sql"""select "cultureid", "name", "modifieddate"::text from "production"."culture"""".query(using CultureRow.jdbcDecoder).selectStream()
  }
  override def selectById(cultureid: CultureId): ZIO[ZConnection, Throwable, Option[CultureRow]] = {
    sql"""select "cultureid", "name", "modifieddate"::text from "production"."culture" where "cultureid" = ${Segment.paramSegment(cultureid)(CultureId.setter)}""".query(using CultureRow.jdbcDecoder).selectOne
  }
  override def selectByIds(cultureids: Array[CultureId]): ZStream[ZConnection, Throwable, CultureRow] = {
    sql"""select "cultureid", "name", "modifieddate"::text from "production"."culture" where "cultureid" = ANY(${Segment.paramSegment(cultureids)(CultureId.arraySetter)})""".query(using CultureRow.jdbcDecoder).selectStream()
  }
  override def selectByIdsTracked(cultureids: Array[CultureId]): ZIO[ZConnection, Throwable, Map[CultureId, CultureRow]] = {
    selectByIds(cultureids).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.cultureid, x)).toMap
      cultureids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[CultureFields, CultureRow] = {
    UpdateBuilder(""""production"."culture"""", CultureFields.structure, CultureRow.jdbcDecoder)
  }
  override def update(row: CultureRow): ZIO[ZConnection, Throwable, Option[CultureRow]] = {
    val cultureid = row.cultureid
    sql"""update "production"."culture"
          set "name" = ${Segment.paramSegment(row.name)(Name.setter)}::varchar,
              "modifieddate" = ${Segment.paramSegment(row.modifieddate)(TypoLocalDateTime.setter)}::timestamp
          where "cultureid" = ${Segment.paramSegment(cultureid)(CultureId.setter)}
          returning "cultureid", "name", "modifieddate"::text"""
      .query(CultureRow.jdbcDecoder)
      .selectOne
  }
  override def upsert(unsaved: CultureRow): ZIO[ZConnection, Throwable, UpdateResult[CultureRow]] = {
    sql"""insert into "production"."culture"("cultureid", "name", "modifieddate")
          values (
            ${Segment.paramSegment(unsaved.cultureid)(CultureId.setter)}::bpchar,
            ${Segment.paramSegment(unsaved.name)(Name.setter)}::varchar,
            ${Segment.paramSegment(unsaved.modifieddate)(TypoLocalDateTime.setter)}::timestamp
          )
          on conflict ("cultureid")
          do update set
            "name" = EXCLUDED."name",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "cultureid", "name", "modifieddate"::text""".insertReturning(using CultureRow.jdbcDecoder)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, CultureRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    val created = sql"""create temporary table culture_TEMP (like "production"."culture") on commit drop""".execute
    val copied = streamingInsert(s"""copy culture_TEMP("cultureid", "name", "modifieddate") from stdin""", batchSize, unsaved)(CultureRow.text)
    val merged = sql"""insert into "production"."culture"("cultureid", "name", "modifieddate")
                       select * from culture_TEMP
                       on conflict ("cultureid")
                       do update set
                         "name" = EXCLUDED."name",
                         "modifieddate" = EXCLUDED."modifieddate"
                       ;
                       drop table culture_TEMP;""".update
    created *> copied *> merged
  }
}
