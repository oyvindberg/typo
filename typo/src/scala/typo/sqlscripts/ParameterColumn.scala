package typo.sqlscripts

/**
 * Analyzed from postgres metadata for prepared statements through jdbc
 */
case class ParameterColumn(
    isNullable: ParameterNullable,
    isSigned: Boolean,
    parameterMode: ParameterMode,
    parameterType: JdbcType,
    parameterTypeName: String,
    precision: Int,
    scale: Int
)
