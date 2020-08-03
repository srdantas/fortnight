package com.fortnight.usecases.withdraws;

import com.fortnight.UnitTest;
import com.fortnight.gateways.WithdrawGetTaxGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Random;

import static java.math.RoundingMode.CEILING;

class CalculateBalanceWithdrawUseCaseTest extends UnitTest {

    @InjectMocks
    private CalculateBalanceWithdrawUseCase calculateBalanceWithdrawUseCase;

    @Mock
    private WithdrawGetTaxGateway getTaxGateway;

    @Test
    public void testCalculateWithdrawWithoutTax() {
        final var balance = BigDecimal.ZERO;
        final var withdraw = BigDecimal.valueOf(new Random().nextFloat());
        final var tax = 0.0;

        final var withdrawBonusPercentage = withdraw.divide(new BigDecimal(100), CEILING);
        final var depositWithTax = withdrawBonusPercentage
                .multiply(BigDecimal.valueOf(tax))
                .add(withdraw);

        Mockito.when(getTaxGateway.getTax()).thenReturn(tax);

        final var result = calculateBalanceWithdrawUseCase.execute(balance, withdraw);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(getTaxGateway, Mockito.times(1)).getTax())
                .assertNext(calculatedBalance -> Assertions.assertEquals(calculatedBalance, balance.subtract(depositWithTax)))
                .verifyComplete();
    }

    @Test
    public void testCalculateWithdrawWithTax() {
        final var balance = BigDecimal.ZERO;
        final var withdraw = BigDecimal.valueOf(new Random().nextFloat());
        final var tax = new Random().nextDouble();

        final var withdrawBonusPercentage = withdraw.divide(new BigDecimal(100), CEILING);
        final var depositWithTax = withdrawBonusPercentage
                .multiply(BigDecimal.valueOf(tax))
                .add(withdraw);

        Mockito.when(getTaxGateway.getTax()).thenReturn(tax);

        final var result = calculateBalanceWithdrawUseCase.execute(balance, withdraw);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(getTaxGateway, Mockito.times(1)).getTax())
                .assertNext(calculatedBalance -> Assertions.assertEquals(calculatedBalance, balance.subtract(depositWithTax)))
                .verifyComplete();
    }
}
