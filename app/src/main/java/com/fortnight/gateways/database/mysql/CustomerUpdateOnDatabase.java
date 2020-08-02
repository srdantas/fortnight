package com.fortnight.gateways.database.mysql;

import com.fortnight.domains.Customer;
import com.fortnight.gateways.CustomerUpdateGateway;
import com.fortnight.gateways.database.mysql.adapters.CustomerEntityAdapter;
import com.fortnight.gateways.database.mysql.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class CustomerUpdateOnDatabase implements CustomerUpdateGateway {

    private final CustomerEntityAdapter adapter;
    private final CustomerRepository repository;

    @Override
    public Mono<Customer> execute(final Customer customer) {
        return Mono.just(customer)
                .map(adapter::to)
                .log("CustomerUpdateOnDatabase.adapter.to", INFO, ON_NEXT)
                .map(repository::save)
                .log("CustomerUpdateOnDatabase.repository.save", INFO, ON_NEXT, ON_ERROR)
                .map(adapter::from);
    }
}
