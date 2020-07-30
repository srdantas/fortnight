package com.fortnight.gateways.database.mysql;

import com.fortnight.domains.Customer;
import com.fortnight.domains.exceptions.CustomerNotFoundException;
import com.fortnight.gateways.CustomerSearchByDocumentGateway;
import com.fortnight.gateways.database.mysql.adapters.CustomerEntityAdapter;
import com.fortnight.gateways.database.mysql.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class CustomerSearchByDocumentOnDatabase implements CustomerSearchByDocumentGateway {

    private final CustomerEntityAdapter adapter;
    private final CustomerRepository repository;

    @Override
    public Mono<Customer> execute(final String document) {
        return Mono.just(document)
                .map(repository::findById)
                .log("CustomerSearchByDocumentOnDatabase.execute.repository.findById", INFO, ON_NEXT)
                .map(Optional::orElseThrow)
                .map(adapter::from)
                .log("CustomerSearchByDocumentOnDatabase.execute.adapter.from", INFO, ON_NEXT, ON_ERROR)
                .onErrorMap(NoSuchElementException.class, (e) -> new CustomerNotFoundException());
    }
}
