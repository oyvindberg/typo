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
import adventureworks.streamingInsert
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.SqlFragment
import zio.jdbc.SqlFragment.Segment
import zio.jdbc.SqlFragment.Setter
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class DocumentRepoImpl extends DocumentRepo {
  override def delete(documentnode: DocumentId): ZIO[ZConnection, Throwable, Boolean] = {
    sql"""delete from production.document where "documentnode" = ${Segment.paramSegment(documentnode)(DocumentId.setter)}""".delete.map(_ > 0)
  }
  override def delete: DeleteBuilder[DocumentFields, DocumentRow] = {
    DeleteBuilder("production.document", DocumentFields.structure)
  }
  override def insert(unsaved: DocumentRow): ZIO[ZConnection, Throwable, DocumentRow] = {
    sql"""insert into production.document("title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate", "documentnode")
          values (${Segment.paramSegment(unsaved.title)(Setter.stringSetter)}, ${Segment.paramSegment(unsaved.owner)(BusinessentityId.setter)}::int4, ${Segment.paramSegment(unsaved.folderflag)(Flag.setter)}::bool, ${Segment.paramSegment(unsaved.filename)(Setter.stringSetter)}, ${Segment.paramSegment(unsaved.fileextension)(Setter.optionParamSetter(Setter.stringSetter))}, ${Segment.paramSegment(unsaved.revision)(Setter.stringSetter)}::bpchar, ${Segment.paramSegment(unsaved.changenumber)(Setter.intSetter)}::int4, ${Segment.paramSegment(unsaved.status)(TypoShort.setter)}::int2, ${Segment.paramSegment(unsaved.documentsummary)(Setter.optionParamSetter(Setter.stringSetter))}, ${Segment.paramSegment(unsaved.document)(Setter.optionParamSetter(TypoBytea.setter))}::bytea, ${Segment.paramSegment(unsaved.rowguid)(TypoUUID.setter)}::uuid, ${Segment.paramSegment(unsaved.modifieddate)(TypoLocalDateTime.setter)}::timestamp, ${Segment.paramSegment(unsaved.documentnode)(DocumentId.setter)})
          returning "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
       """.insertReturning(using DocumentRow.jdbcDecoder).map(_.updatedKeys.head)
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, DocumentRow], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY production.document("title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate", "documentnode") FROM STDIN""", batchSize, unsaved)(DocumentRow.text)
  }
  override def insert(unsaved: DocumentRowUnsaved): ZIO[ZConnection, Throwable, DocumentRow] = {
    val fs = List(
      Some((sql""""title"""", sql"${Segment.paramSegment(unsaved.title)(Setter.stringSetter)}")),
      Some((sql""""owner"""", sql"${Segment.paramSegment(unsaved.owner)(BusinessentityId.setter)}::int4")),
      Some((sql""""filename"""", sql"${Segment.paramSegment(unsaved.filename)(Setter.stringSetter)}")),
      Some((sql""""fileextension"""", sql"${Segment.paramSegment(unsaved.fileextension)(Setter.optionParamSetter(Setter.stringSetter))}")),
      Some((sql""""revision"""", sql"${Segment.paramSegment(unsaved.revision)(Setter.stringSetter)}::bpchar")),
      Some((sql""""status"""", sql"${Segment.paramSegment(unsaved.status)(TypoShort.setter)}::int2")),
      Some((sql""""documentsummary"""", sql"${Segment.paramSegment(unsaved.documentsummary)(Setter.optionParamSetter(Setter.stringSetter))}")),
      Some((sql""""document"""", sql"${Segment.paramSegment(unsaved.document)(Setter.optionParamSetter(TypoBytea.setter))}::bytea")),
      unsaved.folderflag match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""folderflag"""", sql"${Segment.paramSegment(value: Flag)(Flag.setter)}::bool"))
      },
      unsaved.changenumber match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""changenumber"""", sql"${Segment.paramSegment(value: Int)(Setter.intSetter)}::int4"))
      },
      unsaved.rowguid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""rowguid"""", sql"${Segment.paramSegment(value: TypoUUID)(TypoUUID.setter)}::uuid"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""modifieddate"""", sql"${Segment.paramSegment(value: TypoLocalDateTime)(TypoLocalDateTime.setter)}::timestamp"))
      },
      unsaved.documentnode match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((sql""""documentnode"""", sql"${Segment.paramSegment(value: DocumentId)(DocumentId.setter)}"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into production.document default values
            returning "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
         """
    } else {
      val names  = fs.map { case (n, _) => n }.mkFragment(SqlFragment(", "))
      val values = fs.map { case (_, f) => f }.mkFragment(SqlFragment(", "))
      sql"""insert into production.document($names) values ($values) returning "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode""""
    }
    q.insertReturning(using DocumentRow.jdbcDecoder).map(_.updatedKeys.head)
    
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, DocumentRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    streamingInsert(s"""COPY production.document("title", "owner", "filename", "fileextension", "revision", "status", "documentsummary", "document", "folderflag", "changenumber", "rowguid", "modifieddate", "documentnode") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""", batchSize, unsaved)(DocumentRowUnsaved.text)
  }
  override def select: SelectBuilder[DocumentFields, DocumentRow] = {
    SelectBuilderSql("production.document", DocumentFields.structure, DocumentRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, DocumentRow] = {
    sql"""select "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode" from production.document""".query(using DocumentRow.jdbcDecoder).selectStream()
  }
  override def selectById(documentnode: DocumentId): ZIO[ZConnection, Throwable, Option[DocumentRow]] = {
    sql"""select "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode" from production.document where "documentnode" = ${Segment.paramSegment(documentnode)(DocumentId.setter)}""".query(using DocumentRow.jdbcDecoder).selectOne
  }
  override def selectByIds(documentnodes: Array[DocumentId]): ZStream[ZConnection, Throwable, DocumentRow] = {
    sql"""select "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode" from production.document where "documentnode" = ANY(${Segment.paramSegment(documentnodes)(DocumentId.arraySetter)})""".query(using DocumentRow.jdbcDecoder).selectStream()
  }
  override def selectByUniqueRowguid(rowguid: TypoUUID): ZIO[ZConnection, Throwable, Option[DocumentRow]] = {
    sql"""select "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"
          from production.document
          where "rowguid" = ${Segment.paramSegment(rowguid)(TypoUUID.setter)}
       """.query(using DocumentRow.jdbcDecoder).selectOne
  }
  override def update(row: DocumentRow): ZIO[ZConnection, Throwable, Boolean] = {
    val documentnode = row.documentnode
    sql"""update production.document
          set "title" = ${Segment.paramSegment(row.title)(Setter.stringSetter)},
              "owner" = ${Segment.paramSegment(row.owner)(BusinessentityId.setter)}::int4,
              "folderflag" = ${Segment.paramSegment(row.folderflag)(Flag.setter)}::bool,
              "filename" = ${Segment.paramSegment(row.filename)(Setter.stringSetter)},
              "fileextension" = ${Segment.paramSegment(row.fileextension)(Setter.optionParamSetter(Setter.stringSetter))},
              "revision" = ${Segment.paramSegment(row.revision)(Setter.stringSetter)}::bpchar,
              "changenumber" = ${Segment.paramSegment(row.changenumber)(Setter.intSetter)}::int4,
              "status" = ${Segment.paramSegment(row.status)(TypoShort.setter)}::int2,
              "documentsummary" = ${Segment.paramSegment(row.documentsummary)(Setter.optionParamSetter(Setter.stringSetter))},
              "document" = ${Segment.paramSegment(row.document)(Setter.optionParamSetter(TypoBytea.setter))}::bytea,
              "rowguid" = ${Segment.paramSegment(row.rowguid)(TypoUUID.setter)}::uuid,
              "modifieddate" = ${Segment.paramSegment(row.modifieddate)(TypoLocalDateTime.setter)}::timestamp
          where "documentnode" = ${Segment.paramSegment(documentnode)(DocumentId.setter)}""".update.map(_ > 0)
  }
  override def update: UpdateBuilder[DocumentFields, DocumentRow] = {
    UpdateBuilder("production.document", DocumentFields.structure, DocumentRow.jdbcDecoder)
  }
  override def upsert(unsaved: DocumentRow): ZIO[ZConnection, Throwable, UpdateResult[DocumentRow]] = {
    sql"""insert into production.document("title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate", "documentnode")
          values (
            ${Segment.paramSegment(unsaved.title)(Setter.stringSetter)},
            ${Segment.paramSegment(unsaved.owner)(BusinessentityId.setter)}::int4,
            ${Segment.paramSegment(unsaved.folderflag)(Flag.setter)}::bool,
            ${Segment.paramSegment(unsaved.filename)(Setter.stringSetter)},
            ${Segment.paramSegment(unsaved.fileextension)(Setter.optionParamSetter(Setter.stringSetter))},
            ${Segment.paramSegment(unsaved.revision)(Setter.stringSetter)}::bpchar,
            ${Segment.paramSegment(unsaved.changenumber)(Setter.intSetter)}::int4,
            ${Segment.paramSegment(unsaved.status)(TypoShort.setter)}::int2,
            ${Segment.paramSegment(unsaved.documentsummary)(Setter.optionParamSetter(Setter.stringSetter))},
            ${Segment.paramSegment(unsaved.document)(Setter.optionParamSetter(TypoBytea.setter))}::bytea,
            ${Segment.paramSegment(unsaved.rowguid)(TypoUUID.setter)}::uuid,
            ${Segment.paramSegment(unsaved.modifieddate)(TypoLocalDateTime.setter)}::timestamp,
            ${Segment.paramSegment(unsaved.documentnode)(DocumentId.setter)}
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
          returning "title", "owner", "folderflag", "filename", "fileextension", "revision", "changenumber", "status", "documentsummary", "document", "rowguid", "modifieddate"::text, "documentnode"""".insertReturning(using DocumentRow.jdbcDecoder)
  }
}