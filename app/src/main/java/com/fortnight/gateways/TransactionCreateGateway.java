package com.fortnight.gateways;

import com.fortnight.domains.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionCreateGateway {

    Mono<Transaction> execute(Transaction transaction);
}
