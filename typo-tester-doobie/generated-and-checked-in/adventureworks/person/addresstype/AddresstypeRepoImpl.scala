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
import doobie.free.connection.ConnectionIO
import doobie.postgres.syntax.FragmentOps
import doobie.syntax.SqlInterpolator.SingleFragment.fromWrite
import doobie.syntax.string.toSqlInterpolator
import doobie.util.Write
import doobie.util.fragment.Fragment
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class AddresstypeRepoImpl extends AddresstypeRepo {
  override def delete(addresstypeid: AddresstypeId): ConnectionIO[Boolean] = {
    sql"""delete from person.addresstype where "addresstypeid" = ${fromWrite(addresstypeid)(Write.fromPut(AddresstypeId.put))}""".update.run.map(_ > 0)
  }
  override def delete: DeleteBuilder[AddresstypeFields, AddresstypeRow] = {
    DeleteBuilder("person.addresstype", AddresstypeFields.structure)
  }
  override def insert(unsaved: AddresstypeRow): ConnectionIO[AddresstypeRow] = {
    sql"""insert into person.addresstype("addresstypeid", "name", "rowguid", "modifieddate")
          values (${fromWrite(unsaved.addresstypeid)(Write.fromPut(AddresstypeId.put))}::int4, ${fromWrite(unsaved.name)(Write.fromPut(Name.put))}::varchar, ${fromWrite(unsaved.rowguid)(Write.fromPut(TypoUUID.put))}::uuid, ${fromWrite(unsaved.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp)
          returning "addresstypeid", "name", "rowguid", "modifieddate"::text
       """.query(using AddresstypeRow.read).unique
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, AddresstypeRow], batchSize: Int): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY person.addresstype("addresstypeid", "name", "rowguid", "modifieddate") FROM STDIN""").copyIn(unsaved, batchSize)(using AddresstypeRow.text)
  }
  override def insert(unsaved: AddresstypeRowUnsaved): ConnectionIO[AddresstypeRow] = {
    val fs = List(
      Some((Fragment.const(s""""name""""), fr"${fromWrite(unsaved.name)(Write.fromPut(Name.put))}::varchar")),
      unsaved.addresstypeid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s""""addresstypeid""""), fr"${fromWrite(value: AddresstypeId)(Write.fromPut(AddresstypeId.put))}::int4"))
      },
      unsaved.rowguid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s""""rowguid""""), fr"${fromWrite(value: TypoUUID)(Write.fromPut(TypoUUID.put))}::uuid"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s""""modifieddate""""), fr"${fromWrite(value: TypoLocalDateTime)(Write.fromPut(TypoLocalDateTime.put))}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into person.addresstype default values
            returning "addresstypeid", "name", "rowguid", "modifieddate"::text
         """
    } else {
      val CommaSeparate = Fragment.FragmentMonoid.intercalate(fr", ")
      sql"""insert into person.addresstype(${CommaSeparate.combineAllOption(fs.map { case (n, _) => n }).get})
            values (${CommaSeparate.combineAllOption(fs.map { case (_, f) => f }).get})
            returning "addresstypeid", "name", "rowguid", "modifieddate"::text
         """
    }
    q.query(using AddresstypeRow.read).unique
    
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, AddresstypeRowUnsaved], batchSize: Int): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY person.addresstype("name", "addresstypeid", "rowguid", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""").copyIn(unsaved, batchSize)(using AddresstypeRowUnsaved.text)
  }
  override def select: SelectBuilder[AddresstypeFields, AddresstypeRow] = {
    SelectBuilderSql("person.addresstype", AddresstypeFields.structure, AddresstypeRow.read)
  }
  override def selectAll: Stream[ConnectionIO, AddresstypeRow] = {
    sql"""select "addresstypeid", "name", "rowguid", "modifieddate"::text from person.addresstype""".query(using AddresstypeRow.read).stream
  }
  override def selectById(addresstypeid: AddresstypeId): ConnectionIO[Option[AddresstypeRow]] = {
    sql"""select "addresstypeid", "name", "rowguid", "modifieddate"::text from person.addresstype where "addresstypeid" = ${fromWrite(addresstypeid)(Write.fromPut(AddresstypeId.put))}""".query(using AddresstypeRow.read).option
  }
  override def selectByIds(addresstypeids: Array[AddresstypeId]): Stream[ConnectionIO, AddresstypeRow] = {
    sql"""select "addresstypeid", "name", "rowguid", "modifieddate"::text from person.addresstype where "addresstypeid" = ANY(${addresstypeids})""".query(using AddresstypeRow.read).stream
  }
  override def update(row: AddresstypeRow): ConnectionIO[Boolean] = {
    val addresstypeid = row.addresstypeid
    sql"""update person.addresstype
          set "name" = ${fromWrite(row.name)(Write.fromPut(Name.put))}::varchar,
              "rowguid" = ${fromWrite(row.rowguid)(Write.fromPut(TypoUUID.put))}::uuid,
              "modifieddate" = ${fromWrite(row.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp
          where "addresstypeid" = ${fromWrite(addresstypeid)(Write.fromPut(AddresstypeId.put))}"""
      .update
      .run
      .map(_ > 0)
  }
  override def update: UpdateBuilder[AddresstypeFields, AddresstypeRow] = {
    UpdateBuilder("person.addresstype", AddresstypeFields.structure, AddresstypeRow.read)
  }
  override def upsert(unsaved: AddresstypeRow): ConnectionIO[AddresstypeRow] = {
    sql"""insert into person.addresstype("addresstypeid", "name", "rowguid", "modifieddate")
          values (
            ${fromWrite(unsaved.addresstypeid)(Write.fromPut(AddresstypeId.put))}::int4,
            ${fromWrite(unsaved.name)(Write.fromPut(Name.put))}::varchar,
            ${fromWrite(unsaved.rowguid)(Write.fromPut(TypoUUID.put))}::uuid,
            ${fromWrite(unsaved.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp
          )
          on conflict ("addresstypeid")
          do update set
            "name" = EXCLUDED."name",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "addresstypeid", "name", "rowguid", "modifieddate"::text
       """.query(using AddresstypeRow.read).unique
  }
}