package typo
package internal
package sqlfiles

import java.sql.{Connection, PreparedStatement}

case class JdbcMetadata(params: List[MetadataParameterColumn], columns: MaybeReturnsRows[NonEmptyList[MetadataColumn]])

object JdbcMetadata {
  def from(sql: String)(implicit c: Connection): JdbcMetadata = {
    val ps = c.prepareStatement(sql)
    try from(ps)
    finally ps.close()
  }

  def from(ps: PreparedStatement): JdbcMetadata = {
    val params = ps.getParameterMetaData match {
      case metadata: org.postgresql.jdbc.PgParameterMetaData =>
        0.until(metadata.getParameterCount).map(_ + 1).map { n =>
          MetadataParameterColumn(
            isNullable = ParameterNullable.fromInt(metadata.isNullable(n)).getOrElse {
              sys.error(s"Couldn't understand metadata.isNullable($n) = ${metadata.isNullable(n)}")
            },
            isSigned = metadata.isSigned(n),
            parameterMode = ParameterMode.fromInt(metadata.getParameterMode(n)).getOrElse {
              sys.error(s"Couldn't understand metadata.getParameterMode($n) = ${metadata.getParameterMode(n)}")
            },
            parameterType = JdbcType.fromInt(metadata.getParameterType(n)),
            parameterTypeName = metadata.getParameterTypeName(n),
            precision = metadata.getPrecision(n),
            scale = metadata.getScale(n)
          )
        }
      case other => sys.error(s"Expected `org.postgresql.jdbc.PgParameterMetaData`, ot ${other.getClass.getName}")
    }
    def nonEmpty(str: String): Option[String] = if (str.isEmpty) None else Some(str)

    val columns: MaybeReturnsRows[NonEmptyList[MetadataColumn]] =
      Option(ps.getMetaData) match {
        case None => MaybeReturnsRows.Update
        case Some(metadata: org.postgresql.jdbc.PgResultSetMetaData) =>
          val cols = 0
            .until(metadata.getColumnCount)
            .map(_ + 1)
            .map { n =>
              MetadataColumn(
                baseColumnName = nonEmpty(metadata.getBaseColumnName(n)).map(db.ColName.apply),
                baseRelationName = nonEmpty(metadata.getBaseTableName(n)).map(name => db.RelationName(nonEmpty(metadata.getBaseSchemaName(n)), name)),
                catalogName = nonEmpty(metadata.getCatalogName(n)),
                columnClassName = metadata.getColumnClassName(n),
                columnDisplaySize = metadata.getColumnDisplaySize(n),
                columnLabel = db.ColName(metadata.getColumnLabel(n)),
                columnName = db.ColName(metadata.getColumnName(n)),
                columnType = sqlfiles.JdbcType.fromInt(metadata.getColumnType(n)),
                columnTypeName = metadata.getColumnTypeName(n),
                format = metadata.getFormat(n),
                isAutoIncrement = metadata.isAutoIncrement(n),
                isCaseSensitive = metadata.isCaseSensitive(n),
                isCurrency = metadata.isCurrency(n),
                isDefinitelyWritable = metadata.isDefinitelyWritable(n),
                isNullable = ColumnNullable.fromInt(metadata.isNullable(n)).getOrElse {
                  sys.error(s"Couldn't understand metadata.isNullable($n) = ${metadata.isNullable(n)}")
                },
                isReadOnly = metadata.isReadOnly(n),
                isSearchable = metadata.isSearchable(n),
                isSigned = metadata.isSigned(n),
                isWritable = metadata.isWritable(n),
                precision = metadata.getPrecision(n),
                scale = metadata.getScale(n),
                schemaName = nonEmpty(metadata.getSchemaName(n)),
                tableName = nonEmpty(metadata.getTableName(n))
              )
            }

          NonEmptyList.fromList(cols.toList) match {
            case Some(cols) => MaybeReturnsRows.Query(cols)
            case None       => sys.error(s"found no columns for query")
          }

        case Some(other) => sys.error(s"Expected `org.postgresql.jdbc.PgResultSetMetaData`, ot ${other.getClass.getName}")
      }

    JdbcMetadata(params.toList, columns)
  }
}
