package com.fortnight.gateways.database.mysql;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.exceptions.TransactionAlreadyExistsException;
import com.fortnight.gateways.database.mysql.adapters.TransactionEntityAdapter;
import com.fortnight.gateways.database.mysql.adapters.TransactionEntityAdapterImpl;
import com.fortnight.gateways.database.mysql.entities.TransactionEntity;
import com.fortnight.gateways.database.mysql.repositories.TransactionRepository;
import com.fortnight.templates.TransactionEntityTemplate;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import reactor.test.StepVerifier;

import java.sql.SQLException;

class TransactionCreateOnDatabaseTest extends UnitTest {

    @InjectMocks
    private TransactionCreateOnDatabase transactionCreateOnDatabase;

    @Mock
    private TransactionRepository repository;

    @Spy
    private TransactionEntityAdapter adapter = new TransactionEntityAdapterImpl();

    @Test
    public void testCreateTransactionWithSuccess() {
        final TransactionEntity entity = Fixture.from(TransactionEntity.class)
                .gimme(TransactionEntityTemplate.Templates.VALID_NEW.name());

        Mockito.when(repository.save(entity)).thenReturn(entity);

        final var result = transactionCreateOnDatabase.execute(adapter.to(entity));

        StepVerifier.create(result)
                .then(() -> Mockito.verify(repository, Mockito.times(1)).save(entity))
                .assertNext(transaction -> Assertions.assertEquals(transaction, adapter.to(entity)))
                .verifyComplete();


    }

    @Test
    public void testCreateTransactionWhenCorrelationAlreadyExists() {
        final TransactionEntity entity = Fixture.from(TransactionEntity.class)
                .gimme(TransactionEntityTemplate.Templates.VALID_NEW.name());


        Mockito.when(repository.save(entity))
                .thenThrow(new ConstraintViolationException("Violate constraint", new SQLException(), "correlation"));

        final var result = transactionCreateOnDatabase.execute(adapter.to(entity));

        StepVerifier.create(result)
                .then(() -> Mockito.verify(repository, Mockito.times(1)).save(entity))
                .verifyError(TransactionAlreadyExistsException.class);
    }

}
