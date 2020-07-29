package com.fortnight.usecases;

import com.fortnight.domains.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerSearchUseCase {

    public Mono<Customer> execute(final String document) {
        return Mono.error(new IllegalArgumentException());
    }
}
