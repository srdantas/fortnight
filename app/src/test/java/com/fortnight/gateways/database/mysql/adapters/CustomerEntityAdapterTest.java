package com.fortnight.gateways.database.mysql.adapters;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.Customer;
import com.fortnight.gateways.database.mysql.entities.CustomerEntity;
import com.fortnight.templates.CustomerEntityTemplate;
import com.fortnight.templates.CustomerTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class CustomerEntityAdapterTest extends UnitTest {

    @InjectMocks
    private CustomerEntityAdapterImpl adapter;

    @Test
    public void testAdapterEntityFromCustomer() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());

        final var result = adapter.to(customer);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), customer.getName());
        Assertions.assertEquals(result.getDocument(), customer.getDocument());
        Assertions.assertEquals(result.getCreation(), customer.getCreation());
        Assertions.assertEquals(result.getBalance(), customer.getBalance());
    }

    @Test
    public void testAdapterCustomerToEntity() {
        final CustomerEntity entity = Fixture.from(CustomerEntity.class).gimme(CustomerEntityTemplate.Templates.VALID.name());

        final var result = adapter.from(entity);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), entity.getName());
        Assertions.assertEquals(result.getDocument(), entity.getDocument());
        Assertions.assertEquals(result.getCreation(), entity.getCreation());
        Assertions.assertEquals(result.getBalance(), entity.getBalance());
    }
}