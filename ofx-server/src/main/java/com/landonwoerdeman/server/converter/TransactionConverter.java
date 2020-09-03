package com.landonwoerdeman.server.converter;

import com.landonwoerdeman.server.model.Transaction;
import ofx.builder.TransactionBuilder;
import ofx.message.StatementTransaction;

import java.time.Instant;
import java.time.ZoneOffset;

public class TransactionConverter {
    public static Transaction convert(StatementTransaction statementTransaction) {
        if (statementTransaction == null)
            return null;
        Transaction transaction = new Transaction();
        transaction.setAmount(statementTransaction.getAmount());
        if (statementTransaction.getDatePosted() != null) {
            transaction.setDatePosted(statementTransaction.getDatePosted().toInstant().toEpochMilli());
        }
        transaction.setFitId(statementTransaction.getFitId());
        transaction.setMemo(statementTransaction.getMemo());
        transaction.setPayee(statementTransaction.getPayee());
        transaction.setType(statementTransaction.getType());
        return transaction;
    }

    public static StatementTransaction convert(Transaction transaction) {
        if (transaction == null)
            return null;
        TransactionBuilder builder = new TransactionBuilder()
                .fitId(transaction.getFitId())
                .memo(transaction.getMemo())
                .payee(transaction.getPayee())
                .type(transaction.getType());
        if (transaction.getAmount() != null) {
            builder.amount(transaction.getAmount().toString());
        }
        if (transaction.getDatePosted() != null) {
            builder.datePosted(Instant.ofEpochMilli(transaction.getDatePosted()).atOffset(ZoneOffset.UTC));
        }
        return builder.build();
    }
}
