package com.fortnight.usecases;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class CalculateBalanceWithdrawUseCase {

    public Mono<BigDecimal> execute(final BigDecimal balance, final BigDecimal withdraw) {
        return Mono.just(balance)
                .map(b -> b.subtract(withdraw));
    }
}
