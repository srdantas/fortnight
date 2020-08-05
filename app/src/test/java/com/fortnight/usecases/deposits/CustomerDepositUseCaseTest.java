package com.fortnight.usecases.deposits;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.Customer;
import com.fortnight.domains.Transaction;
import com.fortnight.domains.TransactionType;
import com.fortnight.domains.exceptions.CustomerNotFoundException;
import com.fortnight.gateways.CustomerUpdateGateway;
import com.fortnight.templates.CustomerTemplate;
import com.fortnight.templates.TransactionTemplate;
import com.fortnight.usecases.customers.CustomerSearchUseCase;
import com.fortnight.usecases.transactions.CreateTransactionUseCase;
import com.fortnight.usecases.transactions.TransactionSearchByCorrelationAndDocumentUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class CustomerDepositUseCaseTest extends UnitTest {

    @InjectMocks
    private CustomerDepositUseCase customerDepositUseCase;

    @Mock
    private CustomerSearchUseCase searchUseCase;

    @Mock
    private CustomerUpdateGateway customerUpdateGateway;

    @Mock
    private CreateTransactionUseCase createTransactionUseCase;

    @Mock
    private CalculateBalanceDepositUseCase calculateBalanceDepositUseCase;

    @Mock
    private TransactionSearchByCorrelationAndDocumentUseCase transactionSearchByCorrelationAndDocumentUseCase;

    @Captor
    private ArgumentCaptor<Customer> customerCaptor;

    @Test
    public void testCreateDepositWithSuccess() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());
        final String correlation = UUID.randomUUID().toString();
        final BigDecimal amount = BigDecimal.valueOf(new Random().nextFloat());

        final BigDecimal balanceCalculate = BigDecimal.valueOf(new Random().nextFloat());

        Mockito.when(transactionSearchByCorrelationAndDocumentUseCase.execute(correlation, customer.getDocument()))
                .thenReturn(Mono.just(Optional.empty()));

        Mockito.when(searchUseCase.execute(customer.getDocument())).thenReturn(Mono.just(customer));
        Mockito.when(customerUpdateGateway.execute(any(Customer.class))).thenReturn(Mono.just(customer));
        Mockito.when(createTransactionUseCase.execute(correlation, amount, TransactionType.DEPOSIT, customer))
                .thenReturn(Mono.empty());
        Mockito.when(calculateBalanceDepositUseCase.execute(customer.getBalance(), amount))
                .thenReturn(Mono.just(balanceCalculate));

        final var result = customerDepositUseCase.execute(customer.getDocument(), correlation, amount);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(transactionSearchByCorrelationAndDocumentUseCase,
                        Mockito.times(1))
                        .execute(correlation, customer.getDocument()))
                .then(() -> Mockito.verify(searchUseCase, Mockito.times(1))
                        .execute(customer.getDocument()))
                .then(() -> Mockito.verify(customerUpdateGateway, Mockito.times(1))
                        .execute(customerCaptor.capture()))
                .then(() -> Mockito.verify(createTransactionUseCase, Mockito.times(1))
                        .execute(correlation, amount, TransactionType.DEPOSIT, customer))
                .then(() -> Mockito.verify(calculateBalanceDepositUseCase, Mockito.times(1))
                        .execute(customer.getBalance(), amount))
                .then(() -> Assertions.assertEquals(balanceCalculate, customerCaptor.getValue().getBalance()))
                .verifyComplete();
    }

    @Test
    public void testCreateDepositWhenCustomerNotFound() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());
        final String correlation = UUID.randomUUID().toString();
        final BigDecimal amount = BigDecimal.valueOf(new Random().nextFloat());

        Mockito.when(transactionSearchByCorrelationAndDocumentUseCase.execute(correlation, customer.getDocument()))
                .thenReturn(Mono.just(Optional.empty()));

        Mockito.when(searchUseCase.execute(customer.getDocument())).thenReturn(Mono.error(new CustomerNotFoundException()));

        final var result = customerDepositUseCase.execute(customer.getDocument(), correlation, amount);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(transactionSearchByCorrelationAndDocumentUseCase,
                        Mockito.times(1))
                        .execute(correlation, customer.getDocument()))
                .then(() -> Mockito.verify(searchUseCase, Mockito.times(1))
                        .execute(customer.getDocument()))
                .then(() -> Mockito.verify(customerUpdateGateway, Mockito.never()).execute(any()))
                .then(() -> Mockito.verify(createTransactionUseCase, Mockito.never()).execute(anyString(), any(), any(), any()))
                .then(() -> Mockito.verify(calculateBalanceDepositUseCase, Mockito.never()).execute(any(), any()))
                .verifyError(CustomerNotFoundException.class);
    }

    @Test
    public void testCreateDepositWhenTransactionAlreadyExists() {
        final Transaction transaction = Fixture.from(Transaction.class).gimme(TransactionTemplate.Templates.VALID.name());

        Mockito.when(transactionSearchByCorrelationAndDocumentUseCase
                .execute(transaction.getCorrelation(), transaction.getCustomer().getDocument()))
                .thenReturn(Mono.just(Optional.of(transaction)));

        final var result = customerDepositUseCase
                .execute(transaction.getCustomer().getDocument(), transaction.getCorrelation(), transaction.getAmount());

        StepVerifier.create(result)
                .then(() -> Mockito.verify(transactionSearchByCorrelationAndDocumentUseCase,
                        Mockito.times(1))
                        .execute(transaction.getCorrelation(), transaction.getCustomer().getDocument()))
                .then(() -> Mockito.verify(searchUseCase, Mockito.never()).execute(any()))
                .then(() -> Mockito.verify(customerUpdateGateway, Mockito.never()).execute(any()))
                .then(() -> Mockito.verify(createTransactionUseCase, Mockito.never()).execute(anyString(), any(), any(), any()))
                .then(() -> Mockito.verify(calculateBalanceDepositUseCase, Mockito.never()).execute(any(), any()))
                .expectComplete()
                .verify();
    }

}
