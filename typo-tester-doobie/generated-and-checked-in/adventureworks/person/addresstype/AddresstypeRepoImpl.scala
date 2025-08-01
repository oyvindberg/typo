/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package addresstype

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
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

class AddresstypeRepoImpl extends AddresstypeRepo {
  override def delete: DeleteBuilder[AddresstypeFields, AddresstypeRow] = {
    DeleteBuilder(""""person"."addresstype"""", AddresstypeFields.structure)
  }
  override def deleteById(addresstypeid: AddresstypeId): ConnectionIO[Boolean] = {
    sql"""delete from "person"."addresstype" where "addresstypeid" = ${fromWrite(addresstypeid)(new Write.Single(AddresstypeId.put))}""".update.run.map(_ > 0)
  }
  override def deleteByIds(addresstypeids: Array[AddresstypeId]): ConnectionIO[Int] = {
    sql"""delete from "person"."addresstype" where "addresstypeid" = ANY(${addresstypeids})""".update.run
  }
  override def insert(unsaved: AddresstypeRow): ConnectionIO[AddresstypeRow] = {
    sql"""insert into "person"."addresstype"("addresstypeid", "name", "rowguid", "modifieddate")
          values (${fromWrite(unsaved.addresstypeid)(new Write.Single(AddresstypeId.put))}::int4, ${fromWrite(unsaved.name)(new Write.Single(Name.put))}::varchar, ${fromWrite(unsaved.rowguid)(new Write.Single(TypoUUID.put))}::uuid, ${fromWrite(unsaved.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp)
          returning "addresstypeid", "name", "rowguid", "modifieddate"::text
       """.query(using AddresstypeRow.read).unique
  }
  override def insert(unsaved: AddresstypeRowUnsaved): ConnectionIO[AddresstypeRow] = {
    val fs = List(
      Some((Fragment.const0(s""""name""""), fr"${fromWrite(unsaved.name)(new Write.Single(Name.put))}::varchar")),
      unsaved.addresstypeid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const0(s""""addresstypeid""""), fr"${fromWrite(value: AddresstypeId)(new Write.Single(AddresstypeId.put))}::int4"))
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
      sql"""insert into "person"."addresstype" default values
            returning "addresstypeid", "name", "rowguid", "modifieddate"::text
         """
    } else {
      val CommaSeparate = Fragment.FragmentMonoid.intercalate(fr", ")
      sql"""insert into "person"."addresstype"(${CommaSeparate.combineAllOption(fs.map { case (n, _) => n }).get})
            values (${CommaSeparate.combineAllOption(fs.map { case (_, f) => f }).get})
            returning "addresstypeid", "name", "rowguid", "modifieddate"::text
         """
    }
    q.query(using AddresstypeRow.read).unique
    
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, AddresstypeRow], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "person"."addresstype"("addresstypeid", "name", "rowguid", "modifieddate") FROM STDIN""").copyIn(unsaved, batchSize)(using AddresstypeRow.text)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, AddresstypeRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "person"."addresstype"("name", "addresstypeid", "rowguid", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""").copyIn(unsaved, batchSize)(using AddresstypeRowUnsaved.text)
  }
  override def select: SelectBuilder[AddresstypeFields, AddresstypeRow] = {
    SelectBuilderSql(""""person"."addresstype"""", AddresstypeFields.structure, AddresstypeRow.read)
  }
  override def selectAll: Stream[ConnectionIO, AddresstypeRow] = {
    sql"""select "addresstypeid", "name", "rowguid", "modifieddate"::text from "person"."addresstype"""".query(using AddresstypeRow.read).stream
  }
  override def selectById(addresstypeid: AddresstypeId): ConnectionIO[Option[AddresstypeRow]] = {
    sql"""select "addresstypeid", "name", "rowguid", "modifieddate"::text from "person"."addresstype" where "addresstypeid" = ${fromWrite(addresstypeid)(new Write.Single(AddresstypeId.put))}""".query(using AddresstypeRow.read).option
  }
  override def selectByIds(addresstypeids: Array[AddresstypeId]): Stream[ConnectionIO, AddresstypeRow] = {
    sql"""select "addresstypeid", "name", "rowguid", "modifieddate"::text from "person"."addresstype" where "addresstypeid" = ANY(${addresstypeids})""".query(using AddresstypeRow.read).stream
  }
  override def selectByIdsTracked(addresstypeids: Array[AddresstypeId]): ConnectionIO[Map[AddresstypeId, AddresstypeRow]] = {
    selectByIds(addresstypeids).compile.toList.map { rows =>
      val byId = rows.view.map(x => (x.addresstypeid, x)).toMap
      addresstypeids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[AddresstypeFields, AddresstypeRow] = {
    UpdateBuilder(""""person"."addresstype"""", AddresstypeFields.structure, AddresstypeRow.read)
  }
  override def update(row: AddresstypeRow): ConnectionIO[Option[AddresstypeRow]] = {
    val addresstypeid = row.addresstypeid
    sql"""update "person"."addresstype"
          set "name" = ${fromWrite(row.name)(new Write.Single(Name.put))}::varchar,
              "rowguid" = ${fromWrite(row.rowguid)(new Write.Single(TypoUUID.put))}::uuid,
              "modifieddate" = ${fromWrite(row.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp
          where "addresstypeid" = ${fromWrite(addresstypeid)(new Write.Single(AddresstypeId.put))}
          returning "addresstypeid", "name", "rowguid", "modifieddate"::text""".query(using AddresstypeRow.read).option
  }
  override def upsert(unsaved: AddresstypeRow): ConnectionIO[AddresstypeRow] = {
    sql"""insert into "person"."addresstype"("addresstypeid", "name", "rowguid", "modifieddate")
          values (
            ${fromWrite(unsaved.addresstypeid)(new Write.Single(AddresstypeId.put))}::int4,
            ${fromWrite(unsaved.name)(new Write.Single(Name.put))}::varchar,
            ${fromWrite(unsaved.rowguid)(new Write.Single(TypoUUID.put))}::uuid,
            ${fromWrite(unsaved.modifieddate)(new Write.Single(TypoLocalDateTime.put))}::timestamp
          )
          on conflict ("addresstypeid")
          do update set
            "name" = EXCLUDED."name",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "addresstypeid", "name", "rowguid", "modifieddate"::text
       """.query(using AddresstypeRow.read).unique
  }
  override def upsertBatch(unsaved: List[AddresstypeRow]): Stream[ConnectionIO, AddresstypeRow] = {
    Update[AddresstypeRow](
      s"""insert into "person"."addresstype"("addresstypeid", "name", "rowguid", "modifieddate")
          values (?::int4,?::varchar,?::uuid,?::timestamp)
          on conflict ("addresstypeid")
          do update set
            "name" = EXCLUDED."name",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "addresstypeid", "name", "rowguid", "modifieddate"::text"""
    )(using AddresstypeRow.write)
    .updateManyWithGeneratedKeys[AddresstypeRow]("addresstypeid", "name", "rowguid", "modifieddate")(unsaved)(using catsStdInstancesForList, AddresstypeRow.read)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Stream[ConnectionIO, AddresstypeRow], batchSize: Int = 10000): ConnectionIO[Int] = {
    for {
      _ <- sql"""create temporary table addresstype_TEMP (like "person"."addresstype") on commit drop""".update.run
      _ <- new FragmentOps(sql"""copy addresstype_TEMP("addresstypeid", "name", "rowguid", "modifieddate") from stdin""").copyIn(unsaved, batchSize)(using AddresstypeRow.text)
      res <- sql"""insert into "person"."addresstype"("addresstypeid", "name", "rowguid", "modifieddate")
                   select * from addresstype_TEMP
                   on conflict ("addresstypeid")
                   do update set
                     "name" = EXCLUDED."name",
                     "rowguid" = EXCLUDED."rowguid",
                     "modifieddate" = EXCLUDED."modifieddate"
                   ;
                   drop table addresstype_TEMP;""".update.run
    } yield res
  }
}
