package com.fortnight.usecases;

import com.fortnight.domains.Customer;
import com.fortnight.gateways.CustomerSearchByDocumentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerSearchUseCase {

    private final CustomerSearchByDocumentGateway searchByDocumentGateway;

    public Mono<Customer> execute(final String document) {
        return this.searchByDocumentGateway.execute(document);
    }
}
