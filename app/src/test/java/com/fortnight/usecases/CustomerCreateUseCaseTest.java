package com.fortnight.usecases;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.Customer;
import com.fortnight.gateways.CustomerSaveGateway;
import com.fortnight.templates.CustomerTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

class CustomerCreateUseCaseTest extends UnitTest {

    @InjectMocks
    private CustomerCreateUseCase customerCreateUseCase;

    @Mock
    private CustomerSaveGateway customerSaveGateway;

    @Captor
    private ArgumentCaptor<Customer> argumentCaptor;

    @Test
    public void testCreateCustomerWithSuccessWithBalanceZero() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID_CREATION.name());

        Mockito.when(customerSaveGateway.execute(customer)).thenReturn(Mono.empty());

        final var result = customerCreateUseCase.execute(customer);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(customerSaveGateway, Mockito.times(1)).execute(argumentCaptor.capture()))
                .then(() -> Assertions.assertEquals(argumentCaptor.getValue().getBalance(), BigDecimal.ZERO))
                .expectComplete()
                .verify();
    }

    @Test
    public void testCreateCreateCustomerWhenThrowsAnException() {
        final Customer customer = Fixture.from(Customer.class).gimme(CustomerTemplate.Templates.VALID_CREATION.name());

        Mockito.when(customerSaveGateway.execute(customer)).thenReturn(Mono.error(new Exception("Some exception")));

        final var result = customerCreateUseCase.execute(customer);

        StepVerifier.create(result)
                .then(() -> Mockito.verify(customerSaveGateway, Mockito.times(1)).execute(argumentCaptor.capture()))
                .then(() -> Assertions.assertEquals(argumentCaptor.getValue().getBalance(), BigDecimal.ZERO))
                .expectError(Exception.class)
                .verify();
    }
}