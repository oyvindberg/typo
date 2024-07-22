package typo.runtime;

import org.postgresql.geometric.PGbox;
import org.postgresql.geometric.PGcircle;
import org.postgresql.util.PGInterval;
import org.postgresql.util.PGobject;
import typo.data.Inet;

import java.time.*;
import java.util.Arrays;
import java.util.Map;

public interface PgTypes {
    PgType<Double> float8 = PgType.of("float8", PgRead.readDouble, PgWrite.writeDouble, PgText.textDouble);
    PgType<Double[]> float8Array = float8.array(PgRead.readDoubleArray);
    PgType<double[]> float8UnboxedArray = PgType.of("float8[]", PgRead.readUnboxedDoubleArray, PgWrite.writeDoubleArrayUnboxed, PgText.textDoubleArrayUnboxed);
    PgType<Float> float4 = PgType.of("float4", PgRead.readFloat, PgWrite.writeFloat, PgText.textFloat);
    PgType<Float[]> float4Array = float4.array(PgRead.readFloatArray);
    PgType<Inet> inet = PgType.ofPgObject("inet").bimap(Inet::new, Inet::value);
    PgType<Inet[]> inetArray = inet.array(PgRead.massageJdbcArrayTo(PGobject[].class).map(os -> Arrays.stream(os).map(o -> new Inet(o.getValue())).toArray(Inet[]::new)));
    PgType<Instant> timestamptz = PgType.of("timestamptz", PgRead.readInstant, PgWrite.of((ps, i, v) -> ps.setObject(i, v.atOffset(ZoneOffset.UTC))), PgText.instanceToString());
    PgType<Instant[]> timestamptzArray = timestamptz.array(PgRead.readInstantArray);
    PgType<Integer> int4 = PgType.of("int4", PgRead.readInteger, PgWrite.writeInteger, PgText.textInteger);
    PgType<Integer[]> int4Array = int4.array(PgRead.readIntegerArray);
    PgType<LocalDate> date = PgType.of("date", PgRead.readLocalDate, PgWrite.passObjectToJdbc(), PgText.instanceToString());
    PgType<LocalDateTime> timestamp = PgType.of("timestamp", PgRead.readLocalDateTime, PgWrite.passObjectToJdbc(), PgText.instanceToString());
    PgType<LocalDateTime[]> timestampArray = timestamp.array(PgRead.readLocalDateTimeArray);
    PgType<LocalDate[]> dateArray = date.array(PgRead.readLocalDateArray);
    PgType<LocalTime> time = PgType.of("time", PgRead.readLocalTime, PgWrite.passObjectToJdbc(), PgText.instanceToString());
    PgType<LocalTime[]> timeArray = time.array(PgRead.readLocalTimeArray);
    PgType<Long> int8 = PgType.of("int8", PgRead.readLong, PgWrite.writeLong, PgText.textLong);
    PgType<Long[]> int8Array = int8.array(PgRead.readLongArray);
    PgType<Map<String, String>> hstore = PgType.of("hstore", PgRead.readMapStringString, PgWrite.passObjectToJdbc(), PgText.textMapStringString);
    PgType<OffsetTime> timetz = PgType.of("timetz", PgRead.readOffsetTime, PgWrite.passObjectToJdbc(), PgText.instanceToString());
    PgType<OffsetTime[]> timetzArray = timetz.array(PgRead.readOffsetTimeArray);
    PgType<PGInterval> interval = new PgType<>(PgTypename.of("interval"), PgRead.castJdbcObjectTo(PGInterval.class), PgWrite.passObjectToJdbc(), PgText.instanceToString());
    PgType<PGInterval[]> intervalArray = interval.array(PgRead.massageJdbcArrayTo(PGInterval[].class));
    PgType<PGbox> box = new PgType<>(PgTypename.of("box"), PgRead.castJdbcObjectTo(PGbox.class), PgWrite.passObjectToJdbc(), PgText.textPGbox);
    PgType<PGbox[]> boxArray = box.array(PgRead.massageJdbcArrayTo(PGbox[].class));
    PgType<PGcircle> circle = new PgType<>(PgTypename.of("circle"), PgRead.castJdbcObjectTo(PGcircle.class), PgWrite.passObjectToJdbc(), PgText.textPGcircle);
    PgType<PGcircle[]> circleArray = circle.array(PgRead.massageJdbcArrayTo(PGcircle[].class));
    PgType<Short> int2 = PgType.of("int2", PgRead.readShort, PgWrite.writeShort, PgText.textShort);
    PgType<Short> smallint = int2.withTypename(PgTypename.of("smallint"));
    PgType<Short[]> int2Array = int2.array(PgRead.readShortArray);
    //    PgType<Int2Vector> int2vector = int2Array.<Int2Vector>bimap(x -> new Int2Vector(x), Int2Vector::values);
    PgType<Short[]> smallintArray = int2Array.renamed("smallint");
    PgType<String> bpchar = PgType.of("bpchar", PgRead.readString, PgWrite.writeString, PgText.textString);
    PgType<String> text = PgType.of("text", PgRead.readString, PgWrite.writeString, PgText.textString);
    PgType<String[]> bpcharArray = bpchar.array(PgRead.readStringArray);
    PgType<String[]> textArray = text.array(PgRead.readStringArray);
    PgType<byte[]> bytea = PgType.of("bytea", PgRead.readByteArray, PgWrite.writeByteArray, PgText.textByteArray);
//    PgType<Range<Integer>> int4range = PgType.of("int4range", PgRead.readByteArray, PgWrite.writeByteArray, PgText.textByteArray);

    static PgType<String> bpchar(int precision) {
        return PgType.of(PgTypename.of("bpchar", precision), PgRead.readString, PgWrite.writeString, PgText.textString);
    }

    static PgType<String[]> bpcharArray(int n) {
        return bpchar(n).array(PgRead.readStringArray);
    }


}
