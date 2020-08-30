package ofx.parser;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OFXParserTests {

    @Test
    public void testThatReaderIndexIsCorrect() throws IOException {
        OFXParser parser = new OFXParser();
        assertEquals(6, parser.KMPSearch("<STMTTRN>", new BufferedReader(new StringReader("012345<STMTTRN>"))));
    }

    @Test
    public void testThatReaderIndexIsCorrectTest() throws IOException {
        OFXParser parser = new OFXParser();
        assertEquals(0, parser.KMPSearch("<STMTTRN>", new BufferedReader(new StringReader("<STMTTRN>"))));
    }

    @Test
    public void testThatReaderIndexLSIsCorrect() throws IOException {
        OFXParser parser = new OFXParser();
        assertEquals(List.of(
                "<STMTTRN>trans1</STMTTRN>",
                "<STMTTRN>trans2</STMTTRN>"
        ), parser.parse(new BufferedReader(new StringReader("\n\n\n<STMTTRN>trans1</STMTTRN><STMTTRN>trans2</STMTTRN>"))));
    }

    @Test
    public void testThatTransactionChunksAreCorrect() throws IOException {
        OFXParser parser = new OFXParser();
        assertEquals(List.of(
                "<STMTTRN>\n" +
                        "<TRNTYPE>CREDIT\n" +
                        "<DTPOSTED>20200825120000[0:GMT]\n" +
                        "<TRNAMT>0.53\n" +
                        "<FITID>2020082524204290237300903128734\n" +
                        "<NAME>SPOTIFY STMT CREDIT TRXN\n" +
                        "</STMTTRN>",
                "<STMTTRN>\n" +
                        "<TRNTYPE>DEBIT\n" +
                        "<DTPOSTED>20200825120000[0:GMT]\n" +
                        "<TRNAMT>-10.69\n" +
                        "<FITID>2020082524204290237300903128734\n" +
                        "<NAME>Spotify USA\n" +
                        "</STMTTRN>"
        ), parser.parse(new BufferedReader(new StringReader("<STMTTRN>\n" +
                "<TRNTYPE>CREDIT\n" +
                "<DTPOSTED>20200825120000[0:GMT]\n" +
                "<TRNAMT>0.53\n" +
                "<FITID>2020082524204290237300903128734\n" +
                "<NAME>SPOTIFY STMT CREDIT TRXN\n" +
                "</STMTTRN>\n" +
                "<STMTTRN>\n" +
                "<TRNTYPE>DEBIT\n" +
                "<DTPOSTED>20200825120000[0:GMT]\n" +
                "<TRNAMT>-10.69\n" +
                "<FITID>2020082524204290237300903128734\n" +
                "<NAME>Spotify USA\n" +
                "</STMTTRN>"))));
    }
}
