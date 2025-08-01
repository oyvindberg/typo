/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package contacttype

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

class ContacttypeRepoImpl extends ContacttypeRepo {
  override def delete: DeleteBuilder[ContacttypeFields, ContacttypeRow] = {
    DeleteBuilder(""""person"."contacttype"""", ContacttypeFields.structure)
  }
  override def deleteById(contacttypeid: ContacttypeId): ZIO[ZConnection, Throwable, Boolean] = {
    sql"""delete from "person"."contacttype" where "contacttypeid" = ${Segment.paramSegment(contacttypeid)(ContacttypeId.setter)}""".delete.map(_ > 0)
  }
  override def deleteByIds(contacttypeids: Array[ContacttypeId]): ZIO[ZConnection, Throwable, Long] = {
    sql"""delete from "person"."contacttype" where "contacttypeid" = ANY(${contacttypeids})""".delete
  }
  override def insert(unsaved: ContacttypeRow): ZIO[ZConnection, Throwable, ContacttypeRow] = {
    sql"""insert into "person"."contacttype"("contacttypeid", "name", "modifieddate")
          values (${Segment.paramSegment(unsaved.contacttypeid)(ContacttypeId.setter)}::int4, ${Segment.paramSegment(unsaved.name)(Name.setter)}::varchar, ${Segment.paramSegment(unsaved.modifieddate)(TypoLocalDateTime.setter)}::timestamp)
          returning "contacttypeid", "name", "modifieddate"::text
       """.insertReturning(using ContacttypeRow.jdbcDecoder).map(_.updatedKeys.head)
  }
  override def insert(unsaved: ContacttypeRowUnsaved): ZIO[ZConnection, Throwable, ContacttypeRow] = {
    val fs = List(
      Some((sql""""name"""", sql"${Segment.paramSegment(unsaved.name)(Name.setter)}::varchar")),
      unsaved.contacttypeid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""contacttypeid"""", sql"${Segment.paramSegment(value: ContacttypeId)(ContacttypeId.setter)}::int4"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""modifieddate"""", sql"${Segment.paramSegment(value: TypoLocalDateTime)(TypoLocalDateTime.setter)}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into "person"."contacttype" default values
            returning "contacttypeid", "name", "modifieddate"::text
         """
    } else {
      val names  = fs.map { case (n, _) => n }.mkFragment(SqlFragment(", "))
      val values = fs.map { case (_, f) => f }.mkFragment(SqlFragment(", "))
      sql"""insert into "person"."contacttype"($names) values ($values) returning "contacttypeid", "name", "modifieddate"::text"""
    }
    q.insertReturning(using ContacttypeRow.jdbcDecoder).map(_.updatedKeys.head)
    
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, ContacttypeRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY "person"."contacttype"("contacttypeid", "name", "modifieddate") FROM STDIN""", batchSize, unsaved)(ContacttypeRow.text)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, ContacttypeRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY "person"."contacttype"("name", "contacttypeid", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""", batchSize, unsaved)(ContacttypeRowUnsaved.text)
  }
  override def select: SelectBuilder[ContacttypeFields, ContacttypeRow] = {
    SelectBuilderSql(""""person"."contacttype"""", ContacttypeFields.structure, ContacttypeRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, ContacttypeRow] = {
    sql"""select "contacttypeid", "name", "modifieddate"::text from "person"."contacttype"""".query(using ContacttypeRow.jdbcDecoder).selectStream()
  }
  override def selectById(contacttypeid: ContacttypeId): ZIO[ZConnection, Throwable, Option[ContacttypeRow]] = {
    sql"""select "contacttypeid", "name", "modifieddate"::text from "person"."contacttype" where "contacttypeid" = ${Segment.paramSegment(contacttypeid)(ContacttypeId.setter)}""".query(using ContacttypeRow.jdbcDecoder).selectOne
  }
  override def selectByIds(contacttypeids: Array[ContacttypeId]): ZStream[ZConnection, Throwable, ContacttypeRow] = {
    sql"""select "contacttypeid", "name", "modifieddate"::text from "person"."contacttype" where "contacttypeid" = ANY(${Segment.paramSegment(contacttypeids)(ContacttypeId.arraySetter)})""".query(using ContacttypeRow.jdbcDecoder).selectStream()
  }
  override def selectByIdsTracked(contacttypeids: Array[ContacttypeId]): ZIO[ZConnection, Throwable, Map[ContacttypeId, ContacttypeRow]] = {
    selectByIds(contacttypeids).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.contacttypeid, x)).toMap
      contacttypeids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[ContacttypeFields, ContacttypeRow] = {
    UpdateBuilder(""""person"."contacttype"""", ContacttypeFields.structure, ContacttypeRow.jdbcDecoder)
  }
  override def update(row: ContacttypeRow): ZIO[ZConnection, Throwable, Option[ContacttypeRow]] = {
    val contacttypeid = row.contacttypeid
    sql"""update "person"."contacttype"
          set "name" = ${Segment.paramSegment(row.name)(Name.setter)}::varchar,
              "modifieddate" = ${Segment.paramSegment(row.modifieddate)(TypoLocalDateTime.setter)}::timestamp
          where "contacttypeid" = ${Segment.paramSegment(contacttypeid)(ContacttypeId.setter)}
          returning "contacttypeid", "name", "modifieddate"::text"""
      .query(ContacttypeRow.jdbcDecoder)
      .selectOne
  }
  override def upsert(unsaved: ContacttypeRow): ZIO[ZConnection, Throwable, UpdateResult[ContacttypeRow]] = {
    sql"""insert into "person"."contacttype"("contacttypeid", "name", "modifieddate")
          values (
            ${Segment.paramSegment(unsaved.contacttypeid)(ContacttypeId.setter)}::int4,
            ${Segment.paramSegment(unsaved.name)(Name.setter)}::varchar,
            ${Segment.paramSegment(unsaved.modifieddate)(TypoLocalDateTime.setter)}::timestamp
          )
          on conflict ("contacttypeid")
          do update set
            "name" = EXCLUDED."name",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "contacttypeid", "name", "modifieddate"::text""".insertReturning(using ContacttypeRow.jdbcDecoder)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, ContacttypeRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    val created = sql"""create temporary table contacttype_TEMP (like "person"."contacttype") on commit drop""".execute
    val copied = streamingInsert(s"""copy contacttype_TEMP("contacttypeid", "name", "modifieddate") from stdin""", batchSize, unsaved)(ContacttypeRow.text)
    val merged = sql"""insert into "person"."contacttype"("contacttypeid", "name", "modifieddate")
                       select * from contacttype_TEMP
                       on conflict ("contacttypeid")
                       do update set
                         "name" = EXCLUDED."name",
                         "modifieddate" = EXCLUDED."modifieddate"
                       ;
                       drop table contacttype_TEMP;""".update
    created *> copied *> merged
  }
}
