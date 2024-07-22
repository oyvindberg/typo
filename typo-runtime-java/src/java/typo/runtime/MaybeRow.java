package typo.runtime;

import java.util.Optional;

public sealed interface MaybeRow<Row> permits MaybeRow.No, MaybeRow.Yes {
    record No<Row>() implements MaybeRow<Row> {
    }

    record Yes<Row>(Row row, boolean wasMore) implements MaybeRow<Row> {
    }

    default Optional<Row> toOptional() {
        if (this instanceof Yes<Row> one) {
            return Optional.of(one.row);
        } else {
            return Optional.empty();
        }
    }
}
