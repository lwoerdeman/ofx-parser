package com.landonwoerdeman.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landonwoerdeman.server.qualifiers.Google;
import com.landonwoerdeman.server.service.TransactionService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import ofx.message.StatementTransaction;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Controller("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(@Google TransactionService transactionService,
                                 ObjectMapper mapper) {
        this.transactionService = transactionService;
    }

    @Get(produces = APPLICATION_JSON)
    Collection<StatementTransaction> index() throws ExecutionException, InterruptedException {
        return transactionService.allTransactions();
    }

    @Get("/{fitId}")
    @Produces(APPLICATION_JSON)
    Optional<StatementTransaction> findTransaction(String fitId) throws ExecutionException, InterruptedException {
        return transactionService.findTransaction(fitId);
    }

    @Post(processes = APPLICATION_JSON)
    @Status(HttpStatus.CREATED)
    String saveTransaction(@Body StatementTransaction transaction) throws ExecutionException, InterruptedException {
        return transactionService.saveTransaction(transaction);
    }

    @Delete("/{fitId}")
    @Status(HttpStatus.NO_CONTENT)
    void deletePet(String fitId) {
        transactionService.deleteTransaction(fitId);
    }
}
