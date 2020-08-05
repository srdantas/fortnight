package com.fortnight.usecases.withdraws;

import com.fortnight.domains.Customer;
import com.fortnight.domains.TransactionType;
import com.fortnight.domains.exceptions.CustomerBalanceNotEnoughException;
import com.fortnight.gateways.CustomerUpdateGateway;
import com.fortnight.usecases.customers.CustomerSearchUseCase;
import com.fortnight.usecases.transactions.CreateTransactionUseCase;
import com.fortnight.usecases.transactions.TransactionSearchByCorrelationAndDocumentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class CustomerWithdrawUseCase {

    private final CustomerSearchUseCase searchUseCase;
    private final CustomerUpdateGateway customerUpdateGateway;
    private final CreateTransactionUseCase createTransactionUseCase;
    private final CalculateBalanceWithdrawUseCase calculateBalanceWithdrawUseCase;
    private final TransactionSearchByCorrelationAndDocumentUseCase transactionSearchByCorrelationAndDocumentUseCase;

    public Mono<Void> execute(final String document, final String correlation, final BigDecimal withdraw) {
        return transactionSearchByCorrelationAndDocumentUseCase.execute(correlation, document)
                .log("CustomerWithdrawUseCase.searchTransaction", INFO, ON_NEXT, ON_ERROR)
                .filter(Optional::isEmpty)
                .flatMap(transaction -> this.createWithDraw(document, correlation, withdraw));
    }

    private Mono<Void> createWithDraw(final String document, final String correlation, final BigDecimal withdraw) {
        return searchUseCase.execute(document)
                .log("CustomerWithdrawUseCase.searchUseCase", INFO, ON_NEXT, ON_ERROR)
                .flatMap(customer -> this.updateBalance(customer, withdraw))
                .log("CustomerWithdrawUseCase.updateBalance", INFO, ON_NEXT, ON_ERROR)
                .flatMap(this::validateBalance)
                .log("CustomerWithdrawUseCase.validateBalance", INFO, ON_NEXT, ON_ERROR)
                .flatMap(customerUpdateGateway::execute)
                .log("CustomerWithdrawUseCase.customerUpdateGateway", INFO, ON_NEXT, ON_ERROR)
                .flatMap(customer -> createTransactionUseCase.execute(correlation, withdraw, TransactionType.WITHDRAW, customer));
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
