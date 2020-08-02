package com.fortnight.usecases;

import com.fortnight.domains.Customer;
import com.fortnight.gateways.CustomerSaveGateway;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class CustomerCreateUseCase {

    private final CustomerSaveGateway customerSaveGateway;

    public Mono<Void> execute(@NonNull final Customer customer) {
        return Mono.just(customer)
                .map(this::createBalance)
                .log("CustomerCreateUseCase.createBalance", INFO, ON_NEXT)
                .flatMap(customerSaveGateway::execute);
    }

    public Customer createBalance(final Customer customer) {
        customer.setBalance(BigDecimal.ZERO);
        return customer;
    }
}
