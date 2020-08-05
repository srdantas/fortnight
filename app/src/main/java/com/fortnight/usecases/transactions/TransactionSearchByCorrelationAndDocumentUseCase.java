package com.fortnight.usecases.transactions;

import com.fortnight.domains.Transaction;
import com.fortnight.gateways.TransactionSearchByCorrelationAndDocumentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionSearchByCorrelationAndDocumentUseCase {

    private final TransactionSearchByCorrelationAndDocumentGateway searchByCorrelationAndDocumentGateway;

    public Mono<Optional<Transaction>> execute(final String correlation, final String document) {
        return this.searchByCorrelationAndDocumentGateway.execute(correlation, document);
    }
}
