package com.landonwoerdeman.server.service;

import ofx.message.StatementTransaction;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface TransactionService {
    Collection<StatementTransaction> allTransactions() throws ExecutionException, InterruptedException;

    Optional<StatementTransaction> findTransaction(@NotBlank String name) throws ExecutionException, InterruptedException;

    String saveTransaction(@NotNull @Valid StatementTransaction transaction) throws ExecutionException, InterruptedException;

    void deleteTransaction(@NotBlank String name);
}
