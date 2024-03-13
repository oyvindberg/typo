package testdb.hardcoded;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Function;

public class ToStatement<A> {
    final Setter<A> rawSetter;
    public final ParameterMetaData<A> pmd;

    ToStatement(Setter<A> rawSetter, ParameterMetaData<A> pmd) {
        this.rawSetter = rawSetter;
        this.pmd = pmd;
    }

    /**
     * Sets value `a` on statement `ps` at specified `index`.
     */
    public void set(PreparedStatement ps, int index, A a) throws SQLException {
        rawSetter.set(ps, index, a);
    }

    public ToStatement<Optional<A>> opt() {
        Setter<Optional<A>> rawOptSetter = (ps, index, a) -> {
            if (a.isEmpty()) ps.setNull(index, pmd.jdbcType, pmd.sqlType);
            else set(ps, index, a.get());
        };
        return new ToStatement<>(rawOptSetter, pmd.opt());
    }

    public ToStatement<A[]> array() {
        return new ToStatement<>((ps, index, as) -> ps.setArray(index, ps.getConnection().createArrayOf(pmd.sqlType, as)), pmd.array());
    }

    public <B> ToStatement<B> contramap(Function<B, A> f) {
        return new ToStatement<>((ps, index, b) -> set(ps, index, f.apply(b)), pmd.as());
    }

    public interface Setter<A> {
        void set(PreparedStatement ps, int index, A a) throws SQLException;
    }

    public static <A> ToStatement<A> instance(Setter<A> rawSetter, ParameterMetaData<A> pmd) {
        return new ToStatement<>(rawSetter, pmd);
    }

    public static ToStatement<Boolean> BooleanToStatement = instance(PreparedStatement::setBoolean, ParameterMetaData.BooleanParameterMetaData);
    public static ToStatement<BigDecimal> BigDecimalToStatement = instance(PreparedStatement::setBigDecimal, ParameterMetaData.BigDecimalParameterMetaData);
    public static ToStatement<Double> DoubleToStatement = instance(PreparedStatement::setDouble, ParameterMetaData.DoubleParameterMetaData);
    public static ToStatement<Float> FloatToStatement = instance(PreparedStatement::setFloat, ParameterMetaData.FloatParameterMetaData);
    public static ToStatement<Integer> IntegerToStatement = instance(PreparedStatement::setInt, ParameterMetaData.IntegerParameterMetaData);
    public static ToStatement<Long> LongToStatement = instance(PreparedStatement::setLong, ParameterMetaData.LongParameterMetaData);
    public static ToStatement<Short> ShortToStatement = instance(PreparedStatement::setShort, ParameterMetaData.ShortParameterMetaData);
    public static ToStatement<String> StringToStatement = instance(PreparedStatement::setString, ParameterMetaData.StringParameterMetaData);
}
