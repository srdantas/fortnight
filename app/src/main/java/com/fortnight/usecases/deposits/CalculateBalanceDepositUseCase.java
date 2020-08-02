package com.fortnight.usecases.deposits;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
class CalculateBalanceDepositUseCase {

    public Mono<BigDecimal> execute(final BigDecimal balance, final BigDecimal deposit) {
        return Mono.just(balance)
                .map(b -> b.add(deposit));
    }
}
