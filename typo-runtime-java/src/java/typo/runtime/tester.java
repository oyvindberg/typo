package typo.runtime;

import org.postgresql.geometric.PGbox;
import org.postgresql.geometric.PGcircle;
import org.postgresql.geometric.PGpoint;
import org.postgresql.util.PGInterval;
import typo.data.Arr;
import typo.data.Inet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface tester {

    record PgTypeAndExample<A>(PgType<A> type, A example, boolean hasIdentity) {
    }

    List<PgTypeAndExample<?>> All = List.of(
            new PgTypeAndExample<>(PgTypes.bpchar(5), "377  ", true),
            new PgTypeAndExample<>(PgTypes.bpchar, "377", true),
            new PgTypeAndExample<>(PgTypes.bpcharArray(5), new String[]{"377  "}, true),
            new PgTypeAndExample<>(PgTypes.bpcharArray, new String[]{"10101"}, true),
            new PgTypeAndExample<>(PgTypes.bytea, new byte[]{-1, 1, 127}, true),
            new PgTypeAndExample<>(PgTypes.date, LocalDate.now(), true),
            new PgTypeAndExample<>(PgTypes.dateArray, new LocalDate[]{LocalDate.now()}, true),
            new PgTypeAndExample<>(PgTypes.float4, 42.42f, true),
            new PgTypeAndExample<>(PgTypes.float4Array, new Float[]{42.42f}, true),
            new PgTypeAndExample<>(PgTypes.float8, 42.42, true),
            new PgTypeAndExample<>(PgTypes.float8Array, new Double[]{42.42}, true),
            new PgTypeAndExample<>(PgTypes.float8UnboxedArray, new double[]{42.42}, true),
            new PgTypeAndExample<>(PgTypes.hstore, Map.of(",.;{}[]-//#®✅", ",.;{}[]-//#®✅"), true),
            new PgTypeAndExample<>(PgTypes.inet, new Inet("10.1.0.0"), true),
            new PgTypeAndExample<>(PgTypes.inetArray, new Inet[]{new Inet("10.1.0.0")}, true),
            new PgTypeAndExample<>(PgTypes.int2, (short) 42, true),
            new PgTypeAndExample<>(PgTypes.int2Array, new Short[]{42}, true),
            new PgTypeAndExample<>(PgTypes.int4, 42, true),
            new PgTypeAndExample<>(PgTypes.int4Array, new Integer[]{42}, true),
            new PgTypeAndExample<>(PgTypes.int8, 42L, true),
            new PgTypeAndExample<>(PgTypes.int8Array, new Long[]{42L}, true),
            new PgTypeAndExample<>(PgTypes.box, new PGbox(42, 42, 42, 42), false),
            new PgTypeAndExample<>(PgTypes.boxArray, new PGbox[]{new PGbox(42, 42, 42, 42)}, false),
            new PgTypeAndExample<>(PgTypes.circle, new PGcircle(new PGpoint(0.01, 42.34), 101.2), true),
            new PgTypeAndExample<>(PgTypes.circleArray, new PGcircle[]{new PGcircle(new PGpoint(0.01, 42.34), 101.2)}, false),
            new PgTypeAndExample<>(PgTypes.interval, new PGInterval(1, 2, 3, 4, 5, 6.666), true),
            new PgTypeAndExample<>(PgTypes.intervalArray, new PGInterval[]{new PGInterval(1, 2, 3, 4, 5, 6.666)}, false),
            new PgTypeAndExample<>(PgTypes.smallint, (short) 42, true),
            new PgTypeAndExample<>(PgTypes.smallintArray, new Short[]{42}, true),
            new PgTypeAndExample<>(PgTypes.text, ",.;{}[]-//#®✅", true),
            new PgTypeAndExample<>(PgTypes.textArray, new String[]{",.;{}[]-//#®✅"}, true),
            new PgTypeAndExample<>(PgTypes.time, LocalTime.now(), true),
            new PgTypeAndExample<>(PgTypes.timeArray, new LocalTime[]{LocalTime.now()}, true),
            new PgTypeAndExample<>(PgTypes.timestamp, LocalDateTime.now(), true),
            new PgTypeAndExample<>(PgTypes.timestampArray, new LocalDateTime[]{LocalDateTime.now()}, true),
            new PgTypeAndExample<>(PgTypes.timestamptz, Instant.now(), true),
            new PgTypeAndExample<>(PgTypes.timestamptzArray, new Instant[]{Instant.now()}, true),
            new PgTypeAndExample<>(PgTypes.timetz, OffsetTime.now(), true),
            new PgTypeAndExample<>(PgTypes.timetzArray, new OffsetTime[]{OffsetTime.now()}, true)

    );

    // in java
    static <T> T withConnection(SqlFunction<Connection, T> f) {
        try (var conn = java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:6432/Adventureworks?user=postgres&password=password")) {
            conn.setAutoCommit(false);
            try {
                return f.apply(conn);
            } finally {
                conn.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // main
    static void main(String[] args) {
        System.out.println(Arr.of(0, 1, 2, 3).reshape(2, 2));
        System.out.println(Arr.of("a", "b", "c", "d \",d").reshape(2, 2));
        System.out.println(ArrParser.parse(Arr.of(1, 2, 3, 4).encode(Object::toString)));
        System.out.println(ArrParser.parse("{{\"a\",\"b\"},{\"c\",\"d \\\",d\"}}"));
        withConnection(conn -> {
            for (PgTypeAndExample<?> t : All) {
                System.out.println("Testing " + t.type.typename().sqlType() + " with example '" + (format(t.example)) + "'");
                testCase(conn, t);
            }
            return null;
        });
    }

    record Tuple2<A, B>(A a, B b) {
    }

    static <A> void testCase(Connection conn, PgTypeAndExample<A> t) throws SQLException {
        conn.createStatement().execute("create temp table test (v " + t.type.typename().sqlType() + ")");
        var insert = conn.prepareStatement("insert into test (v) values (?)");
        A expected = t.example;
        t.type.write().set(insert, 1, expected);
        insert.execute();
        insert.close();
        streamingInsert.insert("COPY test(v) FROM STDIN", 100, Arrays.asList(t.example).iterator(), conn, t.type.text());

        final PreparedStatement select;
        if (t.hasIdentity) {
            select = conn.prepareStatement("select v, null from test where v = ?");
            t.type.write().set(select, 1, expected);
        } else {
            select = conn.prepareStatement("select v, null from test");
        }

        select.execute();
        var rs = select.getResultSet();
        List<Tuple2<A, Optional<A>>> rows = RowParser.of(t.type, t.type.opt(), Tuple2::new).allRows().apply(rs);
        select.close();
        conn.createStatement().execute("drop table test;");
        assertEquals(rows.get(0).a, expected);
        assertEquals(rows.get(1).a, expected);
    }

    static <A> void assertEquals(A actual, A expected) {
        if (!areEqual(actual, expected)) {
            throw new RuntimeException("actual: '" + format(actual) + "' != expected '" + format(expected) + "'");
        }
    }

    static <A> boolean areEqual(A actual, A expected) {
        if (expected instanceof byte[]) {
            return Arrays.equals((byte[]) actual, (byte[]) expected);
        }
        if (expected instanceof Object[]) {
            return Arrays.equals((Object[]) actual, (Object[]) expected);
        }
        return actual.equals(expected);
    }

    static <A> String format(A a) {
        if (a instanceof byte[]) {
            return Arrays.toString((byte[]) a);
        }
        if (a instanceof Object[]) {
            return Arrays.toString((Object[]) a);
        }
        return a.toString();
    }
}
