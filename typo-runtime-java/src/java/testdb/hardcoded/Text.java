package testdb.hardcoded;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This is `Text` ported from doobie.
 * <p>
 * It is used to encode rows in string format for the COPY command.
 * <p>
 * The generic derivation part of the code is stripped, along with comments.
 */
public class Text<A> {
    final BiConsumer<A, StringBuilder> unsafeEncode;
    final BiConsumer<A, StringBuilder> unsafeArrayEncode;

    public Text(BiConsumer<A, StringBuilder> unsafeEncode, BiConsumer<A, StringBuilder> unsafeArrayEncode) {
        this.unsafeEncode = unsafeEncode;
        this.unsafeArrayEncode = unsafeArrayEncode;
    }

    public <B> Text<B> contramap(Function<B, A> f) {
        return new Text<>((a, sb) -> unsafeEncode.accept(f.apply(a), sb), (a, sb) -> unsafeArrayEncode.accept(f.apply(a), sb));
    }

    public Text<Optional<A>> opt() {
        return new Text<>(
                (oa, sb) -> {
                    if (oa.isPresent()) unsafeEncode.accept(oa.get(), sb);
                    else sb.append(Text.NULL);
                },
                (oa, sb) -> {
                    if (oa.isPresent()) unsafeArrayEncode.accept(oa.get(), sb);
                    else sb.append(Text.NULL);
                }
        );
    }

    public Text<A[]> array() {
        return Text.instance((as, sb) -> {
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

    public static <A> Text<A> instance(BiConsumer<A, StringBuilder> f) {
        return new Text<>(f, f);
    }

    public static final Text<String> stringInstance = new Text<>(StringImpl::unsafeEncode, StringImpl::unsafeArrayEncode);
    public static final Text<Character> charInstance = Text.instance((n, sb) -> sb.append(n.toString()));
    public static final Text<Integer> intInstance = Text.instance((n, sb) -> sb.append(n));
    public static final Text<Short> shortInstance = Text.instance((n, sb) -> sb.append(n));
    public static final Text<Long> longInstance = Text.instance((n, sb) -> sb.append(n));
    public static final Text<Float> floatInstance = Text.instance((n, sb) -> sb.append(n));
    public static final Text<Double> doubleInstance = Text.instance((n, sb) -> sb.append(n));
    public static final Text<BigDecimal> bigDecimalInstance = Text.instance((n, sb) -> sb.append(n));
    public static final Text<Boolean> booleanInstance = Text.instance((n, sb) -> sb.append(n));
    public static final Text<byte[]> byteArrayInstance = Text.instance((bs, sb) -> {
        sb.append("\\\\x");
        if (bs.length > 0) {
            var hex = new BigInteger(1, bs).toString(16);
            var pad = bs.length * 2 - hex.length();
            for (int i = 0; i < pad; i++) sb.append("0");
            sb.append(hex);
        }
    });

    private enum StringImpl {
        ;

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
