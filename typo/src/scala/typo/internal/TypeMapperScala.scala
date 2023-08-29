package typo
package internal

case class TypeMapperScala(
    typeOverride: TypeOverride,
    nullabilityOverride: NullabilityOverride,
    naming: Naming,
    customTypes: CustomTypes
) {
  def col(relation: db.RelationName, col: db.Col, typeFromFK: Option[sc.Type]): sc.Type = {
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

  def sqlFile(maybeOverridden: Option[sc.Type], dbType: db.Type, nullability: Nullability): sc.Type = {
    def go(tpe: db.Type): sc.Type =
      maybeOverridden.map(stripOptionAndArray).getOrElse(baseType(tpe))

    val base = dbType match {
      case db.Type.Array(tpe) =>
        sc.Type.Array.of(go(tpe))
      case other =>
        go(other)
    }

    withNullability(base, nullability)
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
      case db.Type.Array(_) => sys.error("no idea what to do with nested array types")
      case db.Type.Boolean  => sc.Type.Boolean
      case db.Type.Bytea    => sc.Type.Array.of(sc.Type.Byte)
      case db.Type.Bpchar(maybeN) =>
        maybeN match {
          case Some(n) if n != 2147483647 => sc.Type.String.withComment(s"bpchar, max $n chars")
          case _                          => sc.Type.String.withComment(s"bpchar")
        }
      case db.Type.Char            => sc.Type.String
      case db.Type.Date            => customTypes.TypoLocalDate.typoType
      case db.Type.DomainRef(name) => sc.Type.Qualified(naming.domainName(name))
      case db.Type.Float4          => sc.Type.Float
      case db.Type.Float8          => sc.Type.Double
      case db.Type.Hstore          => customTypes.TypoHStore.typoType
      case db.Type.Inet            => customTypes.TypoInet.typoType
      case db.Type.Int2            => customTypes.TypoShort.typoType
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
      case db.Type.aclitem         => customTypes.TypoAclItem.typoType
      case db.Type.anyarray        => customTypes.TypoAnyArray.typoType
      case db.Type.int2vector      => customTypes.TypoInt2Vector.typoType
      case db.Type.oidvector       => customTypes.TypoOidVector.typoType
      case db.Type.pg_node_tree    => customTypes.TypoPgNodeTree.typoType
      case db.Type.regclass        => customTypes.TypoRegclass.typoType
      case db.Type.regconfig       => customTypes.TypoRegconfig.typoType
      case db.Type.regdictionary   => customTypes.TypoRegdictionary.typoType
      case db.Type.regnamespace    => customTypes.TypoRegnamespace.typoType
      case db.Type.regoper         => customTypes.TypoRegoper.typoType
      case db.Type.regoperator     => customTypes.TypoRegoperator.typoType
      case db.Type.regproc         => customTypes.TypoRegproc.typoType
      case db.Type.regprocedure    => customTypes.TypoRegprocedure.typoType
      case db.Type.regrole         => customTypes.TypoRegrole.typoType
      case db.Type.regtype         => customTypes.TypoRegtype.typoType
      case db.Type.xid             => customTypes.TypoXid.typoType
      case db.Type.EnumRef(name)   => sc.Type.Qualified(naming.enumName(name))
      case db.Type.Text            => sc.Type.String
      case db.Type.Time            => customTypes.TypoLocalTime.typoType
      case db.Type.TimeTz          => customTypes.TypoOffsetTime.typoType
      case db.Type.Timestamp       => customTypes.TypoLocalDateTime.typoType
      case db.Type.TimestampTz     => customTypes.TypoOffsetDateTime.typoType
      case db.Type.UUID            => sc.Type.UUID
      case db.Type.Xml             => customTypes.TypoXml.typoType
      case db.Type.VarChar(maybeN) =>
        maybeN match {
          case Some(n) if n != 2147483647 => sc.Type.String.withComment(s"max $n chars")
          case _                          => sc.Type.String
        }
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
