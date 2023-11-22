package testdb.hardcoded;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Optional;

public class ParameterMetaData<A> {
    /**
     * Name of SQL type (see `java.sql.Types`)
     */
    public final String sqlType;
    /**
     * JDBC type (see `java.sql.Types`)
     */
    public final int jdbcType;

    public ParameterMetaData(String sqlType, int jdbcType) {
        this.sqlType = sqlType;
        this.jdbcType = jdbcType;
    }

    static <T> ParameterMetaData<T> instance(String sqlType, int jdbcType) {
        return new ParameterMetaData<T>(sqlType, jdbcType);
    }

    public ParameterMetaData<A[]> array() {
        return instance("_" + sqlType, Types.ARRAY);
    }

    public ParameterMetaData<Optional<A>> opt() {
        return instance(sqlType, jdbcType);
    }

    public <B> ParameterMetaData<B> as() {
        return instance(sqlType, jdbcType);
    }

    static final ParameterMetaData<Boolean> BooleanParameterMetaData = instance("bool", Types.BOOLEAN);
    static final ParameterMetaData<Float> FloatParameterMetaData = instance("float4", Types.FLOAT);
    static final ParameterMetaData<Double> DoubleParameterMetaData = instance("float8", Types.DOUBLE);
    static final ParameterMetaData<Short> ShortParameterMetaData = instance("int2", Types.SMALLINT);
    static final ParameterMetaData<Integer> IntegerParameterMetaData = instance("int4", Types.INTEGER);
    static final ParameterMetaData<Long> LongParameterMetaData = instance("int8", Types.BIGINT);
    static final ParameterMetaData<BigDecimal> BigDecimalParameterMetaData = instance("numeric", Types.DECIMAL);
    static final ParameterMetaData<String> StringParameterMetaData = instance("text", Types.VARCHAR);
}
