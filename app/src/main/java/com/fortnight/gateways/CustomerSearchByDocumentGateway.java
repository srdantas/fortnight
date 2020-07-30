package com.fortnight.gateways;

import com.fortnight.domains.Customer;
import reactor.core.publisher.Mono;

public interface CustomerSearchByDocumentGateway {

    Mono<Customer> execute(String document);
}
