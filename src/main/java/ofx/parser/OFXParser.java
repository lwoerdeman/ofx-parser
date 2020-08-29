package ofx.parser;

import ofx.message.StatementTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OFXParser {

    public OFXParser() {
    }

    List<String> parse(BufferedReader reader) throws IOException {
        String begin = "<STMTTRN>";
        String end = "</STMTTRN>";

        List<String> transactions = new ArrayList<>();
        int matchIndex;

        StringBuilderBufferedReader sbReader = new StringBuilderBufferedReader(reader);

        while (true) {
            matchIndex = KMPSearch(begin, sbReader);
            if (matchIndex == -1) {
                break;
            }
            sbReader.setRecording(true);
            matchIndex = KMPSearch(end, sbReader);
            if (matchIndex != -1) {
                transactions.add(begin + sbReader.build());
            } else {
                break;
            }
        }
        sbReader.close();

        return transactions;
    }

    List<StatementTransaction> parse(List<String> transactions) {
        TransactionParser parser = new TransactionParser();
        return transactions.stream().map(parser::parse).collect(Collectors.toList());
    }

    int KMPSearch(String pat, BufferedReader reader) throws IOException {
        int M = pat.length();

        // create lps[] that will hold the longest
        // prefix suffix values for pattern
        int[] lps = new int[M];
        int j = 0; // index for pat[]
        int i = 0; // index in reader

        // Preprocess the pattern (calculate lps[]
        // array)
        computeLPSArray(pat, M, lps);

        int value = reader.read();
        while (value != -1) {
            char c = (char) value;
            boolean readNext = false;
            if (pat.charAt(j) == c) {
                j++;
                i++;
                readNext = true;
            }
            if (j == M) {
                return i - j;
            }
            if (readNext) {
                value = reader.read();
                c = (char) value;
            }
            // mismatch after j matches
            if (value != -1 && pat.charAt(j) != c) {
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0)
                    j = lps[j - 1];
                else {
                    value = reader.read();
                    i = i + 1;
                }
            }
        }
        if (j == M) {
            return 0;
        } else {
            return -1;
        }
    }

    private void computeLPSArray(String pat, int M, int[] lps) {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

    private static class StringBuilderBufferedReader extends BufferedReader {
        private final StringBuilder builder;
        private boolean recording;

        private StringBuilderBufferedReader(BufferedReader wrapped) {
            super(wrapped);
            this.builder = new StringBuilder();
            this.recording = false;
        }

        @Override
        public int read() throws IOException {
            int value = super.read();
            if (recording && value != -1) {
                builder.append((char) value);
            }
            return value;
        }

        public String build() {
            String str = builder.toString();
            builder.setLength(0);
            recording = false;
            return str;
        }

        public void setRecording(boolean recording) {
            this.recording = recording;
        }
    }
}
