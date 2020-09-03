package com.landonwoerdeman.server.service;

import com.landonwoerdeman.server.qualifiers.Local;
import ofx.message.StatementTransaction;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Local
@Singleton
public class LocalTransactionService implements TransactionService {
    private final Map<String, StatementTransaction> transactions = new ConcurrentHashMap<>();

    public LocalTransactionService() {
        transactions.put("2020082524204290237300903128734",  new StatementTransaction(
                "CREDIT",
                OffsetDateTime.of(2020,8, 25, 12, 0, 0, 0, ZoneOffset.UTC),
                new BigDecimal(".53"),
                "SPOTIFY STMT CREDIT TRXN",
                "2020082524204290237300903128734",
                null
        ));
    }

    @Override
    public Collection<StatementTransaction> allTransactions() {
        return transactions.values();
    }

    @Override
    public Optional<StatementTransaction> findTransaction(@NotBlank String name) {
        return Optional.ofNullable(transactions.get(name));
    }

    @Override
    public String saveTransaction(@NotNull @Valid StatementTransaction transaction) {
        transactions.put(transaction.getFitId(), transaction);
        return transaction.getFitId();
    }

    @Override
    public void deleteTransaction(@NotBlank String name) {
        transactions.remove(name);
    }
}
