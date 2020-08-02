package com.fortnight.usecases.deposits;

import com.fortnight.domains.Customer;
import com.fortnight.gateways.CustomerUpdateGateway;
import com.fortnight.usecases.customers.CustomerSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class CustomerDepositUseCase {

    private final CustomerSearchUseCase searchUseCase;
    private final CustomerUpdateGateway customerUpdateGateway;
    private final CalculateBalanceDepositUseCase calculateBalanceDepositUseCase;

    public Mono<Void> execute(final String document,
                              final String correlation,
                              final BigDecimal deposit) {
        return searchUseCase.execute(document)
                .log("CustomerDepositUseCase.searchUseCase", INFO, ON_NEXT, ON_ERROR)
                .flatMap(customer -> this.updateBalance(customer, deposit))
                .log("CustomerDepositUseCase.updateBalance", INFO, ON_NEXT, ON_ERROR)
                .flatMap(customerUpdateGateway::execute)
                .then();
    }

    private Mono<Customer> updateBalance(final Customer customer,
                                         final BigDecimal deposit) {
        return calculateBalanceDepositUseCase.execute(customer.getBalance(), deposit)
                .log("CustomerDepositUseCase.calculateBalanceDepositUseCase", INFO, ON_NEXT, ON_ERROR)
                .map(customer.toBuilder()::balance)
                .map(Customer.CustomerBuilder::build);
    }
}
