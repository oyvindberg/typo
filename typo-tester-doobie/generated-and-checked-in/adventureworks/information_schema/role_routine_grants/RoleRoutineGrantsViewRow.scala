/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package role_routine_grants

import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class RoleRoutineGrantsViewRow(
  /** Points to [[routine_privileges.RoutinePrivilegesViewRow.grantor]] */
  grantor: Option[/* nullability unknown */ String],
  /** Points to [[routine_privileges.RoutinePrivilegesViewRow.grantee]] */
  grantee: Option[/* nullability unknown */ String],
  /** Points to [[routine_privileges.RoutinePrivilegesViewRow.specificCatalog]] */
  specificCatalog: Option[/* nullability unknown */ String],
  /** Points to [[routine_privileges.RoutinePrivilegesViewRow.specificSchema]] */
  specificSchema: Option[/* nullability unknown */ String],
  /** Points to [[routine_privileges.RoutinePrivilegesViewRow.specificName]] */
  specificName: Option[/* nullability unknown */ String],
  /** Points to [[routine_privileges.RoutinePrivilegesViewRow.routineCatalog]] */
  routineCatalog: Option[/* nullability unknown */ String],
  /** Points to [[routine_privileges.RoutinePrivilegesViewRow.routineSchema]] */
  routineSchema: Option[/* nullability unknown */ String],
  /** Points to [[routine_privileges.RoutinePrivilegesViewRow.routineName]] */
  routineName: Option[/* nullability unknown */ String],
  /** Points to [[routine_privileges.RoutinePrivilegesViewRow.privilegeType]] */
  privilegeType: Option[/* nullability unknown */ String],
  /** Points to [[routine_privileges.RoutinePrivilegesViewRow.isGrantable]] */
  isGrantable: Option[/* nullability unknown */ /* max 3 chars */ String]
)

object RoleRoutineGrantsViewRow {
  implicit lazy val decoder: Decoder[RoleRoutineGrantsViewRow] = Decoder.forProduct10[RoleRoutineGrantsViewRow, Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ /* max 3 chars */ String]]("grantor", "grantee", "specific_catalog", "specific_schema", "specific_name", "routine_catalog", "routine_schema", "routine_name", "privilege_type", "is_grantable")(RoleRoutineGrantsViewRow.apply)(Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[RoleRoutineGrantsViewRow] = Encoder.forProduct10[RoleRoutineGrantsViewRow, Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ String], Option[/* nullability unknown */ /* max 3 chars */ String]]("grantor", "grantee", "specific_catalog", "specific_schema", "specific_name", "routine_catalog", "routine_schema", "routine_name", "privilege_type", "is_grantable")(x => (x.grantor, x.grantee, x.specificCatalog, x.specificSchema, x.specificName, x.routineCatalog, x.routineSchema, x.routineName, x.privilegeType, x.isGrantable))(Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[RoleRoutineGrantsViewRow] = new Read[RoleRoutineGrantsViewRow](
    gets = List(
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => RoleRoutineGrantsViewRow(
      grantor = Meta.StringMeta.get.unsafeGetNullable(rs, i + 0),
      grantee = Meta.StringMeta.get.unsafeGetNullable(rs, i + 1),
      specificCatalog = Meta.StringMeta.get.unsafeGetNullable(rs, i + 2),
      specificSchema = Meta.StringMeta.get.unsafeGetNullable(rs, i + 3),
      specificName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 4),
      routineCatalog = Meta.StringMeta.get.unsafeGetNullable(rs, i + 5),
      routineSchema = Meta.StringMeta.get.unsafeGetNullable(rs, i + 6),
      routineName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 7),
      privilegeType = Meta.StringMeta.get.unsafeGetNullable(rs, i + 8),
      isGrantable = Meta.StringMeta.get.unsafeGetNullable(rs, i + 9)
    )
  )
}