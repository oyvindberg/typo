/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package role_routine_grants

import adventureworks.information_schema.CharacterData
import adventureworks.information_schema.SqlIdentifier
import adventureworks.information_schema.YesOrNo
import typo.dsl.SqlExpr.OptField

trait RoleRoutineGrantsViewFields[Row] {
  val grantor: OptField[SqlIdentifier, Row]
  val grantee: OptField[SqlIdentifier, Row]
  val specificCatalog: OptField[SqlIdentifier, Row]
  val specificSchema: OptField[SqlIdentifier, Row]
  val specificName: OptField[SqlIdentifier, Row]
  val routineCatalog: OptField[SqlIdentifier, Row]
  val routineSchema: OptField[SqlIdentifier, Row]
  val routineName: OptField[SqlIdentifier, Row]
  val privilegeType: OptField[CharacterData, Row]
  val isGrantable: OptField[YesOrNo, Row]
}
object RoleRoutineGrantsViewFields extends RoleRoutineGrantsViewStructure[RoleRoutineGrantsViewRow](None, identity, (_, x) => x)
