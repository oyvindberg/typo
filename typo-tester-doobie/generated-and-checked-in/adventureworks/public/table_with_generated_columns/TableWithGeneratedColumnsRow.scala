/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package table_with_generated_columns

import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** Table: public.table-with-generated-columns
    Primary key: name */
case class TableWithGeneratedColumnsRow(
  name: TableWithGeneratedColumnsId,
  /** Generated ALWAYS, expression: 
      CASE
          WHEN (name IS NOT NULL) THEN 'no-name'::text
          WHEN (name = 'a'::text) THEN 'a-name'::text
          ELSE 'some-name'::text
      END */
  nameTypeAlways: String
){
   val id = name
   def toUnsavedRow(): TableWithGeneratedColumnsRowUnsaved =
     TableWithGeneratedColumnsRowUnsaved(name)
 }

object TableWithGeneratedColumnsRow {
  implicit lazy val decoder: Decoder[TableWithGeneratedColumnsRow] = Decoder.forProduct2[TableWithGeneratedColumnsRow, TableWithGeneratedColumnsId, String]("name", "name-type-always")(TableWithGeneratedColumnsRow.apply)(TableWithGeneratedColumnsId.decoder, Decoder.decodeString)
  implicit lazy val encoder: Encoder[TableWithGeneratedColumnsRow] = Encoder.forProduct2[TableWithGeneratedColumnsRow, TableWithGeneratedColumnsId, String]("name", "name-type-always")(x => (x.name, x.nameTypeAlways))(TableWithGeneratedColumnsId.encoder, Encoder.encodeString)
  implicit lazy val read: Read[TableWithGeneratedColumnsRow] = new Read[TableWithGeneratedColumnsRow](
    gets = List(
      (TableWithGeneratedColumnsId.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => TableWithGeneratedColumnsRow(
      name = TableWithGeneratedColumnsId.get.unsafeGetNonNullable(rs, i + 0),
      nameTypeAlways = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 1)
    )
  )
  implicit lazy val text: Text[TableWithGeneratedColumnsRow] = Text.instance[TableWithGeneratedColumnsRow]{ (row, sb) =>
    TableWithGeneratedColumnsId.text.unsafeEncode(row.name, sb)
  }
  implicit lazy val write: Write[TableWithGeneratedColumnsRow] = new Write[TableWithGeneratedColumnsRow](
    puts = List((TableWithGeneratedColumnsId.put, Nullability.NoNulls)),
    toList = x => List(x.name),
    unsafeSet = (rs, i, a) => {
                  TableWithGeneratedColumnsId.put.unsafeSetNonNullable(rs, i + 0, a.name)
                },
    unsafeUpdate = (ps, i, a) => {
                     TableWithGeneratedColumnsId.put.unsafeUpdateNonNullable(ps, i + 0, a.name)
                   }
  )
}
