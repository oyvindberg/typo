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
import cats.instances.list.catsStdInstancesForList
import doobie.free.connection.ConnectionIO
import doobie.postgres.syntax.FragmentOps
import doobie.syntax.SqlInterpolator.SingleFragment.fromWrite
import doobie.syntax.string.toSqlInterpolator
import doobie.util.Write
import doobie.util.fragment.Fragment
import doobie.util.update.Update
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class ContacttypeRepoImpl extends ContacttypeRepo {
  override def delete: DeleteBuilder[ContacttypeFields, ContacttypeRow] = {
    DeleteBuilder(""""person"."contacttype"""", ContacttypeFields.structure)
  }
  override def deleteById(contacttypeid: ContacttypeId): ConnectionIO[Boolean] = {
    sql"""delete from "person"."contacttype" where "contacttypeid" = ${fromWrite(contacttypeid)(new Write.Single(ContacttypeId.put))}""".update.run.map(_ > 0)
  }
  override def deleteByIds(contacttypeids: Array[ContacttypeId]): ConnectionIO[Int] = {
    sql"""delete from "person"."contacttype" where "contacttypeid" = ANY(${contacttypeids})""".update.run
  }
  override def insert(unsaved: ContacttypeRow): ConnectionIO[ContacttypeRow] = {
    sql"""insert into "person"."contacttype"("contacttypeid", "name", "modifieddate")
          values (${fromWrite(unsaved.contacttypeid)(new Write.Single(ContacttypeId.put))}::int4, ${fromWrite(unsaved.name)(new Write.Single(Name.put))}::varchar, ${fromWrite(unsaved.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp)
          returning "contacttypeid", "name", "modifieddate"::text
       """.query(using ContacttypeRow.read).unique
  }
  override def insert(unsaved: ContacttypeRowUnsaved): ConnectionIO[ContacttypeRow] = {
    val fs = List(
      Some((Fragment.const0(s""""name""""), fr"${fromWrite(unsaved.name)(new Write.Single(Name.put))}::varchar")),
      unsaved.contacttypeid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""contacttypeid""""), fr"${fromWrite(value: ContacttypeId)(new Write.Single(ContacttypeId.put))}::int4"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""modifieddate""""), fr"${fromWrite(value: TypoLocalDateTime)(new Write.Single(TypoLocalDateTime.put))}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into "person"."contacttype" default values
            returning "contacttypeid", "name", "modifieddate"::text
         """
    } else {
      val CommaSeparate = Fragment.FragmentMonoid.intercalate(fr", ")
      sql"""insert into "person"."contacttype"(${CommaSeparate.combineAllOption(fs.map { case (n, _) => n }).get})
            values (${CommaSeparate.combineAllOption(fs.map { case (_, f) => f }).get})
            returning "contacttypeid", "name", "modifieddate"::text
         """
    }
    q.query(using ContacttypeRow.read).unique
    
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, ContacttypeRow], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "person"."contacttype"("contacttypeid", "name", "modifieddate") FROM STDIN""").copyIn(unsaved, batchSize)(using ContacttypeRow.text)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, ContacttypeRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "person"."contacttype"("name", "contacttypeid", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""").copyIn(unsaved, batchSize)(using ContacttypeRowUnsaved.text)
  }
  override def select: SelectBuilder[ContacttypeFields, ContacttypeRow] = {
    SelectBuilderSql(""""person"."contacttype"""", ContacttypeFields.structure, ContacttypeRow.read)
  }
  override def selectAll: Stream[ConnectionIO, ContacttypeRow] = {
    sql"""select "contacttypeid", "name", "modifieddate"::text from "person"."contacttype"""".query(using ContacttypeRow.read).stream
  }
  override def selectById(contacttypeid: ContacttypeId): ConnectionIO[Option[ContacttypeRow]] = {
    sql"""select "contacttypeid", "name", "modifieddate"::text from "person"."contacttype" where "contacttypeid" = ${fromWrite(contacttypeid)(new Write.Single(ContacttypeId.put))}""".query(using ContacttypeRow.read).option
  }
  override def selectByIds(contacttypeids: Array[ContacttypeId]): Stream[ConnectionIO, ContacttypeRow] = {
    sql"""select "contacttypeid", "name", "modifieddate"::text from "person"."contacttype" where "contacttypeid" = ANY(${contacttypeids})""".query(using ContacttypeRow.read).stream
  }
  override def selectByIdsTracked(contacttypeids: Array[ContacttypeId]): ConnectionIO[Map[ContacttypeId, ContacttypeRow]] = {
    selectByIds(contacttypeids).compile.toList.map { rows =>
      val byId = rows.view.map(x => (x.contacttypeid, x)).toMap
      contacttypeids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[ContacttypeFields, ContacttypeRow] = {
    UpdateBuilder(""""person"."contacttype"""", ContacttypeFields.structure, ContacttypeRow.read)
  }
  override def update(row: ContacttypeRow): ConnectionIO[Option[ContacttypeRow]] = {
    val contacttypeid = row.contacttypeid
    sql"""update "person"."contacttype"
          set "name" = ${fromWrite(row.name)(new Write.Single(Name.put))}::varchar,
              "modifieddate" = ${fromWrite(row.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp
          where "contacttypeid" = ${fromWrite(contacttypeid)(new Write.Single(ContacttypeId.put))}
          returning "contacttypeid", "name", "modifieddate"::text""".query(using ContacttypeRow.read).option
  }
  override def upsert(unsaved: ContacttypeRow): ConnectionIO[ContacttypeRow] = {
    sql"""insert into "person"."contacttype"("contacttypeid", "name", "modifieddate")
          values (
            ${fromWrite(unsaved.contacttypeid)(new Write.Single(ContacttypeId.put))}::int4,
            ${fromWrite(unsaved.name)(new Write.Single(Name.put))}::varchar,
            ${fromWrite(unsaved.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp
          )
          on conflict ("contacttypeid")
          do update set
            "name" = EXCLUDED."name",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "contacttypeid", "name", "modifieddate"::text
       """.query(using ContacttypeRow.read).unique
  }
  override def upsertBatch(unsaved: List[ContacttypeRow]): Stream[ConnectionIO, ContacttypeRow] = {
    Update[ContacttypeRow](
      s"""insert into "person"."contacttype"("contacttypeid", "name", "modifieddate")
          values (?::int4,?::varchar,?::timestamp)
          on conflict ("contacttypeid")
          do update set
            "name" = EXCLUDED."name",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "contacttypeid", "name", "modifieddate"::text"""
    )(using ContacttypeRow.write)
    .updateManyWithGeneratedKeys[ContacttypeRow]("contacttypeid", "name", "modifieddate")(unsaved)(using catsStdInstancesForList, ContacttypeRow.read)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Stream[ConnectionIO, ContacttypeRow], batchSize: Int = 10000): ConnectionIO[Int] = {
    for {
      _ <- sql"""create temporary table contacttype_TEMP (like "person"."contacttype") on commit drop""".update.run
      _ <- new FragmentOps(sql"""copy contacttype_TEMP("contacttypeid", "name", "modifieddate") from stdin""").copyIn(unsaved, batchSize)(using ContacttypeRow.text)
      res <- sql"""insert into "person"."contacttype"("contacttypeid", "name", "modifieddate")
                   select * from contacttype_TEMP
                   on conflict ("contacttypeid")
                   do update set
                     "name" = EXCLUDED."name",
                     "modifieddate" = EXCLUDED."modifieddate"
                   ;
                   drop table contacttype_TEMP;""".update.run
    } yield res
  }
}
