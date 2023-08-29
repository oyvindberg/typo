/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_attrdef

import adventureworks.customtypes.TypoPgNodeTree
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

class PgAttrdefStructure[Row](val prefix: Option[String], val extract: Row => PgAttrdefRow, val merge: (Row, PgAttrdefRow) => Row)
  extends Relation[PgAttrdefFields, PgAttrdefRow, Row]
    with PgAttrdefFields[Row] { outer =>

  override val oid = new IdField[PgAttrdefId, Row](prefix, "oid", None, Some("oid"))(x => extract(x).oid, (row, value) => merge(row, extract(row).copy(oid = value)))
  override val adrelid = new Field[/* oid */ Long, Row](prefix, "adrelid", None, Some("oid"))(x => extract(x).adrelid, (row, value) => merge(row, extract(row).copy(adrelid = value)))
  override val adnum = new Field[Int, Row](prefix, "adnum", None, Some("int2"))(x => extract(x).adnum, (row, value) => merge(row, extract(row).copy(adnum = value)))
  override val adbin = new Field[TypoPgNodeTree, Row](prefix, "adbin", None, Some("pg_node_tree"))(x => extract(x).adbin, (row, value) => merge(row, extract(row).copy(adbin = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](oid, adrelid, adnum, adbin)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => PgAttrdefRow, merge: (NewRow, PgAttrdefRow) => NewRow): PgAttrdefStructure[NewRow] =
    new PgAttrdefStructure(prefix, extract, merge)
}