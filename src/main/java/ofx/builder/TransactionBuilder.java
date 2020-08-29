package ofx.builder;

import ofx.message.StatementTransaction;
import ofx.parser.DateParser;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.regex.Pattern;

public class TransactionBuilder {
    private static final Pattern tz = Pattern.compile("^.*\\[[-+](\\d)(\\d?)(:.*)?]$");
    private String type;
    private OffsetDateTime datePosted;
    private BigDecimal amount;
    private String payee;
    private String fitId;
    private String memo;

    public TransactionBuilder() {
    }

    public TransactionBuilder type(String type) {
        this.type = type;
        return this;
    }

    public TransactionBuilder datePosted(String datePosted) {
        this.datePosted = DateParser.parse(datePosted);
        return this;
    }

    public TransactionBuilder amount(String amount) {
        this.amount = new BigDecimal(amount);
        return this;
    }

    public TransactionBuilder payee(String payee) {
        this.payee = payee;
        return this;
    }

    public TransactionBuilder fitId(String fitId) {
        this.fitId = fitId;
        return this;
    }

    public TransactionBuilder memo(String memo) {
        this.memo = memo;
        return this;
    }

    public StatementTransaction build() {
        return new StatementTransaction(this.type, this.datePosted, this.amount, this.payee, this.fitId, this.memo);
    }
}
