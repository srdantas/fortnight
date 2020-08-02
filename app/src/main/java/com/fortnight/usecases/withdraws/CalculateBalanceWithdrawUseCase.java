package com.fortnight.usecases.withdraws;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
class CalculateBalanceWithdrawUseCase {

    public Mono<BigDecimal> execute(final BigDecimal balance, final BigDecimal withdraw) {
        return Mono.just(balance)
                .map(b -> b.subtract(withdraw));
    }
}
