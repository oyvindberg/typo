/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesorderdetail

import adventureworks.Defaulted
import adventureworks.TypoLocalDateTime
import adventureworks.production.product.ProductId
import adventureworks.sales.salesorderheader.SalesorderheaderId
import adventureworks.sales.specialoffer.SpecialofferId
import doobie.free.connection.ConnectionIO
import doobie.syntax.SqlInterpolator.SingleFragment.fromWrite
import doobie.syntax.string.toSqlInterpolator
import doobie.util.Write
import doobie.util.fragment.Fragment
import doobie.util.meta.Meta
import fs2.Stream
import java.util.UUID
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

object SalesorderdetailRepoImpl extends SalesorderdetailRepo {
  override def delete(compositeId: SalesorderdetailId): ConnectionIO[Boolean] = {
    sql"delete from sales.salesorderdetail where salesorderid = ${fromWrite(compositeId.salesorderid)(Write.fromPut(SalesorderheaderId.put))} AND salesorderdetailid = ${fromWrite(compositeId.salesorderdetailid)(Write.fromPut(Meta.IntMeta.put))}".update.run.map(_ > 0)
  }
  override def delete: DeleteBuilder[SalesorderdetailFields, SalesorderdetailRow] = {
    DeleteBuilder("sales.salesorderdetail", SalesorderdetailFields)
  }
  override def insert(unsaved: SalesorderdetailRow): ConnectionIO[SalesorderdetailRow] = {
    sql"""insert into sales.salesorderdetail(salesorderid, salesorderdetailid, carriertrackingnumber, orderqty, productid, specialofferid, unitprice, unitpricediscount, rowguid, modifieddate)
          values (${fromWrite(unsaved.salesorderid)(Write.fromPut(SalesorderheaderId.put))}::int4, ${fromWrite(unsaved.salesorderdetailid)(Write.fromPut(Meta.IntMeta.put))}::int4, ${fromWrite(unsaved.carriertrackingnumber)(Write.fromPutOption(Meta.StringMeta.put))}, ${fromWrite(unsaved.orderqty)(Write.fromPut(Meta.IntMeta.put))}::int2, ${fromWrite(unsaved.productid)(Write.fromPut(ProductId.put))}::int4, ${fromWrite(unsaved.specialofferid)(Write.fromPut(SpecialofferId.put))}::int4, ${fromWrite(unsaved.unitprice)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric, ${fromWrite(unsaved.unitpricediscount)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric, ${fromWrite(unsaved.rowguid)(Write.fromPut(adventureworks.UUIDMeta.put))}::uuid, ${fromWrite(unsaved.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp)
          returning salesorderid, salesorderdetailid, carriertrackingnumber, orderqty, productid, specialofferid, unitprice, unitpricediscount, rowguid, modifieddate::text
       """.query(SalesorderdetailRow.read).unique
  }
  override def insert(unsaved: SalesorderdetailRowUnsaved): ConnectionIO[SalesorderdetailRow] = {
    val fs = List(
      Some((Fragment.const(s"salesorderid"), fr"${fromWrite(unsaved.salesorderid)(Write.fromPut(SalesorderheaderId.put))}::int4")),
      Some((Fragment.const(s"carriertrackingnumber"), fr"${fromWrite(unsaved.carriertrackingnumber)(Write.fromPutOption(Meta.StringMeta.put))}")),
      Some((Fragment.const(s"orderqty"), fr"${fromWrite(unsaved.orderqty)(Write.fromPut(Meta.IntMeta.put))}::int2")),
      Some((Fragment.const(s"productid"), fr"${fromWrite(unsaved.productid)(Write.fromPut(ProductId.put))}::int4")),
      Some((Fragment.const(s"specialofferid"), fr"${fromWrite(unsaved.specialofferid)(Write.fromPut(SpecialofferId.put))}::int4")),
      Some((Fragment.const(s"unitprice"), fr"${fromWrite(unsaved.unitprice)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric")),
      unsaved.salesorderdetailid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s"salesorderdetailid"), fr"${fromWrite(value: Int)(Write.fromPut(Meta.IntMeta.put))}::int4"))
      },
      unsaved.unitpricediscount match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s"unitpricediscount"), fr"${fromWrite(value: BigDecimal)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric"))
      },
      unsaved.rowguid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s"rowguid"), fr"${fromWrite(value: UUID)(Write.fromPut(adventureworks.UUIDMeta.put))}::uuid"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((Fragment.const(s"modifieddate"), fr"${fromWrite(value: TypoLocalDateTime)(Write.fromPut(TypoLocalDateTime.put))}::timestamp"))
      }
    ).flatten
    
    val q = if (fs.isEmpty) {
      sql"""insert into sales.salesorderdetail default values
            returning salesorderid, salesorderdetailid, carriertrackingnumber, orderqty, productid, specialofferid, unitprice, unitpricediscount, rowguid, modifieddate::text
         """
    } else {
      import cats.syntax.foldable.toFoldableOps
      sql"""insert into sales.salesorderdetail(${fs.map { case (n, _) => n }.intercalate(fr", ")})
            values (${fs.map { case (_, f) => f }.intercalate(fr", ")})
            returning salesorderid, salesorderdetailid, carriertrackingnumber, orderqty, productid, specialofferid, unitprice, unitpricediscount, rowguid, modifieddate::text
         """
    }
    q.query(SalesorderdetailRow.read).unique
    
  }
  override def select: SelectBuilder[SalesorderdetailFields, SalesorderdetailRow] = {
    SelectBuilderSql("sales.salesorderdetail", SalesorderdetailFields, SalesorderdetailRow.read)
  }
  override def selectAll: Stream[ConnectionIO, SalesorderdetailRow] = {
    sql"select salesorderid, salesorderdetailid, carriertrackingnumber, orderqty, productid, specialofferid, unitprice, unitpricediscount, rowguid, modifieddate::text from sales.salesorderdetail".query(SalesorderdetailRow.read).stream
  }
  override def selectById(compositeId: SalesorderdetailId): ConnectionIO[Option[SalesorderdetailRow]] = {
    sql"select salesorderid, salesorderdetailid, carriertrackingnumber, orderqty, productid, specialofferid, unitprice, unitpricediscount, rowguid, modifieddate::text from sales.salesorderdetail where salesorderid = ${fromWrite(compositeId.salesorderid)(Write.fromPut(SalesorderheaderId.put))} AND salesorderdetailid = ${fromWrite(compositeId.salesorderdetailid)(Write.fromPut(Meta.IntMeta.put))}".query(SalesorderdetailRow.read).option
  }
  override def update(row: SalesorderdetailRow): ConnectionIO[Boolean] = {
    val compositeId = row.compositeId
    sql"""update sales.salesorderdetail
          set carriertrackingnumber = ${fromWrite(row.carriertrackingnumber)(Write.fromPutOption(Meta.StringMeta.put))},
              orderqty = ${fromWrite(row.orderqty)(Write.fromPut(Meta.IntMeta.put))}::int2,
              productid = ${fromWrite(row.productid)(Write.fromPut(ProductId.put))}::int4,
              specialofferid = ${fromWrite(row.specialofferid)(Write.fromPut(SpecialofferId.put))}::int4,
              unitprice = ${fromWrite(row.unitprice)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric,
              unitpricediscount = ${fromWrite(row.unitpricediscount)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric,
              rowguid = ${fromWrite(row.rowguid)(Write.fromPut(adventureworks.UUIDMeta.put))}::uuid,
              modifieddate = ${fromWrite(row.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp
          where salesorderid = ${fromWrite(compositeId.salesorderid)(Write.fromPut(SalesorderheaderId.put))} AND salesorderdetailid = ${fromWrite(compositeId.salesorderdetailid)(Write.fromPut(Meta.IntMeta.put))}"""
      .update
      .run
      .map(_ > 0)
  }
  override def update: UpdateBuilder[SalesorderdetailFields, SalesorderdetailRow] = {
    UpdateBuilder("sales.salesorderdetail", SalesorderdetailFields, SalesorderdetailRow.read)
  }
  override def upsert(unsaved: SalesorderdetailRow): ConnectionIO[SalesorderdetailRow] = {
    sql"""insert into sales.salesorderdetail(salesorderid, salesorderdetailid, carriertrackingnumber, orderqty, productid, specialofferid, unitprice, unitpricediscount, rowguid, modifieddate)
          values (
            ${fromWrite(unsaved.salesorderid)(Write.fromPut(SalesorderheaderId.put))}::int4,
            ${fromWrite(unsaved.salesorderdetailid)(Write.fromPut(Meta.IntMeta.put))}::int4,
            ${fromWrite(unsaved.carriertrackingnumber)(Write.fromPutOption(Meta.StringMeta.put))},
            ${fromWrite(unsaved.orderqty)(Write.fromPut(Meta.IntMeta.put))}::int2,
            ${fromWrite(unsaved.productid)(Write.fromPut(ProductId.put))}::int4,
            ${fromWrite(unsaved.specialofferid)(Write.fromPut(SpecialofferId.put))}::int4,
            ${fromWrite(unsaved.unitprice)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric,
            ${fromWrite(unsaved.unitpricediscount)(Write.fromPut(Meta.ScalaBigDecimalMeta.put))}::numeric,
            ${fromWrite(unsaved.rowguid)(Write.fromPut(adventureworks.UUIDMeta.put))}::uuid,
            ${fromWrite(unsaved.modifieddate)(Write.fromPut(TypoLocalDateTime.put))}::timestamp
          )
          on conflict (salesorderid, salesorderdetailid)
          do update set
            carriertrackingnumber = EXCLUDED.carriertrackingnumber,
            orderqty = EXCLUDED.orderqty,
            productid = EXCLUDED.productid,
            specialofferid = EXCLUDED.specialofferid,
            unitprice = EXCLUDED.unitprice,
            unitpricediscount = EXCLUDED.unitpricediscount,
            rowguid = EXCLUDED.rowguid,
            modifieddate = EXCLUDED.modifieddate
          returning salesorderid, salesorderdetailid, carriertrackingnumber, orderqty, productid, specialofferid, unitprice, unitpricediscount, rowguid, modifieddate::text
       """.query(SalesorderdetailRow.read).unique
  }
}