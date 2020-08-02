package com.fortnight.gateways.database.mysql;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
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

class CustomerUpdateOnDatabaseTest extends UnitTest {

    @InjectMocks
    private CustomerUpdateOnDatabase customerUpdateOnDatabase;

    @Spy
    private CustomerEntityAdapter adapter = new CustomerEntityAdapterImpl();

    @Mock
    private CustomerRepository repository;

    @Test
    public void testUpdateCustomerWithSuccess() {
        final CustomerEntity entity = Fixture.from(CustomerEntity.class).gimme(CustomerEntityTemplate.Templates.VALID.name());

        Mockito.when(repository.save(entity)).thenReturn(entity);

        final var result = customerUpdateOnDatabase.execute(adapter.from(entity));

        StepVerifier.create(result)
                .then(() -> Mockito.verify(repository, Mockito.times(1)).save(entity))
                .assertNext(customer -> Assertions.assertEquals(customer, adapter.from(entity)))
                .verifyComplete();
    }

    @Test
    public void testUpdateCustomerWhenThrowsAnErrorFromDatabase() {
        final CustomerEntity entity = Fixture.from(CustomerEntity.class).gimme(CustomerEntityTemplate.Templates.VALID.name());

        Mockito.when(repository.save(entity)).thenThrow(new RuntimeException());

        final var result = customerUpdateOnDatabase.execute(adapter.from(entity));

        StepVerifier.create(result)
                .then(() -> Mockito.verify(repository, Mockito.times(1)).save(entity))
                .expectError(Exception.class)
                .verify();
    }
}
