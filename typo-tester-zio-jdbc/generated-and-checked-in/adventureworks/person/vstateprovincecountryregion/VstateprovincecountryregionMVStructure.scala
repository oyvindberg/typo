/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package vstateprovincecountryregion

import adventureworks.person.countryregion.CountryregionId
import adventureworks.person.stateprovince.StateprovinceId
import adventureworks.public.Flag
import adventureworks.public.Name
import adventureworks.sales.salesterritory.SalesterritoryId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.Structure.Relation

class VstateprovincecountryregionMVStructure[Row](val prefix: Option[String], val extract: Row => VstateprovincecountryregionMVRow, val merge: (Row, VstateprovincecountryregionMVRow) => Row)
  extends Relation[VstateprovincecountryregionMVFields, VstateprovincecountryregionMVRow, Row]
    with VstateprovincecountryregionMVFields[Row] { outer =>

  override val stateprovinceid = new Field[StateprovinceId, Row](prefix, "stateprovinceid", None, None)(x => extract(x).stateprovinceid, (row, value) => merge(row, extract(row).copy(stateprovinceid = value)))
  override val stateprovincecode = new Field[/* bpchar, max 3 chars */ String, Row](prefix, "stateprovincecode", None, None)(x => extract(x).stateprovincecode, (row, value) => merge(row, extract(row).copy(stateprovincecode = value)))
  override val isonlystateprovinceflag = new Field[Flag, Row](prefix, "isonlystateprovinceflag", None, None)(x => extract(x).isonlystateprovinceflag, (row, value) => merge(row, extract(row).copy(isonlystateprovinceflag = value)))
  override val stateprovincename = new Field[Name, Row](prefix, "stateprovincename", None, None)(x => extract(x).stateprovincename, (row, value) => merge(row, extract(row).copy(stateprovincename = value)))
  override val territoryid = new Field[SalesterritoryId, Row](prefix, "territoryid", None, None)(x => extract(x).territoryid, (row, value) => merge(row, extract(row).copy(territoryid = value)))
  override val countryregioncode = new Field[CountryregionId, Row](prefix, "countryregioncode", None, None)(x => extract(x).countryregioncode, (row, value) => merge(row, extract(row).copy(countryregioncode = value)))
  override val countryregionname = new Field[Name, Row](prefix, "countryregionname", None, None)(x => extract(x).countryregionname, (row, value) => merge(row, extract(row).copy(countryregionname = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](stateprovinceid, stateprovincecode, isonlystateprovinceflag, stateprovincename, territoryid, countryregioncode, countryregionname)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => VstateprovincecountryregionMVRow, merge: (NewRow, VstateprovincecountryregionMVRow) => NewRow): VstateprovincecountryregionMVStructure[NewRow] =
    new VstateprovincecountryregionMVStructure(prefix, extract, merge)
}