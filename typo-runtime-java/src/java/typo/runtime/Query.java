package typo.runtime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public record Query<T>(Fragment query, ResultSetParser<T> parser) {
    T run(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement(query.render())) {
            try (ResultSet rs = stmt.executeQuery()) {
                return parser.apply(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
