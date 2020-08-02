package com.fortnight.gateways.database.mysql;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.exceptions.CustomerAlreadyExistsException;
import com.fortnight.gateways.database.mysql.adapters.CustomerEntityAdapter;
import com.fortnight.gateways.database.mysql.adapters.CustomerEntityAdapterImpl;
import com.fortnight.gateways.database.mysql.entities.CustomerEntity;
import com.fortnight.gateways.database.mysql.repositories.CustomerRepository;
import com.fortnight.templates.CustomerEntityTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

class CustomerSaveOnDatabaseTest extends UnitTest {

    @InjectMocks
    private CustomerSaveOnDatabase customerSaveOnDatabase;

    @Spy
    private final CustomerEntityAdapter adapter = new CustomerEntityAdapterImpl();
    @Mock
    private CustomerRepository repository;
    @Mock
    private TransactionTemplate transactionTemplate;

    @Test
    public void testSaveCustomerOnDatabaseWithSuccess() {
        final CustomerEntity entity = Fixture.from(CustomerEntity.class).gimme(CustomerEntityTemplate.Templates.VALID.name());

        Mockito.when(transactionTemplate.execute(any())).thenReturn(1);

        final var result = customerSaveOnDatabase.execute(adapter.from(entity));

        StepVerifier.create(result)
                .then(() -> Mockito.verify(transactionTemplate, Mockito.times(1)).execute(any()))
                .verifyComplete();
    }

    @Test
    public void testSaveCustomerOnDatabaseWhenThrowsDataIntegrityViolation() {
        final CustomerEntity entity = Fixture.from(CustomerEntity.class).gimme(CustomerEntityTemplate.Templates.VALID.name());

        Mockito.when(transactionTemplate.execute(any())).thenThrow(new DataIntegrityViolationException("Same Error"));

        final var result = customerSaveOnDatabase.execute(adapter.from(entity));

        StepVerifier.create(result)
                .then(() -> Mockito.verify(transactionTemplate, Mockito.times(1)).execute(any()))
                .expectError(CustomerAlreadyExistsException.class)
                .verify();
    }

}
