package typo.runtime;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public record RowParser<Row>(PgType<?>[] columns, Function<Object[], Row> decode) {

    public ResultSetParser<MaybeRow<Row>> maybeFirstRow() {
        return new ResultSetParser.MaybeFirstRow<>(this);
    }

    public ResultSetParser<List<Row>> allRows() {
        return new ResultSetParser.AllRows<>(this);
    }

    public ResultSetParser<Void> foreachRow(Consumer<Row> consumer) {
        return new ResultSetParser.ForeachRow<>(this, consumer);
    }

    public ResultSetParser<Row> exactlyOneRowOrThrow() {
        return new ResultSetParser.ExactlyOneRowOrThrow<>(this);
    }

    public static <T1, Row> RowParser<Row> of(PgType<T1> type1, Function<T1, Row> decode) {
        return new RowParser<>(new PgType[]{type1}, (objects) -> decode.apply((T1) objects[0]));
    }

    public interface Function2<T1, T2, R> {
        R apply(T1 t1, T2 t2);
    }

    @SuppressWarnings("unchecked")
    public static <T1, T2, Row> RowParser<Row> of(PgType<T1> type1, PgType<T2> type2, Function2<T1, T2, Row> decode) {
        return new RowParser<>(new PgType[]{type1, type2}, (objects) -> decode.apply((T1) objects[0], (T2) objects[1]));
    }

    public interface Function3<T1, T2, T3, R> {
        R apply(T1 t1, T2 t2, T3 t3);
    }

    @SuppressWarnings("unchecked")
    public static <T1, T2, T3, Row> RowParser<Row> of(PgType<T1> type1, PgType<T2> type2, PgType<T3> type3, Function3<T1, T2, T3, Row> decode) {
        return new RowParser<>(new PgType[]{type1, type2, type3}, (objects) -> decode.apply((T1) objects[0], (T2) objects[1], (T3) objects[2]));
    }

    public interface Function4<T1, T2, T3, T4, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    @SuppressWarnings("unchecked")
    public static <T1, T2, T3, T4, Row> RowParser<Row> of(PgType<T1> type1, PgType<T2> type2, PgType<T3> type3, PgType<T4> type4, Function4<T1, T2, T3, T4, Row> decode) {
        return new RowParser<>(new PgType[]{type1, type2, type3, type4}, (objects) -> decode.apply((T1) objects[0], (T2) objects[1], (T3) objects[2], (T4) objects[3]));
    }

    public interface Function5<T1, T2, T3, T4, T5, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    @SuppressWarnings("unchecked")
    public static <T1, T2, T3, T4, T5, Row> RowParser<Row> of(PgType<T1> type1, PgType<T2> type2, PgType<T3> type3, PgType<T4> type4, PgType<T5> type5, Function5<T1, T2, T3, T4, T5, Row> decode) {
        return new RowParser<>(new PgType[]{type1, type2, type3, type4, type5}, (objects) -> decode.apply((T1) objects[0], (T2) objects[1], (T3) objects[2], (T4) objects[3], (T5) objects[4]));
    }

    public interface Function6<T1, T2, T3, T4, T5, T6, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
    }

    @SuppressWarnings("unchecked")
    public static <T1, T2, T3, T4, T5, T6, Row> RowParser<Row> of(PgType<T1> type1, PgType<T2> type2, PgType<T3> type3, PgType<T4> type4, PgType<T5> type5, PgType<T6> type6, Function6<T1, T2, T3, T4, T5, T6, Row> decode) {
        return new RowParser<>(new PgType[]{type1, type2, type3, type4, type5, type6}, (objects) -> decode.apply((T1) objects[0], (T2) objects[1], (T3) objects[2], (T4) objects[3], (T5) objects[4], (T6) objects[5]));
    }

    public interface Function7<T1, T2, T3, T4, T5, T6, T7, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
    }
    @SuppressWarnings("unchecked")
    public static <T1, T2, T3, T4, T5, T6, T7, Row> RowParser<Row> of(PgType<T1> type1, PgType<T2> type2, PgType<T3> type3, PgType<T4> type4, PgType<T5> type5, PgType<T6> type6, PgType<T7> type7, Function7<T1, T2, T3, T4, T5, T6, T7, Row> decode) {
        return new RowParser<>(new PgType[]{type1, type2, type3, type4, type5, type6, type7}, (objects) -> decode.apply((T1) objects[0], (T2) objects[1], (T3) objects[2], (T4) objects[3], (T5) objects[4], (T6) objects[5], (T7) objects[6]));
    }
    public interface Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
    }
    @SuppressWarnings("unchecked")
    public static <T1, T2, T3, T4, T5, T6, T7, T8, Row> RowParser<Row> of(PgType<T1> type1, PgType<T2> type2, PgType<T3> type3, PgType<T4> type4, PgType<T5> type5, PgType<T6> type6, PgType<T7> type7, PgType<T8> type8, Function8<T1, T2, T3, T4, T5, T6, T7, T8, Row> decode) {
        return new RowParser<>(new PgType[]{type1, type2, type3, type4, type5, type6, type7, type8}, (objects) -> decode.apply((T1) objects[0], (T2) objects[1], (T3) objects[2], (T4) objects[3], (T5) objects[4], (T6) objects[5], (T7) objects[6], (T8) objects[7]));
    }

}
