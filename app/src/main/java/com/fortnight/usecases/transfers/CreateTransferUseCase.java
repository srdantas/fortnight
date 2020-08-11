package com.fortnight.usecases.transfers;

import com.fortnight.domains.Customer;
import com.fortnight.domains.Transaction;
import com.fortnight.domains.TransactionType;
import com.fortnight.domains.Transfer;
import com.fortnight.domains.exceptions.CustomerBalanceNotEnoughException;
import com.fortnight.gateways.CustomerUpdateGateway;
import com.fortnight.gateways.TransferCreateGateway;
import com.fortnight.usecases.customers.CustomerSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.time.Instant;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class CreateTransferUseCase {

    private final CustomerSearchUseCase customerSearchUseCase;
    private final TransferCreateGateway transferCreateGateway;
    private final CalculateBalanceCreditorUseCase calculateBalanceCreditorUseCase;
    private final CalculateBalanceDebtorUseCase calculateBalanceDebtorUseCase;

    public Mono<Void> execute(final String correlation,
                              final BigDecimal amount,
                              final String debtor,
                              final String creditor) {
        return customerSearchUseCase.execute(debtor)
                .log("CreateTransferUseCase.search", INFO, ON_NEXT, ON_ERROR)
                .flatMap(customer -> calculateBalanceDebtorUseCase.execute(customer, amount))
                .log("CreateTransferUseCase.calculateBalanceDebtorUseCase", INFO, ON_NEXT, ON_ERROR)
                .flatMap(this::validateBalance)
                .log("CreateTransferUseCase.validateBalance", INFO, ON_NEXT, ON_ERROR)
                .map(customer -> createTransaction(correlation, amount, customer))
                .log("CreateTransferUseCase.transaction", INFO, ON_NEXT, ON_ERROR)
                .zipWith(getCreditorCustomerAndCalculateBalance(creditor, amount))
                .map(this::createTransfer)
                .log("CreateTransferUseCase.transfer", INFO, ON_NEXT, ON_ERROR)
                .flatMap(transferCreateGateway::execute)
                .log("CreateTransferUseCase.transferCreateGateway", INFO, ON_NEXT, ON_ERROR)
                .then();
    }

    private Mono<Customer> getCreditorCustomerAndCalculateBalance(final String document, final BigDecimal amount) {
        return this.customerSearchUseCase.execute(document)
                .log("CreateTransferUseCase.customerSearchUseCase", INFO, ON_NEXT, ON_ERROR)
                .flatMap(customer -> calculateBalanceCreditorUseCase.execute(customer, amount))
                .log("CreateTransferUseCase.calculateBalanceCreditorUseCase", INFO, ON_NEXT, ON_ERROR);
    }

    private Transaction createTransaction(final String correlation,
                                          final BigDecimal amount,
                                          final Customer debtor) {
        return Transaction.builder()
                .correlation(correlation)
                .amount(amount)
                .type(TransactionType.TRANSFER)
                .date(Instant.now())
                .customer(debtor)
                .build();
    }

    private Transfer createTransfer(final Tuple2<Transaction, Customer> tuple) {
        return Transfer.builder()
                .transaction(tuple.getT1())
                .debtor(tuple.getT1().getCustomer())
                .creditor(tuple.getT2())
                .build();
    }

    private Mono<Customer> validateBalance(final Customer customer) {
        if (customer.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            return Mono.error(new CustomerBalanceNotEnoughException());
        }
        return Mono.just(customer);
    }
}
