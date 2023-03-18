package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgTsConfigMapRepoImpl extends PgTsConfigMapRepo {
  override def selectAll(implicit c: Connection): List[PgTsConfigMapRow] = {
    SQL"""select mapcfg, maptokentype, mapseqno, mapdict from pg_catalog.pg_ts_config_map""".as(PgTsConfigMapRow.rowParser.*)
  }
  override def selectById(mapcfgAndMaptokentypeAndMapseqno: PgTsConfigMapId)(implicit c: Connection): Option[PgTsConfigMapRow] = {
    SQL"""select mapcfg, maptokentype, mapseqno, mapdict from pg_catalog.pg_ts_config_map where mapcfg = ${mapcfgAndMaptokentypeAndMapseqno.mapcfg}, maptokentype = ${mapcfgAndMaptokentypeAndMapseqno.maptokentype}, mapseqno = ${mapcfgAndMaptokentypeAndMapseqno.mapseqno}""".as(PgTsConfigMapRow.rowParser.singleOpt)
  }
  override def selectByFieldValues(fieldValues: List[PgTsConfigMapFieldValue[_]])(implicit c: Connection): List[PgTsConfigMapRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTsConfigMapFieldValue.mapcfg(value) => NamedParameter("mapcfg", ParameterValue.from(value))
          case PgTsConfigMapFieldValue.maptokentype(value) => NamedParameter("maptokentype", ParameterValue.from(value))
          case PgTsConfigMapFieldValue.mapseqno(value) => NamedParameter("mapseqno", ParameterValue.from(value))
          case PgTsConfigMapFieldValue.mapdict(value) => NamedParameter("mapdict", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_ts_config_map where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgTsConfigMapRow.rowParser.*)
    }

  }
  override def updateFieldValues(mapcfgAndMaptokentypeAndMapseqno: PgTsConfigMapId, fieldValues: List[PgTsConfigMapFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTsConfigMapFieldValue.mapcfg(value) => NamedParameter("mapcfg", ParameterValue.from(value))
          case PgTsConfigMapFieldValue.maptokentype(value) => NamedParameter("maptokentype", ParameterValue.from(value))
          case PgTsConfigMapFieldValue.mapseqno(value) => NamedParameter("mapseqno", ParameterValue.from(value))
          case PgTsConfigMapFieldValue.mapdict(value) => NamedParameter("mapdict", ParameterValue.from(value))
        }
        SQL"""update pg_catalog.pg_ts_config_map
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where mapcfg = ${mapcfgAndMaptokentypeAndMapseqno.mapcfg}, maptokentype = ${mapcfgAndMaptokentypeAndMapseqno.maptokentype}, mapseqno = ${mapcfgAndMaptokentypeAndMapseqno.mapseqno}"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(mapcfgAndMaptokentypeAndMapseqno: PgTsConfigMapId, unsaved: PgTsConfigMapRowUnsaved)(implicit c: Connection): Unit = {
    val namedParameters = List(
      Some(NamedParameter("mapdict", ParameterValue.from(unsaved.mapdict)))
    ).flatten

    SQL"""insert into pg_catalog.pg_ts_config_map(mapcfg, maptokentype, mapseqno, ${namedParameters.map(_.name).mkString(", ")})
      values (${mapcfgAndMaptokentypeAndMapseqno.mapcfg}, ${mapcfgAndMaptokentypeAndMapseqno.maptokentype}, ${mapcfgAndMaptokentypeAndMapseqno.mapseqno}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      """
      .on(namedParameters :_*)
      .execute()

  }
  override def delete(mapcfgAndMaptokentypeAndMapseqno: PgTsConfigMapId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_ts_config_map where mapcfg = ${mapcfgAndMaptokentypeAndMapseqno.mapcfg}, maptokentype = ${mapcfgAndMaptokentypeAndMapseqno.maptokentype}, mapseqno = ${mapcfgAndMaptokentypeAndMapseqno.mapseqno}""".executeUpdate() > 0
  }
}
