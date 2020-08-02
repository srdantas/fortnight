package com.fortnight.controllers.web;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.IntegrationTest;
import com.fortnight.controllers.web.requests.CustomerRequest;
import com.fortnight.controllers.web.requests.DepositRequest;
import com.fortnight.templates.CustomerRequestTemplate;
import com.fortnight.templates.DepositRequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import static java.lang.String.format;

class CustomerDepositControllerTest extends IntegrationTest {

    private static final String CREATE_CUSTOMER_URI = "/customers";
    private static final String CREATE_DEPOSIT_URI = "/customers/%s/deposits";

    @Test
    public void testCreateDepositWithSuccess() {
        final DepositRequest request = Fixture.from(DepositRequest.class)
                .gimme(DepositRequestTemplate.Templates.VALID.name());

        final var customerRequest = this.createCustomer();

        this.webTestClient
                .post()
                .uri(format(CREATE_DEPOSIT_URI, customerRequest.getDocument()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();
    }

    @ParameterizedTest
    @EnumSource(value = DepositRequestTemplate.Templates.class, names = {"INVALID_AMOUNT", "INVALID_CORRELATION"})
    public void testCreateDepositWithInvalidRequest(DepositRequestTemplate.Templates template) {
        final DepositRequest request = Fixture.from(DepositRequest.class).gimme(template.name());

        final var customerRequest = this.createCustomer();

        this.webTestClient
                .post()
                .uri(format(CREATE_DEPOSIT_URI, customerRequest.getDocument()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testDepositOnCustomerThatNotFound() {
        final DepositRequest request = Fixture.from(DepositRequest.class)
                .gimme(DepositRequestTemplate.Templates.VALID.name());

        final CustomerRequest customerRequest = Fixture.from(CustomerRequest.class)
                .gimme(CustomerRequestTemplate.Templates.VALID.name());

        this.webTestClient
                .post()
                .uri(format(CREATE_DEPOSIT_URI, customerRequest.getDocument()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isNotFound();
    }

    private CustomerRequest createCustomer() {
        final CustomerRequest request = Fixture.from(CustomerRequest.class)
                .gimme(CustomerRequestTemplate.Templates.VALID.name());

        this.webTestClient
                .post()
                .uri(CREATE_CUSTOMER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        return request;
    }
}
