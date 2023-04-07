package typo
package internal

case class TypeMapperScala(
    typeOverride: TypeOverride,
    nullabilityOverride: NullabilityOverride,
    naming: Naming
) {
  def apply(relation: Either[RelPath, db.RelationName], col: db.Col, typeFromFK: Option[sc.Type]): sc.Type = {
    def go(tpe: db.Type): sc.Type = {
      val maybeOverridden = typeOverride(relation, col.name).map(overriddenString => sc.Type.UserDefined(sc.Type.Qualified(overriddenString)))
      val maybeFromFK = typeFromFK.map(stripOptionAndArray)
      val base = baseType(tpe)
      maybeOverridden.orElse(maybeFromFK).getOrElse(base)
    }

    val baseTpe = col.tpe match {
      case db.Type.Array(tpe) =>
        sc.Type.Array.of(go(tpe))
      case other =>
        go(other)
    }

    withNullability(baseTpe, nullabilityOverride.apply(relation, col.name).getOrElse(col.nullability))
  }

  def param(script: RelPath, colName: Option[db.ColName], dbType: db.Type, nullability: Nullability): sc.Type = {
    val base = dbType match {
      case db.Type.Array(tpe) =>
        sc.Type.Array.of(baseType(tpe))
      case other =>
        baseType(other)
    }
    val nullability1: Nullability = colName match {
      case Some(name) => nullabilityOverride(Left(script), name).getOrElse(nullability)
      case None       => nullability
    }
    withNullability(base, nullability1)
  }

  private def baseType(tpe: db.Type): sc.Type = {
    tpe match {
      case db.Type.Array(_)         => sys.error("no idea what to do with nested array types")
      case db.Type.BigInt           => sc.Type.Long
      case db.Type.Boolean          => sc.Type.Boolean
      case db.Type.Bytea            => sc.Type.Array.of(sc.Type.Byte)
      case db.Type.Bpchar           => sc.Type.String.withComment("bpchar")
      case db.Type.Char             => sc.Type.String
      case db.Type.Date             => sc.Type.LocalDate
      case db.Type.Float4           => sc.Type.Float
      case db.Type.Float8           => sc.Type.Double
      case db.Type.Hstore           => sc.Type.JavaMap.of(sc.Type.String, sc.Type.String)
      case db.Type.Inet             => sc.Type.PGobject.withComment("inet") // wip
      case db.Type.Int2             => sc.Type.Int // jdbc driver seems to return ints instead of floats
      case db.Type.Int4             => sc.Type.Int
      case db.Type.Int8             => sc.Type.Long
      case db.Type.Json             => sc.Type.String // wip
      case db.Type.Name             => sc.Type.String
      case db.Type.Numeric          => sc.Type.BigDecimal
      case db.Type.Oid              => sc.Type.Long.withComment("oid")
      case db.Type.PGInterval       => sc.Type.PGInterval
      case db.Type.PGbox            => sc.Type.PGbox
      case db.Type.PGcircle         => sc.Type.PGcircle
      case db.Type.PGline           => sc.Type.PGline
      case db.Type.PGlseg           => sc.Type.PGlseg
      case db.Type.PGlsn            => sc.Type.Long.withComment("pg_lsn")
      case db.Type.PGmoney          => sc.Type.PGmoney
      case db.Type.PGpath           => sc.Type.PGpath
      case db.Type.PGpoint          => sc.Type.PGpoint
      case db.Type.PGpolygon        => sc.Type.PGpolygon
      case db.Type.PgObject(tpe)    => sc.Type.PGobject.withComment(tpe)
      case db.Type.StringEnum(name) => sc.Type.Qualified(naming.enumName(name))
      case db.Type.Text             => sc.Type.String
      case db.Type.Time             => sc.Type.LocalDateTime
      case db.Type.Timestamp        => sc.Type.LocalDateTime
      case db.Type.TimestampTz      => sc.Type.ZonedDateTime
      case db.Type.UUID             => sc.Type.UUID
      case db.Type.Xml              => sc.Type.String.withComment("xml")
      case db.Type.VarChar(_)       => sc.Type.String
    }
  }

  def withNullability(tpe: sc.Type, nullability: Nullability): sc.Type =
    nullability match {
      case Nullability.NoNulls         => tpe
      case Nullability.Nullable        => sc.Type.Option.of(tpe)
      case Nullability.NullableUnknown => sc.Type.Option.of(tpe).withComment("nullability unknown")
    }

  def stripOptionAndArray(tpe: sc.Type): sc.Type =
    tpe match {
      case sc.Type.TApply(sc.Type.Option | sc.Type.Array, List(tpe)) =>
        stripOptionAndArray(tpe)
      case sc.Type.TApply(other, targs) =>
        sc.Type.TApply(stripOptionAndArray(other), targs.map(stripOptionAndArray))
      case sc.Type.Commented(underlying, comment) =>
        sc.Type.Commented(stripOptionAndArray(underlying), comment)
      case sc.Type.UserDefined(underlying) =>
        sc.Type.UserDefined(stripOptionAndArray(underlying))
      case tpe @ (sc.Type.Abstract(_) | sc.Type.Wildcard | sc.Type.Qualified(_)) =>
        tpe
    }
}
