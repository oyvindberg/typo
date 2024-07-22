package typo.runtime;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Function;

public class PgWrite<A> {
    final RawWriter<A> rawSetter;

    PgWrite(RawWriter<A> rawSetter) {
        this.rawSetter = rawSetter;
    }

    public void set(PreparedStatement ps, int index, A a) throws SQLException {
        rawSetter.set(ps, index, a);
    }

    public PgWrite<Optional<A>> opt(PgTypename<A> typename) {
        return of((ps, index, a) -> {
            if (a.isEmpty()) ps.setNull(index, 0, typename.sqlTypeNoPrecision());
            else set(ps, index, a.get());
        });
    }

    public PgWrite<A[]> array(PgTypename<A> typename) {
        return of((ps, index, as) -> ps.setArray(index, ps.getConnection().createArrayOf(typename.sqlTypeNoPrecision(), as)));
    }

    public <B> PgWrite<B> contramap(Function<B, A> f) {
        return of((ps, index, b) -> set(ps, index, f.apply(b)));
    }

    public interface RawWriter<A> {
        void set(PreparedStatement ps, int index, A a) throws SQLException;
    }

    public static <A> PgWrite<A> of(RawWriter<A> rawSetter) {
        return new PgWrite<>(rawSetter);
    }

    public static <A> PgWrite<A> passObjectToJdbc() {
        return of(PreparedStatement::setObject);
    }

    public static PgWrite<byte[]> writeByteArray = of(PreparedStatement::setObject);
    public static PgWrite<Boolean> writeBoolean = of(PreparedStatement::setBoolean);
    public static PgWrite<BigDecimal> writeBigDecimal = of(PreparedStatement::setBigDecimal);
    public static PgWrite<Double> writeDouble = of(PreparedStatement::setDouble);
    public static PgWrite<double[]> writeDoubleArrayUnboxed = of(PreparedStatement::setObject);
    public static PgWrite<Float> writeFloat = of(PreparedStatement::setFloat);
    public static PgWrite<Integer> writeInteger = of(PreparedStatement::setInt);
    public static PgWrite<Long> writeLong = of(PreparedStatement::setLong);
    public static PgWrite<Short> writeShort = of(PreparedStatement::setShort);
    public static PgWrite<String> writeString = of(PreparedStatement::setString);
}
