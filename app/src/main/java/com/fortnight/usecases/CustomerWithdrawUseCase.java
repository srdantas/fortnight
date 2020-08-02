package com.fortnight.usecases;

import com.fortnight.domains.Customer;
import com.fortnight.domains.exceptions.CustomerBalanceNotEnoughException;
import com.fortnight.gateways.CustomerUpdateGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class CustomerWithdrawUseCase {

    private final CustomerSearchUseCase searchUseCase;
    private final CalculateBalanceWithdrawUseCase calculateBalanceWithdrawUseCase;
    private final CustomerUpdateGateway customerUpdateGateway;

    public Mono<Void> execute(final String document, final String correlation, final BigDecimal withdraw) {
        return searchUseCase.execute(document)
                .log("CustomerWithdrawUseCase.searchUseCase", INFO, ON_NEXT, ON_ERROR)
                .flatMap(customer -> this.updateBalance(customer, withdraw))
                .log("CustomerWithdrawUseCase.updateBalance", INFO, ON_NEXT, ON_ERROR)
                .flatMap(this::validateBalance)
                .log("CustomerWithdrawUseCase.validateBalance", INFO, ON_NEXT, ON_ERROR)
                .flatMap(customerUpdateGateway::execute)
                .log("CustomerWithdrawUseCase.customerUpdateGateway", INFO, ON_NEXT, ON_ERROR)
                .then();
    }

    private Mono<Customer> updateBalance(final Customer customer, final BigDecimal withdraw) {
        return calculateBalanceWithdrawUseCase.execute(customer.getBalance(), withdraw)
                .log("CustomerWithdrawUseCase.calculateBalanceWithdrawUseCase", INFO, ON_NEXT, ON_ERROR)
                .map(customer.toBuilder()::balance)
                .map(Customer.CustomerBuilder::build);
    }

    private Mono<Customer> validateBalance(final Customer customer) {
        if (customer.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            return Mono.error(new CustomerBalanceNotEnoughException());
        }
        return Mono.just(customer);
    }
}
