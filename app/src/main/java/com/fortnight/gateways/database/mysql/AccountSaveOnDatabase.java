package com.fortnight.gateways.database.mysql;

import com.fortnight.domains.Account;
import com.fortnight.domains.exceptions.AccountAlreadyExistsException;
import com.fortnight.gateways.AccountSaveGateway;
import com.fortnight.gateways.database.mysql.adapters.AccountEntityAdapter;
import com.fortnight.gateways.database.mysql.entities.AccountEntity;
import com.fortnight.gateways.database.mysql.repositories.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class AccountSaveOnDatabase implements AccountSaveGateway {

    private final AccountEntityAdapter adapter;
    private final AccountRepository repository;
    private final TransactionTemplate transactionTemplate;

    @Override
    public Mono<Void> execute(@NonNull final Account account) {
        return Mono.just(account)
                .map(adapter::to)
                .log("AccountSaveOnDatabase.execute.adapter", INFO, ON_NEXT)
                .map(this::save)
                .log("AccountSaveOnDatabase.execute.repository.save", INFO, ON_NEXT, ON_ERROR)
                .then()
                .onErrorMap(DuplicateKeyException.class, (ex) -> new AccountAlreadyExistsException());
    }

    private AccountEntity save(final AccountEntity entity) {
        entity.setCreation(Instant.now());
        return transactionTemplate.execute(status -> repository.save(entity));
    }
}
