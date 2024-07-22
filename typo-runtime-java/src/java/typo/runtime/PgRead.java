package typo.runtime;

import org.postgresql.jdbc.PgArray;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Map;
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
public sealed interface PgRead<A> permits PgRead.NonNullable, PgRead.Nullable {
    A read(ResultSet rs, int col) throws SQLException;

    <B> PgRead<B> map(SqlFunction<A, B> f);

    /**
     * Derive a `Column` which allows nullable values
     */
    PgRead<Optional<A>> opt();

    @FunctionalInterface
    interface RawRead<A> {
        A apply(ResultSet rs, int column) throws SQLException;
    }

    /**
     * Create an instance of {@link PgRead} from a function that reads a value from a result set.
     *
     * @param f Should not blow up if the value returned is `null`
     */
    static <A> NonNullable<A> of(RawRead<A> f) {
        RawRead<Optional<A>> readNullableA = (rs, col) -> {
            var a = f.apply(rs, col);
            if (rs.wasNull()) return Optional.empty();
            else return Optional.of(a);
        };
        return new NonNullable<>(readNullableA);
    }

    static <A> NonNullable<A> castJdbcObjectTo(Class<A> cls) {
        return of((rs, i) -> cls.cast(rs.getObject(i)));
    }

    PgRead<PgArray> readPgArray = of((rs, i) -> (PgArray) rs.getArray(i));

    static <A> PgRead<A[]> massageJdbcArrayTo(Class<A[]> arrayCls) {
        return readPgArray.map(sqlArray -> {
            Object arrayObj = sqlArray.getArray();
            // if the array is already of the correct type, just return it
            if (arrayCls.isInstance(arrayObj)) return arrayCls.cast(arrayObj);
            // if the array is an Object[], we need to copy it into the correct array type
            Object[] array = (Object[]) arrayObj;
            return Arrays.copyOf(array, array.length, arrayCls);
        });
    }

    static PgRead<double[]> doubleArray() {
        return readPgArray.map(sqlArray -> {
            Object arrayObj = sqlArray.getArray();
            // if the array is already of the correct type, just return it
            if (arrayObj instanceof double[]) return (double[]) arrayObj;
            throw new RuntimeException("foo");
        });
    }

    final class NonNullable<A> implements PgRead<A> {
        final RawRead<Optional<A>> readNullable;

        public NonNullable(RawRead<Optional<A>> readNullable) {
            this.readNullable = readNullable;
        }

        @Override
        public A read(ResultSet rs, int col) throws SQLException {
            return readNullable.apply(rs, col).orElseThrow(() -> new SQLException("null value in column " + col));
        }

        @Override
        public <B> NonNullable<B> map(SqlFunction<A, B> f) {
            return new NonNullable<>((rs, col) -> {
                Optional<A> maybeA = readNullable.apply(rs, col);
                // this looks like map, but there is a checked exception
                if (maybeA.isEmpty()) return Optional.empty();
                return Optional.of(f.apply(maybeA.get()));
            });
        }

        @Override
        public PgRead<Optional<A>> opt() {
            return new Nullable<>(readNullable);
        }
    }

    final class Nullable<A> implements PgRead<Optional<A>> {
        final RawRead<Optional<A>> readNullable;

        public Nullable(RawRead<Optional<A>> readNullable) {
            this.readNullable = readNullable;
        }

        @Override
        public Optional<A> read(ResultSet rs, int col) throws SQLException {
            return readNullable.apply(rs, col);
        }

        @Override
        public <B> PgRead<B> map(SqlFunction<Optional<A>, B> f) {
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

    PgRead<OffsetDateTime> readOffsetDateTime = of((rs, idx) -> rs.getObject(idx, OffsetDateTime.class));
    PgRead<java.sql.Timestamp[]> readTimestampArray = massageJdbcArrayTo(java.sql.Timestamp[].class);
    PgRead<java.sql.Date[]> readDateArray = massageJdbcArrayTo(java.sql.Date[].class);
    PgRead<String> readString = of(ResultSet::getString);

    PgRead<BigDecimal> readBigDecimal = of(ResultSet::getBigDecimal);
    PgRead<Boolean> readBoolean = of(ResultSet::getBoolean);
    PgRead<Byte> readByte = of(ResultSet::getByte);
    PgRead<byte[]> readByteArray = castJdbcObjectTo(byte[].class);
    PgRead<Double> readDouble = of(ResultSet::getDouble);
    PgRead<Double[]> readDoubleArray = PgRead.massageJdbcArrayTo(Double[].class);
    PgRead<double[]> readUnboxedDoubleArray = PgRead.doubleArray();
    PgRead<Float> readFloat = of(ResultSet::getFloat);
    PgRead<Float[]> readFloatArray = PgRead.massageJdbcArrayTo(Float[].class);
    PgRead<Instant> readInstant = readOffsetDateTime.map(OffsetDateTime::toInstant);
    PgRead<Instant[]> readInstantArray = readTimestampArray.map(ts -> Arrays.stream(ts).map(java.sql.Timestamp::toInstant).toArray(Instant[]::new));
    PgRead<Integer> readInteger = of(ResultSet::getInt);
    PgRead<Integer[]> readIntegerArray = PgRead.massageJdbcArrayTo(Integer[].class);
    PgRead<LocalDate> readLocalDate = of((rs, idx) -> rs.getObject(idx, LocalDate.class));
    PgRead<LocalDate[]> readLocalDateArray = readDateArray.map(dates -> Arrays.stream(dates).map(java.sql.Date::toLocalDate).toArray(LocalDate[]::new));
    PgRead<LocalDateTime> readLocalDateTime = of((rs, idx) -> rs.getObject(idx, LocalDateTime.class));
    PgRead<LocalDateTime[]> readLocalDateTimeArray = readTimestampArray.map(ts -> Arrays.stream(ts).map(java.sql.Timestamp::toLocalDateTime).toArray(LocalDateTime[]::new));
    PgRead<LocalTime> readLocalTime = of((rs, idx) -> rs.getObject(idx, LocalTime.class));
    PgRead<LocalTime[]> readLocalTimeArray = readString.map(Impl::parseLocalTimeArray);
    PgRead<Long> readLong = of(ResultSet::getLong);
    PgRead<Long[]> readLongArray = PgRead.massageJdbcArrayTo(Long[].class);
    PgRead<OffsetTime> readOffsetTime = of((rs, idx) -> rs.getObject(idx, OffsetTime.class));
    PgRead<OffsetTime[]> readOffsetTimeArray = readString.map(Impl::parseOffsetTimeArray);
    PgRead<Short> readShort = of(ResultSet::getShort);
    PgRead<Short[]> readShortArray = PgRead.massageJdbcArrayTo(Short[].class);
    PgRead<String[]> readStringArray = PgRead.massageJdbcArrayTo(String[].class);
    PgRead<UUID> readUUID = readString.map(UUID::fromString);
    PgRead<Map<String, String>> readMapStringString = PgRead.of((rs, i) -> {
        var obj = rs.getObject(i);
        if (obj == null) return null;
        return (Map<String, String>) obj;
    });

    interface Impl {
        // postgres driver throws away all precision after whole seconds !?!
        static LocalTime[] parseLocalTimeArray(String str) {
            if (str == null) return null;
            if (str.equals("{}")) return new LocalTime[0];
            if (str.charAt(0) != '{' || str.charAt(str.length() - 1) != '}')
                throw new IllegalArgumentException("Invalid array format");
            String[] strings = str.substring(1, str.length() - 1).split(",");
            LocalTime[] ret = new LocalTime[strings.length];
            for (int i = 0; i < strings.length; i++) {
                ret[i] = LocalTime.parse(strings[i]);
            }
            return ret;
        }

        DateTimeFormatter offsetTimeParser = new DateTimeFormatterBuilder()
                .appendPattern("HH:mm:ss")
                .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
                .appendPattern("X")
                .toFormatter();

        static OffsetTime[] parseOffsetTimeArray(String str) {
            if (str == null) return null;
            if (str.equals("{}")) return new OffsetTime[0];
            if (str.charAt(0) != '{' || str.charAt(str.length() - 1) != '}')
                throw new IllegalArgumentException("Invalid array format");
            String[] strings = str.substring(1, str.length() - 1).split(",");
            var ret = new OffsetTime[strings.length];
            for (int i = 0; i < strings.length; i++) {
                ret[i] = OffsetTime.parse(strings[i], offsetTimeParser);
            }
            return ret;
        }

    }

}
