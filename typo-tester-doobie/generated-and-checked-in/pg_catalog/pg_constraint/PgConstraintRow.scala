/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_constraint

import adventureworks.TypoPgNodeTree
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.sql.ResultSet

case class PgConstraintRow(
  oid: PgConstraintId,
  conname: String,
  connamespace: /* oid */ Long,
  contype: String,
  condeferrable: Boolean,
  condeferred: Boolean,
  convalidated: Boolean,
  conrelid: /* oid */ Long,
  contypid: /* oid */ Long,
  conindid: /* oid */ Long,
  conparentid: /* oid */ Long,
  confrelid: /* oid */ Long,
  confupdtype: String,
  confdeltype: String,
  confmatchtype: String,
  conislocal: Boolean,
  coninhcount: Int,
  connoinherit: Boolean,
  conkey: Option[Array[Int]],
  confkey: Option[Array[Int]],
  conpfeqop: Option[Array[/* oid */ Long]],
  conppeqop: Option[Array[/* oid */ Long]],
  conffeqop: Option[Array[/* oid */ Long]],
  conexclop: Option[Array[/* oid */ Long]],
  conbin: Option[TypoPgNodeTree]
)

object PgConstraintRow {
  implicit val decoder: Decoder[PgConstraintRow] =
    (c: HCursor) =>
      for {
        oid <- c.downField("oid").as[PgConstraintId]
        conname <- c.downField("conname").as[String]
        connamespace <- c.downField("connamespace").as[/* oid */ Long]
        contype <- c.downField("contype").as[String]
        condeferrable <- c.downField("condeferrable").as[Boolean]
        condeferred <- c.downField("condeferred").as[Boolean]
        convalidated <- c.downField("convalidated").as[Boolean]
        conrelid <- c.downField("conrelid").as[/* oid */ Long]
        contypid <- c.downField("contypid").as[/* oid */ Long]
        conindid <- c.downField("conindid").as[/* oid */ Long]
        conparentid <- c.downField("conparentid").as[/* oid */ Long]
        confrelid <- c.downField("confrelid").as[/* oid */ Long]
        confupdtype <- c.downField("confupdtype").as[String]
        confdeltype <- c.downField("confdeltype").as[String]
        confmatchtype <- c.downField("confmatchtype").as[String]
        conislocal <- c.downField("conislocal").as[Boolean]
        coninhcount <- c.downField("coninhcount").as[Int]
        connoinherit <- c.downField("connoinherit").as[Boolean]
        conkey <- c.downField("conkey").as[Option[Array[Int]]]
        confkey <- c.downField("confkey").as[Option[Array[Int]]]
        conpfeqop <- c.downField("conpfeqop").as[Option[Array[/* oid */ Long]]]
        conppeqop <- c.downField("conppeqop").as[Option[Array[/* oid */ Long]]]
        conffeqop <- c.downField("conffeqop").as[Option[Array[/* oid */ Long]]]
        conexclop <- c.downField("conexclop").as[Option[Array[/* oid */ Long]]]
        conbin <- c.downField("conbin").as[Option[TypoPgNodeTree]]
      } yield PgConstraintRow(oid, conname, connamespace, contype, condeferrable, condeferred, convalidated, conrelid, contypid, conindid, conparentid, confrelid, confupdtype, confdeltype, confmatchtype, conislocal, coninhcount, connoinherit, conkey, confkey, conpfeqop, conppeqop, conffeqop, conexclop, conbin)
  implicit val encoder: Encoder[PgConstraintRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "oid" := row.oid,
        "conname" := row.conname,
        "connamespace" := row.connamespace,
        "contype" := row.contype,
        "condeferrable" := row.condeferrable,
        "condeferred" := row.condeferred,
        "convalidated" := row.convalidated,
        "conrelid" := row.conrelid,
        "contypid" := row.contypid,
        "conindid" := row.conindid,
        "conparentid" := row.conparentid,
        "confrelid" := row.confrelid,
        "confupdtype" := row.confupdtype,
        "confdeltype" := row.confdeltype,
        "confmatchtype" := row.confmatchtype,
        "conislocal" := row.conislocal,
        "coninhcount" := row.coninhcount,
        "connoinherit" := row.connoinherit,
        "conkey" := row.conkey,
        "confkey" := row.confkey,
        "conpfeqop" := row.conpfeqop,
        "conppeqop" := row.conppeqop,
        "conffeqop" := row.conffeqop,
        "conexclop" := row.conexclop,
        "conbin" := row.conbin
      )}
  implicit val read: Read[PgConstraintRow] =
    new Read[PgConstraintRow](
      gets = List(
        (Get[PgConstraintId], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[Boolean], Nullability.NoNulls),
        (Get[Boolean], Nullability.NoNulls),
        (Get[Boolean], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[Boolean], Nullability.NoNulls),
        (Get[Int], Nullability.NoNulls),
        (Get[Boolean], Nullability.NoNulls),
        (Get[Array[Int]], Nullability.Nullable),
        (Get[Array[Int]], Nullability.Nullable),
        (Get[Array[/* oid */ Long]], Nullability.Nullable),
        (Get[Array[/* oid */ Long]], Nullability.Nullable),
        (Get[Array[/* oid */ Long]], Nullability.Nullable),
        (Get[Array[/* oid */ Long]], Nullability.Nullable),
        (Get[TypoPgNodeTree], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => PgConstraintRow(
        oid = Get[PgConstraintId].unsafeGetNonNullable(rs, i + 0),
        conname = Get[String].unsafeGetNonNullable(rs, i + 1),
        connamespace = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 2),
        contype = Get[String].unsafeGetNonNullable(rs, i + 3),
        condeferrable = Get[Boolean].unsafeGetNonNullable(rs, i + 4),
        condeferred = Get[Boolean].unsafeGetNonNullable(rs, i + 5),
        convalidated = Get[Boolean].unsafeGetNonNullable(rs, i + 6),
        conrelid = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 7),
        contypid = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 8),
        conindid = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 9),
        conparentid = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 10),
        confrelid = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 11),
        confupdtype = Get[String].unsafeGetNonNullable(rs, i + 12),
        confdeltype = Get[String].unsafeGetNonNullable(rs, i + 13),
        confmatchtype = Get[String].unsafeGetNonNullable(rs, i + 14),
        conislocal = Get[Boolean].unsafeGetNonNullable(rs, i + 15),
        coninhcount = Get[Int].unsafeGetNonNullable(rs, i + 16),
        connoinherit = Get[Boolean].unsafeGetNonNullable(rs, i + 17),
        conkey = Get[Array[Int]].unsafeGetNullable(rs, i + 18),
        confkey = Get[Array[Int]].unsafeGetNullable(rs, i + 19),
        conpfeqop = Get[Array[/* oid */ Long]].unsafeGetNullable(rs, i + 20),
        conppeqop = Get[Array[/* oid */ Long]].unsafeGetNullable(rs, i + 21),
        conffeqop = Get[Array[/* oid */ Long]].unsafeGetNullable(rs, i + 22),
        conexclop = Get[Array[/* oid */ Long]].unsafeGetNullable(rs, i + 23),
        conbin = Get[TypoPgNodeTree].unsafeGetNullable(rs, i + 24)
      )
    )
  

}