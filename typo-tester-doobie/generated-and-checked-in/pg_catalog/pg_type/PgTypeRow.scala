/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_type

import adventureworks.TypoAclItem
import adventureworks.TypoPgNodeTree
import adventureworks.TypoRegproc
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.sql.ResultSet

case class PgTypeRow(
  oid: PgTypeId,
  typname: String,
  typnamespace: /* oid */ Long,
  typowner: /* oid */ Long,
  typlen: Int,
  typbyval: Boolean,
  typtype: String,
  typcategory: String,
  typispreferred: Boolean,
  typisdefined: Boolean,
  typdelim: String,
  typrelid: /* oid */ Long,
  typsubscript: TypoRegproc,
  typelem: /* oid */ Long,
  typarray: /* oid */ Long,
  typinput: TypoRegproc,
  typoutput: TypoRegproc,
  typreceive: TypoRegproc,
  typsend: TypoRegproc,
  typmodin: TypoRegproc,
  typmodout: TypoRegproc,
  typanalyze: TypoRegproc,
  typalign: String,
  typstorage: String,
  typnotnull: Boolean,
  typbasetype: /* oid */ Long,
  typtypmod: Int,
  typndims: Int,
  typcollation: /* oid */ Long,
  typdefaultbin: Option[TypoPgNodeTree],
  typdefault: Option[String],
  typacl: Option[Array[TypoAclItem]]
)

object PgTypeRow {
  implicit val decoder: Decoder[PgTypeRow] =
    (c: HCursor) =>
      for {
        oid <- c.downField("oid").as[PgTypeId]
        typname <- c.downField("typname").as[String]
        typnamespace <- c.downField("typnamespace").as[/* oid */ Long]
        typowner <- c.downField("typowner").as[/* oid */ Long]
        typlen <- c.downField("typlen").as[Int]
        typbyval <- c.downField("typbyval").as[Boolean]
        typtype <- c.downField("typtype").as[String]
        typcategory <- c.downField("typcategory").as[String]
        typispreferred <- c.downField("typispreferred").as[Boolean]
        typisdefined <- c.downField("typisdefined").as[Boolean]
        typdelim <- c.downField("typdelim").as[String]
        typrelid <- c.downField("typrelid").as[/* oid */ Long]
        typsubscript <- c.downField("typsubscript").as[TypoRegproc]
        typelem <- c.downField("typelem").as[/* oid */ Long]
        typarray <- c.downField("typarray").as[/* oid */ Long]
        typinput <- c.downField("typinput").as[TypoRegproc]
        typoutput <- c.downField("typoutput").as[TypoRegproc]
        typreceive <- c.downField("typreceive").as[TypoRegproc]
        typsend <- c.downField("typsend").as[TypoRegproc]
        typmodin <- c.downField("typmodin").as[TypoRegproc]
        typmodout <- c.downField("typmodout").as[TypoRegproc]
        typanalyze <- c.downField("typanalyze").as[TypoRegproc]
        typalign <- c.downField("typalign").as[String]
        typstorage <- c.downField("typstorage").as[String]
        typnotnull <- c.downField("typnotnull").as[Boolean]
        typbasetype <- c.downField("typbasetype").as[/* oid */ Long]
        typtypmod <- c.downField("typtypmod").as[Int]
        typndims <- c.downField("typndims").as[Int]
        typcollation <- c.downField("typcollation").as[/* oid */ Long]
        typdefaultbin <- c.downField("typdefaultbin").as[Option[TypoPgNodeTree]]
        typdefault <- c.downField("typdefault").as[Option[String]]
        typacl <- c.downField("typacl").as[Option[Array[TypoAclItem]]]
      } yield PgTypeRow(oid, typname, typnamespace, typowner, typlen, typbyval, typtype, typcategory, typispreferred, typisdefined, typdelim, typrelid, typsubscript, typelem, typarray, typinput, typoutput, typreceive, typsend, typmodin, typmodout, typanalyze, typalign, typstorage, typnotnull, typbasetype, typtypmod, typndims, typcollation, typdefaultbin, typdefault, typacl)
  implicit val encoder: Encoder[PgTypeRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "oid" := row.oid,
        "typname" := row.typname,
        "typnamespace" := row.typnamespace,
        "typowner" := row.typowner,
        "typlen" := row.typlen,
        "typbyval" := row.typbyval,
        "typtype" := row.typtype,
        "typcategory" := row.typcategory,
        "typispreferred" := row.typispreferred,
        "typisdefined" := row.typisdefined,
        "typdelim" := row.typdelim,
        "typrelid" := row.typrelid,
        "typsubscript" := row.typsubscript,
        "typelem" := row.typelem,
        "typarray" := row.typarray,
        "typinput" := row.typinput,
        "typoutput" := row.typoutput,
        "typreceive" := row.typreceive,
        "typsend" := row.typsend,
        "typmodin" := row.typmodin,
        "typmodout" := row.typmodout,
        "typanalyze" := row.typanalyze,
        "typalign" := row.typalign,
        "typstorage" := row.typstorage,
        "typnotnull" := row.typnotnull,
        "typbasetype" := row.typbasetype,
        "typtypmod" := row.typtypmod,
        "typndims" := row.typndims,
        "typcollation" := row.typcollation,
        "typdefaultbin" := row.typdefaultbin,
        "typdefault" := row.typdefault,
        "typacl" := row.typacl
      )}
  implicit val read: Read[PgTypeRow] =
    new Read[PgTypeRow](
      gets = List(
        (Get[PgTypeId], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[Int], Nullability.NoNulls),
        (Get[Boolean], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[Boolean], Nullability.NoNulls),
        (Get[Boolean], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[TypoRegproc], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[TypoRegproc], Nullability.NoNulls),
        (Get[TypoRegproc], Nullability.NoNulls),
        (Get[TypoRegproc], Nullability.NoNulls),
        (Get[TypoRegproc], Nullability.NoNulls),
        (Get[TypoRegproc], Nullability.NoNulls),
        (Get[TypoRegproc], Nullability.NoNulls),
        (Get[TypoRegproc], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[String], Nullability.NoNulls),
        (Get[Boolean], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[Int], Nullability.NoNulls),
        (Get[Int], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[TypoPgNodeTree], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[Array[TypoAclItem]], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => PgTypeRow(
        oid = Get[PgTypeId].unsafeGetNonNullable(rs, i + 0),
        typname = Get[String].unsafeGetNonNullable(rs, i + 1),
        typnamespace = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 2),
        typowner = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 3),
        typlen = Get[Int].unsafeGetNonNullable(rs, i + 4),
        typbyval = Get[Boolean].unsafeGetNonNullable(rs, i + 5),
        typtype = Get[String].unsafeGetNonNullable(rs, i + 6),
        typcategory = Get[String].unsafeGetNonNullable(rs, i + 7),
        typispreferred = Get[Boolean].unsafeGetNonNullable(rs, i + 8),
        typisdefined = Get[Boolean].unsafeGetNonNullable(rs, i + 9),
        typdelim = Get[String].unsafeGetNonNullable(rs, i + 10),
        typrelid = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 11),
        typsubscript = Get[TypoRegproc].unsafeGetNonNullable(rs, i + 12),
        typelem = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 13),
        typarray = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 14),
        typinput = Get[TypoRegproc].unsafeGetNonNullable(rs, i + 15),
        typoutput = Get[TypoRegproc].unsafeGetNonNullable(rs, i + 16),
        typreceive = Get[TypoRegproc].unsafeGetNonNullable(rs, i + 17),
        typsend = Get[TypoRegproc].unsafeGetNonNullable(rs, i + 18),
        typmodin = Get[TypoRegproc].unsafeGetNonNullable(rs, i + 19),
        typmodout = Get[TypoRegproc].unsafeGetNonNullable(rs, i + 20),
        typanalyze = Get[TypoRegproc].unsafeGetNonNullable(rs, i + 21),
        typalign = Get[String].unsafeGetNonNullable(rs, i + 22),
        typstorage = Get[String].unsafeGetNonNullable(rs, i + 23),
        typnotnull = Get[Boolean].unsafeGetNonNullable(rs, i + 24),
        typbasetype = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 25),
        typtypmod = Get[Int].unsafeGetNonNullable(rs, i + 26),
        typndims = Get[Int].unsafeGetNonNullable(rs, i + 27),
        typcollation = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 28),
        typdefaultbin = Get[TypoPgNodeTree].unsafeGetNullable(rs, i + 29),
        typdefault = Get[String].unsafeGetNullable(rs, i + 30),
        typacl = Get[Array[TypoAclItem]].unsafeGetNullable(rs, i + 31)
      )
    )
  

}