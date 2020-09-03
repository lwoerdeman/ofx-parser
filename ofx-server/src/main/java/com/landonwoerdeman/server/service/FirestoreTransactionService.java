package com.landonwoerdeman.server.service;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.landonwoerdeman.server.converter.TransactionConverter;
import com.landonwoerdeman.server.model.Transaction;
import com.landonwoerdeman.server.qualifiers.Google;
import ofx.message.StatementTransaction;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Google
@Singleton
public class FirestoreTransactionService implements TransactionService {
    private final CollectionReference collectionRef;

    public FirestoreTransactionService() throws IOException {
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId("myfi-01")
                        .setCredentials(GoogleCredentials.getApplicationDefault())
                        .build();
        Firestore db = firestoreOptions.getService();
        this.collectionRef = db.collection("transactions");
    }

    @Override
    public Collection<StatementTransaction> allTransactions() throws ExecutionException, InterruptedException {
        //asynchronously retrieve multiple documents
        ApiFuture<QuerySnapshot> future = this.collectionRef.get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream()
                .map(snapshot -> snapshot.toObject(Transaction.class))
                .map(TransactionConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StatementTransaction> findTransaction(@NotBlank String name) throws ExecutionException, InterruptedException {
        DocumentReference docRef = this.collectionRef.document(name);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            Transaction transaction = document.toObject(Transaction.class);
            return Optional.ofNullable(TransactionConverter.convert(transaction));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String saveTransaction(@NotNull @Valid StatementTransaction transaction) {
        Transaction transaction1 = TransactionConverter.convert(transaction);
        this.collectionRef.document(transaction1.getFitId()).set(transaction1);
        return transaction1.getFitId();
    }

    @Override
    public void deleteTransaction(@NotBlank String name) {
        this.collectionRef.document(name).delete();
    }
}
