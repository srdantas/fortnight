package com.fortnight.usecases;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class CalculateBalanceDepositUseCase {

    public Mono<BigDecimal> execute(final BigDecimal balance, final BigDecimal deposit) {
        return Mono.just(balance)
                .map(b -> b.add(deposit));
    }
}
