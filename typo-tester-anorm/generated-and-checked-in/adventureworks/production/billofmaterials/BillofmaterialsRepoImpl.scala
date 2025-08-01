/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package billofmaterials

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.production.product.ProductId
import adventureworks.production.unitmeasure.UnitmeasureId
import anorm.BatchSql
import anorm.NamedParameter
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

class BillofmaterialsRepoImpl extends BillofmaterialsRepo {
  override def delete: DeleteBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    DeleteBuilder(""""production"."billofmaterials"""", BillofmaterialsFields.structure)
  }
  override def deleteById(billofmaterialsid: Int)(implicit c: Connection): Boolean = {
    SQL"""delete from "production"."billofmaterials" where "billofmaterialsid" = ${ParameterValue(billofmaterialsid, null, ToStatement.intToStatement)}""".executeUpdate() > 0
  }
  override def deleteByIds(billofmaterialsids: Array[Int])(implicit c: Connection): Int = {
    SQL"""delete
          from "production"."billofmaterials"
          where "billofmaterialsid" = ANY(${billofmaterialsids})
       """.executeUpdate()
    
  }
  override def insert(unsaved: BillofmaterialsRow)(implicit c: Connection): BillofmaterialsRow = {
    SQL"""insert into "production"."billofmaterials"("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate")
          values (${ParameterValue(unsaved.billofmaterialsid, null, ToStatement.intToStatement)}::int4, ${ParameterValue(unsaved.productassemblyid, null, ToStatement.optionToStatement(ProductId.toStatement, ProductId.parameterMetadata))}::int4, ${ParameterValue(unsaved.componentid, null, ProductId.toStatement)}::int4, ${ParameterValue(unsaved.startdate, null, TypoLocalDateTime.toStatement)}::timestamp, ${ParameterValue(unsaved.enddate, null, ToStatement.optionToStatement(TypoLocalDateTime.toStatement, TypoLocalDateTime.parameterMetadata))}::timestamp, ${ParameterValue(unsaved.unitmeasurecode, null, UnitmeasureId.toStatement)}::bpchar, ${ParameterValue(unsaved.bomlevel, null, TypoShort.toStatement)}::int2, ${ParameterValue(unsaved.perassemblyqty, null, ToStatement.scalaBigDecimalToStatement)}::numeric, ${ParameterValue(unsaved.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp)
          returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
       """
      .executeInsert(BillofmaterialsRow.rowParser(1).single)
    
  }
  override def insert(unsaved: BillofmaterialsRowUnsaved)(implicit c: Connection): BillofmaterialsRow = {
    val namedParameters = List(
      Some((NamedParameter("productassemblyid", ParameterValue(unsaved.productassemblyid, null, ToStatement.optionToStatement(ProductId.toStatement, ProductId.parameterMetadata))), "::int4")),
      Some((NamedParameter("componentid", ParameterValue(unsaved.componentid, null, ProductId.toStatement)), "::int4")),
      Some((NamedParameter("enddate", ParameterValue(unsaved.enddate, null, ToStatement.optionToStatement(TypoLocalDateTime.toStatement, TypoLocalDateTime.parameterMetadata))), "::timestamp")),
      Some((NamedParameter("unitmeasurecode", ParameterValue(unsaved.unitmeasurecode, null, UnitmeasureId.toStatement)), "::bpchar")),
      Some((NamedParameter("bomlevel", ParameterValue(unsaved.bomlevel, null, TypoShort.toStatement)), "::int2")),
      unsaved.billofmaterialsid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("billofmaterialsid", ParameterValue(value, null, ToStatement.intToStatement)), "::int4"))
      },
      unsaved.startdate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("startdate", ParameterValue(value, null, TypoLocalDateTime.toStatement)), "::timestamp"))
      },
      unsaved.perassemblyqty match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("perassemblyqty", ParameterValue(value, null, ToStatement.scalaBigDecimalToStatement)), "::numeric"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("modifieddate", ParameterValue(value, null, TypoLocalDateTime.toStatement)), "::timestamp"))
      }
    ).flatten
    val quote = '"'.toString
    if (namedParameters.isEmpty) {
      SQL"""insert into "production"."billofmaterials" default values
            returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
         """
        .executeInsert(BillofmaterialsRow.rowParser(1).single)
    } else {
      val q = s"""insert into "production"."billofmaterials"(${namedParameters.map{case (x, _) => quote + x.name + quote}.mkString(", ")})
                  values (${namedParameters.map{ case (np, cast) => s"{${np.name}}$cast"}.mkString(", ")})
                  returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
               """
      SimpleSql(SQL(q), namedParameters.map { case (np, _) => np.tupled }.toMap, RowParser.successful)
        .executeInsert(BillofmaterialsRow.rowParser(1).single)
    }
    
  }
  override def insertStreaming(unsaved: Iterator[BillofmaterialsRow], batchSize: Int = 10000)(implicit c: Connection): Long = {
    streamingInsert(s"""COPY "production"."billofmaterials"("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate") FROM STDIN""", batchSize, unsaved)(BillofmaterialsRow.text, c)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Iterator[BillofmaterialsRowUnsaved], batchSize: Int = 10000)(implicit c: Connection): Long = {
    streamingInsert(s"""COPY "production"."billofmaterials"("productassemblyid", "componentid", "enddate", "unitmeasurecode", "bomlevel", "billofmaterialsid", "startdate", "perassemblyqty", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""", batchSize, unsaved)(BillofmaterialsRowUnsaved.text, c)
  }
  override def select: SelectBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    SelectBuilderSql(""""production"."billofmaterials"""", BillofmaterialsFields.structure, BillofmaterialsRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[BillofmaterialsRow] = {
    SQL"""select "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
          from "production"."billofmaterials"
       """.as(BillofmaterialsRow.rowParser(1).*)
  }
  override def selectById(billofmaterialsid: Int)(implicit c: Connection): Option[BillofmaterialsRow] = {
    SQL"""select "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
          from "production"."billofmaterials"
          where "billofmaterialsid" = ${ParameterValue(billofmaterialsid, null, ToStatement.intToStatement)}
       """.as(BillofmaterialsRow.rowParser(1).singleOpt)
  }
  override def selectByIds(billofmaterialsids: Array[Int])(implicit c: Connection): List[BillofmaterialsRow] = {
    SQL"""select "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
          from "production"."billofmaterials"
          where "billofmaterialsid" = ANY(${billofmaterialsids})
       """.as(BillofmaterialsRow.rowParser(1).*)
    
  }
  override def selectByIdsTracked(billofmaterialsids: Array[Int])(implicit c: Connection): Map[Int, BillofmaterialsRow] = {
    val byId = selectByIds(billofmaterialsids).view.map(x => (x.billofmaterialsid, x)).toMap
    billofmaterialsids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
  }
  override def update: UpdateBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    UpdateBuilder(""""production"."billofmaterials"""", BillofmaterialsFields.structure, BillofmaterialsRow.rowParser)
  }
  override def update(row: BillofmaterialsRow)(implicit c: Connection): Option[BillofmaterialsRow] = {
    val billofmaterialsid = row.billofmaterialsid
    SQL"""update "production"."billofmaterials"
          set "productassemblyid" = ${ParameterValue(row.productassemblyid, null, ToStatement.optionToStatement(ProductId.toStatement, ProductId.parameterMetadata))}::int4,
              "componentid" = ${ParameterValue(row.componentid, null, ProductId.toStatement)}::int4,
              "startdate" = ${ParameterValue(row.startdate, null, TypoLocalDateTime.toStatement)}::timestamp,
              "enddate" = ${ParameterValue(row.enddate, null, ToStatement.optionToStatement(TypoLocalDateTime.toStatement, TypoLocalDateTime.parameterMetadata))}::timestamp,
              "unitmeasurecode" = ${ParameterValue(row.unitmeasurecode, null, UnitmeasureId.toStatement)}::bpchar,
              "bomlevel" = ${ParameterValue(row.bomlevel, null, TypoShort.toStatement)}::int2,
              "perassemblyqty" = ${ParameterValue(row.perassemblyqty, null, ToStatement.scalaBigDecimalToStatement)}::numeric,
              "modifieddate" = ${ParameterValue(row.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp
          where "billofmaterialsid" = ${ParameterValue(billofmaterialsid, null, ToStatement.intToStatement)}
          returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
       """.executeInsert(BillofmaterialsRow.rowParser(1).singleOpt)
  }
  override def upsert(unsaved: BillofmaterialsRow)(implicit c: Connection): BillofmaterialsRow = {
    SQL"""insert into "production"."billofmaterials"("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate")
          values (
            ${ParameterValue(unsaved.billofmaterialsid, null, ToStatement.intToStatement)}::int4,
            ${ParameterValue(unsaved.productassemblyid, null, ToStatement.optionToStatement(ProductId.toStatement, ProductId.parameterMetadata))}::int4,
            ${ParameterValue(unsaved.componentid, null, ProductId.toStatement)}::int4,
            ${ParameterValue(unsaved.startdate, null, TypoLocalDateTime.toStatement)}::timestamp,
            ${ParameterValue(unsaved.enddate, null, ToStatement.optionToStatement(TypoLocalDateTime.toStatement, TypoLocalDateTime.parameterMetadata))}::timestamp,
            ${ParameterValue(unsaved.unitmeasurecode, null, UnitmeasureId.toStatement)}::bpchar,
            ${ParameterValue(unsaved.bomlevel, null, TypoShort.toStatement)}::int2,
            ${ParameterValue(unsaved.perassemblyqty, null, ToStatement.scalaBigDecimalToStatement)}::numeric,
            ${ParameterValue(unsaved.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp
          )
          on conflict ("billofmaterialsid")
          do update set
            "productassemblyid" = EXCLUDED."productassemblyid",
            "componentid" = EXCLUDED."componentid",
            "startdate" = EXCLUDED."startdate",
            "enddate" = EXCLUDED."enddate",
            "unitmeasurecode" = EXCLUDED."unitmeasurecode",
            "bomlevel" = EXCLUDED."bomlevel",
            "perassemblyqty" = EXCLUDED."perassemblyqty",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
       """
      .executeInsert(BillofmaterialsRow.rowParser(1).single)
    
  }
  override def upsertBatch(unsaved: Iterable[BillofmaterialsRow])(implicit c: Connection): List[BillofmaterialsRow] = {
    def toNamedParameter(row: BillofmaterialsRow): List[NamedParameter] = List(
      NamedParameter("billofmaterialsid", ParameterValue(row.billofmaterialsid, null, ToStatement.intToStatement)),
      NamedParameter("productassemblyid", ParameterValue(row.productassemblyid, null, ToStatement.optionToStatement(ProductId.toStatement, ProductId.parameterMetadata))),
      NamedParameter("componentid", ParameterValue(row.componentid, null, ProductId.toStatement)),
      NamedParameter("startdate", ParameterValue(row.startdate, null, TypoLocalDateTime.toStatement)),
      NamedParameter("enddate", ParameterValue(row.enddate, null, ToStatement.optionToStatement(TypoLocalDateTime.toStatement, TypoLocalDateTime.parameterMetadata))),
      NamedParameter("unitmeasurecode", ParameterValue(row.unitmeasurecode, null, UnitmeasureId.toStatement)),
      NamedParameter("bomlevel", ParameterValue(row.bomlevel, null, TypoShort.toStatement)),
      NamedParameter("perassemblyqty", ParameterValue(row.perassemblyqty, null, ToStatement.scalaBigDecimalToStatement)),
      NamedParameter("modifieddate", ParameterValue(row.modifieddate, null, TypoLocalDateTime.toStatement))
    )
    unsaved.toList match {
      case Nil => Nil
      case head :: rest =>
        new anorm.adventureworks.ExecuteReturningSyntax.Ops(
          BatchSql(
            s"""insert into "production"."billofmaterials"("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate")
                values ({billofmaterialsid}::int4, {productassemblyid}::int4, {componentid}::int4, {startdate}::timestamp, {enddate}::timestamp, {unitmeasurecode}::bpchar, {bomlevel}::int2, {perassemblyqty}::numeric, {modifieddate}::timestamp)
                on conflict ("billofmaterialsid")
                do update set
                  "productassemblyid" = EXCLUDED."productassemblyid",
                  "componentid" = EXCLUDED."componentid",
                  "startdate" = EXCLUDED."startdate",
                  "enddate" = EXCLUDED."enddate",
                  "unitmeasurecode" = EXCLUDED."unitmeasurecode",
                  "bomlevel" = EXCLUDED."bomlevel",
                  "perassemblyqty" = EXCLUDED."perassemblyqty",
                  "modifieddate" = EXCLUDED."modifieddate"
                returning "billofmaterialsid", "productassemblyid", "componentid", "startdate"::text, "enddate"::text, "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate"::text
             """,
            toNamedParameter(head),
            rest.map(toNamedParameter)*
          )
        ).executeReturning(BillofmaterialsRow.rowParser(1).*)
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Iterator[BillofmaterialsRow], batchSize: Int = 10000)(implicit c: Connection): Int = {
    SQL"""create temporary table billofmaterials_TEMP (like "production"."billofmaterials") on commit drop""".execute(): @nowarn
    streamingInsert(s"""copy billofmaterials_TEMP("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate") from stdin""", batchSize, unsaved)(BillofmaterialsRow.text, c): @nowarn
    SQL"""insert into "production"."billofmaterials"("billofmaterialsid", "productassemblyid", "componentid", "startdate", "enddate", "unitmeasurecode", "bomlevel", "perassemblyqty", "modifieddate")
          select * from billofmaterials_TEMP
          on conflict ("billofmaterialsid")
          do update set
            "productassemblyid" = EXCLUDED."productassemblyid",
            "componentid" = EXCLUDED."componentid",
            "startdate" = EXCLUDED."startdate",
            "enddate" = EXCLUDED."enddate",
            "unitmeasurecode" = EXCLUDED."unitmeasurecode",
            "bomlevel" = EXCLUDED."bomlevel",
            "perassemblyqty" = EXCLUDED."perassemblyqty",
            "modifieddate" = EXCLUDED."modifieddate"
          ;
          drop table billofmaterials_TEMP;""".executeUpdate()
  }
}
