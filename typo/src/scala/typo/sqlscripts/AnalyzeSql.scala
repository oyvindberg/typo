package typo
package sqlscripts

import play.api.libs.json.{Json, Writes}

import java.sql.{Connection, PreparedStatement}

object AnalyzeSql {
  case class Column(
      baseColumnName: Option[db.ColName],
      baseRelationName: Option[db.RelationName],
      catalogName: Option[String],
      columnClassName: String,
      columnDisplaySize: Int,
      columnLabel: db.ColName,
      columnName: db.ColName,
      columnType: JdbcType,
      columnTypeName: String,
      format: Int,
      isAutoIncrement: Boolean,
      isCaseSensitive: Boolean,
      isCurrency: Boolean,
      isDefinitelyWritable: Boolean,
      isNullable: ColumnNullable,
      isReadOnly: Boolean,
      isSearchable: Boolean,
      isSigned: Boolean,
      isWritable: Boolean,
      precision: Int,
      scale: Int,
      schemaName: Option[String],
      tableName: Option[String]
  ) {
    def name = columnLabel
  }
  object Column {
    implicit val oformat: Writes[Column] = (x: Column) =>
      Json.obj(
        "baseColumnName" -> x.baseColumnName.map(_.value),
        "baseRelationName" -> x.baseRelationName.map(x => s"${x.schema}.${x.name}"),
        "catalogName" -> x.catalogName,
        "columnClassName" -> x.columnClassName,
        "columnDisplaySize" -> x.columnDisplaySize,
        "columnLabel" -> x.columnLabel.value,
        "columnName" -> x.columnName.value,
        "columnType" -> x.columnType.toString,
        "columnTypeName" -> x.columnTypeName,
        "format" -> x.format,
        "isAutoIncrement" -> x.isAutoIncrement,
        "isCaseSensitive" -> x.isCaseSensitive,
        "isCurrency" -> x.isCurrency,
        "isDefinitelyWritable" -> x.isDefinitelyWritable,
        "isNullable" -> x.isNullable.toString,
        "isReadOnly" -> x.isReadOnly,
        "isSearchable" -> x.isSearchable,
        "isSigned" -> x.isSigned,
        "isWritable" -> x.isWritable,
        "precision" -> x.precision,
        "scale" -> x.scale,
        "schemaName" -> x.schemaName,
        "tableName" -> x.tableName
      )
  }

  case class ParameterColumn(
      isNullable: ParameterNullable,
      isSigned: Boolean,
      parameterMode: ParameterMode,
      parameterType: JdbcType,
      parameterTypeName: String,
      precision: Int,
      scale: Int
  )

  case class Analyzed(params: List[ParameterColumn], columns: List[Column])

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
            isNullable = sqlscripts.ParameterNullable.fromInt(metadata.isNullable(n)).getOrElse {
              sys.error(s"Couldn't understand metadata.isNullable($n) = ${metadata.isNullable(n)}")
            },
            isSigned = metadata.isSigned(n),
            parameterMode = sqlscripts.ParameterMode.fromInt(metadata.getParameterMode(n)).getOrElse {
              sys.error(s"Couldn't understand metadata.getParameterMode($n) = ${metadata.getParameterMode(n)}")
            },
            parameterType = sqlscripts.JdbcType.fromInt(metadata.getParameterType(n)),
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
            baseColumnName = nonEmpty(metadata.getBaseColumnName(n)).map(db.ColName.apply),
            baseRelationName = nonEmpty(metadata.getBaseSchemaName(n)).zip(nonEmpty(metadata.getBaseTableName(n))).map(db.RelationName.tupled),
            catalogName = nonEmpty(metadata.getCatalogName(n)),
            columnClassName = metadata.getColumnClassName(n),
            columnDisplaySize = metadata.getColumnDisplaySize(n),
            columnLabel = db.ColName(metadata.getColumnLabel(n)),
            columnName = db.ColName(metadata.getColumnName(n)),
            columnType = sqlscripts.JdbcType.fromInt(metadata.getColumnType(n)),
            columnTypeName = metadata.getColumnTypeName(n),
            format = metadata.getFormat(n),
            isAutoIncrement = metadata.isAutoIncrement(n),
            isCaseSensitive = metadata.isCaseSensitive(n),
            isCurrency = metadata.isCurrency(n),
            isDefinitelyWritable = metadata.isDefinitelyWritable(n),
            isNullable = sqlscripts.ColumnNullable.fromInt(metadata.isNullable(n)).getOrElse {
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

    Analyzed(params.toList, columns.toList)
  }
}
