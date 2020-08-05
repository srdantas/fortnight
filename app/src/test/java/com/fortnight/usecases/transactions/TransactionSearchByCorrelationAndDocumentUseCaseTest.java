package com.fortnight.usecases.transactions;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.Transaction;
import com.fortnight.gateways.TransactionSearchByCorrelationAndDocumentGateway;
import com.fortnight.templates.TransactionTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

class TransactionSearchByCorrelationAndDocumentUseCaseTest extends UnitTest {

    @InjectMocks
    private TransactionSearchByCorrelationAndDocumentUseCase searchTransactionUseCase;

    @Mock
    private TransactionSearchByCorrelationAndDocumentGateway searchTransactionGateway;

    @Test
    public void testSearchTransactionWithSuccess() {
        final Transaction transaction = Fixture.from(Transaction.class).gimme(TransactionTemplate.Templates.VALID.name());

        Mockito.when(searchTransactionGateway.execute(transaction.getCorrelation(), transaction.getCustomer().getDocument()))
                .thenReturn(Mono.just(Optional.of(transaction)));

        final var result = searchTransactionUseCase.execute(transaction.getCorrelation(), transaction.getCustomer().getDocument());

        StepVerifier.create(result)
                .then(() -> Mockito.verify(searchTransactionGateway, Mockito.times(1))
                        .execute(transaction.getCorrelation(), transaction.getCustomer().getDocument()))
                .assertNext(optional -> {
                    Assertions.assertTrue(optional.isPresent());
                    Assertions.assertEquals(optional.get(), transaction);
                })
                .verifyComplete();
    }

    @Test
    public void testSearchTransactionWhenReturnEmpty() {
        final Transaction transaction = Fixture.from(Transaction.class).gimme(TransactionTemplate.Templates.VALID.name());

        Mockito.when(searchTransactionGateway.execute(transaction.getCorrelation(), transaction.getCustomer().getDocument()))
                .thenReturn(Mono.just(Optional.empty()));

        final var result = searchTransactionUseCase.execute(transaction.getCorrelation(), transaction.getCustomer().getDocument());

        StepVerifier.create(result)
                .then(() -> Mockito.verify(searchTransactionGateway, Mockito.times(1))
                        .execute(transaction.getCorrelation(), transaction.getCustomer().getDocument()))
                .assertNext(optional -> Assertions.assertTrue(optional.isEmpty()))
                .verifyComplete();
    }

}