package typo.data;

import java.util.Arrays;

public sealed interface TextExpr {
    static Array array(TextExpr... exprs) {
        return new Array(exprs);
    }

    static Value value(String value) {
        return new Value(value);
    }


    static Null nullValue() {
        return Null.Null;
    }

    static Object obj(TextExpr... exprs) {
        return new Object(exprs);
    }

    record Array(TextExpr[] exprs) implements TextExpr {
        @Override
        public boolean equals(java.lang.Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Array array = (Array) o;
            return Arrays.equals(exprs, array.exprs);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(exprs);
        }

        @Override
        public String toString() {
            return Arrays.toString(exprs);
        }
    }

    record Value(String value) implements TextExpr {
    }

    record Null() implements TextExpr {
        static TextExpr.Null Null = new TextExpr.Null();
    }

    record Object(TextExpr[] exprs) implements TextExpr {
        @Override
        public boolean equals(java.lang.Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Object object = (Object) o;
            return Arrays.equals(exprs, object.exprs);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(exprs);
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            var first = true;
            b.append('{');
            for (var expr : exprs) {
                if (!first) {
                    b.append(",");
                }
                first = false;
                b.append(expr);
            }
            b.append('}');
            return b.toString();
        }
    }
}
