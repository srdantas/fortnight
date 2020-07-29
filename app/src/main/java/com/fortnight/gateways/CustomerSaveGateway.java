package com.fortnight.gateways;

import com.fortnight.domains.Customer;
import reactor.core.publisher.Mono;

public interface CustomerSaveGateway {

    Mono<Void> execute(Customer customer);
}
