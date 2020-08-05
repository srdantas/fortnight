package com.fortnight.gateways.database.mysql;

import com.fortnight.domains.Transaction;
import com.fortnight.gateways.TransactionSearchByCorrelationAndDocumentGateway;
import com.fortnight.gateways.database.mysql.adapters.TransactionEntityAdapter;
import com.fortnight.gateways.database.mysql.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Optional;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class TransactionSearchByCorrelationAndDocumentOnDatabase
        implements TransactionSearchByCorrelationAndDocumentGateway {

    private final TransactionEntityAdapter adapter;
    private final TransactionRepository repository;

    @Override
    public Mono<Optional<Transaction>> execute(final String correlation, final String document) {
        return Mono.just(Tuples.of(correlation, document))
                .map(tuple -> repository.findByCorrelationAndCustomerDocument(tuple.getT1(), tuple.getT2()))
                .log("TransactionSearchByCorrelationAndDocumentOnDatabase.repository", INFO, ON_NEXT, ON_ERROR)
                .map(optional -> optional.map(adapter::to));
    }
}
