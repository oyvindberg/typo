package typo
package internal

import codegen.*

case class ComputedTestInserts(tpe: sc.Type.Qualified, methods: List[ComputedTestInserts.InsertMethod])

object ComputedTestInserts {
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

    val params: List[sc.Param] = {
      val asParams = cols.map { col =>
        val default = sc.Type.base(col.tpe) match {
          case sc.Type.String =>
            col.dbCol.tpe match {
              case db.Type.VarChar(Some(maxLength)) => Some(sc.StrLit(col.dbName.value.take(maxLength)).code)
              case _                                => Some(sc.StrLit(col.dbName.value).code)
            }
          case sc.Type.Boolean                            => Some(code"false")
          case sc.Type.Char                               => Some(code"'c'")
          case sc.Type.Byte                               => Some(code"1")
          case sc.Type.Short                              => Some(code"1")
          case sc.Type.Int                                => Some(code"1")
          case sc.Type.Long                               => Some(code"1L")
          case sc.Type.Float                              => Some(code"1.0f")
          case sc.Type.Double                             => Some(code"1.0")
          case sc.Type.Optional(_)                        => Some(code"${sc.Type.None}")
          case sc.Type.TApply(sc.Type.Array, _)           => Some(code"${sc.Type.Array}.empty")
          case customTypes.TypoLocalDate.typoType         => Some(code"${customTypes.TypoLocalDate.typoType}.now")
          case customTypes.TypoLocalTime.typoType         => Some(code"${customTypes.TypoLocalTime.typoType}.now")
          case customTypes.TypoLocalDateTime.typoType     => Some(code"${customTypes.TypoLocalDateTime.typoType}.now")
          case customTypes.TypoOffsetTime.typoType        => Some(code"${customTypes.TypoOffsetTime.typoType}.now")
          case customTypes.TypoOffsetDateTime.typoType    => Some(code"${customTypes.TypoOffsetDateTime.typoType}.now")
          case sc.Type.TApply(table.default.Defaulted, _) => Some(code"${table.default.Defaulted}.${table.default.UseDefault}")
          case _                                          => None
        }
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
