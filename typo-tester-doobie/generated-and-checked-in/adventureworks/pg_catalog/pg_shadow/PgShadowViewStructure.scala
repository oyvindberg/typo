/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_shadow

import adventureworks.customtypes.TypoInstant
import adventureworks.pg_catalog.pg_authid.PgAuthidId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

class PgShadowViewStructure[Row](val prefix: Option[String], val extract: Row => PgShadowViewRow, val merge: (Row, PgShadowViewRow) => Row)
  extends Relation[PgShadowViewFields, PgShadowViewRow, Row]
    with PgShadowViewFields[Row] { outer =>

  override val usename = new Field[String, Row](prefix, "usename", None, None)(x => extract(x).usename, (row, value) => merge(row, extract(row).copy(usename = value)))
  override val usesysid = new Field[PgAuthidId, Row](prefix, "usesysid", None, None)(x => extract(x).usesysid, (row, value) => merge(row, extract(row).copy(usesysid = value)))
  override val usecreatedb = new Field[Boolean, Row](prefix, "usecreatedb", None, None)(x => extract(x).usecreatedb, (row, value) => merge(row, extract(row).copy(usecreatedb = value)))
  override val usesuper = new Field[Boolean, Row](prefix, "usesuper", None, None)(x => extract(x).usesuper, (row, value) => merge(row, extract(row).copy(usesuper = value)))
  override val userepl = new Field[Boolean, Row](prefix, "userepl", None, None)(x => extract(x).userepl, (row, value) => merge(row, extract(row).copy(userepl = value)))
  override val usebypassrls = new Field[Boolean, Row](prefix, "usebypassrls", None, None)(x => extract(x).usebypassrls, (row, value) => merge(row, extract(row).copy(usebypassrls = value)))
  override val passwd = new OptField[String, Row](prefix, "passwd", None, None)(x => extract(x).passwd, (row, value) => merge(row, extract(row).copy(passwd = value)))
  override val valuntil = new OptField[TypoInstant, Row](prefix, "valuntil", Some("text"), None)(x => extract(x).valuntil, (row, value) => merge(row, extract(row).copy(valuntil = value)))
  override val useconfig = new OptField[Array[String], Row](prefix, "useconfig", None, None)(x => extract(x).useconfig, (row, value) => merge(row, extract(row).copy(useconfig = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](usename, usesysid, usecreatedb, usesuper, userepl, usebypassrls, passwd, valuntil, useconfig)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => PgShadowViewRow, merge: (NewRow, PgShadowViewRow) => NewRow): PgShadowViewStructure[NewRow] =
    new PgShadowViewStructure(prefix, extract, merge)
}