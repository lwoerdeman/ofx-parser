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
        ), parser.parse(new BufferedReader(new StringReader("<STMTTRN>trans1</STMTTRN><STMTTRN>trans2</STMTTRN>"))));
    }
}
