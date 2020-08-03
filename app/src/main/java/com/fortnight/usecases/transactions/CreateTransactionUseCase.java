package com.fortnight.usecases.transactions;

import com.fortnight.domains.Customer;
import com.fortnight.domains.TransactionType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class CreateTransactionUseCase {

    public Mono<Void> execute(final String correlation,
                              final BigDecimal amount,
                              final TransactionType type,
                              final Customer customer) {
        return Mono.empty();
    }
}
