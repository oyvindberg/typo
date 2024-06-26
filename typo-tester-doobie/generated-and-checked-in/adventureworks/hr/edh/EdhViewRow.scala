/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package hr
package edh

import adventureworks.customtypes.TypoLocalDate
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.humanresources.department.DepartmentId
import adventureworks.humanresources.shift.ShiftId
import adventureworks.person.businessentity.BusinessentityId
import doobie.enumerated.Nullability
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** View: hr.edh */
case class EdhViewRow(
  /** Points to [[humanresources.employeedepartmenthistory.EmployeedepartmenthistoryRow.businessentityid]] */
  id: BusinessentityId,
  /** Points to [[humanresources.employeedepartmenthistory.EmployeedepartmenthistoryRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[humanresources.employeedepartmenthistory.EmployeedepartmenthistoryRow.departmentid]] */
  departmentid: DepartmentId,
  /** Points to [[humanresources.employeedepartmenthistory.EmployeedepartmenthistoryRow.shiftid]] */
  shiftid: ShiftId,
  /** Points to [[humanresources.employeedepartmenthistory.EmployeedepartmenthistoryRow.startdate]] */
  startdate: TypoLocalDate,
  /** Points to [[humanresources.employeedepartmenthistory.EmployeedepartmenthistoryRow.enddate]] */
  enddate: Option[TypoLocalDate],
  /** Points to [[humanresources.employeedepartmenthistory.EmployeedepartmenthistoryRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object EdhViewRow {
  implicit lazy val decoder: Decoder[EdhViewRow] = Decoder.forProduct7[EdhViewRow, BusinessentityId, BusinessentityId, DepartmentId, ShiftId, TypoLocalDate, Option[TypoLocalDate], TypoLocalDateTime]("id", "businessentityid", "departmentid", "shiftid", "startdate", "enddate", "modifieddate")(EdhViewRow.apply)(BusinessentityId.decoder, BusinessentityId.decoder, DepartmentId.decoder, ShiftId.decoder, TypoLocalDate.decoder, Decoder.decodeOption(TypoLocalDate.decoder), TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[EdhViewRow] = Encoder.forProduct7[EdhViewRow, BusinessentityId, BusinessentityId, DepartmentId, ShiftId, TypoLocalDate, Option[TypoLocalDate], TypoLocalDateTime]("id", "businessentityid", "departmentid", "shiftid", "startdate", "enddate", "modifieddate")(x => (x.id, x.businessentityid, x.departmentid, x.shiftid, x.startdate, x.enddate, x.modifieddate))(BusinessentityId.encoder, BusinessentityId.encoder, DepartmentId.encoder, ShiftId.encoder, TypoLocalDate.encoder, Encoder.encodeOption(TypoLocalDate.encoder), TypoLocalDateTime.encoder)
  implicit lazy val read: Read[EdhViewRow] = new Read[EdhViewRow](
    gets = List(
      (BusinessentityId.get, Nullability.NoNulls),
      (BusinessentityId.get, Nullability.NoNulls),
      (DepartmentId.get, Nullability.NoNulls),
      (ShiftId.get, Nullability.NoNulls),
      (TypoLocalDate.get, Nullability.NoNulls),
      (TypoLocalDate.get, Nullability.Nullable),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => EdhViewRow(
      id = BusinessentityId.get.unsafeGetNonNullable(rs, i + 0),
      businessentityid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 1),
      departmentid = DepartmentId.get.unsafeGetNonNullable(rs, i + 2),
      shiftid = ShiftId.get.unsafeGetNonNullable(rs, i + 3),
      startdate = TypoLocalDate.get.unsafeGetNonNullable(rs, i + 4),
      enddate = TypoLocalDate.get.unsafeGetNullable(rs, i + 5),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 6)
    )
  )
}
