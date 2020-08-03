package com.fortnight.usecases.transactions;

import com.fortnight.domains.Customer;
import com.fortnight.domains.Transaction;
import com.fortnight.domains.TransactionType;
import com.fortnight.gateways.TransactionCreateGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class CreateTransactionUseCase {

    private final TransactionCreateGateway transactionCreateGateway;

    public Mono<Void> execute(final String correlation,
                              final BigDecimal amount,
                              final TransactionType type,
                              final Customer customer) {
        return Mono.just(Transaction.builder())
                .map(builder -> builder.correlation(correlation))
                .map(builder -> builder.amount(amount))
                .log("CreateTransactionUseCase.builder[amount,correlation]", INFO, ON_NEXT)
                .map(builder -> builder.type(type))
                .map(builder -> builder.date(Instant.now()))
                .log("CreateTransactionUseCase.builder[type,creation]", INFO, ON_NEXT)
                .map(builder -> builder.customer(customer))
                .map(Transaction.TransactionBuilder::build)
                .log("CreateTransactionUseCase.build", INFO, ON_NEXT)
                .flatMap(transactionCreateGateway::execute)
                .log("CreateTransactionUseCase.transactionCreateGateway", INFO, ON_NEXT)
                .then();
    }
}
