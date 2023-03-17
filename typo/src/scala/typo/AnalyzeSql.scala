package typo

import java.sql.{Connection, PreparedStatement}

object AnalyzeSql {
  case class Column(
      baseColumnName: Option[String],
      baseRelationName: Option[db.RelationName],
      catalogName: Option[String],
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
      isNullable: doobie.ColumnNullable,
      isReadOnly: Boolean,
      isSearchable: Boolean,
      isSigned: Boolean,
      isWritable: Boolean,
      precision: Int,
      scale: Int,
      schemaName: Option[String],
      tableName: Option[String]
  ) {
    def name = db.ColName(columnLabel)
  }

  case class ParameterColumn(
      isNullable: doobie.ParameterNullable,
      isSigned: Boolean,
      parameterMode: doobie.ParameterMode,
      parameterType: doobie.JdbcType,
      parameterTypeName: String,
      precision: Int,
      scale: Int
  )

  case class Analyzed(params: Seq[ParameterColumn], columns: Seq[Column])

  def from(c: Connection, sql: String): Analyzed = {
    val ps = c.prepareStatement(sql)
    try from(ps)
    finally ps.close()
  }

  def from(ps: PreparedStatement): Analyzed = {
    val params = ps.getParameterMetaData match {
      case metadata: org.postgresql.jdbc.PgParameterMetaData =>
        0.until(metadata.getParameterCount).map(_ + 1).map { n =>
          ParameterColumn(
            isNullable = doobie.ParameterNullable.fromInt(metadata.isNullable(n)).getOrElse {
              sys.error(s"Couldn't understand metadata.isNullable($n) = ${metadata.isNullable(n)}")
            },
            isSigned = metadata.isSigned(n),
            parameterMode = doobie.ParameterMode.fromInt(metadata.getParameterMode(n)).getOrElse {
              sys.error(s"Couldn't understand metadata.getParameterMode($n) = ${metadata.getParameterMode(n)}")
            },
            parameterType = doobie.JdbcType.fromInt(metadata.getParameterType(n)),
            parameterTypeName = metadata.getParameterTypeName(n),
            precision = metadata.getPrecision(n),
            scale = metadata.getScale(n)
          )
        }
      case other => sys.error(s"Expected `org.postgresql.jdbc.PgParameterMetaData`, ot ${other.getClass.getName}")
    }
    def nonEmpty(str: String): Option[String] = if (str.isEmpty) None else Some(str)

    val columns = ps.getMetaData match {
      case metadata: org.postgresql.jdbc.PgResultSetMetaData =>
        0.until(metadata.getColumnCount).map(_ + 1).map { n =>
          Column(
            baseColumnName = nonEmpty(metadata.getBaseColumnName(n)),
            baseRelationName = nonEmpty(metadata.getBaseSchemaName(n)).zip(nonEmpty(metadata.getBaseTableName(n))).map(db.RelationName.tupled),
            catalogName = nonEmpty(metadata.getCatalogName(n)),
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
            isNullable = doobie.ColumnNullable.fromInt(metadata.isNullable(n)).getOrElse {
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

      case other => sys.error(s"Expected `org.postgresql.jdbc.PgResultSetMetaData`, ot ${other.getClass.getName}")
    }

    Analyzed(params, columns)
  }
}
