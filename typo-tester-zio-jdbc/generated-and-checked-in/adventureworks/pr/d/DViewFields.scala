/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package d

import adventureworks.customtypes.TypoBytea
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.production.document.DocumentId
import adventureworks.public.Flag
import typo.dsl.Path
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait DViewFields {
  def title: Field[/* max 50 chars */ String, DViewRow]
  def owner: Field[BusinessentityId, DViewRow]
  def folderflag: Field[Flag, DViewRow]
  def filename: Field[/* max 400 chars */ String, DViewRow]
  def fileextension: OptField[/* max 8 chars */ String, DViewRow]
  def revision: Field[/* bpchar, max 5 chars */ String, DViewRow]
  def changenumber: Field[Int, DViewRow]
  def status: Field[TypoShort, DViewRow]
  def documentsummary: OptField[String, DViewRow]
  def document: OptField[TypoBytea, DViewRow]
  def rowguid: Field[TypoUUID, DViewRow]
  def modifieddate: Field[TypoLocalDateTime, DViewRow]
  def documentnode: Field[DocumentId, DViewRow]
}

object DViewFields {
  lazy val structure: Relation[DViewFields, DViewRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[DViewFields, DViewRow] {
  
    override lazy val fields: DViewFields = new DViewFields {
      override def title = Field[/* max 50 chars */ String, DViewRow](_path, "title", None, None, x => x.title, (row, value) => row.copy(title = value))
      override def owner = Field[BusinessentityId, DViewRow](_path, "owner", None, None, x => x.owner, (row, value) => row.copy(owner = value))
      override def folderflag = Field[Flag, DViewRow](_path, "folderflag", None, None, x => x.folderflag, (row, value) => row.copy(folderflag = value))
      override def filename = Field[/* max 400 chars */ String, DViewRow](_path, "filename", None, None, x => x.filename, (row, value) => row.copy(filename = value))
      override def fileextension = OptField[/* max 8 chars */ String, DViewRow](_path, "fileextension", None, None, x => x.fileextension, (row, value) => row.copy(fileextension = value))
      override def revision = Field[/* bpchar, max 5 chars */ String, DViewRow](_path, "revision", None, None, x => x.revision, (row, value) => row.copy(revision = value))
      override def changenumber = Field[Int, DViewRow](_path, "changenumber", None, None, x => x.changenumber, (row, value) => row.copy(changenumber = value))
      override def status = Field[TypoShort, DViewRow](_path, "status", None, None, x => x.status, (row, value) => row.copy(status = value))
      override def documentsummary = OptField[String, DViewRow](_path, "documentsummary", None, None, x => x.documentsummary, (row, value) => row.copy(documentsummary = value))
      override def document = OptField[TypoBytea, DViewRow](_path, "document", None, None, x => x.document, (row, value) => row.copy(document = value))
      override def rowguid = Field[TypoUUID, DViewRow](_path, "rowguid", None, None, x => x.rowguid, (row, value) => row.copy(rowguid = value))
      override def modifieddate = Field[TypoLocalDateTime, DViewRow](_path, "modifieddate", Some("text"), None, x => x.modifieddate, (row, value) => row.copy(modifieddate = value))
      override def documentnode = Field[DocumentId, DViewRow](_path, "documentnode", None, None, x => x.documentnode, (row, value) => row.copy(documentnode = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, DViewRow]] =
      List[FieldLikeNoHkt[?, DViewRow]](fields.title, fields.owner, fields.folderflag, fields.filename, fields.fileextension, fields.revision, fields.changenumber, fields.status, fields.documentsummary, fields.document, fields.rowguid, fields.modifieddate, fields.documentnode)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
