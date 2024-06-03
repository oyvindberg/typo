package typo
package internal
package codegen

case class SqlCast(typeName: String) {
  val withColons = s"::$typeName"
  val asCode: sc.Code = sc.Code.Str(withColons)

  override def toString: String = sys.error("don't write directly")
}

object SqlCast {

  /** cast to correctly insert into PG
    */
  def toPg(dbCol: db.Col): Option[SqlCast] =
    toPg(dbCol.tpe, dbCol.udtName)

  def toPg(param: ComputedSqlFile.Param): Option[SqlCast] =
    toPg(param.dbType, Some(param.udtName))

  /** cast to correctly insert into PG
    */
  def toPg(dbType: db.Type, udtName: Option[String]): Option[SqlCast] =
    dbType match {
      case db.Type.Unknown(sqlType)                            => Some(SqlCast(sqlType))
      case db.Type.EnumRef(name)                               => Some(SqlCast(name.value))
      case db.Type.Boolean | db.Type.Text | db.Type.VarChar(_) => None
      case _ =>
        udtName.map {
          case ArrayName(x) => SqlCast(x + "[]")
          case other        => SqlCast(other)
        }
    }

  def toPgCode(c: ComputedColumn): sc.Code =
    toPg(c.dbCol).fold(sc.Code.Empty)(_.asCode)

  /** avoid whatever the postgres driver does for these data formats by going through basic data types
    */
  def fromPg(dbCol: db.Col): Option[SqlCast] =
    dbCol.tpe match {
      case db.Type.Array(db.Type.Unknown(_)) | db.Type.Array(db.Type.DomainRef(_, _, db.Type.Unknown(_))) =>
        Some(SqlCast("text[]"))
      case db.Type.Unknown(_) | db.Type.DomainRef(_, _, db.Type.Unknown(_)) =>
        Some(SqlCast("text"))
      case db.Type.PGmoney =>
        Some(SqlCast("numeric"))
      case db.Type.Vector =>
        Some(SqlCast("float4[]"))
      case db.Type.Array(db.Type.PGmoney) =>
        Some(SqlCast("numeric[]"))
      case db.Type.Array(db.Type.DomainRef(_, underlying, _)) =>
        Some(SqlCast(underlying + "[]"))
      case db.Type.TimestampTz | db.Type.Timestamp | db.Type.TimeTz | db.Type.Time | db.Type.Date =>
        Some(SqlCast("text"))
      case db.Type.Array(db.Type.TimestampTz | db.Type.Timestamp | db.Type.TimeTz | db.Type.Time | db.Type.Date) =>
        Some(SqlCast("text[]"))
      case _ => None
    }

  def fromPgCode(c: ComputedColumn): sc.Code =
    fromPg(c.dbCol).fold(sc.Code.Empty)(_.asCode)
}
