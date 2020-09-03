package com.landonwoerdeman.server.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transaction {
    private String type;
    private Long datePosted;
    private BigDecimal amount;
    private String payee;
    private String fitId;
    private String memo;

    public Transaction() {
    }

    public Transaction(String type, Long datePosted, BigDecimal amount, String payee, String fitId, String memo) {
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

    public void setType(String type) {
        this.type = type;
    }

    public Long getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Long datePosted) {
        this.datePosted = datePosted;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getFitId() {
        return fitId;
    }

    public void setFitId(String fitId) {
        this.fitId = fitId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(datePosted, that.datePosted) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(payee, that.payee) &&
                Objects.equals(fitId, that.fitId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, datePosted, amount, payee, fitId, memo);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", datePosted=" + datePosted +
                ", amount=" + amount +
                ", payee='" + payee + '\'' +
                ", fitId='" + fitId + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
