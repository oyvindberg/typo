package typo

import java.sql.PreparedStatement

object AnalyzeSql {
  case class Column(
      baseColumnName: String,
      baseSchemaName: String,
      baseTableName: String,
      catalogName: String,
      columnClassName: String,
      columnDisplaySize: Int,
      columnLabel: String,
      columnName: String,
      columnType: doobie.JdbcType,
      columnTypeName: String,
      format: Int,
      isAutoIncrement: Boolean,
      isCaseSensitive: Boolean,
      isCurrency: Boolean,
      isDefinitelyWritable: Boolean,
      isNullable: Option[doobie.ColumnNullable],
      isReadOnly: Boolean,
      isSearchable: Boolean,
      isSigned: Boolean,
      isWritable: Boolean,
      precision: Int,
      scale: Int,
      schemaName: String,
      tableName: String
  )

  case class ParameterColumn(
      isNullable: Option[doobie.ParameterNullable],
      isSigned: Boolean,
      parameterMode: Option[doobie.ParameterMode],
      parameterType: doobie.JdbcType,
      parameterTypeName: String,
      precision: Int,
      scale: Int
  )

  case class Analyzed(params: Seq[ParameterColumn], columns: Seq[Column])

  def from(ps: PreparedStatement): Analyzed = {
    val params = ps.getParameterMetaData match {
      case metadata: org.postgresql.jdbc.PgParameterMetaData =>
        0.until(metadata.getParameterCount).map(_ + 1).map { n =>
          ParameterColumn(
            isNullable = doobie.ParameterNullable.fromInt(metadata.isNullable(n)),
            isSigned = metadata.isSigned(n),
            parameterMode = doobie.ParameterMode.fromInt(metadata.getParameterMode(n)),
            parameterType = doobie.JdbcType.fromInt(metadata.getParameterType(n)),
            parameterTypeName = metadata.getParameterTypeName(n),
            precision = metadata.getPrecision(n),
            scale = metadata.getScale(n)
          )
        }
      case other => sys.error(s"Expected `org.postgresql.jdbc.PgParameterMetaData`, ot ${other.getClass.getName}")
    }

    val columns = ps.getMetaData match {
      case metadata: org.postgresql.jdbc.PgResultSetMetaData =>
        0.until(metadata.getColumnCount).map(_ + 1).map { n =>
          Column(
            baseColumnName = metadata.getBaseColumnName(n),
            baseSchemaName = metadata.getBaseSchemaName(n),
            baseTableName = metadata.getBaseTableName(n),
            catalogName = metadata.getCatalogName(n),
            columnClassName = metadata.getColumnClassName(n),
            columnDisplaySize = metadata.getColumnDisplaySize(n),
            columnLabel = metadata.getColumnLabel(n),
            columnName = metadata.getColumnName(n),
            columnType = doobie.JdbcType.fromInt(metadata.getColumnType(n)),
            columnTypeName = metadata.getColumnTypeName(n),
            format = metadata.getFormat(n),
            isAutoIncrement = metadata.isAutoIncrement(n),
            isCaseSensitive = metadata.isCaseSensitive(n),
            isCurrency = metadata.isCurrency(n),
            isDefinitelyWritable = metadata.isDefinitelyWritable(n),
            isNullable = doobie.ColumnNullable.fromInt(metadata.isNullable(n)),
            isReadOnly = metadata.isReadOnly(n),
            isSearchable = metadata.isSearchable(n),
            isSigned = metadata.isSigned(n),
            isWritable = metadata.isWritable(n),
            precision = metadata.getPrecision(n),
            scale = metadata.getScale(n),
            schemaName = metadata.getSchemaName(n),
            tableName = metadata.getTableName(n)
          )
        }

      case other => sys.error(s"Expected `org.postgresql.jdbc.PgResultSetMetaData`, ot ${other.getClass.getName}")
    }

    Analyzed(params, columns)
  }
}
