package typo
package internal

case class TypeMapperScala(
    typeOverride: TypeOverride,
    nullabilityOverride: NullabilityOverride,
    naming: Naming,
    customTypes: CustomTypes
) {
  def col(from: Source, col: db.Col, typeFromFK: Option[sc.Type]): sc.Type = {
    def go(tpe: db.Type): sc.Type = {
      val maybeOverridden = typeOverride(from, col.name).map(overriddenString => sc.Type.UserDefined(sc.Type.Qualified(overriddenString)))
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

    withNullability(baseTpe, nullabilityOverride.apply(from, col.name).getOrElse(col.nullability))
  }

  def param(from: Source.SqlFileParam, maybeColName: Option[db.ColName], dbType: db.Type, nullability: Nullability): sc.Type = {
    def go(tpe: db.Type): sc.Type = {
      val maybeOverridden = {
        for {
          colName <- maybeColName
          overriddenString <- typeOverride(from, colName)
        } yield sc.Type.UserDefined(sc.Type.Qualified(overriddenString))
      }
      maybeOverridden.getOrElse(baseType(tpe))
    }

    val base = dbType match {
      case db.Type.Array(tpe) =>
        sc.Type.Array.of(go(tpe))
      case other =>
        go(other)
    }
    val nullability1: Nullability =
      maybeColName
        .flatMap(colName => nullabilityOverride(from, colName))
        .getOrElse(nullability)

    withNullability(base, nullability1)
  }

  // domains have nullability information, but afaiu it's used for checking,
  // and the optionality should live in the field definitions
  def domain(dbType: db.Type): sc.Type =
    dbType match {
      case db.Type.Array(tpe) =>
        sc.Type.Array.of(baseType(tpe))
      case other =>
        baseType(other)
    }

  private def baseType(tpe: db.Type): sc.Type = {
    tpe match {
      case db.Type.Array(_)        => sys.error("no idea what to do with nested array types")
      case db.Type.Boolean         => sc.Type.Boolean
      case db.Type.Bytea           => sc.Type.Array.of(sc.Type.Byte)
      case db.Type.Bpchar          => sc.Type.String.withComment("bpchar")
      case db.Type.Char            => sc.Type.String
      case db.Type.Date            => sc.Type.LocalDate
      case db.Type.DomainRef(name) => sc.Type.Qualified(naming.domainName(name))
      case db.Type.Float4          => sc.Type.Float
      case db.Type.Float8          => sc.Type.Double
      case db.Type.Hstore          => customTypes.TypoHStore.typoType
      case db.Type.Inet            => sc.Type.PGobject.withComment("inet") // wip
      case db.Type.Int2            => sc.Type.Int // jdbc driver seems to return ints instead of floats
      case db.Type.Int4            => sc.Type.Int
      case db.Type.Int8            => sc.Type.Long
      case db.Type.Json            => customTypes.TypoJson.typoType
      case db.Type.Jsonb           => customTypes.TypoJsonb.typoType
      case db.Type.Name            => sc.Type.String
      case db.Type.Numeric         => sc.Type.BigDecimal
      case db.Type.Oid             => sc.Type.Long.withComment("oid")
      case db.Type.PGInterval      => customTypes.TypoInterval.typoType
      case db.Type.PGbox           => customTypes.TypoBox.typoType
      case db.Type.PGcircle        => customTypes.TypoCircle.typoType
      case db.Type.PGline          => customTypes.TypoLine.typoType
      case db.Type.PGlseg          => customTypes.TypoLineSegment.typoType
      case db.Type.PGlsn           => sc.Type.Long.withComment("pg_lsn")
      case db.Type.PGmoney         => customTypes.TypoMoney.typoType
      case db.Type.PGpath          => customTypes.TypoPath.typoType
      case db.Type.PGpoint         => customTypes.TypoPoint.typoType
      case db.Type.PGpolygon       => customTypes.TypoPolygon.typoType
      case db.Type.PgObject(tpe)   => sc.Type.PGobject.withComment(tpe)
      case db.Type.EnumRef(name)   => sc.Type.Qualified(naming.enumName(name))
      case db.Type.Text            => sc.Type.String
      case db.Type.Time            => sc.Type.LocalTime
      case db.Type.Timestamp       => sc.Type.LocalDateTime
      case db.Type.TimestampTz     => sc.Type.ZonedDateTime
      case db.Type.UUID            => sc.Type.UUID
      case db.Type.Xml             => customTypes.TypoXml.typoType
      case db.Type.VarChar(_)      => sc.Type.String
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
      case sc.Type.ByName(underlying) =>
        sc.Type.ByName(stripOptionAndArray(underlying))
      case sc.Type.UserDefined(underlying) =>
        sc.Type.UserDefined(stripOptionAndArray(underlying))
      case tpe @ (sc.Type.Abstract(_) | sc.Type.Wildcard | sc.Type.Qualified(_)) =>
        tpe
    }
}
