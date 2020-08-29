package ofx.parser;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {
    private static final Pattern tz = Pattern.compile("^.*\\[[-+](\\d)(\\d?)(:.*)?]$");

    public static OffsetDateTime parse(String datetime) {
        Matcher m = tz.matcher(datetime);
        if (m.matches()) {
            if (m.group(2).equals("")) {
                datetime = new StringBuilder(datetime)
                        .replace(m.start(1), m.end(1), "0"+m.group(1))
                        .toString();
            }
        }
        DateTimeFormatter test = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR_OF_ERA, 4)
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .optionalStart()
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .optionalStart()
                .appendLiteral(".")
                .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                .optionalEnd()
                .optionalEnd()
                .optionalStart()
                .appendLiteral("[")
                .appendOffset("+H", "+0")
                .optionalStart()
                .appendLiteral(":")
                .appendZoneText(TextStyle.SHORT)
                .optionalEnd()
                .appendLiteral("]")
                .optionalEnd().toFormatter().withZone(ZoneId.of("GMT"));

        TemporalAccessor temporalAccessor = test.parseBest(datetime, ZonedDateTime::from, LocalDate::from);
        if (temporalAccessor instanceof ZonedDateTime)
            return ((ZonedDateTime) temporalAccessor).toOffsetDateTime();
        else
            return ((LocalDate) temporalAccessor).atStartOfDay(ZoneId.of("GMT")).toOffsetDateTime();
    }
}
