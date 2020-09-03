package ofx.parser;

import ofx.builder.TransactionBuilder;
import ofx.message.StatementTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionParser {
    private final Pattern tagPattern = Pattern.compile("^<([A-Z]+)>(.*)$");

    public TransactionParser() {
    }

    static List<String> tags(String tagString) {
        tagString = tagString.replace("\n", "").replace("\r", "");
        List<String> tags = new ArrayList<>();
        StringBuilder builder = null;
        for (char c : tagString.toCharArray()) {
            if (c == '<') {
                if (builder != null) {
                    tags.add(builder.toString());
                }
                builder = new StringBuilder();
            }
            if (builder != null)
                builder.append(c);
        }
        if (builder != null) {
            tags.add(builder.toString());
        }
        return tags;
    }

    public StatementTransaction parse(String transaction) {
        Pattern stmt = Pattern.compile("^<STMTTRN>(.*?)</STMTTRN>$", Pattern.DOTALL);
        Matcher m = stmt.matcher(transaction);
        if (!m.matches())
            throw new IllegalArgumentException("This is not a statement transaction");
        transaction = m.group(1);
        List<String> tags = tags(transaction);
        TransactionBuilder builder = new TransactionBuilder();
        for (String tag : tags) {
            m = tagPattern.matcher(tag);
            if (!m.matches()) {
                continue;
            }
            String label = m.group(1);
            String value = m.group(2);

            switch (label) {
                case "NAME":
                    builder.payee(value);
                    break;
                case "TRNTYPE":
                    builder.type(value);
                    break;
                case "TRNAMT":
                    builder.amount(value);
                    break;
                case "DTPOSTED":
                    builder.datePosted(DateParser.parse(value));
                    break;
                case "FITID":
                    builder.fitId(value);
                    break;
                case "MEMO":
                    builder.memo(value);
                    break;
                default:
                    break;
            }
        }

        return builder.build();
    }
}
