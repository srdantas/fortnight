package com.fortnight.gateways.database.mysql;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.gateways.database.mysql.adapters.TransactionEntityAdapter;
import com.fortnight.gateways.database.mysql.adapters.TransactionEntityAdapterImpl;
import com.fortnight.gateways.database.mysql.entities.TransactionEntity;
import com.fortnight.gateways.database.mysql.repositories.TransactionRepository;
import com.fortnight.templates.TransactionEntityTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import reactor.test.StepVerifier;

import java.util.Optional;

class TransactionSearchByCorrelationAndDocumentOnDatabaseTest extends UnitTest {

    @InjectMocks
    private TransactionSearchByCorrelationAndDocumentOnDatabase transactionSearchOnDatabase;

    @Spy
    private TransactionEntityAdapter adapter = new TransactionEntityAdapterImpl();

    @Mock
    private TransactionRepository repository;

    @Test
    public void testSearchTransactionWithSuccess() {
        final TransactionEntity entity = Fixture.from(TransactionEntity.class)
                .gimme(TransactionEntityTemplate.Templates.VALID_FROM_DATABASE.name());

        Mockito.when(repository.findByCorrelationAndCustomerDocument(entity.getCorrelation(), entity.getCustomer().getDocument()))
                .thenReturn(Optional.of(entity));

        final var result = transactionSearchOnDatabase
                .execute(entity.getCorrelation(), entity.getCustomer().getDocument());

        StepVerifier.create(result)
                .then(() -> Mockito.verify(repository, Mockito.times(1))
                        .findByCorrelationAndCustomerDocument(entity.getCorrelation(), entity.getCustomer().getDocument()))
                .assertNext(optional -> {
                    Assertions.assertTrue(optional.isPresent());
                    Assertions.assertEquals(optional.get(), adapter.to(entity));
                })
                .verifyComplete();
    }

    @Test
    public void testSearchTransactionWhenNotFound() {
        final TransactionEntity entity = Fixture.from(TransactionEntity.class)
                .gimme(TransactionEntityTemplate.Templates.VALID_FROM_DATABASE.name());

        Mockito.when(repository.findByCorrelationAndCustomerDocument(entity.getCorrelation(), entity.getCustomer().getDocument()))
                .thenReturn(Optional.empty());

        final var result = transactionSearchOnDatabase
                .execute(entity.getCorrelation(), entity.getCustomer().getDocument());

        StepVerifier.create(result)
                .then(() -> Mockito.verify(repository, Mockito.times(1))
                        .findByCorrelationAndCustomerDocument(entity.getCorrelation(), entity.getCustomer().getDocument()))
                .assertNext(optional -> Assertions.assertTrue(optional.isEmpty()))
                .verifyComplete();
    }

}