package com.fortnight.usecases;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.Customer;
import com.fortnight.domains.exceptions.CustomerNotFoundException;
import com.fortnight.gateways.CustomerSearchByDocumentGateway;
import com.fortnight.templates.CustomerTemplate;
import com.fortnight.usecases.customers.CustomerSearchUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CustomerSearchUseCaseTest extends UnitTest {

    @InjectMocks
    private CustomerSearchUseCase customerSearchUseCase;

    @Mock
    private CustomerSearchByDocumentGateway searchByDocumentGateway;

    @Test
    public void testSearchCustomerAndFoundFromGateway() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());

        Mockito.when(searchByDocumentGateway.execute(customer.getDocument())).thenReturn(Mono.just(customer));

        final var result = customerSearchUseCase.execute(customer.getDocument());

        StepVerifier.create(result)
                .then(() -> Mockito.verify(searchByDocumentGateway, Mockito.times(1)).execute(customer.getDocument()))
                .assertNext(customerResult -> Assertions.assertEquals(customerResult, customer))
                .verifyComplete();
    }

    @Test
    public void testSearchCustomerAndItNotFound() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID.name());

        Mockito.when(searchByDocumentGateway.execute(customer.getDocument())).thenReturn(Mono.error(new CustomerNotFoundException()));

        final var result = customerSearchUseCase.execute(customer.getDocument());

        StepVerifier.create(result)
                .then(() -> Mockito.verify(searchByDocumentGateway, Mockito.times(1)).execute(customer.getDocument()))
                .verifyError(CustomerNotFoundException.class);
    }
}
