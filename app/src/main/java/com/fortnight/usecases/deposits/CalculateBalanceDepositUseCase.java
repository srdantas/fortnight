package com.fortnight.usecases.deposits;

import com.fortnight.gateways.DebitGetBonusGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
class CalculateBalanceDepositUseCase {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private final DebitGetBonusGateway getBonusGateway;

    public Mono<BigDecimal> execute(final BigDecimal balance, final BigDecimal deposit) {
        return Mono.just(balance)
                .map(b -> b.add(this.getDepositValue(deposit)));
    }

    private BigDecimal getDepositValue(final BigDecimal deposit) {
        return deposit.divide(ONE_HUNDRED, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(getBonusGateway.getBonus()))
                .add(deposit);
    }
}
