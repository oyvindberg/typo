package typo.runtime;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static typo.runtime.interpolators.FR;
import static typo.runtime.interpolators.SQL;

public class Foo {
    record User(String name, Integer age) {
    }

    static RowParser<User> userRowParser =
            RowParser.of(PgTypes.text, PgTypes.int4, User::new);

    public static void main(String[] args) throws SQLException {
        var value = "Alice";

        var predicate1 = FR."column = \{PgTypes.text.encode(value)}";
        var predicate2 = FR."column = \{value}";
        var frag = SQL."""
            |SELECT *
            |FROM users
            |\{Fragment.whereAnd(predicate1, predicate2)}
        """.stripMargin();

        System.out.println(frag.render());

        var c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "user", "password");
        List<User> users = frag.as(userRowParser.allRows()).run(c);
        User user = frag.as(userRowParser.exactlyOneRowOrThrow()).run(c);

        System.out.println(user);
        System.out.println(users);
    }
}
