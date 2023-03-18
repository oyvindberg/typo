package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgPolicyRow(
  oid: PgPolicyId,
  polname: String,
  polrelid: Long,
  polcmd: String,
  polpermissive: Boolean,
  polroles: Array[Long],
  polqual: Option[String],
  polwithcheck: Option[String]
)

object PgPolicyRow {
  implicit val rowParser: RowParser[PgPolicyRow] = { row =>
    Success(
      PgPolicyRow(
        oid = row[PgPolicyId]("oid"),
        polname = row[String]("polname"),
        polrelid = row[Long]("polrelid"),
        polcmd = row[String]("polcmd"),
        polpermissive = row[Boolean]("polpermissive"),
        polroles = row[Array[Long]]("polroles"),
        polqual = row[Option[String]]("polqual"),
        polwithcheck = row[Option[String]]("polwithcheck")
      )
    )
  }

  implicit val oFormat: OFormat[PgPolicyRow] = new OFormat[PgPolicyRow]{
    override def writes(o: PgPolicyRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "polname" -> o.polname,
      "polrelid" -> o.polrelid,
      "polcmd" -> o.polcmd,
      "polpermissive" -> o.polpermissive,
      "polroles" -> o.polroles,
      "polqual" -> o.polqual,
      "polwithcheck" -> o.polwithcheck
      )

    override def reads(json: JsValue): JsResult[PgPolicyRow] = {
      JsResult.fromTry(
        Try(
          PgPolicyRow(
            oid = json.\("oid").as[PgPolicyId],
            polname = json.\("polname").as[String],
            polrelid = json.\("polrelid").as[Long],
            polcmd = json.\("polcmd").as[String],
            polpermissive = json.\("polpermissive").as[Boolean],
            polroles = json.\("polroles").as[Array[Long]],
            polqual = json.\("polqual").toOption.map(_.as[String]),
            polwithcheck = json.\("polwithcheck").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
