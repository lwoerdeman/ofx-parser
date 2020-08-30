package ofx.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DateParserTests {
    @Test
    public void testFullDateTimePassesEST() {
        String datetime = "19961005132200.124[-5:EST]";
        OffsetDateTime expected = OffsetDateTime.of(
                1996,
                10,
                5,
                13,
                22,
                0,
                124000000,
                ZoneOffset.ofHours(-5)
        );
        Assertions.assertEquals(expected.toInstant(), DateParser.parse(datetime).toInstant());
    }

    @Test
    public void testFullDateTimePassesEDT() {
        String datetime = "19961005132200.124[-4:EDT]";
        OffsetDateTime expected = OffsetDateTime.of(
                1996,
                10,
                5,
                13,
                22,
                0,
                124000000,
                ZoneOffset.ofHours(-4)
        );
        Assertions.assertEquals(expected.toInstant(), DateParser.parse(datetime).toInstant());
    }

    @Test
    public void testFullDateTimePassesOffsetOnly() {
        String datetime = "19961005132200.124[-12]";
        OffsetDateTime expected = OffsetDateTime.of(
                1996,
                10,
                5,
                13,
                22,
                0,
                124000000,
                ZoneOffset.ofHours(-12)
        );
        Assertions.assertEquals(expected.toInstant(), DateParser.parse(datetime).toInstant());
    }

    @Test
    public void testFullDateTimePassesNoOffset() {
        String datetime = "19961005132200.124";
        OffsetDateTime expected = OffsetDateTime.of(
                1996,
                10,
                5,
                13,
                22,
                0,
                124000000,
                ZoneOffset.ofHours(0)
        );
        Assertions.assertEquals(expected.toInstant(), DateParser.parse(datetime).toInstant());
    }

    @Test
    public void testFullDateTimePassesNoMillisecondsWithOffset() {
        String datetime = "19961005132200[-5:EST]";
        OffsetDateTime expected = OffsetDateTime.of(
                1996,
                10,
                5,
                13,
                22,
                0,
                0,
                ZoneOffset.ofHours(-5)
        );
        Assertions.assertEquals(expected.toInstant(), DateParser.parse(datetime).toInstant());
    }

    @Test
    public void testFullDateTimePassesNoMilliseconds() {
        String datetime = "19961005132200";
        OffsetDateTime expected = OffsetDateTime.of(
                1996,
                10,
                5,
                13,
                22,
                0,
                0,
                ZoneOffset.ofHours(0)
        );
        Assertions.assertEquals(expected.toInstant(), DateParser.parse(datetime).toInstant());
    }

    @Test
    public void testFullDateTimePassesNoMillisecondsGMT() {
        String datetime = "20200825120000[0:GMT]";
        OffsetDateTime expected = OffsetDateTime.of(
                2020,
                8,
                25,
                12,
                0,
                0,
                0,
                ZoneOffset.ofHours(0)
        );
        Assertions.assertEquals(expected.toInstant(), DateParser.parse(datetime).toInstant());
    }

    @Test
    public void testDateOnlyPasses() {
        String datetime = "19961005";
        OffsetDateTime expected = OffsetDateTime.of(
                1996,
                10,
                5,
                0,
                0,
                0,
                0,
                ZoneOffset.ofHours(0)
        );
        Assertions.assertEquals(expected.toInstant(), DateParser.parse(datetime).toInstant());
    }

    @Test
    public void testDateOnlyPassesWithOffset() {
        String datetime = "19961005[-5:EST]";
        OffsetDateTime expected = OffsetDateTime.of(
                1996,
                10,
                5,
                0,
                0,
                0,
                0,
                ZoneOffset.ofHours(0)
        );
        Assertions.assertEquals(expected.toInstant(), DateParser.parse(datetime).toInstant());
    }
}
