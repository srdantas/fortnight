package com.fortnight.usecases.transactions;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.Customer;
import com.fortnight.domains.Transaction;
import com.fortnight.domains.TransactionType;
import com.fortnight.domains.exceptions.TransactionAlreadyExistsException;
import com.fortnight.gateways.TransactionCreateGateway;
import com.fortnight.templates.CustomerTemplate;
import com.fortnight.templates.TransactionTemplate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

class CreateTransactionUseCaseTest extends UnitTest {

    @InjectMocks
    private CreateTransactionUseCase createTransactionUseCase;

    @Mock
    private TransactionCreateGateway transactionCreateGateway;

    @ParameterizedTest
    @EnumSource(value = TransactionType.class, names = {"WITHDRAW", "DEPOSIT", "TRANSFER"})
    public void testCreateTransactionWithSuccess(final TransactionType type) {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());
        final var correlation = UUID.randomUUID().toString();
        final var amount = BigDecimal.valueOf(new Random().nextDouble());

        final Transaction transaction = Fixture.from(Transaction.class)
                .gimme(TransactionTemplate.Templates.VALID.name());

        Mockito.when(transactionCreateGateway.execute(any(Transaction.class))).thenReturn(Mono.just(transaction));

        final var result = createTransactionUseCase.execute(correlation, amount, type, customer);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(transactionCreateGateway, Mockito.times(1)).execute(any()))
                .verifyComplete();
    }


    @ParameterizedTest
    @EnumSource(value = TransactionType.class, names = {"WITHDRAW", "DEPOSIT", "TRANSFER"})
    public void testCreateTransactionWhenTransactionAlreadyExists(final TransactionType type) {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());
        final var correlation = UUID.randomUUID().toString();
        final var amount = BigDecimal.valueOf(new Random().nextDouble());

        Mockito.when(transactionCreateGateway.execute(any(Transaction.class)))
                .thenReturn(Mono.error(new TransactionAlreadyExistsException()));

        final var result = createTransactionUseCase.execute(correlation, amount, type, customer);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(transactionCreateGateway, Mockito.times(1)).execute(any()))
                .verifyError(TransactionAlreadyExistsException.class);
    }
}