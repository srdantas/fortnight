package com.fortnight.usecases.withdraws;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.Customer;
import com.fortnight.domains.TransactionType;
import com.fortnight.domains.exceptions.CustomerBalanceNotEnoughException;
import com.fortnight.domains.exceptions.CustomerNotFoundException;
import com.fortnight.gateways.CustomerUpdateGateway;
import com.fortnight.templates.CustomerTemplate;
import com.fortnight.usecases.customers.CustomerSearchUseCase;
import com.fortnight.usecases.transactions.CreateTransactionUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class CustomerWithdrawUseCaseTest extends UnitTest {

    @InjectMocks
    private CustomerWithdrawUseCase customerWithdrawUseCase;

    @Mock
    private CustomerSearchUseCase searchUseCase;

    @Mock
    private CustomerUpdateGateway customerUpdateGateway;

    @Mock
    private CreateTransactionUseCase createTransactionUseCase;

    @Mock
    private CalculateBalanceWithdrawUseCase calculateBalanceWithdrawUseCase;

    @Captor
    private ArgumentCaptor<Customer> customerCaptor;

    @Test
    public void testWithdrawWithSuccess() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());
        final String correlation = UUID.randomUUID().toString();
        final BigDecimal amount = BigDecimal.valueOf(new Random().nextFloat());

        final BigDecimal balanceCalculate = BigDecimal.valueOf(new Random().nextFloat());

        Mockito.when(searchUseCase.execute(customer.getDocument())).thenReturn(Mono.just(customer));
        Mockito.when(customerUpdateGateway.execute(any(Customer.class))).thenReturn(Mono.just(customer));
        Mockito.when(createTransactionUseCase.execute(correlation, amount, TransactionType.WITHDRAW, customer))
                .thenReturn(Mono.empty());
        Mockito.when(calculateBalanceWithdrawUseCase.execute(customer.getBalance(), amount))
                .thenReturn(Mono.just(balanceCalculate));

        final var result = customerWithdrawUseCase.execute(customer.getDocument(), correlation, amount);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(searchUseCase, Mockito.times(1))
                        .execute(customer.getDocument()))
                .then(() -> Mockito.verify(customerUpdateGateway, Mockito.times(1))
                        .execute(customerCaptor.capture()))
                .then(() -> Mockito.verify(createTransactionUseCase, Mockito.times(1))
                        .execute(correlation, amount, TransactionType.WITHDRAW, customer))
                .then(() -> Mockito.verify(calculateBalanceWithdrawUseCase, Mockito.times(1))
                        .execute(customer.getBalance(), amount))
                .then(() -> Assertions.assertEquals(balanceCalculate, customerCaptor.getValue().getBalance()))
                .verifyComplete();

    }

    @Test
    public void testWithdrawWhenCustomerNotFound() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());
        final String correlation = UUID.randomUUID().toString();
        final BigDecimal amount = BigDecimal.valueOf(new Random().nextFloat());

        Mockito.when(searchUseCase.execute(customer.getDocument())).thenReturn(Mono.error(new CustomerNotFoundException()));

        final var result = customerWithdrawUseCase.execute(customer.getDocument(), correlation, amount);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(searchUseCase, Mockito.times(1))
                        .execute(customer.getDocument()))
                .then(() -> Mockito.verify(customerUpdateGateway, Mockito.never()).execute(any()))
                .then(() -> Mockito.verify(createTransactionUseCase, Mockito.never()).execute(anyString(), any(), any(), any()))
                .then(() -> Mockito.verify(calculateBalanceWithdrawUseCase, Mockito.never()).execute(any(), any()))
                .verifyError(CustomerNotFoundException.class);
    }

    @Test
    public void testWithdrawWhenNotEnough() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());
        final String correlation = UUID.randomUUID().toString();
        final BigDecimal amount = BigDecimal.valueOf(new Random().nextFloat());

        // multiply by -1 for create a negative number
        final BigDecimal negativeBalanceCalculate = BigDecimal.valueOf(new Random().nextFloat() * -1);

        Mockito.when(searchUseCase.execute(customer.getDocument())).thenReturn(Mono.just(customer));
        Mockito.when(calculateBalanceWithdrawUseCase.execute(customer.getBalance(), amount))
                .thenReturn(Mono.just(negativeBalanceCalculate));

        final var result = customerWithdrawUseCase.execute(customer.getDocument(), correlation, amount);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(searchUseCase, Mockito.times(1))
                        .execute(customer.getDocument()))
                .then(() -> Mockito.verify(calculateBalanceWithdrawUseCase, Mockito.times(1))
                        .execute(customer.getBalance(), amount))
                .then(() -> Mockito.verify(customerUpdateGateway, Mockito.never()).execute(any()))
                .then(() -> Mockito.verify(createTransactionUseCase, Mockito.never()).execute(anyString(), any(), any(), any()))
                .verifyError(CustomerBalanceNotEnoughException.class);
    }

}
