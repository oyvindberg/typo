/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productdocument

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.document.DocumentId
import adventureworks.production.product.ProductId
import adventureworks.streamingInsert
import anorm.NamedParameter
import anorm.ParameterValue
import anorm.RowParser
import anorm.SQL
import anorm.SimpleSql
import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class ProductdocumentRepoImpl extends ProductdocumentRepo {
  override def delete(compositeId: ProductdocumentId)(implicit c: Connection): Boolean = {
    SQL"""delete from production.productdocument where "productid" = ${ParameterValue(compositeId.productid, null, ProductId.toStatement)} AND "documentnode" = ${ParameterValue(compositeId.documentnode, null, DocumentId.toStatement)}""".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[ProductdocumentFields, ProductdocumentRow] = {
    DeleteBuilder("production.productdocument", ProductdocumentFields.structure)
  }
  override def insert(unsaved: ProductdocumentRow)(implicit c: Connection): ProductdocumentRow = {
    SQL"""insert into production.productdocument("productid", "modifieddate", "documentnode")
          values (${ParameterValue(unsaved.productid, null, ProductId.toStatement)}::int4, ${ParameterValue(unsaved.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp, ${ParameterValue(unsaved.documentnode, null, DocumentId.toStatement)})
          returning "productid", "modifieddate"::text, "documentnode"
       """
      .executeInsert(ProductdocumentRow.rowParser(1).single)
    
  }
  override def insertStreaming(unsaved: Iterator[ProductdocumentRow], batchSize: Int)(implicit c: Connection): Long = {
    streamingInsert(s"""COPY production.productdocument("productid", "modifieddate", "documentnode") FROM STDIN""", batchSize, unsaved)(ProductdocumentRow.text, c)
  }
  override def insert(unsaved: ProductdocumentRowUnsaved)(implicit c: Connection): ProductdocumentRow = {
    val namedParameters = List(
      Some((NamedParameter("productid", ParameterValue(unsaved.productid, null, ProductId.toStatement)), "::int4")),
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
      SQL"""insert into production.productdocument default values
            returning "productid", "modifieddate"::text, "documentnode"
         """
        .executeInsert(ProductdocumentRow.rowParser(1).single)
    } else {
      val q = s"""insert into production.productdocument(${namedParameters.map{case (x, _) => quote + x.name + quote}.mkString(", ")})
                  values (${namedParameters.map{ case (np, cast) => s"{${np.name}}$cast"}.mkString(", ")})
                  returning "productid", "modifieddate"::text, "documentnode"
               """
      SimpleSql(SQL(q), namedParameters.map { case (np, _) => np.tupled }.toMap, RowParser.successful)
        .executeInsert(ProductdocumentRow.rowParser(1).single)
    }
    
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Iterator[ProductdocumentRowUnsaved], batchSize: Int)(implicit c: Connection): Long = {
    streamingInsert(s"""COPY production.productdocument("productid", "modifieddate", "documentnode") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""", batchSize, unsaved)(ProductdocumentRowUnsaved.text, c)
  }
  override def select: SelectBuilder[ProductdocumentFields, ProductdocumentRow] = {
    SelectBuilderSql("production.productdocument", ProductdocumentFields.structure, ProductdocumentRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[ProductdocumentRow] = {
    SQL"""select "productid", "modifieddate"::text, "documentnode"
          from production.productdocument
       """.as(ProductdocumentRow.rowParser(1).*)
  }
  override def selectById(compositeId: ProductdocumentId)(implicit c: Connection): Option[ProductdocumentRow] = {
    SQL"""select "productid", "modifieddate"::text, "documentnode"
          from production.productdocument
          where "productid" = ${ParameterValue(compositeId.productid, null, ProductId.toStatement)} AND "documentnode" = ${ParameterValue(compositeId.documentnode, null, DocumentId.toStatement)}
       """.as(ProductdocumentRow.rowParser(1).singleOpt)
  }
  override def update(row: ProductdocumentRow)(implicit c: Connection): Boolean = {
    val compositeId = row.compositeId
    SQL"""update production.productdocument
          set "modifieddate" = ${ParameterValue(row.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp
          where "productid" = ${ParameterValue(compositeId.productid, null, ProductId.toStatement)} AND "documentnode" = ${ParameterValue(compositeId.documentnode, null, DocumentId.toStatement)}
       """.executeUpdate() > 0
  }
  override def update: UpdateBuilder[ProductdocumentFields, ProductdocumentRow] = {
    UpdateBuilder("production.productdocument", ProductdocumentFields.structure, ProductdocumentRow.rowParser)
  }
  override def upsert(unsaved: ProductdocumentRow)(implicit c: Connection): ProductdocumentRow = {
    SQL"""insert into production.productdocument("productid", "modifieddate", "documentnode")
          values (
            ${ParameterValue(unsaved.productid, null, ProductId.toStatement)}::int4,
            ${ParameterValue(unsaved.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp,
            ${ParameterValue(unsaved.documentnode, null, DocumentId.toStatement)}
          )
          on conflict ("productid", "documentnode")
          do update set
            "modifieddate" = EXCLUDED."modifieddate"
          returning "productid", "modifieddate"::text, "documentnode"
       """
      .executeInsert(ProductdocumentRow.rowParser(1).single)
    
  }
}