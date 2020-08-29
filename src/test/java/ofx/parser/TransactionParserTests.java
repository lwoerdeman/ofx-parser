package ofx.parser;

import ofx.message.StatementTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionParserTests {
    @Test
    public void testThatTagsAreParsed() {
        String tagString = "<TRNTYPE>CREDIT<DTPOSTED>20200802160000.000<TRNAMT>808.61<FITID>FITID20200802808.61PLCLC<NAME>DIRECTPAY FULL BALANCE";
        assertEquals(
                List.of(
                        "<TRNTYPE>CREDIT",
                        "<DTPOSTED>20200802160000.000",
                        "<TRNAMT>808.61",
                        "<FITID>FITID20200802808.61PLCLC",
                        "<NAME>DIRECTPAY FULL BALANCE"
                ),
                TransactionParser.tags(tagString)
        );
    }

    @Test
    public void testThatTransactionParsed() {
        StatementTransaction transaction = new StatementTransaction(
                "CREDIT",
                OffsetDateTime.of(2020,8, 2, 16, 0, 0, 0, ZoneOffset.UTC),
                new BigDecimal("808.61"),
                "DIRECTPAY FULL BALANCE",
                "FITID20200802808.61PLCLC",
                null
        );
        String tagString = "<STMTTRN><TRNTYPE>CREDIT<DTPOSTED>20200802160000.000<TRNAMT>808.61<FITID>FITID20200802808.61PLCLC<NAME>DIRECTPAY FULL BALANCE</STMTTRN>";

        TransactionParser parser = new TransactionParser();
        Assertions.assertEquals(transaction, parser.parse(tagString));
    }
}
