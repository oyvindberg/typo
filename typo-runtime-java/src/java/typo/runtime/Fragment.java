package typo.runtime;

import typo.runtime.internal.stripMargin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public sealed interface Fragment {
    Fragment EMPTY = lit("");

    void render(StringBuilder sb);

    default Fragment append(Fragment other) {
        return new Append(this, other);
    }

    default <T> Query<T> as(ResultSetParser<T> parser) {
        return new Query<>(this, parser);
    }

    default String render() {
        StringBuilder sb = new StringBuilder();
        render(sb);
        return sb.toString();
    }

    record Literal(String value) implements Fragment {
        public void render(StringBuilder sb) {
            sb.append(value);
        }

        public Fragment stripMargin() {
            return new Literal(stripMargin.apply(value));
        }
    }

    static Literal lit(String value) {
        return new Literal(value);
    }

    record Append(Fragment a, Fragment b) implements Fragment {
        public void render(StringBuilder sb) {
            a.render(sb);
            b.render(sb);
        }
    }

    record Value<A>(A value, PgType<A> type) implements Fragment {
        public void render(StringBuilder sb) {
            sb.append('?');
        }
    }

    static <A> Value<A> value(A value, PgType<A> type) {
        return new Value<>(value, type);
    }

    record Concat(List<Fragment> frags) implements Fragment {
        public void render(StringBuilder sb) {
            for (Fragment frag : frags) {
                frag.render(sb);
            }
        }
    }

    record Interpolate(List<String> fragments, List<Fragment> values) implements Fragment {
        public void render(StringBuilder sb) {
            for (int i = 0; i < fragments.size(); i++) {
                sb.append(fragments.get(i));
                if (i < values.size()) {
                    values.get(i).render(sb);
                }
            }
        }

        public Fragment stripMargin() {
            return new Interpolate(fragments.stream().map(stripMargin::apply).toList(), values);
        }
    }

    /**
     * Returns `(f1 AND f2 AND ... fn)`.
     */
    static Fragment and(Fragment... fs) {
        return and(Arrays.asList(fs));
    }

    /**
     * Returns `(f1 AND f2 AND ... fn)` for a non-empty collection.
     */
    static Fragment and(List<Fragment> fs) {
        if (fs.isEmpty()) return EMPTY;
        else return join(fs, lit(" AND "));
    }

    /**
     * Returns `(f1 OR f2 OR ... fn)`.
     */
    static Fragment or(Fragment... fs) {
        return or(Arrays.asList(fs));
    }

    /**
     * Returns `(f1 OR f2 OR ... fn)`
     */
    static Fragment or(List<Fragment> fs) {
        if (fs.isEmpty()) return EMPTY;
        else return join(fs, lit(" OR "));
    }

    /**
     * Returns `WHERE f1 AND f2 AND ... fn`
     */
    static Fragment whereAnd(Fragment... fs) {
        return whereAnd(Arrays.asList(fs));
    }

    /**
     * Returns `WHERE f1 AND f2 AND ... fn`
     */
    static Fragment whereAnd(List<Fragment> fs) {
        if (fs.isEmpty()) {
            return EMPTY;
        } else {
            return lit("WHERE ").append(and(fs));
        }
    }

    /**
     * Returns `WHERE f1 OR f2 OR ... fn`.
     */
    static Fragment whereOr(Fragment... fs) {
        return whereOr(Arrays.asList(fs));
    }

    /**
     * Returns `WHERE f1 OR f2 OR ... fn`.
     */
    static Fragment whereOr(List<Fragment> fs) {
        if (fs.isEmpty()) {
            return EMPTY;
        } else {
            return lit("WHERE ").append(or(fs));
        }
    }

    /**
     * Returns `SET f1, f2, ... fn` or the empty fragment if `fs` is empty.
     */
    static Fragment set(Fragment... fs) {
        return set(Arrays.asList(fs));
    }

    /**
     * Returns `SET f1, f2, ... fn` or the empty fragment if `fs` is empty.
     */
    static Fragment set(List<Fragment> fs) {
        if (fs.isEmpty()) {
            return EMPTY;
        } else {
            return lit("SET ").append(comma(fs));
        }
    }

    /**
     * Returns `(f)`.
     */
    static Fragment parentheses(Fragment f) {
        return lit("(").append(f).append(lit(")"));
    }

    /**
     * Returns `f1, f2, ... fn`.
     */
    static Fragment comma(Fragment... fs) {
        return comma(Arrays.asList(fs));
    }

    /**
     * Returns `f1, f2, ... fn`.
     */
    static Fragment comma(List<Fragment> fs) {
        return join(fs, lit(", "));
    }

    /**
     * Returns `ORDER BY f1, f2, ... fn` or the empty fragment if `fs` is empty.
     */
    static Fragment orderBy(Fragment... fs) {
        return orderBy(Arrays.asList(fs));
    }

    /**
     * Returns `ORDER BY f1, f2, ... fn` or the empty fragment if `fs` is empty.
     */
    static Fragment orderBy(List<Fragment> fs) {
        if (fs.isEmpty()) {
            return EMPTY;
        } else {
            return lit("ORDER BY ").append(comma(fs));
        }
    }

    static Concat join(List<Fragment> fs, Fragment sep) {
        var list = new ArrayList<Fragment>();
        var first = true;
        for (Fragment f : fs) {
            if (!first) {
                list.add(sep);
            }
            list.add(f);
            first = false;
        }
        return new Concat(list);
    }

}
