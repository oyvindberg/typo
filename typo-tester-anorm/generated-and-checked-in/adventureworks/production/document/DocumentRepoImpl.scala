/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package document

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoBytea
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Flag
import anorm.BatchSql
import anorm.NamedParameter
import anorm.ParameterMetaData
import anorm.ParameterValue
import anorm.RowParser
import anorm.SQL
import anorm.SimpleSql
import anorm.SqlStringInterpolation
import anorm.ToStatement
import java.sql.Connection
import scala.annotation.nowarn
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class DocumentRepoImpl extends DocumentRepo {
  override def delete: DeleteBuilder[DocumentFields, DocumentRow] = {
    DeleteBuilder(""""production"."document"""", DocumentFields.structure)
  }
  override def deleteById(documentnode: DocumentId)(implicit c: Connection): Boolean = {
    SQL"""delete from "production"."document" where "documentnode" = ${ParameterValue(documentnode, null, DocumentId.toStatement)}""".executeUpdate() > 0
  }
  override def deleteByIds(documentnodes: Array[DocumentId])(implicit c: Connection): Int = {
    SQL"""delete
          from "production"."document"
          where "documentnode" = ANY(${documentnodes})
       """.executeUpdate()
    
  }
  override def insert(unsaved: DocumentRow)(implicit c: Connection): DocumentRow = {
    SQL"""insert into "production"."document"("title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate", "documentnode")
          values (${ParameterValue(unsaved.title, null, ToStatement.stringToStatement)}, ${ParameterValue(unsaved.owner, null, BusinessentityId.toStatement)}::int4, ${ParameterValue(unsaved.folderflag, null, Flag.toStatement)}::bool, ${ParameterValue(unsaved.filename, null, ToStatement.stringToStatement)}, ${ParameterValue(unsaved.fileextension, null, ToStatement.optionToStatement(ToStatement.stringToStatement, ParameterMetaData.StringParameterMetaData))}, ${ParameterValue(unsaved.revision, null, ToStatement.stringToStatement)}::bpchar, ${ParameterValue(unsaved.changenumber, null, ToStatement.intToStatement)}::int4, ${ParameterValue(unsaved.status, null, TypoShort.toStatement)}::int2, ${ParameterValue(unsaved.documentsummary, null, ToStatement.optionToStatement(ToStatement.stringToStatement, ParameterMetaData.StringParameterMetaData))}, ${ParameterValue(unsaved.document, null, ToStatement.optionToStatement(TypoBytea.toStatement, TypoBytea.parameterMetadata))}::bytea, ${ParameterValue(unsaved.rowguid, null, TypoUUID.toStatement)}::uuid, ${ParameterValue(unsaved.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp, ${ParameterValue(unsaved.documentnode, null, DocumentId.toStatement)})
          returning "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
       """
      .executeInsert(DocumentRow.rowParser(1).single)
    
  }
  override def insert(unsaved: DocumentRowUnsaved)(implicit c: Connection): DocumentRow = {
    val namedParameters = List(
      Some((NamedParameter("title", ParameterValue(unsaved.title, null, ToStatement.stringToStatement)), "")),
      Some((NamedParameter("owner", ParameterValue(unsaved.owner, null, BusinessentityId.toStatement)), "::int4")),
      Some((NamedParameter("filename", ParameterValue(unsaved.filename, null, ToStatement.stringToStatement)), "")),
      Some((NamedParameter("fileextension", ParameterValue(unsaved.fileextension, null, ToStatement.optionToStatement(ToStatement.stringToStatement, ParameterMetaData.StringParameterMetaData))), "")),
      Some((NamedParameter("revision", ParameterValue(unsaved.revision, null, ToStatement.stringToStatement)), "::bpchar")),
      Some((NamedParameter("status", ParameterValue(unsaved.status, null, TypoShort.toStatement)), "::int2")),
      Some((NamedParameter("documentsummary", ParameterValue(unsaved.documentsummary, null, ToStatement.optionToStatement(ToStatement.stringToStatement, ParameterMetaData.StringParameterMetaData))), "")),
      Some((NamedParameter("document", ParameterValue(unsaved.document, null, ToStatement.optionToStatement(TypoBytea.toStatement, TypoBytea.parameterMetadata))), "::bytea")),
      unsaved.folderflag match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("folderflag", ParameterValue(value, null, Flag.toStatement)), "::bool"))
      },
      unsaved.changenumber match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("changenumber", ParameterValue(value, null, ToStatement.intToStatement)), "::int4"))
      },
      unsaved.rowguid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("rowguid", ParameterValue(value, null, TypoUUID.toStatement)), "::uuid"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("modifieddate", ParameterValue(value, null, TypoLocalDateTime.toStatement)), "::timestamp"))
      },
      unsaved.documentnode match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("documentnode", ParameterValue(value, null, DocumentId.toStatement)), ""))
      }
    ).flatten
    val quote = '"'.toString
    if (namedParameters.isEmpty) {
      SQL"""insert into "production"."document" default values
            returning "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
         """
        .executeInsert(DocumentRow.rowParser(1).single)
    } else {
      val q = s"""insert into "production"."document"(${namedParameters.map{case (x, _) => quote + x.name + quote}.mkString(", ")})
                  values (${namedParameters.map{ case (np, cast) => s"{${np.name}}$cast"}.mkString(", ")})
                  returning "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
               """
      SimpleSql(SQL(q), namedParameters.map { case (np, _) => np.tupled }.toMap, RowParser.successful)
        .executeInsert(DocumentRow.rowParser(1).single)
    }
    
  }
  override def insertStreaming(unsaved: Iterator[DocumentRow], batchSize: Int = 10000)(implicit c: Connection): Long = {
    streamingInsert(s"""COPY "production"."document"("title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate", "documentnode") FROM STDIN""", batchSize, unsaved)(DocumentRow.text, c)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Iterator[DocumentRowUnsaved], batchSize: Int = 10000)(implicit c: Connection): Long = {
    streamingInsert(s"""COPY "production"."document"("title", "owner", "filename", "fileextension", "revision", "status", "documentsummary", "document", "folderflag", "changenumber", "rowguid", "modifieddate", "documentnode") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""", batchSize, unsaved)(DocumentRowUnsaved.text, c)
  }
  override def select: SelectBuilder[DocumentFields, DocumentRow] = {
    SelectBuilderSql(""""production"."document"""", DocumentFields.structure, DocumentRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[DocumentRow] = {
    SQL"""select "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
          from "production"."document"
       """.as(DocumentRow.rowParser(1).*)
  }
  override def selectById(documentnode: DocumentId)(implicit c: Connection): Option[DocumentRow] = {
    SQL"""select "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
          from "production"."document"
          where "documentnode" = ${ParameterValue(documentnode, null, DocumentId.toStatement)}
       """.as(DocumentRow.rowParser(1).singleOpt)
  }
  override def selectByIds(documentnodes: Array[DocumentId])(implicit c: Connection): List[DocumentRow] = {
    SQL"""select "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
          from "production"."document"
          where "documentnode" = ANY(${documentnodes})
       """.as(DocumentRow.rowParser(1).*)
    
  }
  override def selectByIdsTracked(documentnodes: Array[DocumentId])(implicit c: Connection): Map[DocumentId, DocumentRow] = {
    val byId = selectByIds(documentnodes).view.map(x => (x.documentnode, x)).toMap
    documentnodes.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
  }
  override def selectByUniqueRowguid(rowguid: TypoUUID)(implicit c: Connection): Option[DocumentRow] = {
    SQL"""select "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
          from "production"."document"
          where "rowguid" = ${ParameterValue(rowguid, null, TypoUUID.toStatement)}
       """.as(DocumentRow.rowParser(1).singleOpt)
    
  }
  override def update: UpdateBuilder[DocumentFields, DocumentRow] = {
    UpdateBuilder(""""production"."document"""", DocumentFields.structure, DocumentRow.rowParser)
  }
  override def update(row: DocumentRow)(implicit c: Connection): Option[DocumentRow] = {
    val documentnode = row.documentnode
    SQL"""update "production"."document"
          set "title" = ${ParameterValue(row.title, null, ToStatement.stringToStatement)},
              "owner" = ${ParameterValue(row.owner, null, BusinessentityId.toStatement)}::int4,
              "folderflag" = ${ParameterValue(row.folderflag, null, Flag.toStatement)}::bool,
              "filename" = ${ParameterValue(row.filename, null, ToStatement.stringToStatement)},
              "fileextension" = ${ParameterValue(row.fileextension, null, ToStatement.optionToStatement(ToStatement.stringToStatement, ParameterMetaData.StringParameterMetaData))},
              "revision" = ${ParameterValue(row.revision, null, ToStatement.stringToStatement)}::bpchar,
              "changenumber" = ${ParameterValue(row.changenumber, null, ToStatement.intToStatement)}::int4,
              "status" = ${ParameterValue(row.status, null, TypoShort.toStatement)}::int2,
              "documentsummary" = ${ParameterValue(row.documentsummary, null, ToStatement.optionToStatement(ToStatement.stringToStatement, ParameterMetaData.StringParameterMetaData))},
              "document" = ${ParameterValue(row.document, null, ToStatement.optionToStatement(TypoBytea.toStatement, TypoBytea.parameterMetadata))}::bytea,
              "rowguid" = ${ParameterValue(row.rowguid, null, TypoUUID.toStatement)}::uuid,
              "modifieddate" = ${ParameterValue(row.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp
          where "documentnode" = ${ParameterValue(documentnode, null, DocumentId.toStatement)}
          returning "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
       """.executeInsert(DocumentRow.rowParser(1).singleOpt)
  }
  override def upsert(unsaved: DocumentRow)(implicit c: Connection): DocumentRow = {
    SQL"""insert into "production"."document"("title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate", "documentnode")
          values (
            ${ParameterValue(unsaved.title, null, ToStatement.stringToStatement)},
            ${ParameterValue(unsaved.owner, null, BusinessentityId.toStatement)}::int4,
            ${ParameterValue(unsaved.folderflag, null, Flag.toStatement)}::bool,
            ${ParameterValue(unsaved.filename, null, ToStatement.stringToStatement)},
            ${ParameterValue(unsaved.fileextension, null, ToStatement.optionToStatement(ToStatement.stringToStatement, ParameterMetaData.StringParameterMetaData))},
            ${ParameterValue(unsaved.revision, null, ToStatement.stringToStatement)}::bpchar,
            ${ParameterValue(unsaved.changenumber, null, ToStatement.intToStatement)}::int4,
            ${ParameterValue(unsaved.status, null, TypoShort.toStatement)}::int2,
            ${ParameterValue(unsaved.documentsummary, null, ToStatement.optionToStatement(ToStatement.stringToStatement, ParameterMetaData.StringParameterMetaData))},
            ${ParameterValue(unsaved.document, null, ToStatement.optionToStatement(TypoBytea.toStatement, TypoBytea.parameterMetadata))}::bytea,
            ${ParameterValue(unsaved.rowguid, null, TypoUUID.toStatement)}::uuid,
            ${ParameterValue(unsaved.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp,
            ${ParameterValue(unsaved.documentnode, null, DocumentId.toStatement)}
          )
          on conflict ("documentnode")
          do update set
            "title" = EXCLUDED."title",
            "owner" = EXCLUDED."owner",
            "folderflag" = EXCLUDED."folderflag",
            "filename" = EXCLUDED."filename",
            "fileextension" = EXCLUDED."fileextension",
            "revision" = EXCLUDED."revision",
            "changenumber" = EXCLUDED."changenumber",
            "status" = EXCLUDED."status",
            "documentsummary" = EXCLUDED."documentsummary",
            "document" = EXCLUDED."document",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
       """
      .executeInsert(DocumentRow.rowParser(1).single)
    
  }
  override def upsertBatch(unsaved: Iterable[DocumentRow])(implicit c: Connection): List[DocumentRow] = {
    def toNamedParameter(row: DocumentRow): List[NamedParameter] = List(
      NamedParameter("title", ParameterValue(row.title, null, ToStatement.stringToStatement)),
      NamedParameter("owner", ParameterValue(row.owner, null, BusinessentityId.toStatement)),
      NamedParameter("folderflag", ParameterValue(row.folderflag, null, Flag.toStatement)),
      NamedParameter("filename", ParameterValue(row.filename, null, ToStatement.stringToStatement)),
      NamedParameter("fileextension", ParameterValue(row.fileextension, null, ToStatement.optionToStatement(ToStatement.stringToStatement, ParameterMetaData.StringParameterMetaData))),
      NamedParameter("revision", ParameterValue(row.revision, null, ToStatement.stringToStatement)),
      NamedParameter("changenumber", ParameterValue(row.changenumber, null, ToStatement.intToStatement)),
      NamedParameter("status", ParameterValue(row.status, null, TypoShort.toStatement)),
      NamedParameter("documentsummary", ParameterValue(row.documentsummary, null, ToStatement.optionToStatement(ToStatement.stringToStatement, ParameterMetaData.StringParameterMetaData))),
      NamedParameter("document", ParameterValue(row.document, null, ToStatement.optionToStatement(TypoBytea.toStatement, TypoBytea.parameterMetadata))),
      NamedParameter("rowguid", ParameterValue(row.rowguid, null, TypoUUID.toStatement)),
      NamedParameter("modifieddate", ParameterValue(row.modifieddate, null, TypoLocalDateTime.toStatement)),
      NamedParameter("documentnode", ParameterValue(row.documentnode, null, DocumentId.toStatement))
    )
    unsaved.toList match {
      case Nil => Nil
      case head :: rest =>
        new anorm.adventureworks.ExecuteReturningSyntax.Ops(
          BatchSql(
            s"""insert into "production"."document"("title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate", "documentnode")
                values ({title}, {owner}::int4, {folderflag}::bool, {filename}, {fileextension}, {revision}::bpchar, {changenumber}::int4, {status}::int2, {documentsummary}, {document}::bytea, {rowguid}::uuid, {modifieddate}::timestamp, {documentnode})
                on conflict ("documentnode")
                do update set
                  "title" = EXCLUDED."title",
                  "owner" = EXCLUDED."owner",
                  "folderflag" = EXCLUDED."folderflag",
                  "filename" = EXCLUDED."filename",
                  "fileextension" = EXCLUDED."fileextension",
                  "revision" = EXCLUDED."revision",
                  "changenumber" = EXCLUDED."changenumber",
                  "status" = EXCLUDED."status",
                  "documentsummary" = EXCLUDED."documentsummary",
                  "document" = EXCLUDED."document",
                  "rowguid" = EXCLUDED."rowguid",
                  "modifieddate" = EXCLUDED."modifieddate"
                returning "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
             """,
            toNamedParameter(head),
            rest.map(toNamedParameter)*
          )
        ).executeReturning(DocumentRow.rowParser(1).*)
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Iterator[DocumentRow], batchSize: Int = 10000)(implicit c: Connection): Int = {
    SQL"""create temporary table document_TEMP (like "production"."document") on commit drop""".execute(): @nowarn
    streamingInsert(s"""copy document_TEMP("title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate", "documentnode") from stdin""", batchSize, unsaved)(DocumentRow.text, c): @nowarn
    SQL"""insert into "production"."document"("title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate", "documentnode")
          select * from document_TEMP
          on conflict ("documentnode")
          do update set
            "title" = EXCLUDED."title",
            "owner" = EXCLUDED."owner",
            "folderflag" = EXCLUDED."folderflag",
            "filename" = EXCLUDED."filename",
            "fileextension" = EXCLUDED."fileextension",
            "revision" = EXCLUDED."revision",
            "changenumber" = EXCLUDED."changenumber",
            "status" = EXCLUDED."status",
            "documentsummary" = EXCLUDED."documentsummary",
            "document" = EXCLUDED."document",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          ;
          drop table document_TEMP;""".executeUpdate()
  }
}
