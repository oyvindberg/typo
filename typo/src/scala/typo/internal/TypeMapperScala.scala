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
        sc.Type.ArrayOf(go(tpe))
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
        sc.Type.ArrayOf(go(tpe))
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
        sc.Type.ArrayOf(baseType(tpe))
      case other =>
        baseType(other)
    }

  private def baseType(tpe: db.Type): sc.Type = {
    tpe match {
      case db.Type.Array(_) => sys.error("no idea what to do with nested array types")
      case db.Type.Boolean  => TypesScala.Boolean
      case db.Type.Bytea    => customTypes.TypoBytea.typoType
      case db.Type.Bpchar(maybeN) =>
        maybeN match {
          case Some(n) if n != 2147483647 => TypesJava.String.withComment(s"bpchar, max $n chars")
          case _                          => TypesJava.String.withComment(s"bpchar")
        }
      case db.Type.Char                  => TypesJava.String
      case db.Type.Date                  => customTypes.TypoLocalDate.typoType
      case db.Type.DomainRef(name, _, _) => sc.Type.Qualified(naming.domainName(name))
      case db.Type.Float4                => TypesScala.Float
      case db.Type.Float8                => TypesScala.Double
      case db.Type.Hstore                => customTypes.TypoHStore.typoType
      case db.Type.Inet                  => customTypes.TypoInet.typoType
      case db.Type.Int2                  => customTypes.TypoShort.typoType
      case db.Type.Int4                  => TypesScala.Int
      case db.Type.Int8                  => TypesScala.Long
      case db.Type.Json                  => customTypes.TypoJson.typoType
      case db.Type.Jsonb                 => customTypes.TypoJsonb.typoType
      case db.Type.Name                  => TypesJava.String
      case db.Type.Numeric               => TypesScala.BigDecimal
      case db.Type.Oid                   => TypesScala.Long.withComment("oid")
      case db.Type.PGInterval            => customTypes.TypoInterval.typoType
      case db.Type.PGbox                 => customTypes.TypoBox.typoType
      case db.Type.PGcircle              => customTypes.TypoCircle.typoType
      case db.Type.PGline                => customTypes.TypoLine.typoType
      case db.Type.PGlseg                => customTypes.TypoLineSegment.typoType
      case db.Type.PGlsn                 => TypesScala.Long.withComment("pg_lsn")
      case db.Type.PGmoney               => customTypes.TypoMoney.typoType
      case db.Type.PGpath                => customTypes.TypoPath.typoType
      case db.Type.PGpoint               => customTypes.TypoPoint.typoType
      case db.Type.PGpolygon             => customTypes.TypoPolygon.typoType
      case db.Type.aclitem               => customTypes.TypoAclItem.typoType
      case db.Type.anyarray              => customTypes.TypoAnyArray.typoType
      case db.Type.int2vector            => customTypes.TypoInt2Vector.typoType
      case db.Type.oidvector             => customTypes.TypoOidVector.typoType
      case db.Type.pg_node_tree          => customTypes.TypoPgNodeTree.typoType
      case db.Type.record                => customTypes.TypoRecord.typoType
      case db.Type.regclass              => customTypes.TypoRegclass.typoType
      case db.Type.regconfig             => customTypes.TypoRegconfig.typoType
      case db.Type.regdictionary         => customTypes.TypoRegdictionary.typoType
      case db.Type.regnamespace          => customTypes.TypoRegnamespace.typoType
      case db.Type.regoper               => customTypes.TypoRegoper.typoType
      case db.Type.regoperator           => customTypes.TypoRegoperator.typoType
      case db.Type.regproc               => customTypes.TypoRegproc.typoType
      case db.Type.regprocedure          => customTypes.TypoRegprocedure.typoType
      case db.Type.regrole               => customTypes.TypoRegrole.typoType
      case db.Type.regtype               => customTypes.TypoRegtype.typoType
      case db.Type.xid                   => customTypes.TypoXid.typoType
      case db.Type.EnumRef(enm)          => sc.Type.Qualified(naming.enumName(enm.name))
      case db.Type.Text                  => TypesJava.String
      case db.Type.Time                  => customTypes.TypoLocalTime.typoType
      case db.Type.TimeTz                => customTypes.TypoOffsetTime.typoType
      case db.Type.Timestamp             => customTypes.TypoLocalDateTime.typoType
      case db.Type.TimestampTz           => customTypes.TypoInstant.typoType
      case db.Type.UUID                  => customTypes.TypoUUID.typoType
      case db.Type.Xml                   => customTypes.TypoXml.typoType
      case db.Type.VarChar(maybeN) =>
        maybeN match {
          case Some(n) if n != 2147483647 => TypesJava.String.withComment(s"max $n chars")
          case _                          => TypesJava.String
        }
      case db.Type.Vector           => customTypes.TypoVector.typoType
      case db.Type.Unknown(sqlType) => customTypes.TypoUnknown(sqlType).typoType
    }
  }

  def withNullability(tpe: sc.Type, nullability: Nullability): sc.Type =
    nullability match {
      case Nullability.NoNulls         => tpe
      case Nullability.Nullable        => TypesScala.Option.of(tpe)
      case Nullability.NullableUnknown => TypesScala.Option.of(tpe).withComment("nullability unknown")
    }

  def stripOptionAndArray(tpe: sc.Type): sc.Type =
    tpe match {
      case sc.Type.ArrayOf(tpe) =>
        stripOptionAndArray(tpe)
      case sc.Type.TApply(TypesScala.Option, List(tpe)) =>
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
