package typo
package internal

import typo.internal.codegen.*

case class ComputedTestInserts(tpe: sc.Type.Qualified, methods: List[ComputedTestInserts.InsertMethod])

object ComputedTestInserts {
  val random: sc.Ident = sc.Ident("random")
  def apply(options: InternalOptions, customTypes: CustomTypes, domains: List[ComputedDomain], enums: List[ComputedStringEnum], computedTables: Iterable[ComputedTable]) = {
    val domainsByName: Map[sc.Type, ComputedDomain] =
      domains.iterator.map(x => x.tpe -> x).toMap
    val enumsByName: Map[sc.Type, ComputedStringEnum] =
      enums.iterator.map(x => x.tpe -> x).toMap
    def defaultFor(table: ComputedTable, tpe: sc.Type, dbType: db.Type) = {
      def defaultLocalDate = code"${TypesJava.LocalDate}.ofEpochDay($random.nextInt(30000).toLong)"
      def defaultLocalTime = code"${TypesJava.LocalTime}.ofSecondOfDay($random.nextInt(24 * 60 * 60).toLong)"
      def defaultLocalDateTime = code"${TypesJava.LocalDateTime}.of($defaultLocalDate, $defaultLocalTime)"
      def defaultZoneOffset = code"${TypesJava.ZoneOffset}.ofHours($random.nextInt(24) - 12)"

      def go(tpe: sc.Type, dbType: db.Type): Option[sc.Code] =
        tpe match {
          case TypesJava.String =>
            val max: Int =
              Option(dbType)
                .collect {
                  case db.Type.VarChar(Some(maxLength)) => maxLength
                  case db.Type.Bpchar(Some(maxLength))  => maxLength
                }
                .getOrElse(20)
                .min(20)
            Some(code"$random.alphanumeric.take($max).mkString")
          case TypesScala.Boolean => Some(code"$random.nextBoolean()")
          case TypesScala.Char    => Some(code"$random.nextPrintableChar()")
          case TypesScala.Byte    => Some(code"$random.nextInt(${TypesScala.Byte}.MaxValue).toByte")
          case TypesScala.Short   => Some(code"$random.nextInt(${TypesScala.Short}.MaxValue).toShort")
          case TypesScala.Int =>
            dbType match {
              case db.Type.Int2 => Some(code"$random.nextInt(${TypesScala.Short}.MaxValue)")
              case _            => Some(code"$random.nextInt()")
            }
          case TypesScala.Long       => Some(code"$random.nextLong()")
          case TypesScala.Float      => Some(code"$random.nextFloat()")
          case TypesScala.Double     => Some(code"$random.nextDouble()")
          case TypesScala.BigDecimal => Some(code"${TypesScala.BigDecimal}.decimal($random.nextDouble())")
          case TypesJava.UUID        => Some(code"${TypesJava.UUID}.nameUUIDFromBytes{val bs = ${TypesScala.Array}.ofDim[${TypesScala.Byte}](16); $random.nextBytes(bs); bs}")
          case TypesScala.Optional(underlying) =>
            go(underlying, dbType) match {
              case None          => Some(TypesScala.None.code)
              case Some(default) => Some(code"if ($random.nextBoolean()) ${TypesScala.None} else ${TypesScala.Some}($default)")
            }
          case sc.Type.TApply(TypesScala.Array, List(underlying)) =>
            dbType match {
              case db.Type.Array(underlyingDb) =>
                go(underlying, underlyingDb).map { default =>
                  code"${TypesScala.Array}.fill(random.nextInt(3))($default)"
                }
              case _ => None
            }

          case customTypes.TypoShort.typoType =>
            Some(code"${customTypes.TypoShort.typoType}($random.nextInt(Short.MaxValue).toShort)")
          case customTypes.TypoLocalDate.typoType =>
            Some(code"${customTypes.TypoLocalDate.typoType}($defaultLocalDate)")
          case customTypes.TypoLocalTime.typoType =>
            Some(code"${customTypes.TypoLocalTime.typoType}($defaultLocalTime)")
          case customTypes.TypoLocalDateTime.typoType =>
            Some(code"${customTypes.TypoLocalDateTime.typoType}($defaultLocalDateTime)")
          case customTypes.TypoOffsetTime.typoType =>
            Some(code"${customTypes.TypoOffsetTime.typoType}($defaultLocalTime.atOffset($defaultZoneOffset))")
          case customTypes.TypoInstant.typoType =>
            Some(code"${customTypes.TypoInstant.typoType}(${TypesJava.Instant}.ofEpochMilli($random.nextLong()))")
          case sc.Type.TApply(table.default.Defaulted, _) =>
            Some(code"${table.default.Defaulted}.${table.default.UseDefault}")
          case tpe if domainsByName.contains(tpe) =>
            go(domainsByName(tpe).underlyingType, dbType).map(inner => code"$tpe($inner)")
          case tpe if enumsByName.contains(tpe) =>
            Some(code"$tpe.All($random.nextInt($tpe.All.length))")
          case _ =>
            None
        }

      go(sc.Type.base(tpe), dbType)
    }

    new ComputedTestInserts(
      sc.Type.Qualified(options.pkg / sc.Ident("testInsert")),
      computedTables.collect {
        case table if !options.readonlyRepo.include(table.dbTable.name) =>
          val cols: NonEmptyList[ComputedColumn] =
            table.maybeUnsavedRow match {
              case Some(unsaved) => unsaved.allCols
              case None          => table.cols
            }

          val params: List[sc.Param] = {
            val asParams = cols.map { col =>
              val default = defaultFor(table, col.tpe, col.dbCol.tpe)
              sc.Param(col.name, col.tpe, default)
            }
            val (requiredParams, optionalParams) = asParams.toList.partition(_.default.isEmpty)
            requiredParams ++ optionalParams
          }

          ComputedTestInserts.InsertMethod(table, params)
      }.toList
    )
  }

  case class InsertMethod(table: ComputedTable, params: List[sc.Param]) {
    val name: sc.Ident =
      table.dbTable.name match {
        case db.RelationName(Some(schema), name) => sc.Ident(s"${Naming.camelCase(schema)}${Naming.titleCase(name)}")
        case db.RelationName(None, name)         => sc.Ident(Naming.titleCase(name))
      }

    val cls = table.maybeUnsavedRow match {
      case Some(unsaved) => unsaved.tpe
      case None          => table.names.RowName
    }
  }
}
