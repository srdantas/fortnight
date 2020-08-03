package com.fortnight.gateways.database.mysql;

import com.fortnight.domains.Transaction;
import com.fortnight.domains.exceptions.TransactionAlreadyExistsException;
import com.fortnight.gateways.TransactionCreateGateway;
import com.fortnight.gateways.database.mysql.adapters.TransactionEntityAdapter;
import com.fortnight.gateways.database.mysql.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class TransactionCreateOnDatabase implements TransactionCreateGateway {

    private final TransactionRepository repository;
    private final TransactionEntityAdapter adapter;

    @Override
    public Mono<Transaction> execute(final Transaction transaction) {
        return Mono.just(transaction)
                .map(adapter::from)
                .log("TransactionCreateOnDatabase.adapter.from", INFO, ON_NEXT)
                .map(repository::save)
                .log("TransactionCreateOnDatabase.repository.save", INFO, ON_NEXT, ON_ERROR)
                .onErrorMap(ConstraintViolationException.class, (ex) -> new TransactionAlreadyExistsException())
                .log("TransactionCreateOnDatabase.map.exception", INFO, ON_NEXT)
                .map(adapter::to);
    }
}
