package com.fortnight.controllers.web.adapters;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.Customer;
import com.fortnight.templates.CustomerTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class CustomerResponseAdapterTest extends UnitTest {

    @InjectMocks
    private CustomerResponseAdapterImpl adapter;

    @Test
    public void testAdapterResponseFromCustomer() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());

        final var response = adapter.from(customer);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getName(), customer.getName());
        Assertions.assertEquals(response.getBalance(), customer.getBalance());
    }
}