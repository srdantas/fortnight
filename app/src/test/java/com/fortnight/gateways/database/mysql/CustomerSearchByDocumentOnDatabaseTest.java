package com.fortnight.gateways.database.mysql;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.exceptions.CustomerNotFoundException;
import com.fortnight.gateways.database.mysql.adapters.CustomerEntityAdapter;
import com.fortnight.gateways.database.mysql.adapters.CustomerEntityAdapterImpl;
import com.fortnight.gateways.database.mysql.entities.CustomerEntity;
import com.fortnight.gateways.database.mysql.repositories.CustomerRepository;
import com.fortnight.templates.CustomerEntityTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import reactor.test.StepVerifier;

import java.util.Optional;

class CustomerSearchByDocumentOnDatabaseTest extends UnitTest {

    @InjectMocks
    private CustomerSearchByDocumentOnDatabase customerSearchByDocumentOnDatabase;

    @Spy
    private final CustomerEntityAdapter adapter = new CustomerEntityAdapterImpl();

    @Mock
    private CustomerRepository repository;

    @Test
    public void testSearchCustomerAndFoundWithSuccess() {
        final CustomerEntity entity = Fixture.from(CustomerEntity.class).gimme(CustomerEntityTemplate.Templates.VALID.name());

        Mockito.when(repository.findById(entity.getDocument())).thenReturn(Optional.of(entity));

        final var result = customerSearchByDocumentOnDatabase.execute(entity.getDocument());

        StepVerifier.create(result)
                .then(() -> Mockito.verify(repository, Mockito.times(1)).findById(entity.getDocument()))
                .assertNext(customer -> Assertions.assertEquals(customer, adapter.from(entity)))
                .verifyComplete();
    }

    @Test
    public void testSearchCustomerWhenNotFound() {
        final CustomerEntity entity = Fixture.from(CustomerEntity.class).gimme(CustomerEntityTemplate.Templates.VALID.name());

        Mockito.when(repository.findById(entity.getDocument())).thenReturn(Optional.empty());

        final var result = customerSearchByDocumentOnDatabase.execute(entity.getDocument());
        StepVerifier.create(result)
                .then(() -> Mockito.verify(repository, Mockito.times(1)).findById(entity.getDocument()))
                .expectError(CustomerNotFoundException.class)
                .verify();
    }

}