package typo.runtime;

import org.postgresql.geometric.PGbox;
import org.postgresql.geometric.PGcircle;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This is `Text` ported from doobie.
 * <p>
 * It is used to encode rows in string format for the COPY command.
 * <p>
 */
public class PgText<A> {
    final BiConsumer<A, StringBuilder> unsafeEncode;
    final BiConsumer<A, StringBuilder> unsafeArrayEncode;

    public PgText(BiConsumer<A, StringBuilder> unsafeEncode, BiConsumer<A, StringBuilder> unsafeArrayEncode) {
        this.unsafeEncode = unsafeEncode;
        this.unsafeArrayEncode = unsafeArrayEncode;
    }

    public PgText(BiConsumer<A, StringBuilder> unsafeEncode) {
        this(unsafeEncode, unsafeEncode);
    }

    public <B> PgText<B> contramap(Function<B, A> f) {
        return new PgText<>((a, sb) -> unsafeEncode.accept(f.apply(a), sb), (a, sb) -> unsafeArrayEncode.accept(f.apply(a), sb));
    }

    public PgText<Optional<A>> opt() {
        return new PgText<>(
                (oa, sb) -> {
                    if (oa.isPresent()) unsafeEncode.accept(oa.get(), sb);
                    else sb.append(PgText.NULL);
                },
                (oa, sb) -> {
                    if (oa.isPresent()) unsafeArrayEncode.accept(oa.get(), sb);
                    else sb.append(PgText.NULL);
                }
        );
    }

    public PgText<A[]> array() {
        return PgText.instance((as, sb) -> {
            var first = true;
            sb.append("{");
            for (var a : as) {
                if (first) first = false;
                else sb.append(',');
                unsafeArrayEncode.accept(a, sb);
            }
            sb.append('}');
        });
    }

    public static char DELIMETER = '\t';
    public static String NULL = "\\N";

    public static <A> PgText<A> instance(BiConsumer<A, StringBuilder> f) {
        return new PgText<>(f, f);
    }

    public static <A> PgText<A> instanceToString() {
        return textString.contramap(Object::toString);
    }

    public static final PgText<String> textString = new PgText<>(StringImpl::unsafeEncode, StringImpl::unsafeArrayEncode);
    public static final PgText<Character> textChar = PgText.instance((n, sb) -> sb.append(n.toString()));
    public static final PgText<Integer> textInteger = PgText.instance((n, sb) -> sb.append(n));
    public static final PgText<Short> textShort = PgText.instance((n, sb) -> sb.append(n));
    public static final PgText<Long> textLong = PgText.instance((n, sb) -> sb.append(n));
    public static final PgText<Float> textFloat = PgText.instance((n, sb) -> sb.append(n));
    public static final PgText<Double> textDouble = PgText.instance((n, sb) -> sb.append(n));
    public static final PgText<double[]> textDoubleArrayUnboxed = PgText.instance((as, sb) -> {
        var first = true;
        sb.append("{");
        for (var a : as) {
            if (first) first = false;
            else sb.append(',');
            sb.append(a);
        }
        sb.append('}');
    });
    public static final PgText<BigDecimal> textBigDecimal = PgText.instance((n, sb) -> sb.append(n));
    public static final PgText<Boolean> textBoolean = PgText.instance((n, sb) -> sb.append(n));
    public static final PgText<byte[]> textByteArray = PgText.instance((bs, sb) -> {
        sb.append("\\\\x");
        if (bs.length > 0) {
            var hex = new BigInteger(1, bs).toString(16);
            var pad = bs.length * 2 - hex.length();
            sb.append("0".repeat(Math.max(0, pad)));
            sb.append(hex);
        }
    });
    public static final PgText<PGbox> textPGbox = PgText.textString.contramap(str -> {
        // let's be defensive since it seems there are so many possibilities for nulls
        if (str == null || str.isNull() || str.point == null) return "null";
        else
            return "((" + str.point[0].x + "," + str.point[0].y + "),(" + str.point[1].x + "," + str.point[1].y + "))";
    });

    public static final PgText<PGcircle> textPGcircle = PgText.textString.contramap(str -> {
        // let's be defensive since it seems there are so many possibilities for nulls
        if (str == null || str.isNull() || str.center == null) return "null";
        else return "<(" + str.center.x + "," + str.center.y + ")," + str.radius + ">";
    });

    public static final PgText<Map<String, String>> textMapStringString = PgText.instance((m, sb) -> {
        var first = true;
        for (var e : m.entrySet()) {
            if (first) first = false;
            else sb.append(',');
            StringImpl.unsafeEncode(e.getKey(), sb);
            sb.append("=>");
            StringImpl.unsafeEncode(e.getValue(), sb);
        }
    });

    private interface StringImpl {
        // Standard char encodings that don't differ in array context
        static void stdChar(char c, StringBuilder sb) {
            switch (c) {
                case '\b':
                    sb.append("\\b");
                case '\f':
                    sb.append("\\f");
                case '\n':
                    sb.append("\\n");
                case '\r':
                    sb.append("\\r");
                case '\t':
                    sb.append("\\t");
                case 0x0b:
                    sb.append("\\v");
                default:
                    sb.append(c);

            }
        }

        static void unsafeEncode(String s, StringBuilder sb) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\\') {
                    sb.append("\\\\"); // backslash must be doubled
                } else {
                    stdChar(c, sb);
                }
            }
        }

        // I am not confident about this encoder. Postgres seems not to be able to cope with low
        // control characters or high whitespace characters so these are simply filtered out in the
        // tests. It should accommodate arrays of non-pathological strings but it would be nice to
        // have a complete specification of what's actually happening.
        static void unsafeArrayEncode(String s, StringBuilder sb) {
            sb.append('"');
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case '\"':
                        sb.append("\\\\\"");
                    case '\\':
                        sb.append("\\\\\\\\"); // srsly
                    default:
                        stdChar(c, sb);
                }
            }
            sb.append('"');
        }
    }
}
