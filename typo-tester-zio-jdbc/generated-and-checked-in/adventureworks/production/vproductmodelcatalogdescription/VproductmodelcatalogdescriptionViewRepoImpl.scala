/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package vproductmodelcatalogdescription

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class VproductmodelcatalogdescriptionViewRepoImpl extends VproductmodelcatalogdescriptionViewRepo {
  override def select: SelectBuilder[VproductmodelcatalogdescriptionViewFields, VproductmodelcatalogdescriptionViewRow] = {
    SelectBuilderSql("production.vproductmodelcatalogdescription", VproductmodelcatalogdescriptionViewFields.structure, VproductmodelcatalogdescriptionViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, VproductmodelcatalogdescriptionViewRow] = {
    sql"""select "productmodelid", "name", "Summary", "manufacturer", "copyright", "producturl", "warrantyperiod", "warrantydescription", "noofyears", "maintenancedescription", "wheel", "saddle", "pedal", "bikeframe", "crankset", "pictureangle", "picturesize", "productphotoid", "material", "color", "productline", "style", "riderexperience", "rowguid", "modifieddate"::text from production.vproductmodelcatalogdescription""".query(using VproductmodelcatalogdescriptionViewRow.jdbcDecoder).selectStream()
  }
}