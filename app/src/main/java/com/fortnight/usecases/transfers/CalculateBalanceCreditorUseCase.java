package com.fortnight.usecases.transfers;

import com.fortnight.domains.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
class CalculateBalanceCreditorUseCase {

    public Mono<Customer> execute(final Customer customer, final BigDecimal amount) {
        return Mono.just(customer)
                .map(Customer::toBuilder)
                .map(builder -> builder.balance(this.calculate(customer.getBalance(), amount)))
                .log("CalculateBalanceCreditorUseCase.calculate", INFO, ON_NEXT, ON_ERROR)
                .map(Customer.CustomerBuilder::build);
    }

    private BigDecimal calculate(final BigDecimal balance, final BigDecimal amount) {
        return balance.add(amount);
    }
}
