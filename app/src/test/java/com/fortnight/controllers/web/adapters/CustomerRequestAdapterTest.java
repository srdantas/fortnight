package com.fortnight.controllers.web.adapters;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.controllers.web.requests.CustomerRequest;
import com.fortnight.templates.CustomerRequestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class CustomerRequestAdapterTest extends UnitTest {

    @InjectMocks
    private CustomerRequestAdapterImpl adapter;

    @Test
    public void testAdapterCustomerFromRequest() {
        final CustomerRequest request = Fixture.from(CustomerRequest.class).gimme(CustomerRequestTemplate.Templates.VALID.name());

        final var result = adapter.from(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), request.getName());
        Assertions.assertEquals(result.getDocument(), request.getDocument());
    }
}