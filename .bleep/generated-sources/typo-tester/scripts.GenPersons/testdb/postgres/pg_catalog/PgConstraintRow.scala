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

case class PgConstraintRow(
  oid: PgConstraintId,
  conname: String,
  connamespace: Long,
  contype: String,
  condeferrable: Boolean,
  condeferred: Boolean,
  convalidated: Boolean,
  conrelid: Long,
  contypid: Long,
  conindid: Long,
  conparentid: Long,
  confrelid: Long,
  confupdtype: String,
  confdeltype: String,
  confmatchtype: String,
  conislocal: Boolean,
  coninhcount: Int,
  connoinherit: Boolean,
  conkey: Option[Array[Short]],
  confkey: Option[Array[Short]],
  conpfeqop: Option[Array[Long]],
  conppeqop: Option[Array[Long]],
  conffeqop: Option[Array[Long]],
  conexclop: Option[Array[Long]],
  conbin: Option[String]
)

object PgConstraintRow {
  implicit val rowParser: RowParser[PgConstraintRow] = { row =>
    Success(
      PgConstraintRow(
        oid = row[PgConstraintId]("oid"),
        conname = row[String]("conname"),
        connamespace = row[Long]("connamespace"),
        contype = row[String]("contype"),
        condeferrable = row[Boolean]("condeferrable"),
        condeferred = row[Boolean]("condeferred"),
        convalidated = row[Boolean]("convalidated"),
        conrelid = row[Long]("conrelid"),
        contypid = row[Long]("contypid"),
        conindid = row[Long]("conindid"),
        conparentid = row[Long]("conparentid"),
        confrelid = row[Long]("confrelid"),
        confupdtype = row[String]("confupdtype"),
        confdeltype = row[String]("confdeltype"),
        confmatchtype = row[String]("confmatchtype"),
        conislocal = row[Boolean]("conislocal"),
        coninhcount = row[Int]("coninhcount"),
        connoinherit = row[Boolean]("connoinherit"),
        conkey = row[Option[Array[Short]]]("conkey"),
        confkey = row[Option[Array[Short]]]("confkey"),
        conpfeqop = row[Option[Array[Long]]]("conpfeqop"),
        conppeqop = row[Option[Array[Long]]]("conppeqop"),
        conffeqop = row[Option[Array[Long]]]("conffeqop"),
        conexclop = row[Option[Array[Long]]]("conexclop"),
        conbin = row[Option[String]]("conbin")
      )
    )
  }

  implicit val oFormat: OFormat[PgConstraintRow] = new OFormat[PgConstraintRow]{
    override def writes(o: PgConstraintRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "conname" -> o.conname,
      "connamespace" -> o.connamespace,
      "contype" -> o.contype,
      "condeferrable" -> o.condeferrable,
      "condeferred" -> o.condeferred,
      "convalidated" -> o.convalidated,
      "conrelid" -> o.conrelid,
      "contypid" -> o.contypid,
      "conindid" -> o.conindid,
      "conparentid" -> o.conparentid,
      "confrelid" -> o.confrelid,
      "confupdtype" -> o.confupdtype,
      "confdeltype" -> o.confdeltype,
      "confmatchtype" -> o.confmatchtype,
      "conislocal" -> o.conislocal,
      "coninhcount" -> o.coninhcount,
      "connoinherit" -> o.connoinherit,
      "conkey" -> o.conkey,
      "confkey" -> o.confkey,
      "conpfeqop" -> o.conpfeqop,
      "conppeqop" -> o.conppeqop,
      "conffeqop" -> o.conffeqop,
      "conexclop" -> o.conexclop,
      "conbin" -> o.conbin
      )

    override def reads(json: JsValue): JsResult[PgConstraintRow] = {
      JsResult.fromTry(
        Try(
          PgConstraintRow(
            oid = json.\("oid").as[PgConstraintId],
            conname = json.\("conname").as[String],
            connamespace = json.\("connamespace").as[Long],
            contype = json.\("contype").as[String],
            condeferrable = json.\("condeferrable").as[Boolean],
            condeferred = json.\("condeferred").as[Boolean],
            convalidated = json.\("convalidated").as[Boolean],
            conrelid = json.\("conrelid").as[Long],
            contypid = json.\("contypid").as[Long],
            conindid = json.\("conindid").as[Long],
            conparentid = json.\("conparentid").as[Long],
            confrelid = json.\("confrelid").as[Long],
            confupdtype = json.\("confupdtype").as[String],
            confdeltype = json.\("confdeltype").as[String],
            confmatchtype = json.\("confmatchtype").as[String],
            conislocal = json.\("conislocal").as[Boolean],
            coninhcount = json.\("coninhcount").as[Int],
            connoinherit = json.\("connoinherit").as[Boolean],
            conkey = json.\("conkey").toOption.map(_.as[Array[Short]]),
            confkey = json.\("confkey").toOption.map(_.as[Array[Short]]),
            conpfeqop = json.\("conpfeqop").toOption.map(_.as[Array[Long]]),
            conppeqop = json.\("conppeqop").toOption.map(_.as[Array[Long]]),
            conffeqop = json.\("conffeqop").toOption.map(_.as[Array[Long]]),
            conexclop = json.\("conexclop").toOption.map(_.as[Array[Long]]),
            conbin = json.\("conbin").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
