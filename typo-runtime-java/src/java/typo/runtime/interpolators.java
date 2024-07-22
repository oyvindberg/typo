package typo.runtime;

import java.lang.StringTemplate.Processor;
import java.util.ArrayList;
import java.util.List;

public enum interpolators {
    ;

    static class InterpolationException extends RuntimeException {
        public InterpolationException(String message) {
            super(message);
        }
    }

    public static Processor<Fragment.Interpolate, InterpolationException> SQL = t -> {
        List<Object> values = t.values();
        var fragmentValues = new ArrayList<Fragment>(values.size());
        for (var v : values) {
            if (v instanceof Fragment) {
                fragmentValues.add((Fragment) v);
            } else {
                throw new InterpolationException(STR."You must encode \{v} as a Fragment. Interpolate in the result of `typo.runtime.PgTypes.<type>.encode(value)` instead");
            }
        }
        return new Fragment.Interpolate(t.fragments(), fragmentValues);

    };
    public static Processor<Fragment.Interpolate, InterpolationException> FR = SQL;
}
