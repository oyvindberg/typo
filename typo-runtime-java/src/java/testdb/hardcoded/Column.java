package testdb.hardcoded;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Describes how to read a column from a {@link ResultSet}
 * <p>
 * Note that the implementation is a bit more complex than you would expect.
 * This is because we need to check {@link ResultSet#wasNull()} "in the middle" of extracting data.
 * <br/>
 * <br/>
 * Correct use of {@code Column} <b>requires</b> use of either
 * <ul>
 *     <li> - pre-defined instances
 *     <li> - or `Column.instance` with a provided function which does not blow up if the value from the {@link ResultSet} is {@code null}
 * </ul>
 * Then you create derived instances with {@code map} and/or {@code opt}</li>
 */
public sealed interface Column<A> permits Column.NonNullable, Column.Nullable {
    A read(ResultSet rs, int col) throws SQLException;

    <B> Column<B> map(SqlFunction<A, B> f);

    /**
     * Derive a `Column` which allows nullable values
     */
    Column<Optional<A>> opt();

    @FunctionalInterface
    interface Read<A> {
        A apply(ResultSet rs, int column) throws SQLException;
    }
    interface SqlFunction<T, R> {
        R apply(T t) throws SQLException;
    }

    /**
     * Create an instance of {@link Column} from a function that reads a value from a result set.
     *
     * @param f Should not blow up if the value returned is `null`
     */
    static <A> NonNullable<A> instance(Read<A> f) {
        Read<Optional<A>> readNullableA = (rs, col) -> {
            var a = f.apply(rs, col);
            if (rs.wasNull()) return Optional.empty();
            else return Optional.of(a);
        };
        return new NonNullable<>(readNullableA);
    }

    final class NonNullable<A> implements Column<A> {
        final Read<Optional<A>> readNullable;

        public NonNullable(Read<Optional<A>> readNullable) {
            this.readNullable = readNullable;
        }

        @Override
        public A read(ResultSet rs, int col) throws SQLException {
            return readNullable.apply(rs, col).orElseThrow(() -> new SQLException("null value in column " + col));
        }

        @Override
        public <B> NonNullable<B> map(SqlFunction<A, B> f) {
            Read<Optional<B>> readNullableB = (rs, col) -> {
                Optional<A> maybeA = readNullable.apply(rs, col);
                if (maybeA.isEmpty()) return Optional.empty();
                return Optional.of(f.apply(maybeA.get()));
            };
            return new NonNullable<>(readNullableB);
        }

        @Override
        public Column<Optional<A>> opt() {
            return new Nullable<>(readNullable);
        }
    }

    final class Nullable<A> implements Column<Optional<A>> {
        final Read<Optional<A>> readNullable;

        public Nullable(Read<Optional<A>> readNullable) {
            this.readNullable = readNullable;
        }

        @Override
        public Optional<A> read(ResultSet rs, int col) throws SQLException {
            return readNullable.apply(rs, col);
        }

        @Override
        public <B> Column<B> map(SqlFunction<Optional<A>, B> f) {
            // note that there is an implicit assertion here -
            // we're not adding another level of optionality and we *know* we have a `B` even if there was no `A`
            // note that `B` may very well be `Optional<>` itself
            return new NonNullable<>((rs, col) -> Optional.of(f.apply(read(rs, col))));
        }

        // just here for completeness, doesn't make much sense
        @Override
        public Nullable<Optional<A>> opt() {
            return new Nullable<>((rs, col) -> {
                Optional<A> maybeA = readNullable.apply(rs, col);
                // avoid `Some(None)`
                if (maybeA.isEmpty()) return Optional.empty();
                return Optional.of(maybeA);
            });
        }
    }

    Column<BigDecimal> columnToJavaBigDecimal = instance(ResultSet::getBigDecimal);
    Column<Boolean> columnToBoolean = instance(ResultSet::getBoolean);
    Column<Byte> columnToByte = instance(ResultSet::getByte);
    Column<Byte[]> columnToByteArray = instance(ResultSet::getArray).map(sqlArray -> (Byte[]) sqlArray.getArray());
    Column<Double> columnToDouble = instance(ResultSet::getDouble);
    Column<Float> columnToFloat = instance(ResultSet::getFloat);
    Column<Instant> columnToInstant = instance((rs, idx) -> rs.getObject(idx, Instant.class));
    Column<Integer> columnToInt = instance(ResultSet::getInt);
    Column<LocalDate> columnToLocalDate = instance((rs, idx) -> rs.getObject(idx, LocalDate.class));
    Column<LocalDateTime> columnToLocalDateTime = instance((rs, idx) -> rs.getObject(idx, LocalDateTime.class));
    Column<Long> columnToLong = instance(ResultSet::getLong);
    Column<Short> columnToShort = instance(ResultSet::getShort);
    Column<String> columnToString = instance(ResultSet::getString);
    Column<UUID> columnToUUID = columnToString.map(UUID::fromString);
    Column<ZonedDateTime> columnToZonedDateTime = instance((rs, idx) -> rs.getObject(idx, ZonedDateTime.class));
}
