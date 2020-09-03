package com.landonwoerdeman.server.converter;

import com.landonwoerdeman.server.model.Transaction;
import ofx.builder.TransactionBuilder;
import ofx.message.StatementTransaction;

import java.time.Instant;
import java.time.ZoneOffset;

public class TransactionConverter {
    public static Transaction convert(StatementTransaction statementTransaction) {
        Transaction transaction = new Transaction();
        transaction.setAmount(statementTransaction.getAmount());
        transaction.setDatePosted(statementTransaction.getDatePosted().toInstant().toEpochMilli());
        transaction.setFitId(statementTransaction.getFitId());
        transaction.setMemo(statementTransaction.getMemo());
        transaction.setPayee(statementTransaction.getPayee());
        transaction.setType(statementTransaction.getType());
        return transaction;
    }

    public static StatementTransaction convert(Transaction transaction) {
        return new TransactionBuilder()
                .amount(transaction.getAmount().toString())
                .datePosted(Instant.ofEpochMilli(transaction.getDatePosted()).atOffset(ZoneOffset.UTC))
                .fitId(transaction.getFitId())
                .memo(transaction.getMemo())
                .payee(transaction.getPayee())
                .type(transaction.getType())
                .build();
    }
}
