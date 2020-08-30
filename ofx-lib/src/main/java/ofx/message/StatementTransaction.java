package ofx.message;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class StatementTransaction {
    private final String type;
    private final OffsetDateTime datePosted;
    private final BigDecimal amount;
    private final String payee;
    private final String fitId;
    private final String memo;

    public StatementTransaction(String type, OffsetDateTime datePosted, BigDecimal amount, String payee, String fitId, String memo) {
        this.type = type;
        this.datePosted = datePosted;
        this.amount = amount;
        this.payee = payee;
        this.fitId = fitId;
        this.memo = memo;
    }

    public String getType() {
        return type;
    }

    public OffsetDateTime getDatePosted() {
        return datePosted;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPayee() {
        return payee;
    }

    public String getFitId() {
        return fitId;
    }

    public String getMemo() {
        return memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatementTransaction that = (StatementTransaction) o;
        return type.equals(that.type) &&
                datePosted.equals(that.datePosted) &&
                amount.equals(that.amount) &&
                Objects.equals(payee, that.payee) &&
                fitId.equals(that.fitId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, datePosted, amount, payee, fitId, memo);
    }

    @Override
    public String toString() {
        return "StatementTransaction{" +
                "type='" + type + '\'' +
                ", datePosted=" + datePosted +
                ", amount=" + amount +
                ", payee='" + payee + '\'' +
                ", fitId='" + fitId + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
