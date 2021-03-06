package ofx.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import ofx.message.StatementTransaction;
import ofx.parser.DateParser;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonPOJOBuilder(withPrefix = "")
public class TransactionBuilder {
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

    public TransactionBuilder datePosted(OffsetDateTime datePosted) {
        this.datePosted = datePosted;
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
