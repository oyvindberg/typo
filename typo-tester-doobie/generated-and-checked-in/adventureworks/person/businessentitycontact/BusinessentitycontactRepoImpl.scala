/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentitycontact

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.person.contacttype.ContacttypeId
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

class BusinessentitycontactRepoImpl extends BusinessentitycontactRepo {
  override def delete: DeleteBuilder[BusinessentitycontactFields, BusinessentitycontactRow] = {
    DeleteBuilder(""""person"."businessentitycontact"""", BusinessentitycontactFields.structure)
  }
  override def deleteById(compositeId: BusinessentitycontactId): ConnectionIO[Boolean] = {
    sql"""delete from "person"."businessentitycontact" where "businessentityid" = ${fromWrite(compositeId.businessentityid)(new Write.Single(BusinessentityId.put))} AND "personid" = ${fromWrite(compositeId.personid)(new Write.Single(BusinessentityId.put))} AND "contacttypeid" = ${fromWrite(compositeId.contacttypeid)(new Write.Single(ContacttypeId.put))}""".update.run.map(_ > 0)
  }
  override def deleteByIds(compositeIds: Array[BusinessentitycontactId]): ConnectionIO[Int] = {
    val businessentityid = compositeIds.map(_.businessentityid)
    val personid = compositeIds.map(_.personid)
    val contacttypeid = compositeIds.map(_.contacttypeid)
    sql"""delete
          from "person"."businessentitycontact"
          where ("businessentityid", "personid", "contacttypeid")
          in (select unnest(${businessentityid}), unnest(${personid}), unnest(${contacttypeid}))
       """.update.run
    
  }
  override def insert(unsaved: BusinessentitycontactRow): ConnectionIO[BusinessentitycontactRow] = {
    sql"""insert into "person"."businessentitycontact"("businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate")
          values (${fromWrite(unsaved.businessentityid)(new Write.Single(BusinessentityId.put))}::int4, ${fromWrite(unsaved.personid)(new Write.Single(BusinessentityId.put))}::int4, ${fromWrite(unsaved.contacttypeid)(new Write.Single(ContacttypeId.put))}::int4, ${fromWrite(unsaved.rowguid)(new Write.Single(TypoUUID.put))}::uuid, ${fromWrite(unsaved.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp)
          returning "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text
       """.query(using BusinessentitycontactRow.read).unique
  }
  override def insert(unsaved: BusinessentitycontactRowUnsaved): ConnectionIO[BusinessentitycontactRow] = {
    val fs = List(
      Some((Fragment.const0(s""""businessentityid""""), fr"${fromWrite(unsaved.businessentityid)(new Write.Single(BusinessentityId.put))}::int4")),
      Some((Fragment.const0(s""""personid""""), fr"${fromWrite(unsaved.personid)(new Write.Single(BusinessentityId.put))}::int4")),
      Some((Fragment.const0(s""""contacttypeid""""), fr"${fromWrite(unsaved.contacttypeid)(new Write.Single(ContacttypeId.put))}::int4")),
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
      sql"""insert into "person"."businessentitycontact" default values
            returning "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text
         """
    } else {
      val CommaSeparate = Fragment.FragmentMonoid.intercalate(fr", ")
      sql"""insert into "person"."businessentitycontact"(${CommaSeparate.combineAllOption(fs.map { case (n, _) => n }).get})
            values (${CommaSeparate.combineAllOption(fs.map { case (_, f) => f }).get})
            returning "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text
         """
    }
    q.query(using BusinessentitycontactRow.read).unique
    
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, BusinessentitycontactRow], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "person"."businessentitycontact"("businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate") FROM STDIN""").copyIn(unsaved, batchSize)(using BusinessentitycontactRow.text)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, BusinessentitycontactRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "person"."businessentitycontact"("businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""").copyIn(unsaved, batchSize)(using BusinessentitycontactRowUnsaved.text)
  }
  override def select: SelectBuilder[BusinessentitycontactFields, BusinessentitycontactRow] = {
    SelectBuilderSql(""""person"."businessentitycontact"""", BusinessentitycontactFields.structure, BusinessentitycontactRow.read)
  }
  override def selectAll: Stream[ConnectionIO, BusinessentitycontactRow] = {
    sql"""select "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text from "person"."businessentitycontact"""".query(using BusinessentitycontactRow.read).stream
  }
  override def selectById(compositeId: BusinessentitycontactId): ConnectionIO[Option[BusinessentitycontactRow]] = {
    sql"""select "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text from "person"."businessentitycontact" where "businessentityid" = ${fromWrite(compositeId.businessentityid)(new Write.Single(BusinessentityId.put))} AND "personid" = ${fromWrite(compositeId.personid)(new Write.Single(BusinessentityId.put))} AND "contacttypeid" = ${fromWrite(compositeId.contacttypeid)(new Write.Single(ContacttypeId.put))}""".query(using BusinessentitycontactRow.read).option
  }
  override def selectByIds(compositeIds: Array[BusinessentitycontactId]): Stream[ConnectionIO, BusinessentitycontactRow] = {
    val businessentityid = compositeIds.map(_.businessentityid)
    val personid = compositeIds.map(_.personid)
    val contacttypeid = compositeIds.map(_.contacttypeid)
    sql"""select "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text
          from "person"."businessentitycontact"
          where ("businessentityid", "personid", "contacttypeid")
          in (select unnest(${businessentityid}), unnest(${personid}), unnest(${contacttypeid}))
       """.query(using BusinessentitycontactRow.read).stream
    
  }
  override def selectByIdsTracked(compositeIds: Array[BusinessentitycontactId]): ConnectionIO[Map[BusinessentitycontactId, BusinessentitycontactRow]] = {
    selectByIds(compositeIds).compile.toList.map { rows =>
      val byId = rows.view.map(x => (x.compositeId, x)).toMap
      compositeIds.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[BusinessentitycontactFields, BusinessentitycontactRow] = {
    UpdateBuilder(""""person"."businessentitycontact"""", BusinessentitycontactFields.structure, BusinessentitycontactRow.read)
  }
  override def update(row: BusinessentitycontactRow): ConnectionIO[Option[BusinessentitycontactRow]] = {
    val compositeId = row.compositeId
    sql"""update "person"."businessentitycontact"
          set "rowguid" = ${fromWrite(row.rowguid)(new Write.Single(TypoUUID.put))}::uuid,
              "modifieddate" = ${fromWrite(row.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp
          where "businessentityid" = ${fromWrite(compositeId.businessentityid)(new Write.Single(BusinessentityId.put))} AND "personid" = ${fromWrite(compositeId.personid)(new Write.Single(BusinessentityId.put))} AND "contacttypeid" = ${fromWrite(compositeId.contacttypeid)(new Write.Single(ContacttypeId.put))}
          returning "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text""".query(using BusinessentitycontactRow.read).option
  }
  override def upsert(unsaved: BusinessentitycontactRow): ConnectionIO[BusinessentitycontactRow] = {
    sql"""insert into "person"."businessentitycontact"("businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate")
          values (
            ${fromWrite(unsaved.businessentityid)(new Write.Single(BusinessentityId.put))}::int4,
            ${fromWrite(unsaved.personid)(new Write.Single(BusinessentityId.put))}::int4,
            ${fromWrite(unsaved.contacttypeid)(new Write.Single(ContacttypeId.put))}::int4,
            ${fromWrite(unsaved.rowguid)(new Write.Single(TypoUUID.put))}::uuid,
            ${fromWrite(unsaved.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp
          )
          on conflict ("businessentityid", "personid", "contacttypeid")
          do update set
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text
       """.query(using BusinessentitycontactRow.read).unique
  }
  override def upsertBatch(unsaved: List[BusinessentitycontactRow]): Stream[ConnectionIO, BusinessentitycontactRow] = {
    Update[BusinessentitycontactRow](
      s"""insert into "person"."businessentitycontact"("businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate")
          values (?::int4,?::int4,?::int4,?::uuid,?::timestamp)
          on conflict ("businessentityid", "personid", "contacttypeid")
          do update set
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate"::text"""
    )(using BusinessentitycontactRow.write)
    .updateManyWithGeneratedKeys[BusinessentitycontactRow]("businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate")(unsaved)(using catsStdInstancesForList, BusinessentitycontactRow.read)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Stream[ConnectionIO, BusinessentitycontactRow], batchSize: Int = 10000): ConnectionIO[Int] = {
    for {
      _ <- sql"""create temporary table businessentitycontact_TEMP (like "person"."businessentitycontact") on commit drop""".update.run
      _ <- new FragmentOps(sql"""copy businessentitycontact_TEMP("businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate") from stdin""").copyIn(unsaved, batchSize)(using BusinessentitycontactRow.text)
      res <- sql"""insert into "person"."businessentitycontact"("businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate")
                   select * from businessentitycontact_TEMP
                   on conflict ("businessentityid", "personid", "contacttypeid")
                   do update set
                     "rowguid" = EXCLUDED."rowguid",
                     "modifieddate" = EXCLUDED."modifieddate"
                   ;
                   drop table businessentitycontact_TEMP;""".update.run
    } yield res
  }
}
