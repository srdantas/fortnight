package com.fortnight.usecases.customers;

import com.fortnight.domains.Customer;
import com.fortnight.gateways.CustomerSearchByDocumentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class CustomerSearchUseCase {

    private final CustomerSearchByDocumentGateway searchByDocumentGateway;

    public Mono<Customer> execute(final String document) {
        return Mono.just(document)
                .log("CustomerSearchUseCase.execute", INFO, ON_NEXT)
                .flatMap(searchByDocumentGateway::execute);
    }
}
