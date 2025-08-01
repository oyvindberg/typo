/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productsubcategory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.production.productcategory.ProductcategoryId
import adventureworks.public.Name
import anorm.BatchSql
import anorm.NamedParameter
import anorm.ParameterValue
import anorm.RowParser
import anorm.SQL
import anorm.SimpleSql
import anorm.SqlStringInterpolation
import java.sql.Connection
import scala.annotation.nowarn
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class ProductsubcategoryRepoImpl extends ProductsubcategoryRepo {
  override def delete: DeleteBuilder[ProductsubcategoryFields, ProductsubcategoryRow] = {
    DeleteBuilder(""""production"."productsubcategory"""", ProductsubcategoryFields.structure)
  }
  override def deleteById(productsubcategoryid: ProductsubcategoryId)(implicit c: Connection): Boolean = {
    SQL"""delete from "production"."productsubcategory" where "productsubcategoryid" = ${ParameterValue(productsubcategoryid, null, ProductsubcategoryId.toStatement)}""".executeUpdate() > 0
  }
  override def deleteByIds(productsubcategoryids: Array[ProductsubcategoryId])(implicit c: Connection): Int = {
    SQL"""delete
          from "production"."productsubcategory"
          where "productsubcategoryid" = ANY(${productsubcategoryids})
       """.executeUpdate()
    
  }
  override def insert(unsaved: ProductsubcategoryRow)(implicit c: Connection): ProductsubcategoryRow = {
    SQL"""insert into "production"."productsubcategory"("productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate")
          values (${ParameterValue(unsaved.productsubcategoryid, null, ProductsubcategoryId.toStatement)}::int4, ${ParameterValue(unsaved.productcategoryid, null, ProductcategoryId.toStatement)}::int4, ${ParameterValue(unsaved.name, null, Name.toStatement)}::varchar, ${ParameterValue(unsaved.rowguid, null, TypoUUID.toStatement)}::uuid, ${ParameterValue(unsaved.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp)
          returning "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate"::text
       """
      .executeInsert(ProductsubcategoryRow.rowParser(1).single)
    
  }
  override def insert(unsaved: ProductsubcategoryRowUnsaved)(implicit c: Connection): ProductsubcategoryRow = {
    val namedParameters = List(
      Some((NamedParameter("productcategoryid", ParameterValue(unsaved.productcategoryid, null, ProductcategoryId.toStatement)), "::int4")),
      Some((NamedParameter("name", ParameterValue(unsaved.name, null, Name.toStatement)), "::varchar")),
      unsaved.productsubcategoryid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("productsubcategoryid", ParameterValue(value, null, ProductsubcategoryId.toStatement)), "::int4"))
      },
      unsaved.rowguid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("rowguid", ParameterValue(value, null, TypoUUID.toStatement)), "::uuid"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("modifieddate", ParameterValue(value, null, TypoLocalDateTime.toStatement)), "::timestamp"))
      }
    ).flatten
    val quote = '"'.toString
    if (namedParameters.isEmpty) {
      SQL"""insert into "production"."productsubcategory" default values
            returning "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate"::text
         """
        .executeInsert(ProductsubcategoryRow.rowParser(1).single)
    } else {
      val q = s"""insert into "production"."productsubcategory"(${namedParameters.map{case (x, _) => quote + x.name + quote}.mkString(", ")})
                  values (${namedParameters.map{ case (np, cast) => s"{${np.name}}$cast"}.mkString(", ")})
                  returning "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate"::text
               """
      SimpleSql(SQL(q), namedParameters.map { case (np, _) => np.tupled }.toMap, RowParser.successful)
        .executeInsert(ProductsubcategoryRow.rowParser(1).single)
    }
    
  }
  override def insertStreaming(unsaved: Iterator[ProductsubcategoryRow], batchSize: Int = 10000)(implicit c: Connection): Long = {
    streamingInsert(s"""COPY "production"."productsubcategory"("productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate") FROM STDIN""", batchSize, unsaved)(ProductsubcategoryRow.text, c)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Iterator[ProductsubcategoryRowUnsaved], batchSize: Int = 10000)(implicit c: Connection): Long = {
    streamingInsert(s"""COPY "production"."productsubcategory"("productcategoryid", "name", "productsubcategoryid", "rowguid", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""", batchSize, unsaved)(ProductsubcategoryRowUnsaved.text, c)
  }
  override def select: SelectBuilder[ProductsubcategoryFields, ProductsubcategoryRow] = {
    SelectBuilderSql(""""production"."productsubcategory"""", ProductsubcategoryFields.structure, ProductsubcategoryRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[ProductsubcategoryRow] = {
    SQL"""select "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate"::text
          from "production"."productsubcategory"
       """.as(ProductsubcategoryRow.rowParser(1).*)
  }
  override def selectById(productsubcategoryid: ProductsubcategoryId)(implicit c: Connection): Option[ProductsubcategoryRow] = {
    SQL"""select "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate"::text
          from "production"."productsubcategory"
          where "productsubcategoryid" = ${ParameterValue(productsubcategoryid, null, ProductsubcategoryId.toStatement)}
       """.as(ProductsubcategoryRow.rowParser(1).singleOpt)
  }
  override def selectByIds(productsubcategoryids: Array[ProductsubcategoryId])(implicit c: Connection): List[ProductsubcategoryRow] = {
    SQL"""select "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate"::text
          from "production"."productsubcategory"
          where "productsubcategoryid" = ANY(${productsubcategoryids})
       """.as(ProductsubcategoryRow.rowParser(1).*)
    
  }
  override def selectByIdsTracked(productsubcategoryids: Array[ProductsubcategoryId])(implicit c: Connection): Map[ProductsubcategoryId, ProductsubcategoryRow] = {
    val byId = selectByIds(productsubcategoryids).view.map(x => (x.productsubcategoryid, x)).toMap
    productsubcategoryids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
  }
  override def update: UpdateBuilder[ProductsubcategoryFields, ProductsubcategoryRow] = {
    UpdateBuilder(""""production"."productsubcategory"""", ProductsubcategoryFields.structure, ProductsubcategoryRow.rowParser)
  }
  override def update(row: ProductsubcategoryRow)(implicit c: Connection): Option[ProductsubcategoryRow] = {
    val productsubcategoryid = row.productsubcategoryid
    SQL"""update "production"."productsubcategory"
          set "productcategoryid" = ${ParameterValue(row.productcategoryid, null, ProductcategoryId.toStatement)}::int4,
              "name" = ${ParameterValue(row.name, null, Name.toStatement)}::varchar,
              "rowguid" = ${ParameterValue(row.rowguid, null, TypoUUID.toStatement)}::uuid,
              "modifieddate" = ${ParameterValue(row.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp
          where "productsubcategoryid" = ${ParameterValue(productsubcategoryid, null, ProductsubcategoryId.toStatement)}
          returning "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate"::text
       """.executeInsert(ProductsubcategoryRow.rowParser(1).singleOpt)
  }
  override def upsert(unsaved: ProductsubcategoryRow)(implicit c: Connection): ProductsubcategoryRow = {
    SQL"""insert into "production"."productsubcategory"("productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate")
          values (
            ${ParameterValue(unsaved.productsubcategoryid, null, ProductsubcategoryId.toStatement)}::int4,
            ${ParameterValue(unsaved.productcategoryid, null, ProductcategoryId.toStatement)}::int4,
            ${ParameterValue(unsaved.name, null, Name.toStatement)}::varchar,
            ${ParameterValue(unsaved.rowguid, null, TypoUUID.toStatement)}::uuid,
            ${ParameterValue(unsaved.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp
          )
          on conflict ("productsubcategoryid")
          do update set
            "productcategoryid" = EXCLUDED."productcategoryid",
            "name" = EXCLUDED."name",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate"::text
       """
      .executeInsert(ProductsubcategoryRow.rowParser(1).single)
    
  }
  override def upsertBatch(unsaved: Iterable[ProductsubcategoryRow])(implicit c: Connection): List[ProductsubcategoryRow] = {
    def toNamedParameter(row: ProductsubcategoryRow): List[NamedParameter] = List(
      NamedParameter("productsubcategoryid", ParameterValue(row.productsubcategoryid, null, ProductsubcategoryId.toStatement)),
      NamedParameter("productcategoryid", ParameterValue(row.productcategoryid, null, ProductcategoryId.toStatement)),
      NamedParameter("name", ParameterValue(row.name, null, Name.toStatement)),
      NamedParameter("rowguid", ParameterValue(row.rowguid, null, TypoUUID.toStatement)),
      NamedParameter("modifieddate", ParameterValue(row.modifieddate, null, TypoLocalDateTime.toStatement))
    )
    unsaved.toList match {
      case Nil => Nil
      case head :: rest =>
        new anorm.adventureworks.ExecuteReturningSyntax.Ops(
          BatchSql(
            s"""insert into "production"."productsubcategory"("productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate")
                values ({productsubcategoryid}::int4, {productcategoryid}::int4, {name}::varchar, {rowguid}::uuid, {modifieddate}::timestamp)
                on conflict ("productsubcategoryid")
                do update set
                  "productcategoryid" = EXCLUDED."productcategoryid",
                  "name" = EXCLUDED."name",
                  "rowguid" = EXCLUDED."rowguid",
                  "modifieddate" = EXCLUDED."modifieddate"
                returning "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate"::text
             """,
            toNamedParameter(head),
            rest.map(toNamedParameter)*
          )
        ).executeReturning(ProductsubcategoryRow.rowParser(1).*)
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Iterator[ProductsubcategoryRow], batchSize: Int = 10000)(implicit c: Connection): Int = {
    SQL"""create temporary table productsubcategory_TEMP (like "production"."productsubcategory") on commit drop""".execute(): @nowarn
    streamingInsert(s"""copy productsubcategory_TEMP("productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate") from stdin""", batchSize, unsaved)(ProductsubcategoryRow.text, c): @nowarn
    SQL"""insert into "production"."productsubcategory"("productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate")
          select * from productsubcategory_TEMP
          on conflict ("productsubcategoryid")
          do update set
            "productcategoryid" = EXCLUDED."productcategoryid",
            "name" = EXCLUDED."name",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          ;
          drop table productsubcategory_TEMP;""".executeUpdate()
  }
}
