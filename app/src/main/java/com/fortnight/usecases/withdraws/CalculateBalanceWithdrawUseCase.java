package com.fortnight.usecases.withdraws;

import com.fortnight.gateways.WithdrawGetTaxGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
class CalculateBalanceWithdrawUseCase {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private final WithdrawGetTaxGateway getTaxGateway;

    public Mono<BigDecimal> execute(final BigDecimal balance, final BigDecimal withdraw) {
        return Mono.just(balance)
                .map(b -> b.subtract(this.getWithdrawValue(withdraw)));
    }

    private BigDecimal getWithdrawValue(final BigDecimal withdraw) {
        return withdraw.divide(ONE_HUNDRED, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(getTaxGateway.getTax()))
                .add(withdraw);
    }
}
