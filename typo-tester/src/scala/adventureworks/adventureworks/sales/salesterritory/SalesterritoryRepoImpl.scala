/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritory

import adventureworks.Defaulted.Provided
import adventureworks.Defaulted.UseDefault
import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection
import java.time.LocalDateTime
import java.util.UUID

object SalesterritoryRepoImpl extends SalesterritoryRepo {
  override def delete(territoryid: SalesterritoryId)(implicit c: Connection): Boolean = {
    SQL"""delete from sales.salesterritory where territoryid = $territoryid""".executeUpdate() > 0
  }
  override def insert(unsaved: SalesterritoryRowUnsaved)(implicit c: Connection): SalesterritoryId = {
    val namedParameters = List(
      Some(NamedParameter("name", ParameterValue.from(unsaved.name))),
      Some(NamedParameter("countryregioncode", ParameterValue.from(unsaved.countryregioncode))),
      Some(NamedParameter("group", ParameterValue.from(unsaved.group))),
      unsaved.salesytd match {
        case UseDefault => None
        case Provided(value) => Some(NamedParameter("salesytd", ParameterValue.from[BigDecimal](value)))
      },
      unsaved.saleslastyear match {
        case UseDefault => None
        case Provided(value) => Some(NamedParameter("saleslastyear", ParameterValue.from[BigDecimal](value)))
      },
      unsaved.costytd match {
        case UseDefault => None
        case Provided(value) => Some(NamedParameter("costytd", ParameterValue.from[BigDecimal](value)))
      },
      unsaved.costlastyear match {
        case UseDefault => None
        case Provided(value) => Some(NamedParameter("costlastyear", ParameterValue.from[BigDecimal](value)))
      },
      unsaved.rowguid match {
        case UseDefault => None
        case Provided(value) => Some(NamedParameter("rowguid", ParameterValue.from[UUID](value)))
      },
      unsaved.modifieddate match {
        case UseDefault => None
        case Provided(value) => Some(NamedParameter("modifieddate", ParameterValue.from[LocalDateTime](value)))
      }
    ).flatten
    
    SQL"""insert into sales.salesterritory(${namedParameters.map(_.name).mkString(", ")})
          values (${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
          returning territoryid
    """
      .on(namedParameters :_*)
      .executeInsert(SalesterritoryId.rowParser("").single)
  
  }
  override def selectAll(implicit c: Connection): List[SalesterritoryRow] = {
    SQL"""select territoryid, name, countryregioncode, group, salesytd, saleslastyear, costytd, costlastyear, rowguid, modifieddate from sales.salesterritory""".as(SalesterritoryRow.rowParser("").*)
  }
  override def selectByFieldValues(fieldValues: List[SalesterritoryFieldOrIdValue[_]])(implicit c: Connection): List[SalesterritoryRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case SalesterritoryFieldValue.territoryid(value) => NamedParameter("territoryid", ParameterValue.from(value))
          case SalesterritoryFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
          case SalesterritoryFieldValue.countryregioncode(value) => NamedParameter("countryregioncode", ParameterValue.from(value))
          case SalesterritoryFieldValue.group(value) => NamedParameter("group", ParameterValue.from(value))
          case SalesterritoryFieldValue.salesytd(value) => NamedParameter("salesytd", ParameterValue.from(value))
          case SalesterritoryFieldValue.saleslastyear(value) => NamedParameter("saleslastyear", ParameterValue.from(value))
          case SalesterritoryFieldValue.costytd(value) => NamedParameter("costytd", ParameterValue.from(value))
          case SalesterritoryFieldValue.costlastyear(value) => NamedParameter("costlastyear", ParameterValue.from(value))
          case SalesterritoryFieldValue.rowguid(value) => NamedParameter("rowguid", ParameterValue.from(value))
          case SalesterritoryFieldValue.modifieddate(value) => NamedParameter("modifieddate", ParameterValue.from(value))
        }
        val q = s"""select * from sales.salesterritory where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .as(SalesterritoryRow.rowParser("").*)
    }
  
  }
  override def selectById(territoryid: SalesterritoryId)(implicit c: Connection): Option[SalesterritoryRow] = {
    SQL"""select territoryid, name, countryregioncode, group, salesytd, saleslastyear, costytd, costlastyear, rowguid, modifieddate from sales.salesterritory where territoryid = $territoryid""".as(SalesterritoryRow.rowParser("").singleOpt)
  }
  override def selectByIds(territoryids: List[SalesterritoryId])(implicit c: Connection): List[SalesterritoryRow] = {
    SQL"""select territoryid, name, countryregioncode, group, salesytd, saleslastyear, costytd, costlastyear, rowguid, modifieddate from sales.salesterritory where territoryid in $territoryids""".as(SalesterritoryRow.rowParser("").*)
  }
  override def update(territoryid: SalesterritoryId, row: SalesterritoryRow)(implicit c: Connection): Boolean = {
    SQL"""update sales.salesterritory
          set name = ${row.name},
              countryregioncode = ${row.countryregioncode},
              group = ${row.group},
              salesytd = ${row.salesytd},
              saleslastyear = ${row.saleslastyear},
              costytd = ${row.costytd},
              costlastyear = ${row.costlastyear},
              rowguid = ${row.rowguid},
              modifieddate = ${row.modifieddate}
          where territoryid = $territoryid""".executeUpdate() > 0
  }
  override def updateFieldValues(territoryid: SalesterritoryId, fieldValues: List[SalesterritoryFieldValue[_]])(implicit c: Connection): Boolean = {
    fieldValues match {
      case Nil => false
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case SalesterritoryFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
          case SalesterritoryFieldValue.countryregioncode(value) => NamedParameter("countryregioncode", ParameterValue.from(value))
          case SalesterritoryFieldValue.group(value) => NamedParameter("group", ParameterValue.from(value))
          case SalesterritoryFieldValue.salesytd(value) => NamedParameter("salesytd", ParameterValue.from(value))
          case SalesterritoryFieldValue.saleslastyear(value) => NamedParameter("saleslastyear", ParameterValue.from(value))
          case SalesterritoryFieldValue.costytd(value) => NamedParameter("costytd", ParameterValue.from(value))
          case SalesterritoryFieldValue.costlastyear(value) => NamedParameter("costlastyear", ParameterValue.from(value))
          case SalesterritoryFieldValue.rowguid(value) => NamedParameter("rowguid", ParameterValue.from(value))
          case SalesterritoryFieldValue.modifieddate(value) => NamedParameter("modifieddate", ParameterValue.from(value))
        }
        val q = s"""update sales.salesterritory
                    set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
                    where territoryid = $territoryid"""
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .executeUpdate() > 0
    }
  
  }
}