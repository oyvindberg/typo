package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgLocksRow(
  locktype: /* unknown nullability */ Option[String],
  database: /* unknown nullability */ Option[Long],
  relation: /* unknown nullability */ Option[Long],
  page: /* unknown nullability */ Option[Int],
  tuple: /* unknown nullability */ Option[Short],
  virtualxid: /* unknown nullability */ Option[String],
  transactionid: /* unknown nullability */ Option[/* xid */ String],
  classid: /* unknown nullability */ Option[Long],
  objid: /* unknown nullability */ Option[Long],
  objsubid: /* unknown nullability */ Option[Short],
  virtualtransaction: /* unknown nullability */ Option[String],
  pid: /* unknown nullability */ Option[Int],
  mode: /* unknown nullability */ Option[String],
  granted: /* unknown nullability */ Option[Boolean],
  fastpath: /* unknown nullability */ Option[Boolean],
  waitstart: /* unknown nullability */ Option[LocalDateTime]
)

object PgLocksRow {
  implicit val rowParser: RowParser[PgLocksRow] = { row =>
    Success(
      PgLocksRow(
        locktype = row[/* unknown nullability */ Option[String]]("locktype"),
        database = row[/* unknown nullability */ Option[Long]]("database"),
        relation = row[/* unknown nullability */ Option[Long]]("relation"),
        page = row[/* unknown nullability */ Option[Int]]("page"),
        tuple = row[/* unknown nullability */ Option[Short]]("tuple"),
        virtualxid = row[/* unknown nullability */ Option[String]]("virtualxid"),
        transactionid = row[/* unknown nullability */ Option[/* xid */ String]]("transactionid"),
        classid = row[/* unknown nullability */ Option[Long]]("classid"),
        objid = row[/* unknown nullability */ Option[Long]]("objid"),
        objsubid = row[/* unknown nullability */ Option[Short]]("objsubid"),
        virtualtransaction = row[/* unknown nullability */ Option[String]]("virtualtransaction"),
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        mode = row[/* unknown nullability */ Option[String]]("mode"),
        granted = row[/* unknown nullability */ Option[Boolean]]("granted"),
        fastpath = row[/* unknown nullability */ Option[Boolean]]("fastpath"),
        waitstart = row[/* unknown nullability */ Option[LocalDateTime]]("waitstart")
      )
    )
  }

  implicit val oFormat: OFormat[PgLocksRow] = new OFormat[PgLocksRow]{
    override def writes(o: PgLocksRow): JsObject =
      Json.obj(
        "locktype" -> o.locktype,
      "database" -> o.database,
      "relation" -> o.relation,
      "page" -> o.page,
      "tuple" -> o.tuple,
      "virtualxid" -> o.virtualxid,
      "transactionid" -> o.transactionid,
      "classid" -> o.classid,
      "objid" -> o.objid,
      "objsubid" -> o.objsubid,
      "virtualtransaction" -> o.virtualtransaction,
      "pid" -> o.pid,
      "mode" -> o.mode,
      "granted" -> o.granted,
      "fastpath" -> o.fastpath,
      "waitstart" -> o.waitstart
      )

    override def reads(json: JsValue): JsResult[PgLocksRow] = {
      JsResult.fromTry(
        Try(
          PgLocksRow(
            locktype = json.\("locktype").toOption.map(_.as[String]),
            database = json.\("database").toOption.map(_.as[Long]),
            relation = json.\("relation").toOption.map(_.as[Long]),
            page = json.\("page").toOption.map(_.as[Int]),
            tuple = json.\("tuple").toOption.map(_.as[Short]),
            virtualxid = json.\("virtualxid").toOption.map(_.as[String]),
            transactionid = json.\("transactionid").toOption.map(_.as[/* xid */ String]),
            classid = json.\("classid").toOption.map(_.as[Long]),
            objid = json.\("objid").toOption.map(_.as[Long]),
            objsubid = json.\("objsubid").toOption.map(_.as[Short]),
            virtualtransaction = json.\("virtualtransaction").toOption.map(_.as[String]),
            pid = json.\("pid").toOption.map(_.as[Int]),
            mode = json.\("mode").toOption.map(_.as[String]),
            granted = json.\("granted").toOption.map(_.as[Boolean]),
            fastpath = json.\("fastpath").toOption.map(_.as[Boolean]),
            waitstart = json.\("waitstart").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}
