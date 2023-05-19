/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package contacttype

import adventureworks.Defaulted
import adventureworks.public.Name
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import doobie.free.connection.ConnectionIO
import doobie.free.connection.pure
import doobie.syntax.string.toSqlInterpolator
import doobie.util.fragment.Fragment
import doobie.util.fragments
import fs2.Stream
import java.sql.ResultSet
import java.time.LocalDateTime

object ContacttypeRepoImpl extends ContacttypeRepo {
  override def delete(contacttypeid: ContacttypeId): ConnectionIO[Boolean] = {
    sql"delete from person.contacttype where contacttypeid = $contacttypeid".update.run.map(_ > 0)
  }
  override def insert(unsaved: ContacttypeRow): ConnectionIO[ContacttypeRow] = {
    sql"""insert into person.contacttype(contacttypeid, "name", modifieddate)
          values (${unsaved.contacttypeid}::int4, ${unsaved.name}::"public"."Name", ${unsaved.modifieddate}::timestamp)
          returning contacttypeid, "name", modifieddate
       """.query.unique
  }
  override def insert(unsaved: ContacttypeRowUnsaved): ConnectionIO[ContacttypeRow] = {
    val fs = List(
      Some((Fragment.const(s""""name""""), fr"""${unsaved.name}::"public"."Name"""")),
      unsaved.contacttypeid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s"contacttypeid"), fr"${value: ContacttypeId}::int4"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s"modifieddate"), fr"${value: LocalDateTime}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into person.contacttype default values
            returning contacttypeid, "name", modifieddate
         """
    } else {
      import cats.syntax.foldable.toFoldableOps
      sql"""insert into person.contacttype(${fs.map { case (n, _) => n }.intercalate(fr", ")})
            values (${fs.map { case (_, f) => f }.intercalate(fr", ")})
            returning contacttypeid, "name", modifieddate
         """
    }
    q.query.unique
  
  }
  override def selectAll: Stream[ConnectionIO, ContacttypeRow] = {
    sql"""select contacttypeid, "name", modifieddate from person.contacttype""".query[ContacttypeRow].stream
  }
  override def selectByFieldValues(fieldValues: List[ContacttypeFieldOrIdValue[_]]): Stream[ConnectionIO, ContacttypeRow] = {
    val where = fragments.whereAnd(
      fieldValues.map {
        case ContacttypeFieldValue.contacttypeid(value) => fr"contacttypeid = $value"
        case ContacttypeFieldValue.name(value) => fr""""name" = $value"""
        case ContacttypeFieldValue.modifieddate(value) => fr"modifieddate = $value"
      } :_*
    )
    sql"select * from person.contacttype $where".query[ContacttypeRow].stream
  
  }
  override def selectById(contacttypeid: ContacttypeId): ConnectionIO[Option[ContacttypeRow]] = {
    sql"""select contacttypeid, "name", modifieddate from person.contacttype where contacttypeid = $contacttypeid""".query[ContacttypeRow].option
  }
  override def selectByIds(contacttypeids: Array[ContacttypeId]): Stream[ConnectionIO, ContacttypeRow] = {
    sql"""select contacttypeid, "name", modifieddate from person.contacttype where contacttypeid = ANY($contacttypeids)""".query[ContacttypeRow].stream
  }
  override def update(row: ContacttypeRow): ConnectionIO[Boolean] = {
    val contacttypeid = row.contacttypeid
    sql"""update person.contacttype
          set "name" = ${row.name}::"public"."Name",
              modifieddate = ${row.modifieddate}::timestamp
          where contacttypeid = $contacttypeid
       """
      .update
      .run
      .map(_ > 0)
  }
  override def updateFieldValues(contacttypeid: ContacttypeId, fieldValues: List[ContacttypeFieldValue[_]]): ConnectionIO[Boolean] = {
    fieldValues match {
      case Nil => pure(false)
      case nonEmpty =>
        val updates = fragments.set(
          nonEmpty.map {
            case ContacttypeFieldValue.name(value) => fr""""name" = $value"""
            case ContacttypeFieldValue.modifieddate(value) => fr"modifieddate = $value"
          } :_*
        )
        sql"""update person.contacttype
              $updates
              where contacttypeid = $contacttypeid
           """.update.run.map(_ > 0)
    }
  }
  override def upsert(unsaved: ContacttypeRow): ConnectionIO[ContacttypeRow] = {
    sql"""insert into person.contacttype(contacttypeid, "name", modifieddate)
          values (
            ${unsaved.contacttypeid}::int4,
            ${unsaved.name}::"public"."Name",
            ${unsaved.modifieddate}::timestamp
          )
          on conflict (contacttypeid)
          do update set
            "name" = EXCLUDED."name",
            modifieddate = EXCLUDED.modifieddate
          returning contacttypeid, "name", modifieddate
       """.query.unique
  }
  implicit val read: Read[ContacttypeRow] =
    new Read[ContacttypeRow](
      gets = List(
        (Get[ContacttypeId], Nullability.NoNulls),
        (Get[Name], Nullability.NoNulls),
        (Get[LocalDateTime], Nullability.NoNulls)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => ContacttypeRow(
        contacttypeid = Get[ContacttypeId].unsafeGetNonNullable(rs, i + 0),
        name = Get[Name].unsafeGetNonNullable(rs, i + 1),
        modifieddate = Get[LocalDateTime].unsafeGetNonNullable(rs, i + 2)
      )
    )
  

}