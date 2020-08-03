package com.fortnight.usecases.deposits;

import com.fortnight.UnitTest;
import com.fortnight.gateways.DebitGetBonusGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Random;

import static java.math.RoundingMode.CEILING;

class CalculateBalanceDepositUseCaseTest extends UnitTest {

    @InjectMocks
    private CalculateBalanceDepositUseCase calculateBalanceDepositUseCase;

    @Mock
    private DebitGetBonusGateway getBonusGateway;

    @Test
    public void testCalculateBalanceDepositWithoutBonus() {
        final var balance = BigDecimal.ZERO;
        final var deposit = BigDecimal.valueOf(new Random().nextFloat());
        final var bonus = 0.0;

        final var depositBonusPercentage = deposit.divide(new BigDecimal(100), CEILING);
        final var depositWithBonus = depositBonusPercentage
                .multiply(BigDecimal.valueOf(bonus))
                .add(deposit);

        Mockito.when(getBonusGateway.getBonus()).thenReturn(bonus);

        final var result = calculateBalanceDepositUseCase.execute(balance, deposit);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(getBonusGateway, Mockito.times(1)).getBonus())
                .assertNext(calculatedBalance -> Assertions.assertEquals(calculatedBalance, balance.add(depositWithBonus)))
                .verifyComplete();
    }

    @Test
    public void testCalculateBalanceDepositWithBonus() {
        final var balance = BigDecimal.ZERO;
        final var deposit = BigDecimal.valueOf(new Random().nextFloat());
            final var bonus = new Random().nextDouble();

        final var depositBonusPercentage = deposit.divide(new BigDecimal(100), CEILING);
        final var depositWithBonus = depositBonusPercentage
                .multiply(BigDecimal.valueOf(bonus))
                .add(deposit);

        Mockito.when(getBonusGateway.getBonus()).thenReturn(bonus);

        final var result = calculateBalanceDepositUseCase.execute(balance, deposit);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(getBonusGateway, Mockito.times(1)).getBonus())
                .assertNext(calculatedBalance -> Assertions.assertEquals(calculatedBalance, balance.add(depositWithBonus)))
                .verifyComplete();
    }

}
