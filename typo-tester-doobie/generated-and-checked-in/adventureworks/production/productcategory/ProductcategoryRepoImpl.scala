/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productcategory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.public.Name
import doobie.free.connection.ConnectionIO
import doobie.postgres.syntax.FragmentOps
import doobie.syntax.SqlInterpolator.SingleFragment.fromWrite
import doobie.syntax.string.toSqlInterpolator
import doobie.util.Write
import doobie.util.fragment.Fragment
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class ProductcategoryRepoImpl extends ProductcategoryRepo {
  override def delete(productcategoryid: ProductcategoryId): ConnectionIO[Boolean] = {
    sql"""delete from production.productcategory where "productcategoryid" = ${fromWrite(productcategoryid)(Write.fromPut(ProductcategoryId.put))}""".update.run.map(_ > 0)
  }
  override def delete: DeleteBuilder[ProductcategoryFields, ProductcategoryRow] = {
    DeleteBuilder("production.productcategory", ProductcategoryFields.structure)
  }
  override def insert(unsaved: ProductcategoryRow): ConnectionIO[ProductcategoryRow] = {
    sql"""insert into production.productcategory("productcategoryid", "name", "rowguid", "modifieddate")
          values (${fromWrite(unsaved.productcategoryid)(Write.fromPut(ProductcategoryId.put))}::int4, ${fromWrite(unsaved.name)(Write.fromPut(Name.put))}::varchar, ${fromWrite(unsaved.rowguid)(Write.fromPut(TypoUUID.put))}::uuid, ${fromWrite(unsaved.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp)
          returning "productcategoryid", "name", "rowguid", "modifieddate"::text
       """.query(using ProductcategoryRow.read).unique
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, ProductcategoryRow], batchSize: Int): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY production.productcategory("productcategoryid", "name", "rowguid", "modifieddate") FROM STDIN""").copyIn(unsaved, batchSize)(using ProductcategoryRow.text)
  }
  override def insert(unsaved: ProductcategoryRowUnsaved): ConnectionIO[ProductcategoryRow] = {
    val fs = List(
      Some((Fragment.const(s""""name""""), fr"${fromWrite(unsaved.name)(Write.fromPut(Name.put))}::varchar")),
      unsaved.productcategoryid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s""""productcategoryid""""), fr"${fromWrite(value: ProductcategoryId)(Write.fromPut(ProductcategoryId.put))}::int4"))
      },
      unsaved.rowguid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s""""rowguid""""), fr"${fromWrite(value: TypoUUID)(Write.fromPut(TypoUUID.put))}::uuid"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s""""modifieddate""""), fr"${fromWrite(value: TypoLocalDateTime)(Write.fromPut(TypoLocalDateTime.put))}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into production.productcategory default values
            returning "productcategoryid", "name", "rowguid", "modifieddate"::text
         """
    } else {
      val CommaSeparate = Fragment.FragmentMonoid.intercalate(fr", ")
      sql"""insert into production.productcategory(${CommaSeparate.combineAllOption(fs.map { case (n, _) => n }).get})
            values (${CommaSeparate.combineAllOption(fs.map { case (_, f) => f }).get})
            returning "productcategoryid", "name", "rowguid", "modifieddate"::text
         """
    }
    q.query(using ProductcategoryRow.read).unique
    
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, ProductcategoryRowUnsaved], batchSize: Int): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY production.productcategory("name", "productcategoryid", "rowguid", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""").copyIn(unsaved, batchSize)(using ProductcategoryRowUnsaved.text)
  }
  override def select: SelectBuilder[ProductcategoryFields, ProductcategoryRow] = {
    SelectBuilderSql("production.productcategory", ProductcategoryFields.structure, ProductcategoryRow.read)
  }
  override def selectAll: Stream[ConnectionIO, ProductcategoryRow] = {
    sql"""select "productcategoryid", "name", "rowguid", "modifieddate"::text from production.productcategory""".query(using ProductcategoryRow.read).stream
  }
  override def selectById(productcategoryid: ProductcategoryId): ConnectionIO[Option[ProductcategoryRow]] = {
    sql"""select "productcategoryid", "name", "rowguid", "modifieddate"::text from production.productcategory where "productcategoryid" = ${fromWrite(productcategoryid)(Write.fromPut(ProductcategoryId.put))}""".query(using ProductcategoryRow.read).option
  }
  override def selectByIds(productcategoryids: Array[ProductcategoryId]): Stream[ConnectionIO, ProductcategoryRow] = {
    sql"""select "productcategoryid", "name", "rowguid", "modifieddate"::text from production.productcategory where "productcategoryid" = ANY(${productcategoryids})""".query(using ProductcategoryRow.read).stream
  }
  override def update(row: ProductcategoryRow): ConnectionIO[Boolean] = {
    val productcategoryid = row.productcategoryid
    sql"""update production.productcategory
          set "name" = ${fromWrite(row.name)(Write.fromPut(Name.put))}::varchar,
              "rowguid" = ${fromWrite(row.rowguid)(Write.fromPut(TypoUUID.put))}::uuid,
              "modifieddate" = ${fromWrite(row.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp
          where "productcategoryid" = ${fromWrite(productcategoryid)(Write.fromPut(ProductcategoryId.put))}"""
      .update
      .run
      .map(_ > 0)
  }
  override def update: UpdateBuilder[ProductcategoryFields, ProductcategoryRow] = {
    UpdateBuilder("production.productcategory", ProductcategoryFields.structure, ProductcategoryRow.read)
  }
  override def upsert(unsaved: ProductcategoryRow): ConnectionIO[ProductcategoryRow] = {
    sql"""insert into production.productcategory("productcategoryid", "name", "rowguid", "modifieddate")
          values (
            ${fromWrite(unsaved.productcategoryid)(Write.fromPut(ProductcategoryId.put))}::int4,
            ${fromWrite(unsaved.name)(Write.fromPut(Name.put))}::varchar,
            ${fromWrite(unsaved.rowguid)(Write.fromPut(TypoUUID.put))}::uuid,
            ${fromWrite(unsaved.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp
          )
          on conflict ("productcategoryid")
          do update set
            "name" = EXCLUDED."name",
            "rowguid" = EXCLUDED."rowguid",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "productcategoryid", "name", "rowguid", "modifieddate"::text
       """.query(using ProductcategoryRow.read).unique
  }
}