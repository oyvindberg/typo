package typo
package internal
package codegen

object sqlCast {

  /** cast to correctly insert into PG
    */
  def toPg(dbCol: db.Col): Option[String] =
    dbCol.tpe match {
      case db.Type.EnumRef(name) =>
        Some(tableName.toCode(name).render)
      case db.Type.DomainRef(name) =>
        Some(tableName.toCode(name).render)
      case db.Type.Boolean | db.Type.Text | db.Type.VarChar(_) => None
      case _ =>
        dbCol.udtName match {
          case Some(value) => Some(value)
          case None        => None
        }
    }

  def toPgCode(c: ComputedColumn): sc.Code =
    toPg(c.dbCol) match {
      case Some(value) => code"::$value"
      case None        => sc.Code.Empty
    }

  /** avoid whatever the postgres driver does for these data formats by going through basic data types
    */
  def fromPg(dbCol: db.Col): Option[String] =
    dbCol.tpe match {
      case db.Type.PGmoney =>
        Some("numeric")
      case db.Type.Array(db.Type.PGmoney) =>
        Some("numeric[]")
      case db.Type.TimestampTz | db.Type.Timestamp | db.Type.TimeTz | db.Type.Time | db.Type.Date =>
        Some("text")
      case db.Type.Array(db.Type.TimestampTz | db.Type.Timestamp | db.Type.TimeTz | db.Type.Time | db.Type.Date) =>
        Some("text[]")
      case _ => None
    }

  def fromPgCode(c: ComputedColumn): sc.Code =
    fromPg(c.dbCol) match {
      case Some(value) => code"::$value"
      case None        => sc.Code.Empty
    }

}
