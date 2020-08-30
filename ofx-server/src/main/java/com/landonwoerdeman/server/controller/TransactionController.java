package com.landonwoerdeman.server.controller;

import com.landonwoerdeman.server.service.TransactionService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import ofx.message.StatementTransaction;

import java.util.Collection;
import java.util.Optional;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Controller("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Get(produces = APPLICATION_JSON)
    Collection<StatementTransaction> index() {
        return transactionService.allTransactions();
    }

    @Get("/{fitId}")
    @Produces(APPLICATION_JSON)
    Optional<StatementTransaction> findTransaction(String fitId) {
        return transactionService.findTransaction(fitId);
    }

    @Post(processes = APPLICATION_JSON)
    @Status(HttpStatus.CREATED)
    StatementTransaction saveTransaction(@Body StatementTransaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @Delete("/{fitId}")
    @Status(HttpStatus.NO_CONTENT)
    void deletePet(String fitId) {
        transactionService.deleteTransaction(fitId);
    }
}
