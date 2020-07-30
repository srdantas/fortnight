package com.fortnight.gateways.database.mysql;

import com.fortnight.domains.Customer;
import com.fortnight.domains.exceptions.CustomerAlreadyExistsException;
import com.fortnight.gateways.CustomerSaveGateway;
import com.fortnight.gateways.database.mysql.adapters.CustomerEntityAdapter;
import com.fortnight.gateways.database.mysql.entities.CustomerEntity;
import com.fortnight.gateways.database.mysql.repositories.CustomerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class CustomerSaveOnDatabase implements CustomerSaveGateway {

    private final CustomerEntityAdapter adapter;
    private final CustomerRepository repository;
    private final TransactionTemplate transactionTemplate;

    @Override
    public Mono<Void> execute(@NonNull final Customer customer) {
        return Mono.just(customer)
                .map(adapter::to)
                .log("CustomerSaveOnDatabase.execute.adapter", INFO, ON_NEXT)
                .map(this::save)
                .log("CustomerSaveOnDatabase.execute.repository.save", INFO, ON_NEXT, ON_ERROR)
                .then()
                .onErrorMap(DataIntegrityViolationException.class, (ex) -> new CustomerAlreadyExistsException());
    }

    private CustomerEntity save(final CustomerEntity entity) {
        entity.setCreation(Instant.now());
        return transactionTemplate.execute(status -> repository.save(entity));
    }
}
