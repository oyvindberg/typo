/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package phonenumbertype

import adventureworks.Defaulted.Provided
import adventureworks.Defaulted.UseDefault
import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection
import java.time.LocalDateTime

object PhonenumbertypeRepoImpl extends PhonenumbertypeRepo {
  override def delete(phonenumbertypeid: PhonenumbertypeId)(implicit c: Connection): Boolean = {
    SQL"""delete from person.phonenumbertype where phonenumbertypeid = $phonenumbertypeid""".executeUpdate() > 0
  }
  override def insert(unsaved: PhonenumbertypeRowUnsaved)(implicit c: Connection): PhonenumbertypeId = {
    val namedParameters = List(
      Some(NamedParameter("name", ParameterValue.from(unsaved.name))),
      unsaved.modifieddate match {
        case UseDefault => None
        case Provided(value) => Some(NamedParameter("modifieddate", ParameterValue.from[LocalDateTime](value)))
      }
    ).flatten
    
    SQL"""insert into person.phonenumbertype(${namedParameters.map(_.name).mkString(", ")})
          values (${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
          returning phonenumbertypeid
    """
      .on(namedParameters :_*)
      .executeInsert(PhonenumbertypeId.rowParser("").single)
  
  }
  override def selectAll(implicit c: Connection): List[PhonenumbertypeRow] = {
    SQL"""select phonenumbertypeid, name, modifieddate from person.phonenumbertype""".as(PhonenumbertypeRow.rowParser("").*)
  }
  override def selectByFieldValues(fieldValues: List[PhonenumbertypeFieldOrIdValue[_]])(implicit c: Connection): List[PhonenumbertypeRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PhonenumbertypeFieldValue.phonenumbertypeid(value) => NamedParameter("phonenumbertypeid", ParameterValue.from(value))
          case PhonenumbertypeFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
          case PhonenumbertypeFieldValue.modifieddate(value) => NamedParameter("modifieddate", ParameterValue.from(value))
        }
        val q = s"""select * from person.phonenumbertype where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .as(PhonenumbertypeRow.rowParser("").*)
    }
  
  }
  override def selectById(phonenumbertypeid: PhonenumbertypeId)(implicit c: Connection): Option[PhonenumbertypeRow] = {
    SQL"""select phonenumbertypeid, name, modifieddate from person.phonenumbertype where phonenumbertypeid = $phonenumbertypeid""".as(PhonenumbertypeRow.rowParser("").singleOpt)
  }
  override def selectByIds(phonenumbertypeids: List[PhonenumbertypeId])(implicit c: Connection): List[PhonenumbertypeRow] = {
    SQL"""select phonenumbertypeid, name, modifieddate from person.phonenumbertype where phonenumbertypeid in $phonenumbertypeids""".as(PhonenumbertypeRow.rowParser("").*)
  }
  override def update(phonenumbertypeid: PhonenumbertypeId, row: PhonenumbertypeRow)(implicit c: Connection): Boolean = {
    SQL"""update person.phonenumbertype
          set name = ${row.name},
              modifieddate = ${row.modifieddate}
          where phonenumbertypeid = $phonenumbertypeid""".executeUpdate() > 0
  }
  override def updateFieldValues(phonenumbertypeid: PhonenumbertypeId, fieldValues: List[PhonenumbertypeFieldValue[_]])(implicit c: Connection): Boolean = {
    fieldValues match {
      case Nil => false
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PhonenumbertypeFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
          case PhonenumbertypeFieldValue.modifieddate(value) => NamedParameter("modifieddate", ParameterValue.from(value))
        }
        val q = s"""update person.phonenumbertype
                    set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
                    where phonenumbertypeid = $phonenumbertypeid"""
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .executeUpdate() > 0
    }
  
  }
}