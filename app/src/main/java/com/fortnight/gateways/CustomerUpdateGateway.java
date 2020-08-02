package com.fortnight.gateways;

import com.fortnight.domains.Customer;
import reactor.core.publisher.Mono;

public interface CustomerUpdateGateway {

    Mono<Customer> execute(Customer customer);
}
