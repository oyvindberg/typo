package typo.dsl

/** Expresses that (tuples of) `Fields` structures can be joined.
  *
  * This also serves as the type-level connection between `Fields` and `Row`.
  */
trait Structure[Fields[_], Row] {
  val fields: Fields[Row]
  def imap[NewRow](extract: NewRow => Row)(merge: (NewRow, Row) => NewRow): Structure[Fields, NewRow]
  val columns: List[SqlExpr.FieldLikeNoHkt[?, Row]]

  final def join[Fields2[_], Row2](other: Structure[Fields2, Row2]): Structure[Joined[Fields, Fields2, *], (Row, Row2)] =
    new Structure.Tupled(
      left = this.imap[(Row, Row2)] { case (row1, _) => row1 } { case ((_, row2), updatedRow) => (updatedRow, row2) },
      right = other.imap[(Row, Row2)] { case (_, row2) => row2 } { case ((row1, _), updatedRow) => (row1, updatedRow) }
    )

  final def leftJoin[Fields2[_], Row2](other: Structure[Fields2, Row2]): Structure[LeftJoined[Fields, Fields2, *], (Row, Option[Row2])] =
    new Structure.LeftTupled(
      left = this.imap[(Row, Option[Row2])] { case (row1, _) => row1 } { case ((_, row2), updatedRow) => (updatedRow, row2) },
      right = other.imap[(Row, Option[Row2])] { case (_, row2) => row2.get } { case ((row1, _), updatedRow) => (row1, Some(updatedRow)) }
    )
}

object Structure {
  trait Relation[Fields[_], OriginalRow, Row] extends Structure[Fields, Row] { outer =>

    val prefix: Option[String]
    val extract: Row => OriginalRow
    val merge: (Row, OriginalRow) => Row

    def copy[NewRow](prefix: Option[String], extract: NewRow => OriginalRow, merge: (NewRow, OriginalRow) => NewRow): Relation[Fields, OriginalRow, NewRow]

    override def imap[NewRow](extract: NewRow => Row)(merge: (NewRow, Row) => NewRow): Relation[Fields, OriginalRow, NewRow] =
      copy[NewRow](
        prefix,
        newRow => outer.extract(extract(newRow)),
        (newRow, originalRow) => merge(newRow, outer.merge(extract(newRow), originalRow))
      )

    def withPrefix(newPrefix: String): Relation[Fields, OriginalRow, Row] =
      copy(Some(newPrefix), extract, merge)
  }

  private class Tupled[Fields1[_], Fields2[_], Row](val left: Structure[Fields1, Row], val right: Structure[Fields2, Row]) extends Structure[Joined[Fields1, Fields2, *], Row] {
    override val fields: Joined[Fields1, Fields2, Row] = (left.fields, right.fields)

    override def imap[NewRow](extract: NewRow => Row)(merge: (NewRow, Row) => NewRow): Tupled[Fields1, Fields2, NewRow] =
      new Tupled(left.imap[NewRow](extract)(merge), right.imap[NewRow](extract)(merge))

    override val columns: List[SqlExpr.FieldLikeNoHkt[?, Row]] =
      left.columns ++ right.columns
  }

  private class LeftTupled[Fields1[_], Fields2[_], Row](val left: Structure[Fields1, Row], val right: Structure[Fields2, Row]) extends Structure[LeftJoined[Fields1, Fields2, *], Row] {
    override val fields: LeftJoined[Fields1, Fields2, Row] = (left.fields, new OuterJoined(right.fields))

    override def imap[NewRow](extract: NewRow => Row)(merge: (NewRow, Row) => NewRow): LeftTupled[Fields1, Fields2, NewRow] =
      new LeftTupled(left.imap[NewRow](extract)(merge), right.imap[NewRow](extract)(merge))

    override val columns: List[SqlExpr.FieldLikeNoHkt[?, Row]] =
      left.columns ++ right.columns
  }
}
