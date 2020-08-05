package com.fortnight.gateways;

import com.fortnight.domains.Transaction;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface TransactionSearchByCorrelationAndDocumentGateway {

    Mono<Optional<Transaction>> execute(String correlation, String document);
}
