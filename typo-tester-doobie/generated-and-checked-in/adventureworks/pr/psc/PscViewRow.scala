/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package psc

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.production.productcategory.ProductcategoryId
import adventureworks.production.productsubcategory.ProductsubcategoryId
import adventureworks.public.Name
import doobie.enumerated.Nullability
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** View: pr.psc */
case class PscViewRow(
  /** Points to [[production.productsubcategory.ProductsubcategoryRow.productsubcategoryid]] */
  id: ProductsubcategoryId,
  /** Points to [[production.productsubcategory.ProductsubcategoryRow.productsubcategoryid]] */
  productsubcategoryid: ProductsubcategoryId,
  /** Points to [[production.productsubcategory.ProductsubcategoryRow.productcategoryid]] */
  productcategoryid: ProductcategoryId,
  /** Points to [[production.productsubcategory.ProductsubcategoryRow.name]] */
  name: Name,
  /** Points to [[production.productsubcategory.ProductsubcategoryRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[production.productsubcategory.ProductsubcategoryRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object PscViewRow {
  implicit lazy val decoder: Decoder[PscViewRow] = Decoder.forProduct6[PscViewRow, ProductsubcategoryId, ProductsubcategoryId, ProductcategoryId, Name, TypoUUID, TypoLocalDateTime]("id", "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate")(PscViewRow.apply)(ProductsubcategoryId.decoder, ProductsubcategoryId.decoder, ProductcategoryId.decoder, Name.decoder, TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[PscViewRow] = Encoder.forProduct6[PscViewRow, ProductsubcategoryId, ProductsubcategoryId, ProductcategoryId, Name, TypoUUID, TypoLocalDateTime]("id", "productsubcategoryid", "productcategoryid", "name", "rowguid", "modifieddate")(x => (x.id, x.productsubcategoryid, x.productcategoryid, x.name, x.rowguid, x.modifieddate))(ProductsubcategoryId.encoder, ProductsubcategoryId.encoder, ProductcategoryId.encoder, Name.encoder, TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[PscViewRow] = new Read[PscViewRow](
    gets = List(
      (ProductsubcategoryId.get, Nullability.NoNulls),
      (ProductsubcategoryId.get, Nullability.NoNulls),
      (ProductcategoryId.get, Nullability.NoNulls),
      (Name.get, Nullability.NoNulls),
      (TypoUUID.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PscViewRow(
      id = ProductsubcategoryId.get.unsafeGetNonNullable(rs, i + 0),
      productsubcategoryid = ProductsubcategoryId.get.unsafeGetNonNullable(rs, i + 1),
      productcategoryid = ProductcategoryId.get.unsafeGetNonNullable(rs, i + 2),
      name = Name.get.unsafeGetNonNullable(rs, i + 3),
      rowguid = TypoUUID.get.unsafeGetNonNullable(rs, i + 4),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 5)
    )
  )
}
