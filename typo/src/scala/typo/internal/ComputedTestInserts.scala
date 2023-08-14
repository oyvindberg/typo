package typo
package internal

import typo.internal.codegen.*

case class ComputedTestInserts(tpe: sc.Type.Qualified, methods: List[ComputedTestInserts.InsertMethod])

object ComputedTestInserts {
  val random: sc.Ident = sc.Ident("random")
  def apply(options: InternalOptions, customTypes: CustomTypes, computedTables: Iterable[ComputedTable]) =
    new ComputedTestInserts(
      sc.Type.Qualified(options.pkg / sc.Ident("testInsert")),
      computedTables.map(x => ComputedTestInserts.InsertMethod(options, customTypes, x)).toList
    )

  case class InsertMethod(options: InternalOptions, customTypes: CustomTypes, table: ComputedTable) {
    val name: sc.Ident =
      table.dbTable.name match {
        case db.RelationName(Some(schema), name) => sc.Ident(s"${Naming.camelCase(schema)}${Naming.titleCase(name)}")
        case db.RelationName(None, name)         => sc.Ident(Naming.titleCase(name))
      }

    val cols: NonEmptyList[ComputedColumn] =
      table.maybeUnsavedRow match {
        case Some(unsaved) => unsaved.allCols
        case None          => table.cols
      }

    def defaultFor(tpe: sc.Type, dbType: db.Type) = {
      def defaultLocalDate = code"${sc.Type.LocalDate}.ofEpochDay($random.nextInt(30000).toLong)"
      def defaultLocalTime = code"${sc.Type.LocalTime}.ofSecondOfDay($random.nextInt(24 * 60 * 60).toLong)"
      def defaultLocalDateTime = code"${sc.Type.LocalDateTime}.of($defaultLocalDate, $defaultLocalTime)"
      def defaultZoneOffset = code"${sc.Type.ZoneOffset}.ofHours($random.nextInt(24) - 12)"

      def go(tpe: sc.Type, dbType: db.Type): Option[sc.Code] =
        tpe match {
          case sc.Type.String =>
            val max: Int =
              Option(dbType)
                .collect { case db.Type.VarChar(Some(maxLength)) if maxLength < 20 => maxLength }
                .getOrElse(20)
            Some(code"$random.alphanumeric.take($max).mkString")
          case sc.Type.Boolean => Some(code"$random.nextBoolean()")
          case sc.Type.Char    => Some(code"$random.nextPrintableChar()")
          case sc.Type.Byte    => Some(code"$random.nextInt(${sc.Type.Byte}.MaxValue).toByte")
          case sc.Type.Short   => Some(code"$random.nextInt(${sc.Type.Short}.MaxValue).toShort")
          case sc.Type.Int     => Some(code"$random.nextInt()")
          case sc.Type.Long    => Some(code"$random.nextLong()")
          case sc.Type.Float   => Some(code"$random.nextFloat()")
          case sc.Type.Double  => Some(code"$random.nextDouble()")
          case sc.Type.Optional(underlying) =>
            go(underlying, dbType) match {
              case None          => Some(sc.Type.None.code)
              case Some(default) => Some(code"if ($random.nextBoolean()) ${sc.Type.None} else ${sc.Type.Some}($default)")
            }
          case sc.Type.TApply(sc.Type.Array, List(underlying)) =>
            dbType match {
              case db.Type.Array(underlyingDb) =>
                go(underlying, underlyingDb).map { default =>
                  code"${sc.Type.Array}.fill(random.nextInt(3))($default)"
                }
              case _ => None
            }

          case customTypes.TypoLocalDate.typoType =>
            Some(code"${customTypes.TypoLocalDate.typoType}($defaultLocalDate)")
          case customTypes.TypoLocalTime.typoType =>
            Some(code"${customTypes.TypoLocalTime.typoType}($defaultLocalTime)")
          case customTypes.TypoLocalDateTime.typoType =>
            Some(code"${customTypes.TypoLocalDateTime.typoType}($defaultLocalDateTime)")
          case customTypes.TypoOffsetTime.typoType =>
            Some(code"${customTypes.TypoOffsetTime.typoType}(${defaultLocalTime}.atOffset($defaultZoneOffset))")
          case customTypes.TypoOffsetDateTime.typoType =>
            Some(code"${customTypes.TypoOffsetDateTime.typoType}($defaultLocalDateTime.atOffset($defaultZoneOffset))")
          case sc.Type.TApply(table.default.Defaulted, _) =>
            Some(code"${table.default.Defaulted}.${table.default.UseDefault}")
          case _ => None
        }
      go(sc.Type.base(tpe), dbType)
    }

    val params: List[sc.Param] = {
      val asParams = cols.map { col =>
        val default = defaultFor(col.tpe, col.dbCol.tpe)
        sc.Param(col.name, col.tpe, default)
      }
      val (requiredParams, optionalParams) = asParams.toList.partition(_.default.isEmpty)
      requiredParams ++ optionalParams
    }

    val cls = table.maybeUnsavedRow match {
      case Some(unsaved) => unsaved.tpe
      case None          => table.names.RowName
    }
  }
}
