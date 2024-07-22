package typo.runtime;

import org.postgresql.util.PGobject;

import java.util.Optional;
import java.util.function.Function;

public record PgType<A>(
        PgTypename<A> typename,
        PgRead<A> read,
        PgWrite<A> write,
        PgText<A> text
) {
    public Fragment.Value<A> encode(A value) {
        return new Fragment.Value<>(value, this);
    }

    public PgType<A> withTypename(PgTypename<A> typename) {
        return new PgType<>(typename, read, write, text);
    }

    public PgType<A> renamed(String value) {
        return new PgType<>(typename.renamed(value), read, write, text);
    }

    public PgType<A> withRead(PgRead<A> read) {
        return new PgType<>(typename, read, write, text);
    }

    public PgType<A> withWrite(PgWrite<A> write) {
        return new PgType<>(typename, read, write, text);
    }

    public PgType<A> withText(PgText<A> text) {
        return new PgType<>(typename, read, write, text);
    }

    public PgType<Optional<A>> opt() {
        return new PgType<>(typename.opt(), read.opt(), write.opt(typename), text.opt());
    }

    public PgType<A[]> array(PgRead<A[]> read) {
        return new PgType<>(typename.array(), read, write.array(typename), text.array());
    }

    public <B> PgType<B> bimap(SqlFunction<A, B> f, Function<B, A> g) {
        return new PgType<>(typename.as(), read.map(f), write.contramap(g), text.contramap(g));
    }

    public static <A> PgType<A> of(String tpe, PgRead<A> r, PgWrite<A> w, PgText<A> t) {
        return new PgType<>(PgTypename.of(tpe), r, w, t);
    }

    public static <A> PgType<A> of(PgTypename<A> typename, PgRead<A> r, PgWrite<A> w, PgText<A> t) {
        return new PgType<>(typename, r, w, t);
    }

    public static PgType<String> ofPgObject(String sqlType) {
        return of(
                sqlType,
                PgRead.of((rs, i) -> {
                    PGobject object = (PGobject) rs.getObject(i);
                    if (object == null) return null;
                    return object.getValue();
                }),
                PgWrite.of((ps, i, str) -> {
                    var obj = new PGobject();
                    obj.setType(sqlType);
                    obj.setValue(str);
                    ps.setObject(i, obj, java.sql.Types.OTHER);
                }),
                PgText.textString
        );
    }

}
