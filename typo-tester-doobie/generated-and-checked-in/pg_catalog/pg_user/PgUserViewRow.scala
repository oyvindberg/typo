/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_user

import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.sql.ResultSet
import java.time.OffsetDateTime

case class PgUserViewRow(
  /** Points to [[pg_shadow.PgShadowViewRow.usename]] */
  usename: Option[String],
  /** Points to [[pg_shadow.PgShadowViewRow.usesysid]] */
  usesysid: Option[/* oid */ Long],
  /** Points to [[pg_shadow.PgShadowViewRow.usecreatedb]] */
  usecreatedb: Option[Boolean],
  /** Points to [[pg_shadow.PgShadowViewRow.usesuper]] */
  usesuper: Option[Boolean],
  /** Points to [[pg_shadow.PgShadowViewRow.userepl]] */
  userepl: Option[Boolean],
  /** Points to [[pg_shadow.PgShadowViewRow.usebypassrls]] */
  usebypassrls: Option[Boolean],
  passwd: Option[String],
  /** Points to [[pg_shadow.PgShadowViewRow.valuntil]] */
  valuntil: Option[OffsetDateTime],
  /** Points to [[pg_shadow.PgShadowViewRow.useconfig]] */
  useconfig: Option[Array[String]]
)

object PgUserViewRow {
  implicit val decoder: Decoder[PgUserViewRow] =
    (c: HCursor) =>
      for {
        usename <- c.downField("usename").as[Option[String]]
        usesysid <- c.downField("usesysid").as[Option[/* oid */ Long]]
        usecreatedb <- c.downField("usecreatedb").as[Option[Boolean]]
        usesuper <- c.downField("usesuper").as[Option[Boolean]]
        userepl <- c.downField("userepl").as[Option[Boolean]]
        usebypassrls <- c.downField("usebypassrls").as[Option[Boolean]]
        passwd <- c.downField("passwd").as[Option[String]]
        valuntil <- c.downField("valuntil").as[Option[OffsetDateTime]]
        useconfig <- c.downField("useconfig").as[Option[Array[String]]]
      } yield PgUserViewRow(usename, usesysid, usecreatedb, usesuper, userepl, usebypassrls, passwd, valuntil, useconfig)
  implicit val encoder: Encoder[PgUserViewRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "usename" := row.usename,
        "usesysid" := row.usesysid,
        "usecreatedb" := row.usecreatedb,
        "usesuper" := row.usesuper,
        "userepl" := row.userepl,
        "usebypassrls" := row.usebypassrls,
        "passwd" := row.passwd,
        "valuntil" := row.valuntil,
        "useconfig" := row.useconfig
      )}
  implicit val read: Read[PgUserViewRow] =
    new Read[PgUserViewRow](
      gets = List(
        (Get[String], Nullability.Nullable),
        (Get[/* oid */ Long], Nullability.Nullable),
        (Get[Boolean], Nullability.Nullable),
        (Get[Boolean], Nullability.Nullable),
        (Get[Boolean], Nullability.Nullable),
        (Get[Boolean], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[OffsetDateTime], Nullability.Nullable),
        (Get[Array[String]], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => PgUserViewRow(
        usename = Get[String].unsafeGetNullable(rs, i + 0),
        usesysid = Get[/* oid */ Long].unsafeGetNullable(rs, i + 1),
        usecreatedb = Get[Boolean].unsafeGetNullable(rs, i + 2),
        usesuper = Get[Boolean].unsafeGetNullable(rs, i + 3),
        userepl = Get[Boolean].unsafeGetNullable(rs, i + 4),
        usebypassrls = Get[Boolean].unsafeGetNullable(rs, i + 5),
        passwd = Get[String].unsafeGetNullable(rs, i + 6),
        valuntil = Get[OffsetDateTime].unsafeGetNullable(rs, i + 7),
        useconfig = Get[Array[String]].unsafeGetNullable(rs, i + 8)
      )
    )
  

}