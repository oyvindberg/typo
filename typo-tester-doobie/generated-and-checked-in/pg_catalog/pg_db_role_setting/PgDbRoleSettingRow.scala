/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_db_role_setting

import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.sql.ResultSet

case class PgDbRoleSettingRow(
  setdatabase: /* oid */ Long,
  setrole: /* oid */ Long,
  setconfig: Option[Array[String]]
){
   val compositeId: PgDbRoleSettingId = PgDbRoleSettingId(setdatabase, setrole)
 }

object PgDbRoleSettingRow {
  implicit val decoder: Decoder[PgDbRoleSettingRow] =
    (c: HCursor) =>
      for {
        setdatabase <- c.downField("setdatabase").as[/* oid */ Long]
        setrole <- c.downField("setrole").as[/* oid */ Long]
        setconfig <- c.downField("setconfig").as[Option[Array[String]]]
      } yield PgDbRoleSettingRow(setdatabase, setrole, setconfig)
  implicit val encoder: Encoder[PgDbRoleSettingRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "setdatabase" := row.setdatabase,
        "setrole" := row.setrole,
        "setconfig" := row.setconfig
      )}
  implicit val read: Read[PgDbRoleSettingRow] =
    new Read[PgDbRoleSettingRow](
      gets = List(
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[Array[String]], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => PgDbRoleSettingRow(
        setdatabase = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 0),
        setrole = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 1),
        setconfig = Get[Array[String]].unsafeGetNullable(rs, i + 2)
      )
    )
  

}