package typo
package internal

import typo.internal.codegen.*

case class ComputedTestInserts(tpe: sc.Type.Qualified, methods: List[ComputedTestInserts.InsertMethod])

object ComputedTestInserts {
  val random: sc.Ident = sc.Ident("random")
  def apply(projectName: String, options: InternalOptions, customTypes: CustomTypes, domains: List[ComputedDomain], enums: List[ComputedStringEnum], computedTables: Iterable[ComputedTable]) = {
    val domainsByName: Map[sc.Type, ComputedDomain] =
      domains.iterator.map(x => x.tpe -> x).toMap
    val enumsByName: Map[sc.Type, ComputedStringEnum] =
      enums.iterator.map(x => x.tpe -> x).toMap

    def defaultFor(table: ComputedTable, tpe: sc.Type, dbType: db.Type) = {
      def defaultLocalDate = code"${TypesJava.LocalDate}.ofEpochDay($random.nextInt(30000).toLong)"
      def defaultLocalTime = code"${TypesJava.LocalTime}.ofSecondOfDay($random.nextInt(24 * 60 * 60).toLong)"
      def defaultLocalDateTime = code"${TypesJava.LocalDateTime}.of($defaultLocalDate, $defaultLocalTime)"
      def defaultZoneOffset = code"${TypesJava.ZoneOffset}.ofHours($random.nextInt(24) - 12)"

      def go(tpe: sc.Type, dbType: db.Type, tableUnaryId: Option[IdComputed.Unary]): Option[sc.Code] =
        tpe match {
          case tpe if tableUnaryId.exists(_.tpe == tpe) =>
            tableUnaryId.get match {
              case x: IdComputed.UnaryNormal        => go(x.underlying, x.col.dbCol.tpe, None).map(default => code"${x.tpe}($default)")
              case x: IdComputed.UnaryInherited     => go(x.underlying, x.col.dbCol.tpe, None).map(default => code"${x.tpe}($default)")
              case x: IdComputed.UnaryNoIdType      => go(x.underlying, x.col.dbCol.tpe, None)
              case _: IdComputed.UnaryKnownValues   => None // todo
              case _: IdComputed.UnaryUserSpecified => None
            }
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
            go(underlying, dbType, tableUnaryId) match {
              case None          => Some(TypesScala.None.code)
              case Some(default) => Some(code"if ($random.nextBoolean()) ${TypesScala.None} else ${TypesScala.Some}($default)")
            }
          case sc.Type.ArrayOf(underlying) =>
            dbType match {
              case db.Type.Array(underlyingDb) =>
                go(underlying, underlyingDb, tableUnaryId).map { default =>
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
            // 2001-09-09T01:46:40Z -> 2033-05-18T03:33:20Z
            Some(code"${customTypes.TypoInstant.typoType}(${TypesJava.Instant}.ofEpochMilli(1000000000000L + $random.nextLong(1000000000000L)))")
          case sc.Type.TApply(table.default.Defaulted, _) =>
            Some(code"${table.default.Defaulted}.${table.default.UseDefault}")
          case tpe if domainsByName.contains(tpe) =>
            go(domainsByName(tpe).underlyingType, dbType, tableUnaryId).map(inner => code"$tpe($inner)")
          case tpe if enumsByName.contains(tpe) =>
            Some(code"$tpe.All($random.nextInt($tpe.All.length))")
          case _ =>
            None
        }

      go(sc.Type.base(tpe), dbType, table.maybeId.collect { case x: IdComputed.Unary => x })
    }

    new ComputedTestInserts(
      sc.Type.Qualified(options.pkg / sc.Ident(s"${Naming.titleCase(projectName)}TestInsert")),
      computedTables.collect {
        case table if !options.readonlyRepo.include(table.dbTable.name) =>
          val cols: NonEmptyList[ComputedColumn] =
            table.maybeUnsavedRow match {
              case Some(unsaved) => unsaved.allCols
              case None          => table.cols
            }

          val colNameToForeignKeys: Map[db.ColName, List[db.ForeignKey]] =
            table.dbTable.foreignKeys
              .flatMap(fk => fk.cols.toList.map(colName => (colName, fk)))
              .groupMap { case (colName, _) => colName } { case (_, fk) => fk }

          val params: List[sc.Param] = {
            val asParams = cols.map { col =>
              val hasFkToOtherTable = colNameToForeignKeys.get(col.dbName) match {
                case Some(fks) => fks.exists(_.otherTable != table.dbTable.name)
                case None      => false
              }
              val notDefaulted = !col.dbCol.isDefaulted
              val default =
                if (hasFkToOtherTable && notDefaulted && col.dbCol.nullability == Nullability.NoNulls) None
                else defaultFor(table, col.tpe, col.dbCol.tpe)
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
