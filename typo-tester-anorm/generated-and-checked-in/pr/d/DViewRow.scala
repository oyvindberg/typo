/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package d

import adventureworks.TypoLocalDateTime
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.production.document.DocumentId
import adventureworks.public.Flag
import anorm.Column
import anorm.RowParser
import anorm.Success
import java.util.UUID
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class DViewRow(
  /** Points to [[production.document.DocumentRow.title]] */
  title: Option[/* max 50 chars */ String],
  /** Points to [[production.document.DocumentRow.owner]] */
  owner: Option[BusinessentityId],
  /** Points to [[production.document.DocumentRow.folderflag]] */
  folderflag: Flag,
  /** Points to [[production.document.DocumentRow.filename]] */
  filename: Option[/* max 400 chars */ String],
  /** Points to [[production.document.DocumentRow.fileextension]] */
  fileextension: Option[/* max 8 chars */ String],
  /** Points to [[production.document.DocumentRow.revision]] */
  revision: Option[/* bpchar */ String],
  /** Points to [[production.document.DocumentRow.changenumber]] */
  changenumber: Option[Int],
  /** Points to [[production.document.DocumentRow.status]] */
  status: Option[Int],
  /** Points to [[production.document.DocumentRow.documentsummary]] */
  documentsummary: Option[String],
  /** Points to [[production.document.DocumentRow.document]] */
  document: Option[Byte],
  /** Points to [[production.document.DocumentRow.rowguid]] */
  rowguid: Option[UUID],
  /** Points to [[production.document.DocumentRow.modifieddate]] */
  modifieddate: Option[TypoLocalDateTime],
  /** Points to [[production.document.DocumentRow.documentnode]] */
  documentnode: Option[DocumentId]
)

object DViewRow {
  implicit lazy val reads: Reads[DViewRow] = Reads[DViewRow](json => JsResult.fromTry(
      Try(
        DViewRow(
          title = json.\("title").toOption.map(_.as(Reads.StringReads)),
          owner = json.\("owner").toOption.map(_.as(BusinessentityId.reads)),
          folderflag = json.\("folderflag").as(Flag.reads),
          filename = json.\("filename").toOption.map(_.as(Reads.StringReads)),
          fileextension = json.\("fileextension").toOption.map(_.as(Reads.StringReads)),
          revision = json.\("revision").toOption.map(_.as(Reads.StringReads)),
          changenumber = json.\("changenumber").toOption.map(_.as(Reads.IntReads)),
          status = json.\("status").toOption.map(_.as(Reads.IntReads)),
          documentsummary = json.\("documentsummary").toOption.map(_.as(Reads.StringReads)),
          document = json.\("document").toOption.map(_.as(Reads.ByteReads)),
          rowguid = json.\("rowguid").toOption.map(_.as(Reads.uuidReads)),
          modifieddate = json.\("modifieddate").toOption.map(_.as(TypoLocalDateTime.reads)),
          documentnode = json.\("documentnode").toOption.map(_.as(DocumentId.reads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[DViewRow] = RowParser[DViewRow] { row =>
    Success(
      DViewRow(
        title = row(idx + 0)(Column.columnToOption(Column.columnToString)),
        owner = row(idx + 1)(Column.columnToOption(BusinessentityId.column)),
        folderflag = row(idx + 2)(Flag.column),
        filename = row(idx + 3)(Column.columnToOption(Column.columnToString)),
        fileextension = row(idx + 4)(Column.columnToOption(Column.columnToString)),
        revision = row(idx + 5)(Column.columnToOption(Column.columnToString)),
        changenumber = row(idx + 6)(Column.columnToOption(Column.columnToInt)),
        status = row(idx + 7)(Column.columnToOption(Column.columnToInt)),
        documentsummary = row(idx + 8)(Column.columnToOption(Column.columnToString)),
        document = row(idx + 9)(Column.columnToOption(Column.columnToByte)),
        rowguid = row(idx + 10)(Column.columnToOption(Column.columnToUUID)),
        modifieddate = row(idx + 11)(Column.columnToOption(TypoLocalDateTime.column)),
        documentnode = row(idx + 12)(Column.columnToOption(DocumentId.column))
      )
    )
  }
  implicit lazy val writes: OWrites[DViewRow] = OWrites[DViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "title" -> Writes.OptionWrites(Writes.StringWrites).writes(o.title),
      "owner" -> Writes.OptionWrites(BusinessentityId.writes).writes(o.owner),
      "folderflag" -> Flag.writes.writes(o.folderflag),
      "filename" -> Writes.OptionWrites(Writes.StringWrites).writes(o.filename),
      "fileextension" -> Writes.OptionWrites(Writes.StringWrites).writes(o.fileextension),
      "revision" -> Writes.OptionWrites(Writes.StringWrites).writes(o.revision),
      "changenumber" -> Writes.OptionWrites(Writes.IntWrites).writes(o.changenumber),
      "status" -> Writes.OptionWrites(Writes.IntWrites).writes(o.status),
      "documentsummary" -> Writes.OptionWrites(Writes.StringWrites).writes(o.documentsummary),
      "document" -> Writes.OptionWrites(Writes.ByteWrites).writes(o.document),
      "rowguid" -> Writes.OptionWrites(Writes.UuidWrites).writes(o.rowguid),
      "modifieddate" -> Writes.OptionWrites(TypoLocalDateTime.writes).writes(o.modifieddate),
      "documentnode" -> Writes.OptionWrites(DocumentId.writes).writes(o.documentnode)
    ))
  )
}