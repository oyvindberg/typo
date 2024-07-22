package typo.runtime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface ResultSetParser<Row> {
    Row apply(ResultSet resultSet) throws SQLException;

    static <Row> Row parseCurrentRow(RowParser<Row> rowParser, ResultSet rs) throws SQLException {
        PgType<?>[] columns = rowParser.columns();
        Object[] currentRow = new Object[columns.length];
        for (int i = 0; i < columns.length; i++) {
            currentRow[i] = columns[i].read().read(rs, i + 1);
        }
        return rowParser.decode().apply(currentRow);
    }

    record AllRows<Row>(RowParser<Row> rowParser) implements ResultSetParser<List<Row>> {
        @Override
        public List<Row> apply(ResultSet resultSet) throws SQLException {
            ArrayList<Row> rows = new ArrayList<>();
            while (resultSet.next()) {
                rows.add(parseCurrentRow(rowParser, resultSet));
            }
            return rows;
        }
    }

    record ForeachRow<Row>(RowParser<Row> rowParser, Consumer<Row> consumer) implements ResultSetParser<Void> {
        @Override
        public Void apply(ResultSet resultSet) throws SQLException {
            while (resultSet.next()) {
                consumer.accept(parseCurrentRow(rowParser, resultSet));
            }
            return null;
        }
    }

    record MaybeFirstRow<Row>(RowParser<Row> rowParser) implements ResultSetParser<MaybeRow<Row>> {
        @Override
        public MaybeRow<Row> apply(ResultSet resultSet) throws SQLException {
            if (resultSet.next()) {
                return new MaybeRow.Yes<>(parseCurrentRow(rowParser, resultSet), resultSet.next());
            } else {
                return new MaybeRow.No<>();
            }
        }
    }

    record ExactlyOneRowOrThrow<Row>(RowParser<Row> rowParser) implements ResultSetParser<Row> {
        @Override
        public Row apply(ResultSet resultSet) throws SQLException {
            if (resultSet.next()) {
                Row row = parseCurrentRow(rowParser, resultSet);
                if (resultSet.next()) {
                    throw new SQLException("Expected single row, but found more");
                } else {
                    return row;
                }
            } else {
                throw new SQLException("No rows when expecting a single one");
            }
        }
    }
}

